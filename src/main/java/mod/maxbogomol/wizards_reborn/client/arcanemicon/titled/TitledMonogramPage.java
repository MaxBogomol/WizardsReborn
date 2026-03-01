package mod.maxbogomol.wizards_reborn.client.arcanemicon.titled;

import com.mojang.blaze3d.systems.RenderSystem;
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

public class TitledMonogramPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/monogram_page.png");
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
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        gui.blit(BACKGROUND, x, y, 128, 0, 128, 20);

        String title = I18n.get(this.title);
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 15 - Minecraft.getInstance().font.lineHeight);
        drawWrappingText(book, gui, I18n.get(text), x + 4, y + 24, 124);

        gui.blit(BACKGROUND, x + 49, y + 126, 128, 20, 30, 30);
        RenderSystem.disableDepthTest();
        monogram.renderIcon(gui, x + 56, y + 133);
        RenderSystem.enableDepthTest();

        if (mouseX >= x + 56 && mouseY >= y + 133 && mouseX < x + 56 + 16 && mouseY < y + 133 + 16) {
            gui.renderTooltip(Minecraft.getInstance().font, monogram.getComponentList(), Optional.empty(), mouseX, mouseY);
        }
    }
}