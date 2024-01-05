package mod.maxbogomol.wizards_reborn.mixin.client;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LogoRenderer.class)
public abstract class LogoRendererMixin {

    @Final
    @Shadow
    private boolean keepLogoThroughFade;

    @Unique
    private static final ResourceLocation WIZARDS_REBORN_LOGO = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/title/wizards_reborn.png");

    /*@Inject(at = @At("HEAD"), method = "renderLogo*", cancellable = true)
    public void renderLogo(GuiGraphics guiGraphics, int screenWidth, float transparency, int height, CallbackInfo ci) {
        ci.cancel();

        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.keepLogoThroughFade ? 1.0F : transparency);
        int i = screenWidth / 2 - 128;
        guiGraphics.blit(WIZARDS_REBORN_LOGO, i, height, 0.0F, 0.0F, 256, 64, 256, 64);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }*/
}
