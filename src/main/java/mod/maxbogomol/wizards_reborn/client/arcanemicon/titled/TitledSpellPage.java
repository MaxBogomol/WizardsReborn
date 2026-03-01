package mod.maxbogomol.wizards_reborn.client.arcanemicon.titled;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TitledSpellPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/spell_page.png");
    public String text, title;
    public Spell spell;

    public TitledSpellPage(String textKey, Spell spell) {
        super(BACKGROUND);
        this.text = textKey;
        this.title = textKey + ".title";
        this.spell = spell;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        gui.blit(BACKGROUND, x, y, 128, 0, 128, 20);

        String title = I18n.get(this.title);
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 15 - Minecraft.getInstance().font.lineHeight);
        drawWrappingText(book, gui, I18n.get(text), x + 4, y + 24, 124);

        if (KnowledgeUtil.isSpell(Minecraft.getInstance().player, spell)) {
            gui.blit(spell.getIcon(), x + 56, y + 133, 0, 0, 16, 16, 16, 16);
            if (mouseX >= x + 56 && mouseY >= y + 133 && mouseX < x + 56 + 16 && mouseY < y + 133 + 16) {
                gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(spell.getTranslatedName()), mouseX, mouseY);
            }
        } else {
            gui.blit(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/unknown.png"), x + 56, y + 133, 0, 0, 16, 16, 16, 16);
        }

        for (int i = 0; i < spell.getCrystalTypes().size(); i ++) {
            int w = 0;
            if (i >= 4) {
                w = 78;
            }
            gui.blit(spell.getCrystalTypes().get(i).getIcon(), x + 3 + (i * 9) + w, y + 130, 0, 0, 8, 8, 8, 8);
        }

        for (int i = 0; i < spell.getCrystalTypes().size(); i ++) {
            int w = 0;
            if (i >= 4) {
                w = 78;
            }
            if (mouseX >= x + 3 + (i * 9) + w && mouseY >= y + 130 && mouseX < x + 3 + (i * 9) + w + 8 && mouseY < y + 130 + 8) {
                gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(spell.getCrystalTypes().get(i).getTranslatedName()), mouseX, mouseY);
            }
        }
    }
}