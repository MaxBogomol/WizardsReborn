package mod.maxbogomol.wizards_reborn.mixin.common;

import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.FireworkJumpArcaneEnchantment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireworkRocketEntity.class)
public abstract class FireworkRocketEntityMixin {

    @Unique
    public LivingEntity wizards_reborn$livingEntity;

    @Inject(method = "dealExplosionDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    public void wizards_reborn$dealExplosionDamage(CallbackInfo ci) {
        FireworkRocketEntity self = (FireworkRocketEntity) ((Object) this);
        LivingEntity livingEntity = wizards_reborn$livingEntity;

        if (livingEntity != null) {
            FireworkJumpArcaneEnchantment.onHit(self, wizards_reborn$livingEntity);
        }
    }

    @ModifyVariable(method = "dealExplosionDamage", at = @At("STORE"))
    public LivingEntity wizards_reborn$dealExplosionDamage(LivingEntity livingEntity) {
        FireworkRocketEntity self = (FireworkRocketEntity) ((Object) this);
        this.wizards_reborn$livingEntity = livingEntity;
        FireworkJumpArcaneEnchantment.onHitFirst(self, wizards_reborn$livingEntity);
        return livingEntity;
    }
}
