package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;

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
            gui.currentPage = 0;
            gui.currentChapter = chapter;
            Minecraft.getInstance().player.playSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            return true;
        }
        return false;
    }

    public void draw(ArcanemiconGui gui, MatrixStack mStack, int x, int y, boolean right, int mouseX, int mouseY) {
        int w = 20;
        if (!right) x -= 20;
        w += hoveramount * 18;
        if (!right) x -= hoveramount * 18;

        boolean hover = mouseX >= x && mouseY >= y && mouseX <= x + w && mouseY <= y + 18;
        if (hover && hoveramount < 1) hoveramount += Minecraft.getInstance().getRenderPartialTicks() / 8;
        else if (!hover && hoveramount > 0) hoveramount -= Minecraft.getInstance().getRenderPartialTicks() / 8;
        hoveramount = MathHelper.clamp(hoveramount, 0, 1);

        if (right) {
            x -= 18;
            x += hoveramount * 18;
        }

        Minecraft.getInstance().getTextureManager().bindTexture(ArcanemiconGui.BACKGROUND);
        gui.blit(mStack, x, y, 312, right ? 0 : 18, 39, 18, 512, 512);
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(icon, x + (right ? 21 : 2), y + 1);
    }

    public void drawTooltip(ArcanemiconGui gui, MatrixStack mStack, int x, int y, boolean right, int mouseX, int mouseY) {
        int w = 20;
        if (!right) x -= 20;
        w += hoveramount * 18;
        if (!right) x -= hoveramount * 18;

        boolean hover = mouseX >= x && mouseY >= y && mouseX <= x + w && mouseY <= y + 18;
        if (hover) gui.renderTooltip(mStack, new TranslationTextComponent("wizards_reborn.arcanemicon.category." + key), mouseX, mouseY);
    }
}