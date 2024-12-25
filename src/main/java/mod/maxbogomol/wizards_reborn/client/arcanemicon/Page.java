package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornKeyMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
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
    public static void drawItem(ArcanemiconScreen book, GuiGraphics gui, ItemStack stack, int x, int y, int mouseX, int mouseY) {
        gui.renderItem(stack, x, y);
        gui.renderItemDecorations(Minecraft.getInstance().font, stack, x, y, null);
        if (mouseX >= x && mouseY >= y && mouseX <= x + 16 && mouseY <= y + 16) {
            gui.renderTooltip(Minecraft.getInstance().font, stack, mouseX, mouseY);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawText(ArcanemiconScreen book, GuiGraphics gui, String text, int x, int y) {
        Font font = Minecraft.getInstance().font;
        gui.drawString(font, text, x + 1, y + 1, ArcanemiconScreen.TEXT_SHADOW_COLOR_INT, false);
        gui.drawString(font, text, x, y, ArcanemiconScreen.TEXT_COLOR_INT, false);
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawWrappingText(ArcanemiconScreen book, GuiGraphics gui, String text, int x, int y, int w) {
        Font font = Minecraft.getInstance().font;
        List<String> lines = new ArrayList<>();
        if (text.contains("wizards_reborn:wandMenu")) {
            text = text.replace("wizards_reborn:wandMenu", WizardsRebornKeyMappings.OPEN_SELECTION_MENU.getTranslatedKeyMessage().getString());
        }
        if (text.contains("wizards_reborn:bagMenu")) {
            text = text.replace("wizards_reborn:bagMenu", WizardsRebornKeyMappings.OPEN_BAG_MENU.getTranslatedKeyMessage().getString());
        }
        if (text.contains("wizards_reborn:nextSpell")) {
            text = text.replace("wizards_reborn:nextSpell", WizardsRebornKeyMappings.NEXT_SPELL.getTranslatedKeyMessage().getString());
        }
        if (text.contains("wizards_reborn:previousSpell")) {
            text = text.replace("wizards_reborn:previousSpell", WizardsRebornKeyMappings.PREVIOUS_SPELL.getTranslatedKeyMessage().getString());
        }
        if (text.contains("wizards_reborn:spellSetsToggle")) {
            text = text.replace("wizards_reborn:spellSetsToggle", WizardsRebornKeyMappings.SPELL_SETS_TOGGLE.getTranslatedKeyMessage().getString());
        }
        if (text.contains("minecraft:sneak")) {
            text = text.replace("minecraft:sneak", Minecraft.getInstance().options.keyShift.getTranslatedKeyMessage().getString());
        }
        String[] words = text.split(" ");
        String line = "";
        for (String s : words) {
            if (s.equals("\n")) {
                lines.add(line);
                line = "";
            } else if (font.width(line) + font.width(s) > w) {
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
    public void tick(ArcanemiconScreen book) {

    }

    @OnlyIn(Dist.CLIENT)
    public void fullRender(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        renderBackground(book, gui, x, y, mouseX, mouseY);
        render(book, gui, x, y, mouseX, mouseY);
        renderIngredients(book, gui, x, y, mouseX, mouseY);
    }

    @OnlyIn(Dist.CLIENT)
    public void renderBackground(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        gui.blit(background, x, y, 0, 0, 128, 160);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean click(ArcanemiconScreen book, int x, int y, int mouseX, int mouseY) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean mouseScrolled(ArcanemiconScreen book, int x, int y, int mouseX, int mouseY, int delta) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {}

    @OnlyIn(Dist.CLIENT)
    public void renderIngredients(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {}
}