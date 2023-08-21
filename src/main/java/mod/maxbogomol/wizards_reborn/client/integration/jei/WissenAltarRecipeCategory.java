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
import mod.maxbogomol.wizards_reborn.common.recipe.WissenAltarRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;

public class WissenAltarRecipeCategory implements IRecipeCategory<WissenAltarRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(WizardsReborn.MOD_ID, "wissen_altar");
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/wissen_altar.png");

    private final IDrawable background;
    private final IDrawable icon;

    public WissenAltarRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 48, 48);
        icon = helper.createDrawableIngredient(new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends WissenAltarRecipe> getRecipeClass() {
        return WissenAltarRecipe.class;
    }

    @Override
    public String getTitle() {
        return WizardsReborn.WISSEN_ALTAR.get().getTranslatedName().getString();
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
    public void setIngredients(WissenAltarRecipe recipe, IIngredients ingredients) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        builder.add(Arrays.asList(recipe.getIngredientRecipe().getMatchingStacks()));
        ingredients.setInputLists(VanillaTypes.ITEM, builder.build());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WissenAltarRecipe recipe, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 15, 11);

        recipeLayout.getItemStacks().set(ingredients);
    }

    @Override
    public void draw(WissenAltarRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        FontRenderer font_renderer = Minecraft.getInstance().fontRenderer;
        String text_wissen = Integer.toString(recipe.getRecipeWissen());
        int stringWidth = font_renderer.getStringWidth(text_wissen);

        font_renderer.drawStringWithShadow(matrixStack, text_wissen, 15 - (stringWidth/2) + font_renderer.FONT_HEIGHT, 38, 0xffffff);
    }
}
