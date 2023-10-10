package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramRecipe;
import mod.maxbogomol.wizards_reborn.api.monogram.Monograms;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
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
        renderRecipes(book, gui, x, y, mouseX, mouseY, false);
        renderRecipes(book, gui, x, y, mouseX, mouseY, true);
    }

    @OnlyIn(Dist.CLIENT)
    public void renderRecipes(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY, boolean renderTooltip) {
        int i = 0;
        int ii = 0;
        boolean right = false;

        for (MonogramRecipe recipe : Monograms.getRecipes().values()) {
            if (recipe.getInputs().contains(monogram)) {
                Monogram extraMonogram = recipe.getInputs().get(1);
                if (extraMonogram.equals(monogram)) {
                    extraMonogram = recipe.getInputs().get(0);
                }

                if (!renderTooltip) {
                    gui.blit(BACKGROUND, x + (right ? 69 : 9), y + 7 + (ii * 12), 158, 20, 50, 10);

                    monogram.renderArcanemiconMiniIcon(book, gui, x + (right ? 69 : 9) + 1, y + 7 + (ii * 12) + 1);
                    extraMonogram.renderArcanemiconMiniIcon(book, gui, x + (right ? 69 : 9) + 21, y + 7 + (ii * 12) + 1);
                    recipe.getOutput().renderArcanemiconMiniIcon(book, gui, x + (right ? 69 : 9) + 41, y + 7 + (ii * 12) + 1);
                } else {
                    if (mouseX >= x + (right ? 69 : 9) + 1 && mouseY >= y + 7 + (ii * 12) + 1 && mouseX <= x + (right ? 69 : 9) + 1 + 8 && mouseY <= y + 7 + (ii * 12) + 1 + 8) {
                        gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(monogram.getTranslatedName()), mouseX, mouseY);
                    }

                    if (mouseX >= x + (right ? 69 : 9) + 21 && mouseY >= y + 7 + (ii * 12) + 1 && mouseX <= x + (right ? 69 : 9) + 21 + 8 && mouseY <= y + 7 + (ii * 12) + 1 + 8) {
                        gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(extraMonogram.getTranslatedName()), mouseX, mouseY);
                    }

                    if (mouseX >= x + (right ? 69 : 9) + 41 && mouseY >= y + 7 + (ii * 12) + 1 && mouseX <= x + (right ? 69 : 9) + 41+ 8 && mouseY <= y + 7 + (ii * 12) + 1 + 8) {
                        gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(recipe.getOutput().getTranslatedName()), mouseX, mouseY);
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

        MonogramRecipe recipe = Monograms.getRecipe(monogram.getId());
        if (recipe != null) {
            if (!renderTooltip) {
                gui.blit(BACKGROUND, x + 39, y + 7 + (ii * 12), 158, 20, 50, 10);

                recipe.getInputs().get(0).renderArcanemiconMiniIcon(book, gui, x + 39 + 1, y + 7 + (ii * 12) + 1);
                recipe.getInputs().get(1).renderArcanemiconMiniIcon(book, gui, x + 39 + 21, y + 7 + (ii * 12) + 1);
                recipe.getOutput().renderArcanemiconMiniIcon(book, gui, x + 39 + 41, y + 7 + (ii * 12) + 1);
            } else {
                if (mouseX >= x + 39 + 1 && mouseY >= y + 7 + (ii * 12) + 1 && mouseX <= x + 39 + 1 + 8 && mouseY <= y + 7 + (ii * 12) + 1 + 8) {
                    gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(recipe.getInputs().get(0).getTranslatedName()), mouseX, mouseY);
                }

                if (mouseX >= x + 39 + 21 && mouseY >= y + 7 + (ii * 12) + 1 && mouseX <= x + 39 + 21 + 8 && mouseY <= y + 7 + (ii * 12) + 1 + 8) {
                    gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(recipe.getInputs().get(1).getTranslatedName()), mouseX, mouseY);
                }

                if (mouseX >= x + 39 + 41 && mouseY >= y + 7 + (ii * 12) + 1 && mouseX <= x + 39 + 41 + 8 && mouseY <= y + 7 + (ii * 12) + 1 + 8) {
                    gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(recipe.getOutput().getTranslatedName()), mouseX, mouseY);
                }
            }
        }
    }
}