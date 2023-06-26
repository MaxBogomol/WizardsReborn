package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
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
    public void render(ArcanemiconGui gui, MatrixStack mStack, int x, int y, int mouseX, int mouseY) {
        Minecraft.getInstance().getTextureManager().bindTexture(BACKGROUND);
        gui.blit(mStack, x, y, 128, 0, 128, 20);

        String title = I18n.format(this.title);
        int titleWidth = Minecraft.getInstance().fontRenderer.getStringWidth(title);
        drawText(gui, mStack, title, x + 64 - titleWidth / 2, y + 15 - Minecraft.getInstance().fontRenderer.FONT_HEIGHT);
        drawWrappingText(gui, mStack, I18n.format(text), x + 4, y + 24, 124);

        Minecraft.getInstance().getTextureManager().bindTexture(spell.getIcon());
        gui.blit(mStack, x + 56, y + 133, 0, 0, 16, 16, 16, 16);
        for (int i = 0; i < spell.getCrystalTypes().size(); i ++) {
            int w = 0;
            if (i >= 4) {
                w = 78;
            }
            Minecraft.getInstance().getTextureManager().bindTexture(spell.getCrystalTypes().get(i).getIcon());
            gui.blit(mStack, x + 3, y + 130 + (i * 9) + w, 0, 0, 8, 8, 8, 8);
        }
    }
}