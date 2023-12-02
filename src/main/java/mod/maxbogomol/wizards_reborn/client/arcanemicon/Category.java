package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class Category {
    public ItemStack icon;
    public String key;
    public Chapter chapter;
    public float hoveramount = 0;

    public Category(String name, ItemStack icon, Chapter chapter) {
        this.icon = icon;
        this.key = name;
        this.chapter = chapter;
    }

    protected void reset() {
        hoveramount = 0;
    }

    public boolean click(ArcanemiconGui gui, int x, int y, boolean right, int mouseX, int mouseY) {
        int w = 20;
        if (!right) x -= 20;
        w += hoveramount * 18;
        if (!right) x -= hoveramount * 18;

        boolean hover = mouseX >= x && mouseY >= y && mouseX <= x + w && mouseY <= y + 18;
        if (hover) {
            ArcanemiconGui.historyEntries.clear();
            ArcanemiconGui.currentHistory = 0;
            gui.changeChapter(chapter);
            Minecraft.getInstance().player.playNotifySound(SoundEvents.UI_BUTTON_CLICK.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
            return true;
        }
        return false;
    }

    public void draw(ArcanemiconGui book, GuiGraphics gui, int x, int y, boolean right, int mouseX, int mouseY) {
        int w = 20;
        if (!right) x -= 20;
        w += hoveramount * 18;
        if (!right) x -= hoveramount * 18;

        boolean hover = mouseX >= x && mouseY >= y && mouseX <= x + w && mouseY <= y + 18;
        if (hover && hoveramount < 1) hoveramount += Minecraft.getInstance().getFrameTime() / 8;
        else if (!hover && hoveramount > 0) hoveramount -= Minecraft.getInstance().getFrameTime() / 8;
        hoveramount = Mth.clamp(hoveramount, 0, 1);

        if (right) {
            x -= 18;
            x += hoveramount * 18;
        }

        gui.blit(ArcanemiconGui.BACKGROUND, x, y, 312, right ? 0 : 18, 39, 18, 512, 512);
        gui.renderItem(icon, x + (right ? 21 : 2), y + 1);
        gui.renderItemDecorations(Minecraft.getInstance().font, icon, x + (right ? 21 : 2), y + 1, null);
    }

    public void drawTooltip(ArcanemiconGui book, GuiGraphics gui, int x, int y, boolean right, int mouseX, int mouseY) {
        int w = 20;
        if (!right) x -= 20;
        w += hoveramount * 18;
        if (!right) x -= hoveramount * 18;

        boolean hover = mouseX >= x && mouseY >= y && mouseX <= x + w && mouseY <= y + 18;
        if (hover) book.renderTooltip(gui, Component.translatable("wizards_reborn.arcanemicon.category." + key), mouseX, mouseY);
    }
}