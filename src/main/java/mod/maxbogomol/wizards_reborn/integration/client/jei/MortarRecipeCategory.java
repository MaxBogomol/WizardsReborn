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
import mod.maxbogomol.wizards_reborn.common.item.equipment.MortarItem;
import mod.maxbogomol.wizards_reborn.common.recipe.MortarRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MortarRecipeCategory implements IRecipeCategory<MortarRecipe> {
    public static final RecipeType<MortarRecipe> TYPE = RecipeType.create(WizardsReborn.MOD_ID, "mortar", MortarRecipe.class);
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/mortar.png");

    private final IDrawable background;
    private final IDrawable icon;

    public MortarRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 148, 48);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(WizardsRebornItems.ARCANE_WOOD_MORTAR.get()));
    }

    @NotNull
    @Override
    public RecipeType<MortarRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.nullToEmpty(Component.translatable("gui.wizards_reborn.jei.mortar").getString());
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
        builder.addSlot(RecipeIngredientRole.INPUT, 4, 16).addItemStacks(getMortarItems());
        builder.addSlot(RecipeIngredientRole.INPUT, 63, 16).addIngredients(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 16).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
    }

    public static List<ItemStack> getMortarItems() {
        List<ItemStack> items = new ArrayList<>();
        for (Item item : MortarItem.mortarList) {
            ItemStack itemStack = new ItemStack(item);
            items.add(itemStack);
        }

        return items;
    }
}