package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class TitledSpellPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_spell_page.png");
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
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        gui.blit(BACKGROUND, x, y, 128, 0, 128, 20);

        String title = I18n.get(this.title);
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 15 - Minecraft.getInstance().font.lineHeight);
        drawWrappingText(book, gui, I18n.get(text), x + 4, y + 24, 124);

        gui.blit(spell.getIcon(), x + 56, y + 133, 0, 0, 16, 16, 16, 16);
        for (int i = 0; i < spell.getCrystalTypes().size(); i ++) {
            int w = 0;
            if (i >= 4) {
                w = 78;
            }
            gui.blit(spell.getCrystalTypes().get(i).getIcon(), x + 3, y + 130 + (i * 9) + w, 0, 0, 8, 8, 8, 8);
        }
    }
}