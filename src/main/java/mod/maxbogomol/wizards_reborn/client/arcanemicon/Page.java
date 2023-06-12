package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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
    public static void drawItem(ArcanemiconGui gui, MatrixStack mStack, ItemStack stack, int x, int y, int mouseX, int mouseY) {
        ItemRenderer ir = Minecraft.getInstance().getItemRenderer();
        ir.renderItemAndEffectIntoGUI(stack, x, y);
        ir.renderItemOverlayIntoGUI(Minecraft.getInstance().fontRenderer, stack, x, y, null);
        if (mouseX >= x && mouseY >= y && mouseX <= x + 16 && mouseY <= y + 16) {
            gui.renderTooltip(mStack, stack, mouseX, mouseY);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawText(ArcanemiconGui gui, MatrixStack mStack, String text, int x, int y) {
        FontRenderer font = Minecraft.getInstance().fontRenderer;
        font.drawString(mStack, text, x + 1, y + 1, ColorUtils.packColor(255, 220, 199, 182));
        font.drawString(mStack, text, x, y, ColorUtils.packColor(255, 56, 33, 39));
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawWrappingText(ArcanemiconGui gui, MatrixStack mStack, String text, int x, int y, int w) {
        FontRenderer font = Minecraft.getInstance().fontRenderer;
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        String line = "";
        for (String s : words) {
            if (font.getStringWidth(line) + font.getStringWidth(s) > w) {
                lines.add(line);
                line = s + " ";
            }
            else line += s + " ";
        }
        if (!line.isEmpty()) lines.add(line);
        for (int i = 0; i < lines.size(); i ++) {
            drawText(gui, mStack, lines.get(i), x, y + i * (font.FONT_HEIGHT + 1));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void fullRender(ArcanemiconGui gui, MatrixStack mStack, int x, int y, int mouseX, int mouseY) {
        Minecraft.getInstance().getTextureManager().bindTexture(background);
        renderBackground(gui, mStack, x, y, mouseX, mouseY);
        render(gui, mStack, x, y, mouseX, mouseY);
        renderIngredients(gui, mStack, x, y, mouseX, mouseY);
    }

    @OnlyIn(Dist.CLIENT)
    public void renderBackground(ArcanemiconGui gui, MatrixStack mStack, int x, int y, int mouseX, int mouseY) {
        Minecraft.getInstance().getTextureManager().bindTexture(background);
        gui.blit(mStack, x, y, 0, 0, 128, 160);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean click(ArcanemiconGui gui, int x, int y, int mouseX, int mouseY) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui gui, MatrixStack mStack, int x, int y, int mouseX, int mouseY) {}

    @OnlyIn(Dist.CLIENT)
    public void renderIngredients(ArcanemiconGui gui, MatrixStack mStack, int x, int y, int mouseX, int mouseY) {}
}