package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.fluffy_fur.common.damage.DamageHandler;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import net.minecraft.world.entity.Entity;
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
        if (ArcaneEnchantmentUtil.isArcaneItem(stack)) {
            if (ArcaneEnchantmentUtil.getArcaneEnchantment(stack, WizardsRebornArcaneEnchantments.WISSEN_MENDING) > 0) return false;
            return ArcaneEnchantmentUtil.getArcaneEnchantmentTypes(stack).contains(ArcaneEnchantmentTypes.BREAKABLE);
        }
        return false;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public static void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        if (!level.isClientSide()) {
            if (entity instanceof Player player) {
                int enchantmentLevel = ArcaneEnchantmentUtil.getArcaneEnchantment(stack, WizardsRebornArcaneEnchantments.LIFE_MENDING);

                if (enchantmentLevel > 0 && stack.getDamageValue() > 0) {
                    int tick = 100;
                    if (entity.tickCount % tick == 0) {
                        if (player.getHealth() > 1f) {
                            player.setHealth(player.getHealth() - 1f);
                        } else {
                            player.invulnerableTime = 0;
                            player.hurt(DamageHandler.create(player.level(), WizardsRebornDamageTypes.RITUAL, player), 100);
                        }
                        stack.setDamageValue(stack.getDamageValue() - (3 * enchantmentLevel));
                    }
                }
            }
        }
    }
}
