package mod.maxbogomol.wizards_reborn.mixin.common;

import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.WissenChargeArcaneEnchantment;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {

    @Inject(method = "tick", at = @At("RETURN"))
    private void wizards_reborn$createArrow(CallbackInfo ci) {
        AbstractArrow self = (AbstractArrow) ((Object) this);
        WissenChargeArcaneEnchantment.arrowTick(self);
    }

    @Inject(method = "onHitBlock", at = @At("RETURN"))
    private void wizards_reborn$onHitBlock(BlockHitResult result, CallbackInfo ci) {
        AbstractArrow self = (AbstractArrow) ((Object) this);
        WissenChargeArcaneEnchantment.onHitBlock(result, self);
    }
}