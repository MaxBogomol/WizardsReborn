package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalStat;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.ItemBackedInventory;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
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
import java.util.List;

public class ArcaneWandItem extends Item implements IWissenItem {
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
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        CompoundTag nbt = stack.getTag();

        if (!world.isClientSide) {
            if (nbt == null) {
                nbt = new CompoundTag();
                nbt.putBoolean("crystal", false);
                nbt.putString("spell", "");
                nbt.putInt("cooldown", 0);
                SimpleContainer item = getInventory(stack);
                stack.setTag(nbt);
            }

            WissenItemUtils.existWissen(stack);
        }

        if (nbt.contains("cooldown")) {
            if (nbt.getInt("cooldown") > 0) {
                nbt.putInt("cooldown", nbt.getInt("cooldown")  - 1);
                if (nbt.getInt("cooldown") == 0) {
                    if (nbt.getString("spell") != "") {
                        Spell spell = Spells.getSpell(nbt.getString("spell"));
                        spell.onReload(stack, world, entity, slot, isSelected);
                    }
                }
            }
        }
    }

    @Override
    public int getMaxWissen() {
        return 10000;
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

        CompoundTag nbt = stack.getTag();
        if (canSpell(stack)) {
            Spell spell = Spells.getSpell(nbt.getString("spell"));
            if (spell.canSpell(world, player, hand)) {
                if (spell.canWandWithCrystal(stack)) {
                    spell.useSpell(world, player, hand);
                    return InteractionResultHolder.success(stack);
                }
            }
        }

        return InteractionResultHolder.fail(stack);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        CompoundTag nbt = stack.getTag();
        if (canSpell(stack)) {
            Spell spell = Spells.getSpell(nbt.getString("spell"));
            if (spell.canWandWithCrystal(stack)) {
                spell.onWandUseFirst(stack, context);
            }
        }

        return super.onItemUseFirst(stack, context);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        CompoundTag nbt = stack.getTag();
        if (canSpell(stack)) {
            Spell spell = Spells.getSpell(nbt.getString("spell"));
            if (spell.canWandWithCrystal(stack)) {
                spell.onUseTick(level, livingEntity, stack, remainingUseDuration);
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        CompoundTag nbt = stack.getTag();
        if (canSpell(stack)) {
            Spell spell = Spells.getSpell(nbt.getString("spell"));
            if (spell.canWandWithCrystal(stack)) {
                spell.releaseUsing(stack, level, entityLiving, timeLeft);
            }
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        CompoundTag nbt = stack.getTag();
        if (canSpell(stack)) {
            Spell spell = Spells.getSpell(nbt.getString("spell"));
            if (spell.canWandWithCrystal(stack)) {
                spell.finishUsingItem(stack, level, entityLiving);
            }
        }

        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (canSpell(stack)) {
            Spell spell = Spells.getSpell(nbt.getString("spell"));
            return spell.getUseDuration(stack);
        }

        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (canSpell(stack)) {
            Spell spell = Spells.getSpell(nbt.getString("spell"));
            return spell.getUseAnimation(stack);
        }
        return UseAnim.NONE;
    }

    public boolean canSpell(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt.getBoolean("crystal")) {
            if (nbt.getString("spell") != "") {
                Spell spell = Spells.getSpell(nbt.getString("spell"));
                return (KnowledgeUtils.isSpell(WizardsReborn.proxy.getPlayer(), spell));
            }
        }
        return false;
    }

    @Override
    public Component getName(ItemStack stack) {
        Component displayName = super.getName(stack);

        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.getBoolean("crystal")) {
            if (ArcaneWandItem.getInventory(stack).getItem(0).getItem() instanceof CrystalItem crystal) {
                Component crystalName = getCrystalTranslate(crystal.getName(stack));
                return displayName.copy().append(crystalName);
            }
        }

        return displayName;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        list.add(Component.empty());
        list.add(Component.translatable("lore.wizards_reborn.arcane_wand.crystal").withStyle(ChatFormatting.GRAY));

        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.getBoolean("crystal")) {
            if (ArcaneWandItem.getInventory(stack).getItem(0).getItem() instanceof CrystalItem crystal) {
                CrystalType type = crystal.getType();
                Color color = crystal.getType().getColor();
                for (CrystalStat stat : type.getStats()) {
                    int statlevel = crystal.getStatLevel(ArcaneWandItem.getInventory(stack).getItem(0), stat);
                    int red = (int) Mth.lerp((float) statlevel / stat.getMaxLevel(), Color.GRAY.getRed(), color.getRed());
                    int green = (int) Mth.lerp((float) statlevel / stat.getMaxLevel(), Color.GRAY.getGreen(), color.getGreen());
                    int blue = (int) Mth.lerp((float) statlevel / stat.getMaxLevel(), Color.GRAY.getBlue(), color.getBlue());

                    int packColor = ColorUtils.packColor(255, red, green, blue);
                    list.add(Component.literal(" ").append(Component.translatable(stat.getTranslatedName()).append(": " + statlevel).withStyle(Style.EMPTY.withColor(packColor))));
                }
            }
        }

        list.add(Component.translatable("lore.wizards_reborn.arcane_wand.spell").withStyle(ChatFormatting.GRAY));

        if (nbt.getString("spell") != "") {
            Spell spell = Spells.getSpell(nbt.getString("spell"));
            Color color = spell.getColor();
            int packColor = ColorUtils.packColor(255, color.getRed(), color.getGreen(), color.getBlue());
            list.add(Component.literal(" ").append(Component.translatable(spell.getTranslatedName()).withStyle(Style.EMPTY.withColor(packColor))));
        }
    }

    public static Component getCrystalTranslate(Component component) {
        Component crystal = Component.literal(" - ").append(component);
        return crystal;
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawWandGui(GuiGraphics gui) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        ItemStack main = mc.player.getMainHandItem();
        ItemStack offhand = mc.player.getOffhandItem();

        boolean render = false;
        ItemStack stack = ItemStack.EMPTY;

        if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
            stack = main;
            render = true;
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                stack = offhand;
                render = true;
            }
        }

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (render) {
            if (!player.isSpectator()) {
                ArcaneWandItem wand = (ArcaneWandItem) stack.getItem();
                CompoundTag nbt = stack.getTag();
                Spell spell = null;
                if (nbt.contains("spell")) {
                    if (nbt.getString("spell") != "") {
                        spell = Spells.getSpell(nbt.getString("spell"));
                    }
                }

                int x = 1;
                int y = 1;

                gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/arcane_wand_frame.png"), x, y, 0, 0, 52, 18, 64, 64);
                gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/cooldown_frame.png"), x + 2, y + 19, 0, 0, 48, 10, 64, 64);
                gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png"), x + 2, y + 30, 0, 0, 48, 10, 64, 64);

                int width = 32;
                if (spell != null && nbt.getInt("cooldown") > 0) {
                    CompoundTag stats = spell.getStats(stack);
                    width /= (double) spell.getCooldownWithStat(stats) / (double) nbt.getInt("cooldown");
                } else {
                    width = -32;
                }
                gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/cooldown_frame.png"), x + 10, y + 20, 0, 10, 32 - width, 8, 64, 64);

                width = 32;
                width /= (double) wand.getMaxWissen() / (double) WissenItemUtils.getWissen(stack);
                gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png"), x + 10, y + 31, 0, 10, width, 8, 64, 64);

                if (nbt.getBoolean("crystal")) {
                    SimpleContainer stack_inv = ArcaneWandItem.getInventory(stack);
                    gui.renderItem(stack_inv.getItem(0), x + 8, y);
                }

                if (spell != null) {
                    if (KnowledgeUtils.isSpell(Minecraft.getInstance().player, spell)) {
                        gui.blit(spell.getIcon(), x + 28, y + 1, 0, 0, 16, 16, 16, 16);
                        if (!spell.canWandWithCrystal(stack)) {
                            gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/arcane_wand_frame.png"), x + 27, y, 0, 18, 18, 18, 64, 64);
                        }
                    } else {
                        gui.blit(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_unknown.png"), x + 28, y + 1, 0, 0, 16, 16, 16, 16);
                    }
                }
            }
        }

        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
    }
}
