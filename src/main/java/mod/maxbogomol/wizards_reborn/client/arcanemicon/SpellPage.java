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

public class SpellPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_spell_page.png");
    public String text;
    public Spell spell;

    public SpellPage(String textKey, Spell spell) {
        super(BACKGROUND);
        this.text = textKey;
        this.spell = spell;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        drawWrappingText(book, gui, I18n.get(text), x + 4, y + 4, 124);

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