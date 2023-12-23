package mod.maxbogomol.wizards_reborn.mixin.client;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Fox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoxRenderer.class)
public abstract class FoxRendererMixin {
    @Unique
    private static final ResourceLocation FOX_FOXPLANE_LOCATION = new ResourceLocation(WizardsReborn.MOD_ID,"textures/entity/fox/foxplane.png");
    @Unique
    private static final ResourceLocation FOX_FOXPLANE_SLEEP_LOCATION = new ResourceLocation(WizardsReborn.MOD_ID,"textures/entity/fox/foxplane_sleep.png");

    @Inject(at = @At("HEAD"), method = "getTextureLocation", cancellable = true)
    public void getTextureLocation(Fox entity, CallbackInfoReturnable<ResourceLocation> ci) {
        String s = ChatFormatting.stripFormatting(entity.getName().getString());
        if ("FoxPlane".equals(s)) {
            if (entity.isSleeping()) {
                ci.setReturnValue(FOX_FOXPLANE_SLEEP_LOCATION);
            }
            ci.setReturnValue(FOX_FOXPLANE_LOCATION);
        }
    }
}
