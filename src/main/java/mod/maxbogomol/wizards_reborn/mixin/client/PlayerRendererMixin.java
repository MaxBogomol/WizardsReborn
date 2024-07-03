package mod.maxbogomol.wizards_reborn.mixin.client;

import mod.maxbogomol.wizards_reborn.client.render.entity.layer.ExtraLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin<T extends LivingEntity>  {

    @Inject(at = @At("RETURN"), method = "<init>")
    public void wizards_reborn$setupLayers(EntityRendererProvider.Context pContext, boolean pUseSlimModel, CallbackInfo ci) {
        PlayerRenderer self = (PlayerRenderer) ((Object) this);
        self.addLayer(new ExtraLayer<>(self));
    }
}
