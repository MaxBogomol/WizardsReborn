package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        gui.blit(BACKGROUND, x, y, 128, 0, 128, 20);

        String title = I18n.get(this.title);
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 15 - Minecraft.getInstance().font.lineHeight);
        drawWrappingText(book, gui, I18n.get(text), x + 4, y + 24, 124);

        gui.blit(BACKGROUND, x + 49, y + 126, 128, 20, 30, 30);
        monogram.renderArcanemiconIcon(book, gui, x + 56, y + 133);

        if (mouseX >= x + 56 && mouseY >= y + 133 && mouseX <= x + 56 + 16 && mouseY <= y + 133 + 16) {
            gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(monogram.getTranslatedName()), mouseX, mouseY);
        }
    }
}