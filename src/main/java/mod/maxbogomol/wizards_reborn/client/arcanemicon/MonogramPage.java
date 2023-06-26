package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MonogramPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_monogram_page.png");
    public String text;
    public Monogram monogram;

    public MonogramPage(String textKey, Monogram monogram) {
        super(BACKGROUND);
        this.text = textKey;
        this.monogram = monogram;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui gui, MatrixStack mStack, int x, int y, int mouseX, int mouseY) {
        drawWrappingText(gui, mStack, I18n.format(text), x + 4, y + 4, 124);

        Minecraft.getInstance().getTextureManager().bindTexture(BACKGROUND);
        gui.blit(mStack, x + 49, y + 126, 128, 20, 30, 30);

        monogram.renderArcanemiconIcon(gui, mStack, x + 56, y + 133);
    }
}