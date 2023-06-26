package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramRecipe;
import mod.maxbogomol.wizards_reborn.api.monogram.Monograms;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MonogramRecipesPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_monogram_page.png");
    public Monogram monogram;

    public MonogramRecipesPage(Monogram monogram) {
        super(BACKGROUND);
        this.monogram = monogram;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui gui, MatrixStack mStack, int x, int y, int mouseX, int mouseY) {
        int i = 0;
        int ii = 0;
        boolean right = false;

        for (MonogramRecipe recipe : Monograms.getRecipes().values()) {
            if (recipe.getInputs().contains(monogram)) {
                Monogram extraMonogram = recipe.getInputs().get(1);
                if (extraMonogram.equals(monogram)) {
                    extraMonogram = recipe.getInputs().get(0);
                }

                Minecraft.getInstance().getTextureManager().bindTexture(BACKGROUND);
                gui.blit(mStack, x + (right ? 69 : 9), y + 7 + (ii * 12), 158, 20, 50, 10);

                monogram.renderArcanemiconMiniIcon(gui, mStack, x + (right? 69 : 9) + 1, y + 7 + (ii * 12) + 1);
                extraMonogram.renderArcanemiconMiniIcon(gui, mStack, x + (right ? 69 : 9) + 21, y + 7 + (ii * 12) + 1);
                recipe.getOutput().renderArcanemiconMiniIcon(gui, mStack, x + (right ? 69 : 9) + 41, y + 7 + (ii * 12) + 1);

                i++;
                if (i % 2 == 0) {
                    ii++;
                    right = false;
                } else {
                    right = true;
                }
            }
        }

        if (i % 2 != 0) {
            ii++;
        }

        MonogramRecipe recipe = Monograms.getRecipe(monogram.getId());
        if (recipe != null) {
            Minecraft.getInstance().getTextureManager().bindTexture(BACKGROUND);
            gui.blit(mStack, x + 39, y + 7 + (ii * 12), 158, 20, 50, 10);

            recipe.getInputs().get(0).renderArcanemiconMiniIcon(gui, mStack, x + 39 + 1, y + 7 + (ii * 12) + 1);
            recipe.getInputs().get(1).renderArcanemiconMiniIcon(gui, mStack, x + 39 + 21, y + 7 + (ii * 12) + 1);
            recipe.getOutput().renderArcanemiconMiniIcon(gui, mStack, x + 39 + 41, y + 7 + (ii * 12) + 1);
        }
    }
}