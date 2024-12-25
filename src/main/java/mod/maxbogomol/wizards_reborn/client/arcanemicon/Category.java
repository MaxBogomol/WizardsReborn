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

    public boolean click(ArcanemiconScreen gui, int x, int y, boolean right, int mouseX, int mouseY) {
        int w = 20;
        int xx = x;
        if (!right) x -= 20;
        w += hoveramount * 18;
        if (!right) x -= hoveramount * 18;

        boolean hover = mouseX >= x && mouseY >= y && mouseX <= xx && mouseY <= y + 18;
        if (right)  hover = mouseX >= x && mouseY >= y && mouseX <= x + w && mouseY <= y + 18;
        if (hover) {
            ArcanemiconScreen.historyEntries.clear();
            ArcanemiconScreen.currentHistory = 0;
            gui.changeChapter(chapter);
            Minecraft.getInstance().player.playNotifySound(SoundEvents.UI_BUTTON_CLICK.get(), SoundSource.NEUTRAL, 0.5f, 1.0f);
            return true;
        }
        return false;
    }

    public void draw(ArcanemiconScreen book, GuiGraphics gui, int x, int y, boolean right, int mouseX, int mouseY) {
        int w = 20;
        int xx = x;
        if (!right) x -= 20;
        w += hoveramount * 18;
        if (!right) x -= hoveramount * 18;

        boolean hover = mouseX >= x && mouseY >= y && mouseX <= xx && mouseY <= y + 18;
        if (right)  hover = mouseX >= x && mouseY >= y && mouseX <= x + w && mouseY <= y + 18;
        if (hover && hoveramount < 1) hoveramount += Minecraft.getInstance().getDeltaFrameTime();
        else if (!hover && hoveramount > 0) hoveramount -= Minecraft.getInstance().getDeltaFrameTime();
        hoveramount = Mth.clamp(hoveramount, 0, 1);

        if (right) {
            x -= 18;
            x += hoveramount * 18;
        }

        gui.blit(ArcanemiconScreen.BACKGROUND, x, y, 312, right ? 0 : 18, 39, 18, 512, 512);
        gui.renderItem(icon, x + (right ? 21 : 2), y + 1);
        gui.renderItemDecorations(Minecraft.getInstance().font, icon, x + (right ? 21 : 2), y + 1, null);
    }

    public void drawTooltip(ArcanemiconScreen book, GuiGraphics gui, int x, int y, boolean right, int mouseX, int mouseY) {
        int w = 20;
        int xx = x;
        if (!right) x -= 20;
        w += hoveramount * 18;
        if (!right) x -= hoveramount * 18;

        boolean hover = mouseX >= x && mouseY >= y && mouseX <= xx && mouseY <= y + 18;
        if (right)  hover = mouseX >= x && mouseY >= y && mouseX <= x + w && mouseY <= y + 18;
        if (hover) book.renderTooltip(gui, Component.translatable("wizards_reborn.arcanemicon.category." + key), mouseX, mouseY);
    }
}