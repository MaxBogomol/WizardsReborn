package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.IHeatBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightTypeStack;
import mod.maxbogomol.wizards_reborn.api.wissen.*;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_machine.AlchemyMachineBlockEntity;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.util.NumericalUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class WissenWandItem extends Item {
    public static List<Tooltip> tooltips = new ArrayList<>();
    public static List<ControlType> controlTypes = new ArrayList<>();

    public WissenWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (!slotChanged) {
            return false;
        }
        return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            CompoundTag nbt = stack.getOrCreateTag();

            if (!nbt.contains("mode")) {
                nbt.putInt("mode", 0);
            }

            nbt.putBoolean("block", false);
            nbt.putInt("mode", (nbt.getInt("mode") + 1 ) % 5);

            if (nbt.getInt("mode") == 3 || nbt.getInt("mode") == 0) {
                nbt.putBoolean("block", true);
            }

            stack.setTag(nbt);
            player.displayClientMessage(getModeTranslate(stack), true);

            player.awardStat(Stats.ITEM_USED.get(this));
            level.playSound(null, player.blockPosition(), WizardsRebornSounds.CRYSTAL_RESONATE.get(), SoundSource.PLAYERS, 1.0f, 1.2f);

            return InteractionResultHolder.success(stack);
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level level = context.getLevel();
        InteractionResult result = InteractionResult.PASS;

        if(!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(context.getClickedPos());
            CompoundTag nbt = stack.getOrCreateTag();

            if (!nbt.contains("block")) {
                nbt.putBoolean("block", false);
            }
            if (!nbt.contains("mode")) {
                nbt.putInt("mode", 0);
            }

            if (nbt.getInt("mode") != 4) {
                boolean control = controlled(stack, context, blockEntity);
                if (control) result = InteractionResult.SUCCESS;

                if (blockEntity instanceof IWissenWandFunctionalBlockEntity functionalBlock) {
                    if (nbt.getInt("mode") == 0) {
                        functionalBlock.wissenWandFunction();
                    }
                    stack.setTag(nbt);
                    result = InteractionResult.SUCCESS;
                }
            }
        }

        if (result == InteractionResult.SUCCESS) {
            context.getPlayer().awardStat(Stats.ITEM_USED.get(this));
            level.playSound(null, context.getClickedPos(), WizardsRebornSounds.CRYSTAL_RESONATE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }

        return result;
    }

    public boolean controlled(ItemStack stack, UseOnContext context, BlockEntity blockEntity) {
        int mode = getMode(stack);

        for (ControlType controlType : controlTypes) {
            if (controlType.controlled(stack, context, blockEntity)) return true;
        }

        if (blockEntity instanceof IWissenWandControlledBlockEntity controlledBlockEntity) {
            if (mode == 1) {
                return controlledBlockEntity.wissenWandReceiveConnect(stack, context, blockEntity);
            }
            if (mode == 2) {
                return controlledBlockEntity.wissenWandSendConnect(stack, context, blockEntity);
            }
            if (mode == 3) {
                return controlledBlockEntity.wissenWandReload(stack, context, blockEntity);
            }
        }

        return false;
    }

    public static boolean isClickable(ItemStack stack) {
        if (stack.getItem() instanceof WissenWandItem) {
            return WissenWandItem.getMode(stack) == 4;
        }

        return true;
    }

    public static int getMode(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("mode")) {
            return nbt.getInt("mode");
        }

        return 0;
    }

    public static void setMode(ItemStack stack, int mode) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("mode", mode);
    }

    public static boolean getBlock(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("block")) {
            return nbt.getBoolean("block");
        }

        return false;
    }

    public static void setBlock(ItemStack stack, boolean block) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putBoolean("block", block);
    }

    public static BlockPos getBlockPos(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("blockX") && nbt.contains("blockY") && nbt.contains("blockZ")) {
            return new BlockPos(nbt.getInt("blockX"), nbt.getInt("blockY"), nbt.getInt("blockZ"));
        }

        return BlockPos.ZERO;
    }

    public static void setBlockPos(ItemStack stack, BlockPos blockPos) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("blockX", blockPos.getX());
        nbt.putInt("blockY", blockPos.getY());
        nbt.putInt("blockZ", blockPos.getZ());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        ItemSkin skin = ItemSkin.getSkinFromItem(stack);
        if (skin != null) list.add(skin.getSkinComponent());

        list.add(Component.translatable(getModeString(stack)).withStyle(getModeColor(stack)));
    }

    @Override
    public Component getHighlightTip(ItemStack stack, Component displayName) {
        Component mode = getModeTranslate(stack);
        return displayName.copy().append(mode);
    }

    public static String getModeString(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null) {
            if (nbt.contains("mode")) {
                return getModeString(nbt.getInt("mode"));
            }
        }

        return "lore.wizards_reborn.wissen_wand_mode.functional";
    }

    public static String getModeString(int mode) {
        return switch (mode) {
            case 0 -> "lore.wizards_reborn.wissen_wand_mode.functional";
            case 1 -> "lore.wizards_reborn.wissen_wand_mode.receive_connect";
            case 2 -> "lore.wizards_reborn.wissen_wand_mode.send_connect";
            case 3 -> "lore.wizards_reborn.wissen_wand_mode.reload";
            case 4 -> "lore.wizards_reborn.wissen_wand_mode.off";
            default -> "lore.wizards_reborn.wissen_wand_mode.functional";
        };

    }

    public static ChatFormatting getModeColor(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null) {
            if (nbt.contains("mode")) {
                return getModeColor(nbt.getInt("mode"));
            }
        }

        return ChatFormatting.DARK_AQUA;
    }

    public static ChatFormatting getModeColor(int mode) {
        return switch (mode) {
            case 0 -> ChatFormatting.DARK_AQUA;
            case 1 -> ChatFormatting.GREEN;
            case 2 -> ChatFormatting.AQUA;
            case 3 -> ChatFormatting.YELLOW;
            case 4 -> ChatFormatting.GRAY;
            default -> ChatFormatting.DARK_AQUA;
        };

    }

    public static Component getModeTranslate(ItemStack stack) {
        return Component.literal(" (")
                .append(Component.translatable(getModeString(stack)).withStyle(getModeColor(stack)))
                .append(")");
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawWissenGui(GuiGraphics gui) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        ItemStack main = minecraft.player.getMainHandItem();
        ItemStack offhand = minecraft.player.getOffhandItem();
        ItemStack wand = main;
        CameraType cameraType = Minecraft.getInstance().options.getCameraType();

        boolean render = false;

        if (!main.isEmpty() && main.getItem() instanceof WissenWandItem) {
            render = true;
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof WissenWandItem) {
                render = true;
                wand = offhand;
            }
        }

        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        gui.pose().pushPose();
        gui.pose().translate(0, 0, -200);

        if (render && cameraType.isFirstPerson()) {
            if (!player.isSpectator()) {
                ChatFormatting chatFormatting = getModeColor(wand);
                int color = chatFormatting.getColor().intValue();
                float r = ColorUtil.getRed(color) / 255f;
                float g = ColorUtil.getGreen(color) / 255f;
                float b = ColorUtil.getBlue(color) / 255f;

                RenderSystem.setShaderColor(r, g, b, 1F);

                int X = (gui.guiWidth() - 11) / 2;
                int Y = (gui.guiHeight() - 11) / 2;
                gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_wand_mode.png"), X, Y, 0, 0, 11, 11, 16, 16);
                RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

                HitResult pos = minecraft.hitResult;
                if (pos != null) {
                    BlockPos bpos = pos.getType() == HitResult.Type.BLOCK ? ((BlockHitResult) pos).getBlockPos() : null;

                    int i = 0;
                    for (Tooltip tooltip : tooltips) {
                        tooltip.setYOffset(i);
                        tooltip.draw(gui, bpos);
                        i = tooltip.getYOffset();
                    }
                }
            }
        }

        gui.pose().popPose();

        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
    }

    public static void setupTooltips() {
        addTooltip(new ItemResultTooltip());
        addTooltip(new WissenTooltip());
        addTooltip(new CooldownTooltip());
        addTooltip(new LightTooltip());
        addTooltip(new FluidTooltip());
        addTooltip(new ExperienceTooltip());
        addTooltip(new HeatTooltip());
        addTooltip(new SteamTooltip());
        addTooltip(new AlchemyMachineTooltip());
    }

    public static void addTooltip(Tooltip tooltip) {
        tooltips.add(tooltip);
    }

    public static class Tooltip {
        public int yOffset = 0;

        public void setYOffset(int offset) {
            yOffset = offset;
        }

        public void addYOffset(int offset) {
            yOffset = yOffset + offset;
        }

        public int getYOffset() {
            return yOffset;
        }

        @OnlyIn(Dist.CLIENT)
        public void drawCenteredText(GuiGraphics gui, String string, int x, int y) {
            gui.drawCenteredString(Minecraft.getInstance().font, string, x, y, 0xffffff);
        }

        @OnlyIn(Dist.CLIENT)
        public void drawCenteredText(GuiGraphics gui, Component string, int x, int y) {
            gui.drawCenteredString(Minecraft.getInstance().font, string, x, y, 0xffffff);
        }

        @OnlyIn(Dist.CLIENT)
        public void drawBar(GuiGraphics gui, ResourceLocation barTexture, int x, int y, float value, float maxValue) {
            gui.blit(barTexture, x, y, 0, 0, 48, 10, 64, 64);
            int width = 32;
            width /= (double) maxValue / (double) value;
            gui.blit(barTexture, x + 8, y + 1, 0, 10, width, 8, 64, 64);
        }

        @OnlyIn(Dist.CLIENT)
        public void drawBar(GuiGraphics gui, ResourceLocation barTexture, int x, int y, float value) {
            gui.blit(barTexture, x, y, 0, 0, 48, 10, 64, 64);
            gui.blit(barTexture, x + 8, y + 1, 0, 10, (int) value, 8, 64, 64);
        }

        @OnlyIn(Dist.CLIENT)
        public ResourceLocation getBarTexture() {
            return new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png");
        }

        @OnlyIn(Dist.CLIENT)
        public void draw(GuiGraphics gui, BlockPos pos) {

        }
    }

    public static class ItemResultTooltip extends Tooltip {
        @Override
        @OnlyIn(Dist.CLIENT)
        public void draw(GuiGraphics gui, BlockPos pos) {
            Minecraft minecraft = Minecraft.getInstance();
            BlockEntity blockEntity = pos != null ? minecraft.level.getBlockEntity(pos) : null;

            if (blockEntity != null) {
                if (blockEntity instanceof IItemResultBlockEntity resultBlockEntity) {
                    List<ItemStack> list = resultBlockEntity.getItemsResult();
                    int i = 0;
                    for (ItemStack item : list) {
                        int x = minecraft.getWindow().getGuiScaledWidth() / 2 - 8 + (i * 16) - ((list.size() - 1) * 8);
                        int y = minecraft.getWindow().getGuiScaledHeight() / 2 - 26;
                        gui.renderItem(item, x, y);
                        gui.renderItemDecorations(Minecraft.getInstance().font, item, x, y);
                        i++;
                    }
                }
            }
        }
    }

    public static class WissenTooltip extends Tooltip {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ResourceLocation getBarTexture() {
            return new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png");
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public void draw(GuiGraphics gui, BlockPos pos) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            BlockEntity blockEntity = pos != null ? minecraft.level.getBlockEntity(pos) : null;

            if (blockEntity != null) {
                if (blockEntity instanceof IWissenBlockEntity wissenBlockEntity) {
                    if (player.isShiftKeyDown() && WizardsRebornClientConfig.NUMERICAL_WISSEN.get()) {
                        int x = minecraft.getWindow().getGuiScaledWidth() / 2;
                        int y = minecraft.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();
                        Component name = NumericalUtil.getWissenName(wissenBlockEntity.getWissen(), wissenBlockEntity.getMaxWissen());
                        drawCenteredText(gui, name.getString(), x, y);
                        addYOffset(11);
                    }

                    int x = minecraft.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                    int y = minecraft.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

                    drawBar(gui, getBarTexture(), x, y, wissenBlockEntity.getWissen(), wissenBlockEntity.getMaxWissen());
                    addYOffset(11);
                }
            }
        }
    }

    public static class CooldownTooltip extends Tooltip {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ResourceLocation getBarTexture() {
            return new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/cooldown_frame.png");
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public void draw(GuiGraphics gui, BlockPos pos) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            BlockEntity blockEntity = pos != null ? minecraft.level.getBlockEntity(pos) : null;

            if (blockEntity != null) {
                if (blockEntity instanceof ICooldownBlockEntity cooldownBlockEntity) {
                    if (player.isShiftKeyDown() && WizardsRebornClientConfig.NUMERICAL_COOLDOWN.get()) {
                        int x = minecraft.getWindow().getGuiScaledWidth() / 2;
                        int y = minecraft.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();
                        Component name = NumericalUtil.getCooldownName(cooldownBlockEntity.getCooldown());
                        drawCenteredText(gui, name.getString(), x, y);
                        addYOffset(11);
                    }

                    int x = minecraft.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                    int y = minecraft.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

                    drawBar(gui, getBarTexture(), x, y, 32 / cooldownBlockEntity.getCooldown());
                    addYOffset(11);
                }
            }
        }
    }

    public static class LightTooltip extends Tooltip {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ResourceLocation getBarTexture() {
            return new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/light_frame.png");
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public void draw(GuiGraphics gui, BlockPos pos) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            BlockEntity blockEntity = pos != null ? minecraft.level.getBlockEntity(pos) : null;

            if (blockEntity != null) {
                if (blockEntity instanceof ILightBlockEntity lightBlockEntity) {
                    if (player.isShiftKeyDown() && WizardsRebornClientConfig.SHOW_LIGHT_NAME.get()) {
                        int x = minecraft.getWindow().getGuiScaledWidth() / 2;
                        int y = minecraft.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();
                        Component name = NumericalUtil.getLightName();
                        drawCenteredText(gui, name.getString(), x, y);
                        addYOffset(11);
                        y = y + 11;

                        for (LightTypeStack lightType : lightBlockEntity.getLightTypes()) {
                            drawCenteredText(gui, lightType.getType().getColoredName(), x, y);
                            addYOffset(11);
                            y = y + 11;
                        }
                    }

                    int x = minecraft.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                    int y = minecraft.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

                    int width = 32;
                    if (lightBlockEntity.getLight() <= 0) width = 0;
                    drawBar(gui, getBarTexture(), x, y, width);
                    addYOffset(11);
                }
            }
        }
    }

    public static class FluidTooltip extends Tooltip {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ResourceLocation getBarTexture() {
            return new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/fluid_frame.png");
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public void draw(GuiGraphics gui, BlockPos pos) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            BlockEntity blockEntity = pos != null ? minecraft.level.getBlockEntity(pos) : null;

            if (blockEntity != null) {
                if (blockEntity instanceof IFluidBlockEntity fluidBlockEntity) {
                    int x = minecraft.getWindow().getGuiScaledWidth() / 2;
                    int y = minecraft.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();

                    Component name = NumericalUtil.getFluidName(fluidBlockEntity.getFluidStack(), fluidBlockEntity.getFluidMaxAmount());
                    if (!WizardsRebornClientConfig.NUMERICAL_FLUID.get()) {
                        name = NumericalUtil.getFluidName(fluidBlockEntity.getFluidStack());
                    }
                    drawCenteredText(gui, name.getString(), x, y);
                    addYOffset(11);

                    x = minecraft.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                    y = minecraft.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

                    drawBar(gui, getBarTexture(), x, y, fluidBlockEntity.getFluidAmount(), fluidBlockEntity.getFluidMaxAmount());
                    addYOffset(11);
                }
            }
        }
    }

    public static class ExperienceTooltip extends Tooltip {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ResourceLocation getBarTexture() {
            return new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/experience_frame.png");
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public void draw(GuiGraphics gui, BlockPos pos) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            BlockEntity blockEntity = pos != null ? minecraft.level.getBlockEntity(pos) : null;

            if (blockEntity != null) {
                if (blockEntity instanceof IExperienceBlockEntity experienceBlockEntity) {
                    if (player.isShiftKeyDown() && WizardsRebornClientConfig.NUMERICAL_EXPERIENCE.get()) {
                        int x = minecraft.getWindow().getGuiScaledWidth() / 2;
                        int y = minecraft.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();
                        Component name = NumericalUtil.getExperienceName(experienceBlockEntity.getExperience(), experienceBlockEntity.getMaxExperience());
                        drawCenteredText(gui, name.getString(), x, y);
                        addYOffset(11);
                    }

                    int x = minecraft.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                    int y = minecraft.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

                    drawBar(gui, getBarTexture(), x, y, experienceBlockEntity.getExperience(), experienceBlockEntity.getMaxExperience());
                    addYOffset(11);
                }
            }
        }
    }

    public static class HeatTooltip extends Tooltip {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ResourceLocation getBarTexture() {
            return new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/heat_frame.png");
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public void draw(GuiGraphics gui, BlockPos pos) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            BlockEntity blockEntity = pos != null ? minecraft.level.getBlockEntity(pos) : null;

            if (blockEntity != null) {
                if (blockEntity instanceof IHeatBlockEntity heatBlockEntity) {
                    if (player.isShiftKeyDown() && WizardsRebornClientConfig.NUMERICAL_HEAT.get()) {
                        int x = minecraft.getWindow().getGuiScaledWidth() / 2;
                        int y = minecraft.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();
                        Component name = NumericalUtil.getHeatName(heatBlockEntity.getHeat(), heatBlockEntity.getMaxHeat());
                        drawCenteredText(gui, name.getString(), x, y);
                        addYOffset(11);
                    }

                    int x = minecraft.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                    int y = minecraft.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

                    drawBar(gui, getBarTexture(), x, y, heatBlockEntity.getHeat(), heatBlockEntity.getMaxHeat());
                    addYOffset(11);
                }
            }
        }
    }

    public static class SteamTooltip extends Tooltip {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ResourceLocation getBarTexture() {
            return new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/steam_frame.png");
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public void draw(GuiGraphics gui, BlockPos pos) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            BlockEntity blockEntity = pos != null ? minecraft.level.getBlockEntity(pos) : null;

            if (blockEntity != null) {
                if (blockEntity instanceof ISteamBlockEntity steamBlockEntity) {
                    if (player.isShiftKeyDown() && WizardsRebornClientConfig.NUMERICAL_STEAM.get()) {
                        int x = minecraft.getWindow().getGuiScaledWidth() / 2;
                        int y = minecraft.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();
                        Component name = NumericalUtil.getSteamName(steamBlockEntity.getSteam(), steamBlockEntity.getMaxSteam());
                        drawCenteredText(gui, name.getString(), x, y);
                        addYOffset(11);
                    }

                    int x = minecraft.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                    int y = minecraft.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

                    drawBar(gui, getBarTexture(), x, y, steamBlockEntity.getSteam(), steamBlockEntity.getMaxSteam());
                    addYOffset(11);
                }
            }
        }
    }

    public static class AlchemyMachineTooltip extends Tooltip {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ResourceLocation getBarTexture() {
            return new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/fluid_frame.png");
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public void draw(GuiGraphics gui, BlockPos pos) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            BlockEntity blockEntity = pos != null ? minecraft.level.getBlockEntity(pos) : null;

            if (blockEntity != null) {
                if (blockEntity instanceof AlchemyMachineBlockEntity machine) {
                    for (int ii = 0; ii <= 2; ii++) {
                        int x = minecraft.getWindow().getGuiScaledWidth() / 2;
                        int y = minecraft.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();

                        Component name = NumericalUtil.getFluidName(machine.getFluidStack(ii), machine.getMaxCapacity());
                        if (!WizardsRebornClientConfig.NUMERICAL_FLUID.get()) {
                            name = NumericalUtil.getFluidName(machine.getFluidStack(ii));
                        }
                        drawCenteredText(gui, name.getString(), x, y);
                        addYOffset(11);

                        x = minecraft.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                        y = minecraft.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

                        drawBar(gui, getBarTexture(), x, y, machine.getTank(ii).getFluidAmount(), machine.getMaxCapacity());
                        addYOffset(11);
                    }
                }
            }
        }
    }

    public static void setupControlTypes() {
        addControlType(new WissenControlType());
        addControlType(new LightControlType());
    }

    public static void addControlType(ControlType controlType) {
        controlTypes.add(controlType);
    }

    public static class ControlType {
        public boolean controlled(ItemStack stack, UseOnContext context, BlockEntity blockEntity) {
            return false;
        }
    }

    public static class WissenControlType extends ControlType {
        @Override
        public boolean controlled(ItemStack stack, UseOnContext context, BlockEntity blockEntity) {
            int mode = getMode(stack);

            if (blockEntity instanceof IWissenBlockEntity wissenBlockEntity) {
                if (!getBlock(stack)) {
                    if ((mode == 1 && wissenBlockEntity.canConnectReceiveWissen()) || (mode == 2 && wissenBlockEntity.canConnectSendWissen())) {
                        setBlockPos(stack, context.getClickedPos());
                        setBlock(stack, true);
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static class LightControlType extends ControlType {
        @Override
        public boolean controlled(ItemStack stack, UseOnContext context, BlockEntity blockEntity) {
            int mode = getMode(stack);

            if (blockEntity instanceof ILightBlockEntity lightBlockEntity) {
                if (!getBlock(stack)) {
                    if ((mode == 1 && lightBlockEntity.canConnectReceiveLight()) || (mode == 2 && lightBlockEntity.canConnectSendLight())) {
                        setBlockPos(stack, context.getClickedPos());
                        setBlock(stack, true);
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
