package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IndexPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_index_page.png");
    IndexEntry[] entries;

    public IndexPage(IndexEntry... pages) {
        super(BACKGROUND);
        this.entries = pages;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean click(ArcanemiconGui gui, int x, int y, int mouseX, int mouseY) {
        for (int i = 0; i < entries.length; i ++) if (entries[i].isUnlocked()) {
            if (mouseX >= x + 2 && mouseX <= x + 124 && mouseY >= y + 8 + i * 20 && mouseY <= y + 26 + i * 20) {
                gui.changeChapter(entries[i].chapter);
                Minecraft.getInstance().player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                return true;
            }
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui gui, MatrixStack mStack, int x, int y, int mouseX, int mouseY) {
        Minecraft.getInstance().getTextureManager().bindTexture(BACKGROUND);
        for (int i = 0; i < entries.length; i ++) {
            gui.blit(mStack, x, y + 7 + (i * 20), 128, 20, 128, 18);
        }

        for (int i = 0; i < entries.length; i ++) {
            if (entries[i].isUnlocked()) {
                Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(entries[i].icon, x + 3, y + 8 + i * 20);
                drawText(gui, mStack, I18n.format(entries[i].chapter.titleKey), x + 24, y + 20 + i * 20 - Minecraft.getInstance().fontRenderer.FONT_HEIGHT);
            } else {
                Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_unknown.png"));
                gui.blit(mStack, x + 3, y + 8 + i * 20, 0, 0, 16, 16, 16, 16);
                drawText(gui, mStack, I18n.format("wizards_reborn.arcanemicon.unknown"), x + 24, y + 20 + i * 20 - Minecraft.getInstance().fontRenderer.FONT_HEIGHT);
            }
        }
    }
}
