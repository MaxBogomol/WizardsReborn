package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SplitArrowEntity;
import mod.maxbogomol.wizards_reborn.common.network.AddScreenshakePacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;

public class SplitArcaneEnchantment extends ArcaneEnchantment {

    public SplitArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    @Override
    public Color getColor() {
        return new Color(231, 164, 78);
    }

    @Override
    public boolean canEnchantItem(ItemStack stack) {
        if (stack.getItem() instanceof IArcaneItem item) {
            if (ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.EAGLE_SHOT_ARCANE_ENCHANTMENT) > 0) return false;
            return item.getArcaneEnchantmentTypes().contains(ArcaneEnchantmentType.BOW);
        }
        return false;
    }

    public static void onBowShot(AbstractArrow abstractarrow, ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player && ArcaneEnchantmentUtils.isArcaneItem(stack)) {
            int enchantmentLevel = ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.SPLIT_ARCANE_ENCHANTMENT);
            if (enchantmentLevel > 0) {
                if (stack.getUseDuration() - timeLeft > 35) {
                    float costModifier = WissenUtils.getWissenCostModifierWithDiscount(player);
                    List<ItemStack> items = WissenUtils.getWissenItemsNoneAndStorage(WissenUtils.getWissenItemsCurios(player));
                    int wissen = WissenUtils.getWissenInItems(items);
                    int cost = (int) ((30 + (enchantmentLevel * 25)) * (1 - costModifier));
                    if (cost <= 0) {
                        cost = 1;
                    }

                    if (WissenUtils.canRemoveWissen(wissen, cost)) {
                        WissenUtils.removeWissenFromWissenItems(items, cost);

                        int charge = WissenChargeArcaneEnchantment.getCharge(abstractarrow);
                        if (charge > 0) {
                            charge = charge / 3;
                            WissenChargeArcaneEnchantment.setCharge(abstractarrow, charge);
                        }

                        abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() / 2f);
                        abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;

                        for (int i = 0; i < enchantmentLevel * 3; i++) {
                            SplitArrowEntity arrow = new SplitArrowEntity(level, player);
                            arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.9F, 10.0F);

                            arrow.setBaseDamage(abstractarrow.getBaseDamage());
                            arrow.setKnockback(abstractarrow.getKnockback());
                            arrow.setSecondsOnFire(abstractarrow.getRemainingFireTicks());
                            arrow.pickup = AbstractArrow.Pickup.DISALLOWED;

                            if (charge > 0) {
                                WissenChargeArcaneEnchantment.setCharge(arrow, charge);
                            }

                            level.addFreshEntity(arrow);
                        }

                        if (!player.level().isClientSide()) {
                            PacketHandler.sendTo(player, new AddScreenshakePacket(0.45f));
                            player.knockback(0.5f, player.getLookAngle().x(), player.getLookAngle().z());
                            player.hurtMarked = true;
                            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ANVIL_PLACE, SoundSource.PLAYERS, 0.05f, 1.5f);
                        }
                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static float getFOW(Player player, ItemStack stack, float fow) {
        int enchantmentLevel = ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.SPLIT_ARCANE_ENCHANTMENT);
        if (enchantmentLevel > 0) {
            if (player.getTicksUsingItem() > 35) {
                float costModifier = WissenUtils.getWissenCostModifierWithDiscount(player);
                List<ItemStack> items = WissenUtils.getWissenItemsNoneAndStorage(WissenUtils.getWissenItemsCurios(player));
                int wissen = WissenUtils.getWissenInItems(items);
                int cost = (int) ((30 + (enchantmentLevel * 25)) * (1 - costModifier));
                if (cost <= 0) {
                    cost = 1;
                }

                if (WissenUtils.canRemoveWissen(wissen, cost)) {
                    fow = fow + 0.2f;
                }
            }
        }
        return fow;
    }
}
