package mod.maxbogomol.wizards_reborn.integration.client.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcaneWorkbenchRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ArcaneWorkbenchRecipeCategory implements IRecipeCategory<ArcaneWorkbenchRecipe> {
    public static final RecipeType<ArcaneWorkbenchRecipe> TYPE = RecipeType.create(WizardsReborn.MOD_ID, "arcane_workbench", ArcaneWorkbenchRecipe.class);
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/arcane_workbench.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ArcaneWorkbenchRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 176, 112);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(WizardsRebornItems.ARCANE_WORKBENCH.get()));
    }

    @NotNull
    @Override
    public RecipeType<ArcaneWorkbenchRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.nullToEmpty(WizardsRebornBlocks.ARCANE_WORKBENCH.get().getName().getString());
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
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull ArcaneWorkbenchRecipe recipe, @NotNull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 30, 30).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 30).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 66, 30).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 30, 48).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 48).addIngredients(recipe.getIngredients().get(4));
        builder.addSlot(RecipeIngredientRole.INPUT, 66, 48).addIngredients(recipe.getIngredients().get(5));
        builder.addSlot(RecipeIngredientRole.INPUT, 30, 66).addIngredients(recipe.getIngredients().get(6));
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 66).addIngredients(recipe.getIngredients().get(7));
        builder.addSlot(RecipeIngredientRole.INPUT, 66, 66).addIngredients(recipe.getIngredients().get(8));

        builder.addSlot(RecipeIngredientRole.INPUT, 48, 8).addIngredients(recipe.getIngredients().get(9));
        builder.addSlot(RecipeIngredientRole.INPUT, 88, 48).addIngredients(recipe.getIngredients().get(10));
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 88).addIngredients(recipe.getIngredients().get(11));
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 48).addIngredients(recipe.getIngredients().get(12));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 146, 48).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
    }

    @Override
    public void draw(@NotNull ArcaneWorkbenchRecipe recipe, @NotNull IRecipeSlotsView view, @NotNull GuiGraphics gui, double mouseX, double mouseY) {
        Font font_renderer = Minecraft.getInstance().font;
        String text_wissen = Integer.toString(recipe.getRecipeWissen());
        int stringWidth = font_renderer.width(text_wissen);

        gui.drawString(Minecraft.getInstance().font, text_wissen, 154 - (stringWidth / 2 ), 95, 0xffffff);
    }
}
