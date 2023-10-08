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
import mod.maxbogomol.wizards_reborn.common.recipe.ArcanumDustTransmutationRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ArcanumDustTransmutationRecipeCategory implements IRecipeCategory<ArcanumDustTransmutationRecipe> {
    public static final RecipeType<ArcanumDustTransmutationRecipe> TYPE = RecipeType.create(WizardsReborn.MOD_ID, "arcanum_dust_transmutation", ArcanumDustTransmutationRecipe.class);
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/arcanum_dust_transmutation.png");

    private final IDrawable background;
    private final IDrawable display;
    private final IDrawable icon;

    public ArcanumDustTransmutationRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 118, 48);
        display = helper.createDrawable(TEXTURE, 0, 48, 118, 96);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(WizardsReborn.ARCANUM_DUST.get()));
    }

    @NotNull
    @Override
    public RecipeType<ArcanumDustTransmutationRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.nullToEmpty(Component.translatable("gui.wizards_reborn.category.arcanum_dust_transmutation").getString());
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
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull ArcanumDustTransmutationRecipe recipe, @NotNull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 15).addItemStack(WizardsReborn.ARCANUM_DUST.get().getDefaultInstance());
        builder.addSlot(RecipeIngredientRole.INPUT, 51, 15).addIngredients(recipe.getIngredientRecipe());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 101, 15).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
    }
}