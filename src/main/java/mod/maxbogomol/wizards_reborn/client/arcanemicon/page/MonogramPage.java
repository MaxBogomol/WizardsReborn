package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class MonogramPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/monogram_page.png");
    public String text;
    public Monogram monogram;

    public MonogramPage(String textKey, Monogram monogram) {
        super(BACKGROUND);
        this.text = textKey;
        this.monogram = monogram;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        drawWrappingText(book, gui, I18n.get(text), x + 4, y + 4, 124);

        gui.blit(BACKGROUND, x + 49, y + 126, 128, 20, 30, 30);

        monogram.renderIcon(gui, x + 56, y + 133);

        if (mouseX >= x + 56 && mouseY >= y + 133 && mouseX <= x + 56 + 16 && mouseY <= y + 133 + 16) {
            gui.renderTooltip(Minecraft.getInstance().font, monogram.getComponentList(), Optional.empty(), mouseX, mouseY);
        }
    }
}