package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconChapters;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpellCharPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/spell_char_page.png");
    public String text;
    public Spell spell;

    public SpellCharPage(String textKey, Spell spell) {
        super(BACKGROUND);
        this.text = textKey;
        this.spell = spell;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean click(ArcanemiconGui book, int x, int y, int mouseX, int mouseY) {
        if (!KnowledgeUtil.isSpell(Minecraft.getInstance().player, spell)) {
            int points = KnowledgeUtil.getKnowledgePoints(Minecraft.getInstance().player) - KnowledgeUtil.getSpellPoints(Minecraft.getInstance().player);
            if (points < 0) {
                points = 0;
            }

            if (spell.getPoints() <= points) {
                if (mouseX >= x + 55 && mouseY >= y + 131 && mouseX <= x + 55 + 18 && mouseY <= y + 131 + 18) {
                    if (spell.getResearch() != null) {
                        ArcanemiconChapters.RESEARCH_MAIN.lastChapter = book.currentChapter;
                        ArcanemiconGui.currentChapter = ArcanemiconChapters.RESEARCH;
                        ArcanemiconChapters.RESEARCH_MAIN.createNap(spell);
                        Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        if (I18n.exists(text)) drawWrappingText(book, gui, I18n.get(text), x + 4, y + 4, 124);

        if (!KnowledgeUtil.isSpell(Minecraft.getInstance().player, spell)) {
            int points = KnowledgeUtil.getKnowledgePoints(Minecraft.getInstance().player) - KnowledgeUtil.getSpellPoints(Minecraft.getInstance().player);
            if (points < 0) {
                points = 0;
            }

            Font font = Minecraft.getInstance().font;
            String stringSize = String.valueOf(spell.getPoints()) + "/" + String.valueOf(points);
            drawText(book, gui, stringSize, x + 25 - (font.width(stringSize) / 2), y + 138);

            gui.blit(BACKGROUND, x + 55, y + 131, 128, 20, 18, 18);
            if (spell.getPoints() <= points) {
                if (mouseX >= x + 55 && mouseY >= y + 131 && mouseX <= x + 55 + 18 && mouseY <= y + 131 + 18) {
                    gui.blit(BACKGROUND, x + 55, y + 131, 146, 20, 18, 18);
                    gui.renderTooltip(Minecraft.getInstance().font, Component.translatable("wizards_reborn.arcanemicon.research"), mouseX, mouseY);
                }
            }
        }
    }
}