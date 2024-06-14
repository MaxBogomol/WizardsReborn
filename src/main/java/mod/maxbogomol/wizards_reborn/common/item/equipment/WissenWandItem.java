package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidTileEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.IHeatTileEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamTileEntity;
import mod.maxbogomol.wizards_reborn.api.light.ILightTileEntity;
import mod.maxbogomol.wizards_reborn.api.skin.Skin;
import mod.maxbogomol.wizards_reborn.api.wissen.*;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.common.tileentity.AlchemyMachineTileEntity;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import mod.maxbogomol.wizards_reborn.utils.NumericalUtils;
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
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            CompoundTag nbt = stack.getTag();
            if (nbt==null) {
                nbt = new CompoundTag();
                stack.setTag(nbt);
            }

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
            world.playSound(WizardsReborn.proxy.getPlayer(), player.blockPosition(), WizardsReborn.CRYSTAL_RESONATE_SOUND.get(), SoundSource.PLAYERS, 1.0f, 1.2f);

            return InteractionResultHolder.success(stack);
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level world = context.getLevel();
        InteractionResult result = InteractionResult.PASS;

        if(!world.isClientSide) {
            BlockEntity tileentity = world.getBlockEntity(context.getClickedPos());
            CompoundTag nbt = stack.getOrCreateTag();

            if (!nbt.contains("block")) {
                nbt.putBoolean("block", false);
            }
            if (!nbt.contains("mode")) {
                nbt.putInt("mode", 0);
            }

            if (nbt.getInt("mode") != 4) {
                boolean control = controlled(stack, context, tileentity);
                if (control) result = InteractionResult.SUCCESS;

                if (tileentity instanceof IWissenWandFunctionalTileEntity functionalTile) {
                    if (nbt.getInt("mode") == 0) {
                        functionalTile.wissenWandFunction();
                    }
                    stack.setTag(nbt);
                    result = InteractionResult.SUCCESS;
                }
            }
        }

        if (result == InteractionResult.SUCCESS) {
            context.getPlayer().awardStat(Stats.ITEM_USED.get(this));
            world.playSound(WizardsReborn.proxy.getPlayer(), context.getClickedPos(), WizardsReborn.CRYSTAL_RESONATE_SOUND.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }

        return result;
    }

    public boolean controlled(ItemStack stack, UseOnContext context, BlockEntity tile) {
        int mode = getMode(stack);

        for (ControlType controlType : controlTypes) {
            if (controlType.controlled(stack, context, tile)) return true;
        }

        if (tile instanceof IWissenWandControlledTileEntity controlledTile) {
            if (mode == 1) {
                return controlledTile.wissenWandReceiveConnect(stack, context, tile);
            }
            if (mode == 2) {
                return controlledTile.wissenWandSendConnect(stack, context, tile);
            }
            if (mode == 3) {
                return controlledTile.wissenWandReload(stack, context, tile);
            }
        }

        return false;
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

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        Skin skin = Skin.getSkinFromItem(stack);
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
        switch (mode) {
            case 0:
                return "lore.wizards_reborn.wissen_wand_mode.functional";
            case 1:
                return "lore.wizards_reborn.wissen_wand_mode.receive_connect";
            case 2:
                return "lore.wizards_reborn.wissen_wand_mode.send_connect";
            case 3:
                return "lore.wizards_reborn.wissen_wand_mode.reload";
            case 4:
                return "lore.wizards_reborn.wissen_wand_mode.off";
        }

        return "lore.wizards_reborn.wissen_wand_mode.functional";
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
        switch (mode) {
            case 0:
                return ChatFormatting.DARK_AQUA;
            case 1:
                return ChatFormatting.GREEN;
            case 2:
                return ChatFormatting.AQUA;
            case 3:
                return ChatFormatting.YELLOW;
            case 4:
                return ChatFormatting.GRAY;
        }

        return ChatFormatting.DARK_AQUA;
    }

    public static Component getModeTranslate(ItemStack stack) {
        Component mode = Component.literal(" (")
                .append(Component.translatable(getModeString(stack)).withStyle(getModeColor(stack)))
                .append(")");
        return mode;
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawWissenGui(GuiGraphics gui) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        ItemStack main = mc.player.getMainHandItem();
        ItemStack offhand = mc.player.getOffhandItem();
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
                float r = ColorUtils.getRed(color) / 255f;
                float g = ColorUtils.getGreen(color) / 255f;
                float b = ColorUtils.getBlue(color) / 255f;

                RenderSystem.setShaderColor(r, g, b, 1F);

                int X = (gui.guiWidth() - 11) / 2;
                int Y = (gui.guiHeight() - 11) / 2;
                gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_wand_mode.png"), X, Y, 0, 0, 11, 11, 16, 16);
                RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

                HitResult pos = mc.hitResult;
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
            int stringWidth = Minecraft.getInstance().font.width(string);
            gui.drawString(Minecraft.getInstance().font, string, x - (stringWidth / 2), y, 0xffffff);
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
            Minecraft mc = Minecraft.getInstance();
            BlockEntity tileentity = pos != null ? mc.level.getBlockEntity(pos) : null;

            if (tileentity != null) {
                if (tileentity instanceof IItemResultTileEntity tile) {
                    List<ItemStack> list = tile.getItemsResult();
                    int i = 0;
                    for (ItemStack item : list) {
                        int x = mc.getWindow().getGuiScaledWidth() / 2 - 8 + (i * 16) - ((list.size() - 1) * 8);
                        int y = mc.getWindow().getGuiScaledHeight() / 2 - 26;
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
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            BlockEntity tileentity = pos != null ? mc.level.getBlockEntity(pos) : null;

            if (tileentity != null) {
                if (tileentity instanceof IWissenTileEntity tile) {
                    if (player.isShiftKeyDown() && ClientConfig.NUMERICAL_WISSEN.get()) {
                        int x = mc.getWindow().getGuiScaledWidth() / 2;
                        int y = mc.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();
                        Component name = NumericalUtils.getWissenName(tile.getWissen(), tile.getMaxWissen());
                        drawCenteredText(gui, name.getString(), x, y);
                        addYOffset(11);
                    }

                    int x = mc.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                    int y = mc.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

                    drawBar(gui, getBarTexture(), x, y, tile.getWissen(), tile.getMaxWissen());
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
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            BlockEntity tileentity = pos != null ? mc.level.getBlockEntity(pos) : null;

            if (tileentity != null) {
                if (tileentity instanceof ICooldownTileEntity tile) {
                    if (player.isShiftKeyDown() && ClientConfig.NUMERICAL_COOLDOWN.get()) {
                        int x = mc.getWindow().getGuiScaledWidth() / 2;
                        int y = mc.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();
                        Component name = NumericalUtils.getCooldownName(tile.getCooldown());
                        drawCenteredText(gui, name.getString(), x, y);
                        addYOffset(11);
                    }

                    int x = mc.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                    int y = mc.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

                    drawBar(gui, getBarTexture(), x, y, 32 / tile.getCooldown());
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
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            BlockEntity tileentity = pos != null ? mc.level.getBlockEntity(pos) : null;

            if (tileentity != null) {
                if (tileentity instanceof ILightTileEntity tile) {
                    if (player.isShiftKeyDown() && ClientConfig.SHOW_LIGHT_NAME.get()) {
                        int x = mc.getWindow().getGuiScaledWidth() / 2;
                        int y = mc.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();
                        Component name = NumericalUtils.getLightName();
                        drawCenteredText(gui, name.getString(), x, y);
                        addYOffset(11);
                    }

                    int x = mc.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                    int y = mc.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

                    int width = 32;
                    if (tile.getLight() <= 0) width = 0;
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
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            BlockEntity tileentity = pos != null ? mc.level.getBlockEntity(pos) : null;

            if (tileentity != null) {
                if (tileentity instanceof IFluidTileEntity tile) {
                    int x = mc.getWindow().getGuiScaledWidth() / 2;
                    int y = mc.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();

                    Component name = NumericalUtils.getFluidName(tile.getFluidStack(), tile.getFluidMaxAmount());
                    if (!ClientConfig.NUMERICAL_FLUID.get()) {
                        name = NumericalUtils.getFluidName(tile.getFluidStack());
                    }
                    drawCenteredText(gui, name.getString(), x, y);
                    addYOffset(11);

                    x = mc.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                    y = mc.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

                    drawBar(gui, getBarTexture(), x, y, tile.getFluidAmount(), tile.getFluidMaxAmount());
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
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            BlockEntity tileentity = pos != null ? mc.level.getBlockEntity(pos) : null;

            if (tileentity != null) {
                if (tileentity instanceof IExperienceTileEntity tile) {
                    if (player.isShiftKeyDown() && ClientConfig.NUMERICAL_EXPERIENCE.get()) {
                        int x = mc.getWindow().getGuiScaledWidth() / 2;
                        int y = mc.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();
                        Component name = NumericalUtils.getExperienceName(tile.getExperience(), tile.getMaxExperience());
                        drawCenteredText(gui, name.getString(), x, y);
                        addYOffset(11);
                    }

                    int x = mc.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                    int y = mc.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

                    drawBar(gui, getBarTexture(), x, y, tile.getExperience(), tile.getMaxExperience());
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
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            BlockEntity tileentity = pos != null ? mc.level.getBlockEntity(pos) : null;

            if (tileentity != null) {
                if (tileentity instanceof IHeatTileEntity tile) {
                    if (player.isShiftKeyDown() && ClientConfig.NUMERICAL_HEAT.get()) {
                        int x = mc.getWindow().getGuiScaledWidth() / 2;
                        int y = mc.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();
                        Component name = NumericalUtils.getHeatName(tile.getHeat(), tile.getMaxHeat());
                        drawCenteredText(gui, name.getString(), x, y);
                        addYOffset(11);
                    }

                    int x = mc.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                    int y = mc.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

                    drawBar(gui, getBarTexture(), x, y, tile.getHeat(), tile.getMaxHeat());
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
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            BlockEntity tileentity = pos != null ? mc.level.getBlockEntity(pos) : null;

            if (tileentity != null) {
                if (tileentity instanceof ISteamTileEntity tile) {
                    if (player.isShiftKeyDown() && ClientConfig.NUMERICAL_STEAM.get()) {
                        int x = mc.getWindow().getGuiScaledWidth() / 2;
                        int y = mc.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();
                        Component name = NumericalUtils.getHeatName(tile.getSteam(), tile.getMaxSteam());
                        drawCenteredText(gui, name.getString(), x, y);
                        addYOffset(11);
                    }

                    int x = mc.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                    int y = mc.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

                    drawBar(gui, getBarTexture(), x, y, tile.getSteam(), tile.getMaxSteam());
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
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            BlockEntity tileentity = pos != null ? mc.level.getBlockEntity(pos) : null;

            if (tileentity != null) {
                if (tileentity instanceof AlchemyMachineTileEntity machine) {
                    for (int ii = 0; ii <= 2; ii++) {
                        if (player.isShiftKeyDown()) {
                            int x = mc.getWindow().getGuiScaledWidth() / 2;
                            int y = mc.getWindow().getGuiScaledHeight() / 2 + 12 + getYOffset();

                            Component name = NumericalUtils.getFluidName(machine.getFluidStack(ii), machine.getMaxCapacity());
                            if (!ClientConfig.NUMERICAL_FLUID.get()) {
                                name = NumericalUtils.getFluidName(machine.getFluidStack(ii));
                            }
                            drawCenteredText(gui, name.getString(), x, y);
                            addYOffset(11);
                        }

                        int x = mc.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                        int y = mc.getWindow().getGuiScaledHeight() / 2 + 11 + getYOffset();

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
        public boolean controlled(ItemStack stack, UseOnContext context, BlockEntity tile) {
            return false;
        }
    }

    public static class WissenControlType extends ControlType {
        @Override
        public boolean controlled(ItemStack stack, UseOnContext context, BlockEntity tile) {
            int mode = getMode(stack);

            if (tile instanceof IWissenTileEntity wissenTile) {
                if (!getBlock(stack)) {
                    if ((mode == 1 && wissenTile.canConnectReceiveWissen()) || (mode == 2 && wissenTile.canConnectSendWissen())) {
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
        public boolean controlled(ItemStack stack, UseOnContext context, BlockEntity tile) {
            int mode = getMode(stack);

            if (tile instanceof ILightTileEntity lightTile) {
                if (!getBlock(stack)) {
                    if ((mode == 1 && lightTile.canConnectReceiveLight()) || (mode == 2 && lightTile.canConnectSendLight())) {
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
