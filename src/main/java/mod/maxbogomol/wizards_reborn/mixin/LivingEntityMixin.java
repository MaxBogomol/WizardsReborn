package mod.maxbogomol.wizards_reborn.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "isFallFlying", at = @At("HEAD"))
    public void isFallFlying(CallbackInfoReturnable<Boolean> ci) {
        //if ((LivingEntity)(Object)this instanceof Player p) {
        //    ci.setReturnValue(true);
        //}
    }
}