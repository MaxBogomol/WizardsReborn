package mod.maxbogomol.wizards_reborn.client.integration.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.data.recipes.ArcaneWorkbenchRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArcaneWorkbenchRecipeCategory implements IRecipeCategory<ArcaneWorkbenchRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(WizardsReborn.MOD_ID, "arcane_workbench");
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/arcane_workbench.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ArcaneWorkbenchRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 176, 112);
        icon = helper.createDrawableIngredient(new ItemStack(WizardsReborn.ARCANE_WORKBENCH_ITEM.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends ArcaneWorkbenchRecipe> getRecipeClass() {
        return ArcaneWorkbenchRecipe.class;
    }

    @Override
    public String getTitle() {
        return WizardsReborn.ARCANE_WORKBENCH.get().getTranslatedName().getString();
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(ArcaneWorkbenchRecipe recipe, IIngredients ingredients) {
        List<List<ItemStack>> list = new ArrayList<>();
        for (Ingredient ingr : recipe.getIngredients()) {
            list.add(Arrays.asList(ingr.getMatchingStacks()));
        }

        ingredients.setInputLists(VanillaTypes.ITEM, list);
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ArcaneWorkbenchRecipe recipe, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 29, 29);
        recipeLayout.getItemStacks().init(1, true, 47, 29);
        recipeLayout.getItemStacks().init(2, true, 65, 29);
        recipeLayout.getItemStacks().init(3, true, 29, 47);
        recipeLayout.getItemStacks().init(4, true, 47, 47);
        recipeLayout.getItemStacks().init(5, true, 65, 47);
        recipeLayout.getItemStacks().init(6, true, 29, 65);
        recipeLayout.getItemStacks().init(7, true, 48, 65);
        recipeLayout.getItemStacks().init(8, true, 65, 65);

        recipeLayout.getItemStacks().init(9, true, 47, 7);
        recipeLayout.getItemStacks().init(10, true, 87, 47);
        recipeLayout.getItemStacks().init(11, true, 47, 87);
        recipeLayout.getItemStacks().init(12, true, 7, 47);

        int index = 0;
        for (List<ItemStack> o : ingredients.getInputs(VanillaTypes.ITEM)) {
            if (!o.isEmpty()) {
                recipeLayout.getItemStacks().set(index, o);
            }
            index++;
        }

        recipeLayout.getItemStacks().init(13, false, 145, 47);

        recipeLayout.getItemStacks().set(ingredients);
    }

    public static Vector2f rotatePointAbout(Vector2f in, Vector2f about, double degrees) {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Vector2f((float) newX, (float) newY);
    }

    @Override
    public void draw(ArcaneWorkbenchRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        FontRenderer font_renderer = Minecraft.getInstance().fontRenderer;
        String text_wissen = Integer.toString(recipe.getRecipeWissen());
        int stringWidth = font_renderer.getStringWidth(text_wissen);

        font_renderer.drawStringWithShadow(matrixStack, text_wissen, 154 - (stringWidth/2) + font_renderer.FONT_HEIGHT, 95, 0xffffff);
    }
}
