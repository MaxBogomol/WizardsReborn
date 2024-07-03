package mod.maxbogomol.wizards_reborn.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.spell.charge.ChargeSpell;
import mod.maxbogomol.wizards_reborn.common.spell.ray.RaySpell;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Inject(at = @At("HEAD"), method = "renderEntity")
    public <E extends Entity> void wizards_reborn$render(Entity pEntity, double pCamX, double pCamY, double pCamZ, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, CallbackInfo ci) {
        if (pEntity instanceof SpellProjectileEntity projectile) {
            if (projectile.getSpell() instanceof RaySpell spell) {
                CompoundTag spellData = projectile.getSpellData();
                if (spellData.getInt("tick_left") <= 0) {
                    spell.updatePos(projectile);
                }
            }

            if (projectile.getSpell() instanceof ChargeSpell spell) {
                CompoundTag spellData = projectile.getSpellData();
                if (!spellData.getBoolean("throw")) {
                    spell.updatePos(projectile);
                }
            }
        }
    }

    /*@Inject(at = @At("HEAD"), method = "addParticleInternal", cancellable = true)
    public void addParticleInternal(ParticleOptions pOptions, boolean pForce, boolean pDecreased, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, CallbackInfoReturnable<Particle> ci) {
        if (pOptions instanceof GenericParticleData) {
            //Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
            //if (camera.getPosition().distanceToSqr(pX, pY, pZ) > 5120.0D) {
                ci.setReturnValue(null);
            //}
        }
    }*/
}
