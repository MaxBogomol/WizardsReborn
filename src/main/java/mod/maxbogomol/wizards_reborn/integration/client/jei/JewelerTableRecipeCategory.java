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
import mod.maxbogomol.wizards_reborn.common.recipe.JewelerTableRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class JewelerTableRecipeCategory implements IRecipeCategory<JewelerTableRecipe> {
    public static final RecipeType<JewelerTableRecipe> TYPE = RecipeType.create(WizardsReborn.MOD_ID, "jeweler_table", JewelerTableRecipe.class);
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/jeweler_table.png");

    private final IDrawable background;
    private final IDrawable icon;

    public JewelerTableRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 148, 48);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(WizardsRebornItems.JEWELER_TABLE.get()));
    }

    @NotNull
    @Override
    public RecipeType<JewelerTableRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.nullToEmpty(WizardsRebornBlocks.JEWELER_TABLE.get().getName().getString());
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
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull JewelerTableRecipe recipe, @NotNull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 4, 16).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 63, 16).addIngredients(recipe.getIngredients().get(1));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 16).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
    }

    @Override
    public void draw(@NotNull JewelerTableRecipe recipe, @NotNull IRecipeSlotsView view, @NotNull GuiGraphics gui, double mouseX, double mouseY) {
        Font font_renderer = Minecraft.getInstance().font;
        String text_wissen = Integer.toString(recipe.getRecipeWissen());
        int stringWidth = font_renderer.width(text_wissen);

        gui.drawString(Minecraft.getInstance().font, text_wissen, 120 - (stringWidth / 2), 38, 0xffffff);
    }
}
