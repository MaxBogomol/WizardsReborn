package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public abstract class Page {
    public ResourceLocation background;

    public Page(ResourceLocation background) {
        this.background = background;
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawItem(ArcanemiconGui book, GuiGraphics gui, ItemStack stack, int x, int y, int mouseX, int mouseY) {
        gui.renderItem(stack, x, y);
        gui.renderItemDecorations(Minecraft.getInstance().font, stack, x, y, null);
        if (mouseX >= x && mouseY >= y && mouseX <= x + 16 && mouseY <= y + 16) {
            gui.renderTooltip(Minecraft.getInstance().font, stack, mouseX, mouseY);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawText(ArcanemiconGui book, GuiGraphics gui, String text, int x, int y) {
        Font font = Minecraft.getInstance().font;
        gui.drawString(font, text, x + 1, y + 1, ColorUtils.packColor(255, 220, 199, 182), false);
        gui.drawString(font, text, x, y, ColorUtils.packColor(255, 56, 33, 39), false);
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawWrappingText(ArcanemiconGui book, GuiGraphics gui, String text, int x, int y, int w) {
        Font font = Minecraft.getInstance().font;
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        String line = "";
        for (String s : words) {
            if (font.width(line) + font.width(s) > w) {
                lines.add(line);
                line = s + " ";
            }
            else line += s + " ";
        }
        if (!line.isEmpty()) lines.add(line);
        for (int i = 0; i < lines.size(); i ++) {
            drawText(book, gui, lines.get(i), x, y + i * (font.lineHeight + 1));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void fullRender(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        renderBackground(book, gui, x, y, mouseX, mouseY);
        render(book, gui, x, y, mouseX, mouseY);
        renderIngredients(book, gui, x, y, mouseX, mouseY);
    }

    @OnlyIn(Dist.CLIENT)
    public void renderBackground(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        gui.blit(background, x, y, 0, 0, 128, 160);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean click(ArcanemiconGui book, int x, int y, int mouseX, int mouseY) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {}

    @OnlyIn(Dist.CLIENT)
    public void renderIngredients(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {}
}