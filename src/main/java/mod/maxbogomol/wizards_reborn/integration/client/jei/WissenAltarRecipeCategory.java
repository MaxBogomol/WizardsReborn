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
import mod.maxbogomol.wizards_reborn.common.recipe.WissenAltarRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WissenAltarRecipeCategory implements IRecipeCategory<WissenAltarRecipe> {
    public static final RecipeType<WissenAltarRecipe> TYPE = RecipeType.create(WizardsReborn.MOD_ID, "wissen_altar", WissenAltarRecipe.class);
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/wissen_altar.png");

    private final IDrawable background;
    private final IDrawable icon;

    public WissenAltarRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 56, 56);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get()));
    }

    @NotNull
    @Override
    public RecipeType<WissenAltarRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.nullToEmpty(WizardsRebornBlocks.WISSEN_ALTAR.get().getName().getString());
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
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull WissenAltarRecipe recipe, @NotNull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 20, 20).addIngredients(recipe.getIngredientRecipe());
    }

    @Override
    public void draw(@NotNull WissenAltarRecipe recipe, @NotNull IRecipeSlotsView view, @NotNull GuiGraphics gui, double mouseX, double mouseY) {
        Font font_renderer = Minecraft.getInstance().font;
        String text_wissen = Integer.toString(recipe.getRecipeWissen());
        int stringWidth = font_renderer.width(text_wissen);

        gui.drawString(Minecraft.getInstance().font, text_wissen, (56 - stringWidth) / 2, 34 + font_renderer.lineHeight, 0xffffff);
    }
}
