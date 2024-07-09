package mod.maxbogomol.wizards_reborn.mixin;

import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.EagleShotArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.WissenChargeArcaneEnchantment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowItem.class)
public class BowItemMixin {

    @Unique
    public AbstractArrow wizards_reborn$abstractArrow;

    @ModifyVariable(method = "releaseUsing", at = @At("STORE"))
    public AbstractArrow wizards_reborn$releaseUsing(AbstractArrow abstractarrow, ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        this.wizards_reborn$abstractArrow = abstractarrow;
        WissenChargeArcaneEnchantment.onBowShot(abstractarrow, stack, level, entityLiving, timeLeft);
        return abstractarrow;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), method = "releaseUsing")
    public void wizards_reborn$releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft, CallbackInfo ci) {
        EagleShotArcaneEnchantment.onBowShot(this.wizards_reborn$abstractArrow, stack, level, entityLiving, timeLeft);
    }
}