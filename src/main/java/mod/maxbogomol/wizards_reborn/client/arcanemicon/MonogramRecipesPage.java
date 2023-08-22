package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramRecipe;
import mod.maxbogomol.wizards_reborn.api.monogram.Monograms;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
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
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        int i = 0;
        int ii = 0;
        boolean right = false;

        for (MonogramRecipe recipe : Monograms.getRecipes().values()) {
            if (recipe.getInputs().contains(monogram)) {
                Monogram extraMonogram = recipe.getInputs().get(1);
                if (extraMonogram.equals(monogram)) {
                    extraMonogram = recipe.getInputs().get(0);
                }

                gui.blit(BACKGROUND, x + (right ? 69 : 9), y + 7 + (ii * 12), 158, 20, 50, 10);

                monogram.renderArcanemiconMiniIcon(book, gui, x + (right? 69 : 9) + 1, y + 7 + (ii * 12) + 1);
                extraMonogram.renderArcanemiconMiniIcon(book, gui, x + (right ? 69 : 9) + 21, y + 7 + (ii * 12) + 1);
                recipe.getOutput().renderArcanemiconMiniIcon(book, gui, x + (right ? 69 : 9) + 41, y + 7 + (ii * 12) + 1);

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
            gui.blit(BACKGROUND, x + 39, y + 7 + (ii * 12), 158, 20, 50, 10);

            recipe.getInputs().get(0).renderArcanemiconMiniIcon(book, gui, x + 39 + 1, y + 7 + (ii * 12) + 1);
            recipe.getInputs().get(1).renderArcanemiconMiniIcon(book, gui, x + 39 + 21, y + 7 + (ii * 12) + 1);
            recipe.getOutput().renderArcanemiconMiniIcon(book, gui, x + 39 + 41, y + 7 + (ii * 12) + 1);
        }
    }
}