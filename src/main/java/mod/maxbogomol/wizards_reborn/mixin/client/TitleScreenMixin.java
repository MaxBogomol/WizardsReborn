package mod.maxbogomol.wizards_reborn.mixin.client;

import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {

    @Inject(at = @At("RETURN"), method = "render")
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci) {
        if (WizardsRebornClient.optifinePresent) {
            TitleScreen self = (TitleScreen) ((Object) this);
            Font font = Minecraft.getInstance().font;
            String string = Component.translatable("gui.wizards_reborn.menu.optifine.0").getString();
            pGuiGraphics.drawString(font, string, (self.width - font.width(string)) / 2, 1, 16777215);
            string = Component.translatable("gui.wizards_reborn.menu.optifine.1").getString();
            pGuiGraphics.drawString(font, string, (self.width - font.width(string)) / 2, 2 + font.lineHeight, 16777215);
        }
    }
}
