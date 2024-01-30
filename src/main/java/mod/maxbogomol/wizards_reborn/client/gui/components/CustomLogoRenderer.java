package mod.maxbogomol.wizards_reborn.client.gui.components;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CustomLogoRenderer extends LogoRenderer {
    private static final ResourceLocation WIZARDS_REBORN_LOGO = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/title/wizards_reborn.png");

    public CustomLogoRenderer(boolean pKeepLogoThroughFade) {
        super(pKeepLogoThroughFade);
    }

    @Override
    public void renderLogo(GuiGraphics pGuiGraphics, int pScreenWidth, float pTransparency) {
        this.renderLogo(pGuiGraphics, pScreenWidth, pTransparency, 30);
    }

    @Override
    public void renderLogo(GuiGraphics guiGraphics, int screenWidth, float transparency, int height) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.keepLogoThroughFade ? 1.0F : transparency);
        int i = screenWidth / 2 - 128;
        guiGraphics.blit(WIZARDS_REBORN_LOGO, i, height, 0.0F, 0.0F, 256, 64, 256, 64);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
