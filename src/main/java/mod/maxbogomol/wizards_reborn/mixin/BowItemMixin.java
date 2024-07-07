package mod.maxbogomol.wizards_reborn.mixin;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.capability.IWissenCharge;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(BowItem.class)
public class BowItemMixin {

    @ModifyVariable(method = "releaseUsing", at = @At("STORE"))
    public AbstractArrow wizards_reborn$getTooltip(AbstractArrow abstractarrow, ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player && ArcaneEnchantmentUtils.isArcaneItem(stack)) {
            int enchantmentLevel = ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.WISSEN_CHARGE_ARCANE_ENCHANTMENT);
            if (enchantmentLevel > 0) {
                if (BowItem.getPowerForTime(stack.getUseDuration() - timeLeft) >= 1f) {
                    abstractarrow.getCapability(IWissenCharge.INSTANCE, null).ifPresent((w) -> {
                        int time = stack.getUseDuration() - timeLeft;
                        if (time > 100) {
                            time = 100;
                        }
                        if (time > 50 && enchantmentLevel == 1) {
                            time = 50;
                        }

                        float costModifier = WissenUtils.getWissenCostModifierWithDiscount(player);
                        List<ItemStack> items = WissenUtils.getWissenItemsNoneAndStorage(WissenUtils.getWissenItemsCurios(player));
                        int wissen = WissenUtils.getWissenInItems(items);
                        int cost = (int) ((30 + time) * (1 - costModifier));
                        if (cost <= 0) {
                            cost = 1;
                        }

                        if (WissenUtils.canRemoveWissen(wissen, cost)) {
                            WissenUtils.removeWissenFromWissenItems(items, cost);
                            w.setCharge(time);
                        }
                    });
                }
            }
        }
        return abstractarrow;
    }
}