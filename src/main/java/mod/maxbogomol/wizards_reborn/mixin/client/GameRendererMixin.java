package mod.maxbogomol.wizards_reborn.mixin.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Unique
    public GuiGraphics wizards_reborn$guiGraphics;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 0), method = "render")
    public void wizards_reborn$renderScreenOverlay(float partialTicks, long nanoTime, boolean renderLevel, CallbackInfo ci) {
        //RainFogRenderHandler.renderOverlay(wizards_reborn$guiGraphics);
    }

    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 0)
    public GuiGraphics wizards_reborn$getGui(GuiGraphics guiGraphics) {
        wizards_reborn$guiGraphics = guiGraphics;
        return guiGraphics;
    }
}
