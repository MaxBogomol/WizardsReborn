package mod.maxbogomol.wizards_reborn.integration.client.jei;

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
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ArcanumDustTransmutationRecipeCategory implements IRecipeCategory<ArcanumDustTransmutationRecipe> {
    public static final RecipeType<ArcanumDustTransmutationRecipe> TYPE = RecipeType.create(WizardsReborn.MOD_ID, "arcanum_dust_transmutation", ArcanumDustTransmutationRecipe.class);
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/arcanum_dust_transmutation.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ArcanumDustTransmutationRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 148, 48);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(WizardsRebornItems.ARCANUM_DUST.get()));
    }

    @NotNull
    @Override
    public RecipeType<ArcanumDustTransmutationRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.nullToEmpty(Component.translatable("gui.wizards_reborn.jei.arcanum_dust_transmutation").getString());
    }

    @Override
    @SuppressWarnings("removal")
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull ArcanumDustTransmutationRecipe recipe, @NotNull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 4, 16).addItemStack(WizardsRebornItems.ARCANUM_DUST.get().getDefaultInstance());
        builder.addSlot(RecipeIngredientRole.INPUT, 63, 16).addIngredients(recipe.getInput());

        if (recipe.getDisplay() == ItemStack.EMPTY) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 16).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
        } else {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 16).addItemStack(recipe.getDisplay());
        }
    }
}