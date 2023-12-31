package mod.maxbogomol.wizards_reborn.client.integration.jei;

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
import mod.maxbogomol.wizards_reborn.common.recipe.CenserRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CenserRecipeCategory implements IRecipeCategory<CenserRecipe> {
    public static final RecipeType<CenserRecipe> TYPE = RecipeType.create(WizardsReborn.MOD_ID, "censer", CenserRecipe.class);
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/censer.png");

    private final IDrawable background;
    private final IDrawable icon;

    public CenserRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 68, 88);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(WizardsReborn.ARCANE_CENSER_ITEM.get()));
    }

    @NotNull
    @Override
    public RecipeType<CenserRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.nullToEmpty(Component.translatable("gui.wizards_reborn.category.censer").getString());
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
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull CenserRecipe recipe, @NotNull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 26, 58).addIngredients(recipe.getIngredientRecipe());
    }

    @Override
    public void draw(@NotNull CenserRecipe recipe, @NotNull IRecipeSlotsView view, @NotNull GuiGraphics gui, double mouseX, double mouseY) {
        if (mouseX >= 8 && mouseY >= 5 && mouseX <= 8 + 55 && mouseY <= 5 + 44) {
            List<Component> tooltips = new ArrayList<>();
            PotionUtils.addPotionTooltip(recipe.getEffects(), tooltips, 1.0f);
            gui.renderTooltip(Minecraft.getInstance().font, tooltips, Optional.empty(), ItemStack.EMPTY, (int) mouseX, (int) mouseY);
        }
    }
}
