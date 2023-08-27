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
import mod.maxbogomol.wizards_reborn.common.recipe.WissenCrystallizerRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

public class WissenCrystallizerRecipeCategory implements IRecipeCategory<WissenCrystallizerRecipe> {
    public static final RecipeType<WissenCrystallizerRecipe> TYPE = RecipeType.create(WizardsReborn.MOD_ID, "wissen_crystallizer", WissenCrystallizerRecipe.class);
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/wissen_crystallizer.png");

    private final IDrawable background;
    private final IDrawable icon;

    public WissenCrystallizerRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 142, 88);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get()));
    }

    @NotNull
    @Override
    public RecipeType<WissenCrystallizerRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.nullToEmpty(WizardsReborn.WISSEN_CRYSTALLIZER.get().getName().getString());
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
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull WissenCrystallizerRecipe recipe, @NotNull IFocusGroup focusGroup) {
        int index = 1;
        double angleBetweenEach = 360.0 / (recipe.getIngredients().size() - 1);
        Vec2 point = new Vec2(36, 0), center = new Vec2(36, 36);
        boolean first = false;

        for (Ingredient o : recipe.getIngredients()) {
            if (first) {
                builder.addSlot(RecipeIngredientRole.INPUT, (int) point.x, (int) point.y).addIngredients(o);
                index += 1;
                point = rotatePointAbout(point, center, angleBetweenEach);
            } else {
                builder.addSlot(RecipeIngredientRole.INPUT, 36, 36).addIngredients(o);
                first = true;
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 121, 36).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
    }

    public static Vec2 rotatePointAbout(Vec2 in, Vec2 about, double degrees) {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Vec2((float) newX, (float) newY);
    }

    @Override
    public void draw(@NotNull WissenCrystallizerRecipe recipe, @NotNull IRecipeSlotsView view, @NotNull GuiGraphics gui, double mouseX, double mouseY) {
        Font font_renderer = Minecraft.getInstance().font;
        String text_wissen = Integer.toString(recipe.getRecipeWissen());
        int stringWidth = font_renderer.width(text_wissen);

        gui.drawString(Minecraft.getInstance().font, text_wissen, 120 - (stringWidth/2) + font_renderer.lineHeight, 65, 0xffffff);
    }
}
