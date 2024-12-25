package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

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

public class SpellPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/spell_page.png");
    public String text;
    public Spell spell;

    public SpellPage(String textKey, Spell spell) {
        super(BACKGROUND);
        this.text = textKey;
        this.spell = spell;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        drawWrappingText(book, gui, I18n.get(text), x + 4, y + 4, 124);

        if (KnowledgeUtil.isSpell(Minecraft.getInstance().player, spell)) {
            gui.blit(spell.getIcon(), x + 56, y + 133, 0, 0, 16, 16, 16, 16);
            if (mouseX >= x + 56 && mouseY >= y + 133 && mouseX <= x + 56 + 16 && mouseY <= y + 133 + 16) {
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
            if (mouseX >= x + 3 + (i * 9) + w && mouseY >= y + 130 && mouseX <= x + 3 + (i * 9) + w + 8 && mouseY <= y + 130 + 8) {
                gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(spell.getCrystalTypes().get(i).getTranslatedName()), mouseX, mouseY);
            }
        }
    }
}