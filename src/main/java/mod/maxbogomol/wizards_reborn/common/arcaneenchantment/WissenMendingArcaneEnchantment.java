package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.List;

public class WissenMendingArcaneEnchantment extends ArcaneEnchantment {

    public WissenMendingArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    @Override
    public Color getColor() {
        return new Color(87, 127, 184);
    }

    @Override
    public boolean canEnchantItem(ItemStack stack) {
        if (ArcaneEnchantmentUtil.isArcaneItem(stack)) {
            if (ArcaneEnchantmentUtil.getArcaneEnchantment(stack, WizardsRebornArcaneEnchantments.LIFE_MENDING) > 0) return false;
            return ArcaneEnchantmentUtil.getArcaneEnchantmentTypes(stack).contains(ArcaneEnchantmentTypes.BREAKABLE);
        }
        return false;
    }

    public static void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        if (!level.isClientSide()) {
            if (entity instanceof Player player) {
                int enchantmentLevel = ArcaneEnchantmentUtil.getArcaneEnchantment(stack, WizardsRebornArcaneEnchantments.WISSEN_MENDING);

                if (enchantmentLevel > 0 && stack.getDamageValue() > 0) {
                    int tick = 100 - ((enchantmentLevel - 1) * 50);
                    if (enchantmentLevel >= 3) {
                        tick = 20;
                    }
                    if (entity.tickCount % tick == 0) {
                        float costModifier = WissenUtil.getWissenCostModifierWithDiscount(player);
                        List<ItemStack> items = WissenUtil.getWissenItemsNoneAndStorage(WissenUtil.getWissenItemsCurios(player));
                        int wissen = WissenUtil.getWissenInItems(items);
                        int cost = (int) (5 * (1 - costModifier));
                        if (cost <= 0) {
                            cost = 1;
                        }

                        if (WissenUtil.canRemoveWissen(wissen, cost)) {
                            WissenUtil.removeWissenFromWissenItems(items, cost);
                            stack.setDamageValue(stack.getDamageValue() - 1);
                        }
                    }
                }
            }
        }
    }

    public static int damageItem(ItemStack stack, int amount, LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            if (amount > 0) {
                if (entity instanceof Player player) {
                    int enchantmentLevel = ArcaneEnchantmentUtil.getArcaneEnchantment(stack, WizardsRebornArcaneEnchantments.WISSEN_MENDING);
                    float costModifier = WissenUtil.getWissenCostModifierWithDiscount(player);
                    List<ItemStack> items = WissenUtil.getWissenItemsNoneAndStorage(WissenUtil.getWissenItemsCurios(player));
                    int wissen = WissenUtil.getWissenInItems(items);
                    int cost = (int) (5 * (1 - costModifier));
                    if (cost <= 0) {
                        cost = 1;
                    }

                    if (enchantmentLevel >= 3 && WissenUtil.canRemoveWissen(wissen, cost)) {
                        WissenUtil.removeWissenFromWissenItems(items, cost);
                        amount--;
                    }
                }
            }
        }

        return amount;
    }
}
