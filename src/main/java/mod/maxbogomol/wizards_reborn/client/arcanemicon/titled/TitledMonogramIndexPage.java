package mod.maxbogomol.wizards_reborn.client.arcanemicon.titled;

import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.MonogramIndexEntry;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.page.MonogramIndexPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TitledMonogramIndexPage extends MonogramIndexPage {
    public String title;

    public TitledMonogramIndexPage(String textKey, MonogramIndexEntry... pages) {
        super(pages);
        this.title = textKey + ".title";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean click(ArcanemiconScreen gui, int x, int y, int mouseX, int mouseY) {
        return super.click(gui, x, y + 16, mouseX, mouseY);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        gui.blit(BACKGROUND, x, y, 128, 0, 128, 20);
        String title = I18n.get(this.title);
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 15 - Minecraft.getInstance().font.lineHeight);

        super.render(book, gui, x, y + 16, mouseX, mouseY);
    }
}
