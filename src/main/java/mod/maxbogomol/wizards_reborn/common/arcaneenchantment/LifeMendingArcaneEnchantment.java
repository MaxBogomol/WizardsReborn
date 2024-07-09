package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import mod.maxbogomol.wizards_reborn.common.damage.DamageSourceRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.awt.*;

public class LifeMendingArcaneEnchantment extends ArcaneEnchantment {

    public LifeMendingArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    @Override
    public Color getColor() {
        return new Color(176, 29, 91);
    }

    @Override
    public boolean canEnchantItem(ItemStack stack) {
        if (stack.getItem() instanceof IArcaneItem item) {
            if (ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.WISSEN_MENDING_ARCANE_ENCHANTMENT) > 0) return false;
            return item.getArcaneEnchantmentTypes().contains(ArcaneEnchantmentType.BREAKABLE);
        }
        return false;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public static void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        if (!world.isClientSide()) {
            if (entity instanceof Player player) {
                int enchantmentLevel = ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.LIFE_MENDING_ARCANE_ENCHANTMENT);

                if (enchantmentLevel > 0 && stack.getDamageValue() > 0) {
                    int tick = 100 - ((enchantmentLevel - 1) * 50);
                    if (enchantmentLevel >= 3) {
                        tick = 20;
                    }
                    if (entity.tickCount % tick == 0) {
                        if (player.getHealth() > 0.25f) {
                            player.setHealth(player.getHealth() - 0.25f);
                        } else {
                            player.invulnerableTime = 0;
                            player.hurt(new DamageSource(DamageSourceRegistry.create(player.level(), DamageSourceRegistry.RITUAL).typeHolder(), player), 100);
                        }
                        stack.setDamageValue(stack.getDamageValue() - 1);
                    }
                }
            }
        }
    }

    public static int damageItem(ItemStack stack, int amount, LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            if (amount > 0) {
                if (entity instanceof Player player) {
                    int enchantmentLevel = ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.LIFE_MENDING_ARCANE_ENCHANTMENT);

                    if (enchantmentLevel >= 3) {
                        if (player.getHealth() > 0.25f) {
                            player.setHealth(player.getHealth() - 0.25f);
                        } else {
                            player.invulnerableTime = 0;
                            player.hurt(new DamageSource(DamageSourceRegistry.create(player.level(), DamageSourceRegistry.RITUAL).typeHolder(), player), 100);
                        }
                        amount--;
                    }
                }
            }
        }

        return amount;
    }
}
