package mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe;

import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RecipePage extends Page {
    public RecipePage(ResourceLocation background) {
        super(background);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isChanged(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void renderChanged(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        if (isChanged(book, gui, x, y, mouseX, mouseY)) {
            renderChangedText(book, gui, x, y, mouseX, mouseY);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void renderChangedText(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        String title = I18n.get(getChangedText());
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 15 - Minecraft.getInstance().font.lineHeight);
    }

    public String getChangedText() {
        return "wizards_reborn.arcanemicon.recipe_changed";
    }
}