package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TitledMonogramRecipesPage extends MonogramRecipesPage {
    public String title;

    public TitledMonogramRecipesPage(String textKey, Monogram monogram) {
        super(monogram);
        this.title = textKey + ".title";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        gui.blit(BACKGROUND, x, y, 128, 0, 128, 20);
        String title = I18n.get(this.title);
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 15 - Minecraft.getInstance().font.lineHeight);

        RenderSystem.setShaderTexture(0, BACKGROUND);
        super.render(book, gui, x, y + 16, mouseX, mouseY);
    }
}
