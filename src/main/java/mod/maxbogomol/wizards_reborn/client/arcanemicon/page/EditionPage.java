package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.IndexEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EditionPage extends IndexPage {

    public EditionPage(IndexEntry... pages) {
        super(pages);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        super.render(book, gui, x, y, mouseX, mouseY);

        int edition = WizardsReborn.VERSION_NUMBER;
        String title = I18n.get("wizards_reborn.arcanemicon.edition", edition);
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 145 - Minecraft.getInstance().font.lineHeight);
    }
}
