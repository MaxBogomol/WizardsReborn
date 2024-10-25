package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.item.ICustomAnimationItem;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.fluffy_fur.common.item.ItemBackedInventory;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalStat;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellHandler;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemType;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtil;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import mod.maxbogomol.wizards_reborn.util.NumericalUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class ArcaneWandItem extends Item implements IWissenItem, ICustomAnimationItem, IGuiParticleItem {

    public ArcaneWandItem(Properties properties) {
        super(properties);
    }

    public static SimpleContainer getInventory(ItemStack stack) {
        return new ItemBackedInventory(stack, 1);
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag oldCapNbt) {
        return new InvProvider(stack);
    }

    private static class InvProvider implements ICapabilityProvider {
        private final LazyOptional<IItemHandler> opt;

        public InvProvider(ItemStack stack) {
            opt = LazyOptional.of(() -> new InvWrapper(getInventory(stack)));
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
            return ForgeCapabilities.ITEM_HANDLER.orEmpty(capability, opt);
        }
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        Iterator<ItemStack> iter = new Iterator<>() {
            private int i = 0;
            private final SimpleContainer inventory = getInventory(itemEntity.getItem());

            @Override
            public boolean hasNext() {
                return i < inventory.getContainerSize();
            }

            @Override
            public ItemStack next() {
                return inventory.getItem(i++);
            }
        };

        ItemUtils.onContainerDestroyed(itemEntity, Stream.iterate(iter.next(), t -> iter.hasNext(), t -> iter.next()));
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        WissenItemUtil.existWissen(stack);
        return stack;
    }

    @Override
    public int getMaxWissen() {
        return 10000;
    }

    @Override
    public WissenItemType getWissenItemType() {
        return WissenItemType.USING;
    }

    public static void existTags(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("crystal")) {
            nbt.putBoolean("crystal", false);
        }
        if (!nbt.contains("spell")) {
            nbt.putString("spell", "");
        }
        if (!nbt.contains("cooldown")) {
            nbt.putInt("cooldown", 0);
        }
        if (!nbt.contains("maxCooldown")) {
            nbt.putInt("maxCooldown", 0);
        }
        if (!nbt.contains("spellData")) {
            nbt.put("spellData", new CompoundTag());
        }
    }

    public static boolean getCrystal(ItemStack stack) {
        existTags(stack);
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getBoolean("crystal");
    }

    public static String getSpell(ItemStack stack) {
        existTags(stack);
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getString("spell");
    }

    public static int getCooldown(ItemStack stack) {
        existTags(stack);
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getInt("cooldown");
    }

    public static int getMaxCooldown(ItemStack stack) {
        existTags(stack);
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getInt("maxCooldown");
    }

    public static CompoundTag getSpellData(ItemStack stack) {
        existTags(stack);
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getCompound("spellData");
    }

    public static void setCrystal(ItemStack stack, boolean crystal) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putBoolean("crystal", crystal);
    }

    public static void setSpell(ItemStack stack, String spell) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putString("spell", spell);
    }

    public static void setCooldown(ItemStack stack, int cooldown) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("cooldown", cooldown);
    }

    public static void setMaxCooldown(ItemStack stack, int cooldown) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("maxCooldown", cooldown);
    }

    public static void setSpellData(ItemStack stack, CompoundTag spellData) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.put("spellData", spellData);
    }

    public boolean canSpell(ItemStack stack, Player player) {
        if (getCrystal(stack)) {
            if (!getSpell(stack).isEmpty()) {
                Spell spell = SpellHandler.getSpell(getSpell(stack));
                return (KnowledgeUtil.isSpell(player, spell));
            }
        }
        return false;
    }

    public boolean canSpell(ItemStack stack) {
        if (getCrystal(stack)) {
            return !getSpell(stack).isEmpty();
        }
        return false;
    }

    public static ItemStack getItemCrystal(ItemStack stack) {
        return ArcaneWandItem.getInventory(stack).getItem(0);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (!slotChanged) {
            return false;
        }
        return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        WissenItemUtil.existWissen(stack);
        existTags(stack);

        if (getCooldown(stack) > 0) {
            setCooldown(stack, getCooldown(stack) - 1);
            if (getCooldown(stack) <= 0) {
                setMaxCooldown(stack, 0);
                if (!getSpell(stack).isEmpty()) {
                    Spell spell = SpellHandler.getSpell(getSpell(stack));
                    spell.onReload(stack, level, entity, slot, isSelected);
                }
            }
        }
    }

    public boolean canSpell(Spell spell, Player player, ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getBoolean("crystal") && nbt.getInt("cooldown") <= 0 && WissenItemUtil.canRemoveWissen(stack, spell.getWissenCostWithStat(Spell.getStats(stack), player));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (canSpell(stack, player)) {
            Spell spell = SpellHandler.getSpell(getSpell(stack));
            if (canSpell(spell, player, stack) && spell.canSpell(level, spell.getWandContext(player, stack)) && spell.canSpellAir(level, player, hand)) {
                if (spell.canWandWithCrystal(getItemCrystal(stack))) {
                    spell.useWand(level, player, hand, stack);
                    return InteractionResultHolder.success(stack);
                }
            }
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (canSpell(stack, context.getPlayer())) {
            Spell spell = SpellHandler.getSpell(getSpell(stack));
            if (spell.canWandWithCrystal(getItemCrystal(stack))) {
                return spell.useWandOn(stack, context);
            }
        }
        return super.onItemUseFirst(stack, context);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (canSpell(stack, (Player) livingEntity)) {
            Spell spell = SpellHandler.getSpell(getSpell(stack));
            if (spell.canWandWithCrystal(getItemCrystal(stack))) {
                spell.useWandTick(level, livingEntity, stack, remainingUseDuration);
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        if (canSpell(stack, (Player) livingEntity)) {
            Spell spell = SpellHandler.getSpell(getSpell(stack));
            if (spell.canWandWithCrystal(getItemCrystal(stack))) {
                spell.releaseUsing(stack, level, livingEntity, timeLeft);
            }
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (canSpell(stack, (Player) livingEntity)) {
            Spell spell = SpellHandler.getSpell(getSpell(stack));
            if (spell.canWandWithCrystal(getItemCrystal(stack))) {
                spell.finishUsingItem(stack, level, livingEntity);
            }
        }

        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        if (canSpell(stack)) {
            Spell spell = SpellHandler.getSpell(getSpell(stack));
            return spell.getUseDuration(stack);
        }

        return 72000;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        if (!WizardsRebornClientConfig.SPELLS_FIRST_PERSON_ITEM_ANIMATIONS.get()) {
            return UseAnim.NONE;
        }
        if (canSpell(stack, WizardsReborn.proxy.getPlayer())) {
            Spell spell = SpellHandler.getSpell(getSpell(stack));
            return spell.getUseAnimation(stack);
        }
        return UseAnim.NONE;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemAnimation getAnimation(ItemStack stack) {
        if (canSpell(stack, WizardsReborn.proxy.getPlayer())) {
            Spell spell = SpellHandler.getSpell(getSpell(stack));
            if (spell.hasCustomAnimation(stack)) {
                return spell.getAnimation(stack);
            }
        }

        return null;
    }

    @Override
    public Component getName(ItemStack stack) {
        Component displayName = super.getName(stack);

        if (getCrystal(stack)) {
            if (getItemCrystal(stack).getItem() instanceof CrystalItem crystal) {
                Component crystalName = getCrystalTranslate(crystal.getName(stack));
                return displayName.copy().append(crystalName);
            }
        }

        return displayName;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        ItemSkin skin = ItemSkin.getSkinFromItem(stack);
        if (skin != null) list.add(skin.getSkinComponent());

        if (WizardsRebornClientConfig.NUMERICAL_WISSEN.get()) {
            WissenItemUtil.existWissen(stack);
            list.add(NumericalUtil.getWissenName(WissenItemUtil.getWissen(stack), getMaxWissen()).copy().withStyle(ChatFormatting.GRAY));
        }

        list.add(Component.empty());
        list.add(Component.translatable("lore.wizards_reborn.arcane_wand.crystal").withStyle(ChatFormatting.GRAY));

        if (getCrystal(stack)) {
            if (getItemCrystal(stack).getItem() instanceof CrystalItem crystal) {
                CrystalType type = crystal.getType();
                Color color = crystal.getType().getColor();
                for (CrystalStat stat : type.getStats()) {
                    int statLevel = crystal.getStatLevel(getItemCrystal(stack), stat);
                    int red = (int) Mth.lerp((float) statLevel / stat.getMaxLevel(), Color.GRAY.getRed(), color.getRed());
                    int green = (int) Mth.lerp((float) statLevel / stat.getMaxLevel(), Color.GRAY.getGreen(), color.getGreen());
                    int blue = (int) Mth.lerp((float) statLevel / stat.getMaxLevel(), Color.GRAY.getBlue(), color.getBlue());

                    int packColor = ColorUtil.packColor(255, red, green, blue);
                    list.add(Component.literal(" ").append(Component.translatable(stat.getTranslatedName()).append(": " + statLevel).withStyle(Style.EMPTY.withColor(packColor))));
                }
            }
        }

        list.add(Component.translatable("lore.wizards_reborn.arcane_wand.spell").withStyle(ChatFormatting.GRAY));

        if (!getSpell(stack).isEmpty()) {
            Spell spell = SpellHandler.getSpell(getSpell(stack));
            Color color = spell.getColor();
            int packColor = ColorUtil.packColor(255, color.getRed(), color.getGreen(), color.getBlue());
            list.add(Component.literal(" ").append(Component.translatable(spell.getTranslatedName()).withStyle(Style.EMPTY.withColor(packColor))));
        }
    }

    public static Component getCrystalTranslate(Component component) {
        return Component.literal(" - ").append(component);
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawWandGui(GuiGraphics gui) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        ItemStack main = minecraft.player.getMainHandItem();
        ItemStack offhand = minecraft.player.getOffhandItem();

        boolean isMain = false;
        boolean isOff = false;
        boolean render = false;
        if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
            render = true;
            isMain = true;
        }
        if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
            render = true;
            isOff = true;
        }

        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        gui.pose().pushPose();
        gui.pose().translate(0, 0, -200);

        if (render) {
            if (!player.isSpectator()) {
                boolean up = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_UP.get();
                boolean right = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_RIGHT.get();
                boolean sideHud = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_SIDE_HUD.get();
                boolean sideBar = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_SIDE_BAR.get();
                boolean horizontalBar = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_HORIZONTAL_BAR.get();

                int xOff = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_X_OFFSET.get();
                int yOff = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_Y_OFFSET.get();
                int xTwoOff = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_SECOND_X_OFFSET.get();
                int yTwoOff = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_SECOND_Y_OFFSET.get();
                int xBarOff = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_BAR_X_OFFSET.get();
                int yBarOff = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_BAR_Y_OFFSET.get();

                boolean twoHudFree = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_SECOND_HUD_FREE.get();
                boolean barFree = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_BAR_FREE.get();

                boolean drawCooldown = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_COOLDOWN_TEXT.get();
                boolean drawWissen = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_WISSEN_TEXT.get();
                boolean reverseBar = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_REVERSE_BAR.get();
                boolean showEmpty = WizardsRebornClientConfig.ARCANE_WAND_OVERLAY_SHOW_EMPTY.get();

                boolean rightBar = right;
                if (up && horizontalBar) {
                    rightBar = false;
                }
                if (!up && horizontalBar) {
                    rightBar = true;
                }

                List<Spell> spells = getSpellSet(showEmpty, reverseBar);
                int spellsOffset = (spells.size() * 18);

                int x = 1;
                int y = 1;

                int width = gui.guiWidth();
                int height = gui.guiHeight();

                int xOffset = xOff;
                int yOffset = yOff;

                if (!up) {
                    y = height - 43;
                }

                if (right) {
                    x = width - 53;
                }

                if (isMain) {
                    drawWandHUD(gui, x + xOffset, y + yOffset, main, drawCooldown, drawWissen);
                    if (sideHud) {
                        xOffset = xOffset + (right ? -54 : 54);
                    } else {
                        yOffset = yOffset - (up ? -43 : 43);
                    }
                    if (isOff) {
                        if (twoHudFree) {
                            xOffset = 0;
                            yOffset = 0;
                        }
                        xOffset = xOffset + xTwoOff;
                        yOffset = yOffset + yTwoOff;
                    }
                }
                if (isOff) {
                    drawWandHUD(gui, x + xOffset, y + yOffset, offhand, drawCooldown, drawWissen);
                    if (sideHud) {
                        xOffset = xOffset + (right ? -54 : 54);
                    } else {
                        yOffset = yOffset - (up ? -43 : 43);
                    }
                }

                if (!sideBar && sideHud) {
                    xOffset = xOff;
                    yOffset = yOff;
                    yOffset = yOffset - (up ? -43 : 43);
                }
                if (sideBar && !sideHud) {
                    xOffset = xOff;
                    yOffset = yOff;
                    xOffset = xOffset + (right ? -54 : 54);
                }

                if (barFree) {
                    xOffset = 0;
                    yOffset = 0;
                }

                if (up) {
                    if (horizontalBar && right) {
                       xOffset = xOffset - spellsOffset + 4;
                    }
                } else {
                    if (horizontalBar) {
                        if (right) xOffset = xOffset - spellsOffset + 4;
                        yOffset = yOffset + 25;
                    } else {
                        yOffset = yOffset - spellsOffset + 28;
                    }
                }

                if (right) {
                    xOffset = xOffset + 34;
                }

                xOffset = xOffset + xBarOff;
                yOffset = yOffset + yBarOff;

                drawBar(gui, x + xOffset, y + yOffset, horizontalBar, rightBar, showEmpty, reverseBar);
            }
        }

        gui.pose().popPose();

        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawWandHUD(GuiGraphics gui, int x, int y, ItemStack stack, boolean drawCooldown, boolean drawWissen) {
        if (stack.getItem() instanceof ArcaneWandItem wand) {
            existTags(stack);
            WissenItemUtil.existWissen(stack);
            Spell spell = null;

            if (!getSpell(stack).isEmpty()) {
                spell = SpellHandler.getSpell(getSpell(stack));
            }

            int cooldown = getCooldown(stack);
            int maxCooldown = getMaxCooldown(stack);
            int wissen = WissenItemUtil.getWissen(stack);
            int maxWissen = wand.getMaxWissen();


            gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/arcane_wand_frame.png"), x, y, 0, 0, 52, 18, 64, 64);
            gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/cooldown_frame.png"), x + 2, y + 19, 0, 0, 48, 10, 64, 64);
            gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png"), x + 2, y + 30, 0, 0, 48, 10, 64, 64);

            int width = 32;
            if (spell != null && cooldown > 0) {
                width /= (double) maxCooldown / (double) cooldown;
            } else {
                width = -32;
            }
            gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/cooldown_frame.png"), x + 10, y + 20, 0, 10, 32 - width, 8, 64, 64);

            width = 32;
            width /= (double) maxWissen / (double) wissen;
            gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png"), x + 10, y + 31, 0, 10, width, 8, 64, 64);

            if (getCrystal(stack)) {
                gui.renderItem(getItemCrystal(stack), x + 8, y);
            }

            if (spell != null) {
                if (KnowledgeUtil.isSpell(Minecraft.getInstance().player, spell)) {
                    gui.blit(spell.getIcon(), x + 28, y + 1, 0, 0, 16, 16, 16, 16);
                    if (!spell.canWandWithCrystal(getItemCrystal(stack))) {
                        gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/arcane_wand_frame.png"), x + 27, y, 0, 18, 18, 18, 64, 64);
                    }
                } else {
                    gui.blit(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/unknown.png"), x + 28, y + 1, 0, 0, 16, 16, 16, 16);
                }
            }

            Font font_renderer = Minecraft.getInstance().font;
            if (drawCooldown) {
                String textCooldown = Integer.toString(cooldown);
                int cooldownStringWidth = font_renderer.width(textCooldown);

                gui.drawString(Minecraft.getInstance().font, textCooldown, x + 26 - (cooldownStringWidth / 2), y + 20, 0xffffff);
            }

            if (drawWissen) {
                String textWissen = Integer.toString(wissen);
                int wissenStringWidth = font_renderer.width(textWissen);

                gui.drawString(Minecraft.getInstance().font, textWissen, x + 26 - (wissenStringWidth / 2), y + 31, 0xffffff);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawBar(GuiGraphics gui, int x, int y, boolean horizontal, boolean right, boolean showEmpty, boolean reverse) {
        Minecraft minecraft = Minecraft.getInstance();
        int currentSpellInSet = getCurrentSpellInSet(showEmpty, reverse);

        int x1 = 18;
        int y1 = 18;
        int x2 = 18;
        int y2 = 24;
        int x3 = 42;
        int y3 = 18;

        int u1 = 16;
        int v1 = 6;
        int u3 = 8;
        int v3 = 16;

        int xOffset = 0;
        int yOffset = 1;

        int xo = 1;
        int yo = 0;

        if (horizontal) {
            x1 = 20;
            y1 = 30;
            x2 = 26;
            y2 = 30;
            x3 = 34;
            y3 = 42;

            u1 = 6;
            v1 = 16;
            u3 = 16;
            v3 = 8;

            xOffset = -1;
            yOffset = 0;

            xo = 1;
            yo = 0;

            if (right) {
                x3 = 34;
                y3 = 34;
            }
        } else if (right) {
            x3 = 34;
            y3 = 18;
        }

        gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/arcane_wand_frame.png"), x + xOffset + xo, y + yOffset + yo, x1, y1, u1, v1, 64, 64);

        if (horizontal) {
            xOffset = xOffset + 7;
        } else {
            yOffset = yOffset + 7;
        }

        List<Spell> spells = getSpellSet(showEmpty, reverse);

        int i = 0;
        for (Spell spellI : spells) {
            ResourceLocation resource = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/research.png");
            if (spellI != null) {
                if (!KnowledgeUtil.isSpell(minecraft.player, spellI)) {
                    resource = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/unknown.png");
                } else {
                    resource = spellI.getIcon();
                }
            }

            if (!(KnowledgeUtil.isSpell(Minecraft.getInstance().player, spellI)) && spellI != null) {
                resource = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/unknown.png");
            }

            int xof = 0;
            int yof = 0;
            int xc = 0;
            int yc = 0;
            if (currentSpellInSet == i) {
                if (horizontal) {
                    if (right) {
                        yof = -8;
                        yc = 7;
                    } else {
                        yof = 8;
                    }
                } else {
                    if (right) {
                        xof = -8;
                        xc = 8;
                    } else {
                        xof = 8;
                    }
                }
                gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/arcane_wand_frame.png"), x + xOffset + xo + xc, y + yOffset + yo + yc, x3, y3, u3, v3, 64, 64);
            }

            gui.blit(resource, x + 1 + xOffset + xof, y + yOffset + yof, 0, 0, 16, 16, 16, 16);
            if (horizontal) {
                xOffset = xOffset + 18;
            } else {
                yOffset = yOffset + 18;
            }
            i++;
        }

        if (horizontal) {
            xOffset = xOffset - 1;
        } else {
            yOffset = yOffset - 1;
        }

        gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/arcane_wand_frame.png"), x + xOffset + xo, y + yOffset + yo, x2, y2, u1, v1, 64, 64);
    }

    @OnlyIn(Dist.CLIENT)
    public static List<Spell> getSpellSet(boolean showEmpty, boolean reverse) {
        Minecraft minecraft = Minecraft.getInstance();

        int currentSpellSet = KnowledgeUtil.getCurrentSpellSet(minecraft.player);
        int currentSpellInSet = KnowledgeUtil.getCurrentSpellInSet(minecraft.player);

        List<Spell> spells = KnowledgeUtil.getSpellSet(minecraft.player, currentSpellSet);
        List<Spell> spellSet = new ArrayList<>();

        int ii = 0;
        if (reverse) ii = 9;
        for (int i = 0; i < 10; i++) {
            Spell spell = spells.get(ii);
            boolean add = showEmpty || spell != null || currentSpellInSet == ii;
            if (add) spellSet.add(spell);
            if (reverse) {
                ii--;
            } else {
                ii++;
            }
        }

        return spellSet;
    }

    @OnlyIn(Dist.CLIENT)
    public static int getCurrentSpellInSet(boolean showEmpty, boolean reverse) {
        Minecraft minecraft = Minecraft.getInstance();

        int currentSpellSet = KnowledgeUtil.getCurrentSpellSet(minecraft.player);
        int currentSpellInSet = KnowledgeUtil.getCurrentSpellInSet(minecraft.player);

        List<Spell> spells = KnowledgeUtil.getSpellSet(minecraft.player, currentSpellSet);

        int ii = 0;
        if (reverse) ii = 9;
        for (int i = 0; i < 10; i++) {
            Spell spell = spells.get(ii);
            boolean add = (!showEmpty && spell == null) || currentSpellInSet == ii;
            if (add) return i;
            if (reverse) {
                ii--;
            } else {
                ii++;
            }
        }

        return currentSpellInSet;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack poseStack, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        if (getItemCrystal(stack).getItem() instanceof CrystalItem crystal) {
            if (crystal.getPolishing().getPolishingLevel() > 0) {
                int polishingLevel = crystal.getPolishing().getPolishingLevel();
                if (polishingLevel > 4) polishingLevel = 4;
                Color color = crystal.getType().getColor();
                int seedI = this.getDescriptionId().length();

                poseStack.pushPose();
                poseStack.translate(x + 12, y + 4, 100);
                RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                        .setColor(color).setAlpha(0.75F * (polishingLevel / 4f))
                        .renderDragon(poseStack, 7f, ClientTickHandler.partialTicks, seedI)
                        .endBatch();
                poseStack.popPose();

                if (crystal.getPolishing().hasParticle()) {
                    Color polishingColor = crystal.getPolishing().getColor();
                    poseStack.pushPose();
                    poseStack.translate(x + 12, y + 4, 100);
                    RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                            .setColor(polishingColor).setAlpha(0.5f)
                            .renderDragon(poseStack, 6f, ClientTickHandler.partialTicks, seedI + 1)
                            .endBatch();
                    poseStack.popPose();
                }
            }
        }
    }
}
