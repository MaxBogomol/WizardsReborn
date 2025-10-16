package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CensoredTextPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/blank_page.png");
    public String text, censoredText;

    public CensoredTextPage(String textKey) {
        super(BACKGROUND);
        this.text = textKey;
        this.censoredText = textKey + ".censored";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        if (WizardsRebornClientConfig.ARCANEMICON_CENSORSHIP.get()) {
            drawWrappingText(book, gui, I18n.get(censoredText), x + 4, y + 4, 124);
        } else {
            drawWrappingText(book, gui, I18n.get(text), x + 4, y + 4, 124);
        }
    }
}