package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TitledSpellCharPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_spell_char_page.png");
    public String text, title;
    public Spell spell;

    public TitledSpellCharPage(String textKey, Spell spell) {
        super(BACKGROUND);
        this.text = textKey;
        this.title = textKey + ".title";
        this.spell = spell;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean click(ArcanemiconGui book, int x, int y, int mouseX, int mouseY) {
        if (mouseX >= x + 55 && mouseY >= y + 131 && mouseX <= x + 55 + 18 && mouseY <= y + 131 + 18) {
            ArcanemiconChapters.RESEARCH_MAIN.lastChapter = book.currentChapter;
            book.currentChapter = ArcanemiconChapters.RESEARCH;
            ArcanemiconChapters.RESEARCH_MAIN.createNap(spell);
            Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
            return true;
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        gui.blit(BACKGROUND, x, y, 128, 0, 128, 20);

        String title = I18n.get(this.title);
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 15 - Minecraft.getInstance().font.lineHeight);
        drawWrappingText(book, gui, I18n.get(text), x + 4, y + 24, 124);

        gui.blit(BACKGROUND, x + 55, y + 132, 128, 20, 18, 18);
        if (mouseX >= x + 55 && mouseY >= y + 131 && mouseX <= x + 55 + 18 && mouseY <= y + 131 + 18) {
            gui.blit(BACKGROUND, x + 55, y + 131, 146, 20, 18, 18);
        }
    }
}