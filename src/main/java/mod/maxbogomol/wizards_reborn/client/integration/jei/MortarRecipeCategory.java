package mod.maxbogomol.wizards_reborn.client.integration.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.recipe.MortarRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MortarRecipeCategory implements IRecipeCategory<MortarRecipe> {
    public static final RecipeType<MortarRecipe> TYPE = RecipeType.create(WizardsReborn.MOD_ID, "mortar", MortarRecipe.class);
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/mortar.png");

    private final IDrawable background;
    private final IDrawable icon;

    public MortarRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 148, 48);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(WizardsReborn.ARCANE_WOOD_MORTAR.get()));
    }

    @NotNull
    @Override
    public RecipeType<MortarRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.nullToEmpty(Component.translatable("gui.wizards_reborn.category.mortar").getString());
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
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull MortarRecipe recipe, @NotNull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 4, 16).addItemStack(WizardsReborn.ARCANE_WOOD_MORTAR.get().getDefaultInstance());
        builder.addSlot(RecipeIngredientRole.INPUT, 63, 16).addIngredients(recipe.getIngredientRecipe());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 16).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
    }
}