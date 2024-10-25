package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramRecipe;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramHandler;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class MonogramRecipesPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/monogram_page.png");
    public Monogram monogram;

    public MonogramRecipesPage(Monogram monogram) {
        super(BACKGROUND);
        this.monogram = monogram;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        renderRecipes(book, gui, x, y, mouseX, mouseY, false);
        renderRecipes(book, gui, x, y, mouseX, mouseY, true);
    }

    @OnlyIn(Dist.CLIENT)
    public void renderRecipes(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY, boolean renderTooltip) {
        int i = 0;
        int ii = 0;
        boolean right = false;

        for (MonogramRecipe recipe : MonogramHandler.getRecipes().values()) {
            if (recipe.getInputs().contains(monogram)) {
                Monogram extraMonogram = recipe.getInputs().get(1);
                if (extraMonogram.equals(monogram)) {
                    extraMonogram = recipe.getInputs().get(0);
                }

                if (!renderTooltip) {
                    gui.blit(BACKGROUND, x + (right ? 69 : 9), y + 7 + (ii * 12), 158, 20, 50, 10);

                    monogram.renderMiniIcon(gui, x + (right ? 69 : 9) + 1, y + 7 + (ii * 12) + 1);
                    extraMonogram.renderMiniIcon(gui, x + (right ? 69 : 9) + 21, y + 7 + (ii * 12) + 1);
                    recipe.getOutput().renderMiniIcon(gui, x + (right ? 69 : 9) + 41, y + 7 + (ii * 12) + 1);
                } else {
                    if (mouseX >= x + (right ? 69 : 9) + 1 && mouseY >= y + 7 + (ii * 12) + 1 && mouseX <= x + (right ? 69 : 9) + 1 + 8 && mouseY <= y + 7 + (ii * 12) + 1 + 8) {
                        gui.renderTooltip(Minecraft.getInstance().font, monogram.getComponentList(), Optional.empty(), mouseX, mouseY);
                    }

                    if (mouseX >= x + (right ? 69 : 9) + 21 && mouseY >= y + 7 + (ii * 12) + 1 && mouseX <= x + (right ? 69 : 9) + 21 + 8 && mouseY <= y + 7 + (ii * 12) + 1 + 8) {
                        gui.renderTooltip(Minecraft.getInstance().font, extraMonogram.getComponentList(), Optional.empty(), mouseX, mouseY);
                    }

                    if (mouseX >= x + (right ? 69 : 9) + 41 && mouseY >= y + 7 + (ii * 12) + 1 && mouseX <= x + (right ? 69 : 9) + 41+ 8 && mouseY <= y + 7 + (ii * 12) + 1 + 8) {
                        gui.renderTooltip(Minecraft.getInstance().font, recipe.getOutput().getComponentList(), Optional.empty(), mouseX, mouseY);
                    }
                }

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

        MonogramRecipe recipe = MonogramHandler.getRecipe(monogram.getId());
        if (recipe != null) {
            if (!renderTooltip) {
                gui.blit(BACKGROUND, x + 39, y + 7 + (ii * 12), 158, 20, 50, 10);

                recipe.getInputs().get(0).renderMiniIcon(gui, x + 39 + 1, y + 7 + (ii * 12) + 1);
                recipe.getInputs().get(1).renderMiniIcon(gui, x + 39 + 21, y + 7 + (ii * 12) + 1);
                recipe.getOutput().renderMiniIcon(gui, x + 39 + 41, y + 7 + (ii * 12) + 1);
            } else {
                if (mouseX >= x + 39 + 1 && mouseY >= y + 7 + (ii * 12) + 1 && mouseX <= x + 39 + 1 + 8 && mouseY <= y + 7 + (ii * 12) + 1 + 8) {
                    gui.renderTooltip(Minecraft.getInstance().font, recipe.getInputs().get(0).getComponentList(), Optional.empty(), mouseX, mouseY);
                }

                if (mouseX >= x + 39 + 21 && mouseY >= y + 7 + (ii * 12) + 1 && mouseX <= x + 39 + 21 + 8 && mouseY <= y + 7 + (ii * 12) + 1 + 8) {
                    gui.renderTooltip(Minecraft.getInstance().font, recipe.getInputs().get(1).getComponentList(), Optional.empty(), mouseX, mouseY);
                }

                if (mouseX >= x + 39 + 41 && mouseY >= y + 7 + (ii * 12) + 1 && mouseX <= x + 39 + 41 + 8 && mouseY <= y + 7 + (ii * 12) + 1 + 8) {
                    gui.renderTooltip(Minecraft.getInstance().font, recipe.getOutput().getComponentList(), Optional.empty(), mouseX, mouseY);
                }
            }
        }
    }
}