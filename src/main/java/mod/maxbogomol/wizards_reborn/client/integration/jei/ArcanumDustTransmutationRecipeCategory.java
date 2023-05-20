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
import mod.maxbogomol.wizards_reborn.common.data.recipes.ArcanumDustTransmutationRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Arrays;
import java.util.List;

public class ArcanumDustTransmutationRecipeCategory implements IRecipeCategory<ArcanumDustTransmutationRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(WizardsReborn.MOD_ID, "arcanum_dust_transmutation");
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/arcanum_dust_transmutation.png");

    private final IDrawable background;
    private final IDrawable display;
    private final IDrawable icon;

    public ArcanumDustTransmutationRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 118, 48);
        display = helper.createDrawable(TEXTURE, 0, 48, 118, 96);
        icon = helper.createDrawableIngredient(new ItemStack(WizardsReborn.ARCANUM_DUST.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends ArcanumDustTransmutationRecipe> getRecipeClass() {
        return ArcanumDustTransmutationRecipe.class;
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("gui.wizards_reborn.category.arcanum_dust_transmutation").getString();
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
    public void setIngredients(ArcanumDustTransmutationRecipe recipe, IIngredients ingredients) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        builder.add(Arrays.asList(WizardsReborn.ARCANUM_DUST.get().getDefaultInstance()));
        builder.add(Arrays.asList(recipe.getIngredientRecipe().getMatchingStacks()));
        ingredients.setInputLists(VanillaTypes.ITEM, builder.build());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());

        if (recipe.getDisplay() != ItemStack.EMPTY) {
            ingredients.setOutput(VanillaTypes.ITEM, recipe.getDisplay());
        }
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ArcanumDustTransmutationRecipe recipe, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 0, 14);
        recipeLayout.getItemStacks().init(1, true, 50, 14);

        recipeLayout.getItemStacks().init(2, false, 100, 14);

        recipeLayout.getItemStacks().set(ingredients);
    }

    @Override
    public void draw(ArcanumDustTransmutationRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        if (recipe.getDisplay() != ItemStack.EMPTY) {
            display.draw(matrixStack, 0, 0);
        }
    }
}