package mod.maxbogomol.wizards_reborn.client.integration.jei;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.data.recipes.WissenCrystallizerRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WissenCrystallizerRecipeCategory implements IRecipeCategory<WissenCrystallizerRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(WizardsReborn.MOD_ID, "wissen_crystallizer");
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/wissen_crystallizer.png");

    private final IDrawable background;
    private final IDrawable icon;

    public WissenCrystallizerRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 142, 88);
        icon = helper.createDrawableIngredient(new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends WissenCrystallizerRecipe> getRecipeClass() {
        return WissenCrystallizerRecipe.class;
    }

    @Override
    public String getTitle() {
        return WizardsReborn.WISSEN_CRYSTALLIZER.get().getTranslatedName().getString();
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
    public void setIngredients(WissenCrystallizerRecipe recipe, IIngredients ingredients) {
        List<List<ItemStack>> list = new ArrayList<>();
        for (Ingredient ingr : recipe.getIngredients()) {
            list.add(Arrays.asList(ingr.getMatchingStacks()));
        }

        ingredients.setInputLists(VanillaTypes.ITEM, list);
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WissenCrystallizerRecipe recipe, IIngredients ingredients) {
        int index = 1;
        double angleBetweenEach = 360.0 / (ingredients.getInputs(VanillaTypes.ITEM).size() - 1);
        Vector2f point = new Vector2f(35, 0), center = new Vector2f(35, 35);
        boolean first = false;

        for (List<ItemStack> o : ingredients.getInputs(VanillaTypes.ITEM)) {
            if (first) {
                recipeLayout.getItemStacks().init(index, true, (int) point.x, (int) point.y);
                recipeLayout.getItemStacks().set(index, o);
                index += 1;
                point = rotatePointAbout(point, center, angleBetweenEach);
            } else {
                recipeLayout.getItemStacks().init(0, true, 35, 35);
                recipeLayout.getItemStacks().set(0, o);
                first = true;
            }
        }

        recipeLayout.getItemStacks().init(index, false, 120, 35);
        recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }

    public static Vector2f rotatePointAbout(Vector2f in, Vector2f about, double degrees) {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Vector2f((float) newX, (float) newY);
    }

    @Override
    public void draw(WissenCrystallizerRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        FontRenderer font_renderer = Minecraft.getInstance().fontRenderer;
        String text_wissen = Integer.toString(recipe.getRecipeWissen());
        int stringWidth = font_renderer.getStringWidth(text_wissen);

        font_renderer.drawStringWithShadow(matrixStack, text_wissen, 120 - (stringWidth/2) + font_renderer.FONT_HEIGHT, 65, 0xffffff);
    }
}
