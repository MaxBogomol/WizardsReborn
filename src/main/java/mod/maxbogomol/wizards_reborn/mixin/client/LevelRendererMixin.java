package mod.maxbogomol.wizards_reborn.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.entity.ThrownShearsEntity;
import mod.maxbogomol.wizards_reborn.common.spell.charge.ChargeSpell;
import mod.maxbogomol.wizards_reborn.common.spell.charge.ChargeSpellComponent;
import mod.maxbogomol.wizards_reborn.common.spell.ray.RaySpell;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Inject(at = @At("HEAD"), method = "renderEntity")
    public <E extends Entity> void wizards_reborn$render(Entity entity, double camX, double camY, double camZ, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, CallbackInfo ci) {
        if (entity instanceof SpellEntity projectile) {
            if (projectile.getSpell() instanceof RaySpell spell) {
                spell.updatePos(projectile);
            }

            if (projectile.getSpell() instanceof ChargeSpell spell) {
                ChargeSpellComponent spellComponent = spell.getSpellComponent(projectile);
                if (!spellComponent.thrown) {
                    spell.updatePos(projectile);
                }
            }
        }

        if (entity instanceof ThrownShearsEntity projectile) {
            Player player = projectile.getSender();
            if (player != null && projectile.getIsCut()) {
                projectile.setPos(player.position());
            }
        }
    }
}
