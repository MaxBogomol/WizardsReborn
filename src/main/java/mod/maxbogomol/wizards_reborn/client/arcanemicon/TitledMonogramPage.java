package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TitledMonogramPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_monogram_page.png");
    public String text, title;
    public Monogram monogram;

    public TitledMonogramPage(String textKey, Monogram monogram) {
        super(BACKGROUND);
        this.text = textKey;
        this.title = textKey + ".title";
        this.monogram = monogram;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui gui, MatrixStack mStack, int x, int y, int mouseX, int mouseY) {
        Minecraft.getInstance().getTextureManager().bindTexture(BACKGROUND);
        gui.blit(mStack, x, y, 128, 0, 128, 20);

        String title = I18n.format(this.title);
        int titleWidth = Minecraft.getInstance().fontRenderer.getStringWidth(title);
        drawText(gui, mStack, title, x + 64 - titleWidth / 2, y + 15 - Minecraft.getInstance().fontRenderer.FONT_HEIGHT);
        drawWrappingText(gui, mStack, I18n.format(text), x + 4, y + 24, 124);

        Minecraft.getInstance().getTextureManager().bindTexture(BACKGROUND);
        gui.blit(mStack, x + 49, y + 126, 128, 20, 30, 30);
        monogram.renderArcanemiconIcon(gui, mStack, x + 56, y + 133);
    }
}