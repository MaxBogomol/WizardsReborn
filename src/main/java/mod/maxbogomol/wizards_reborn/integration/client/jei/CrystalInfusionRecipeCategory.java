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
import mod.maxbogomol.wizards_reborn.api.crystal.Crystals;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.common.recipe.CrystalInfusionRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystalRituals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CrystalInfusionRecipeCategory implements IRecipeCategory<CrystalInfusionRecipe> {
    public static final RecipeType<CrystalInfusionRecipe> TYPE = RecipeType.create(WizardsReborn.MOD_ID, "crystal_infusion", CrystalInfusionRecipe.class);
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/crystal_infusion.png");

    private final IDrawable background;
    private final IDrawable icon;

    public CrystalInfusionRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 144, 88);
        ItemStack item = new ItemStack(WizardsRebornItems.RUNIC_WISESTONE_PLATE.get());
        CrystalRitualUtil.setCrystalRitual(item, WizardsRebornCrystalRituals.CRYSTAL_INFUSION);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, item);
    }

    @NotNull
    @Override
    public RecipeType<CrystalInfusionRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(WizardsRebornCrystalRituals.CRYSTAL_INFUSION.getTranslatedName());
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
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull CrystalInfusionRecipe recipe, @NotNull IFocusGroup focusGroup) {
        int index = 1;
        double angleBetweenEach = 360.0 / (recipe.getIngredients().size());
        Vec2 point = new Vec2(35, 0), center = new Vec2(35, 36);

        List<ItemStack> items = new ArrayList<>();
        for (Item item : Crystals.getItems()) {
            items.add(new ItemStack(item));
        }

        builder.addSlot(RecipeIngredientRole.INPUT, 35, 36).addItemStacks(items);

        for (Ingredient o : recipe.getIngredients()) {
            builder.addSlot(RecipeIngredientRole.INPUT, (int) point.x, (int) point.y).addIngredients(o);
            index += 1;
            point = rotatePointAbout(point, center, angleBetweenEach);
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
    public void draw(@NotNull CrystalInfusionRecipe recipe, @NotNull IRecipeSlotsView view, @NotNull GuiGraphics gui, double mouseX, double mouseY) {
        Font font_renderer = Minecraft.getInstance().font;
        String text_wissen = Integer.toString(recipe.getRecipeLight());
        int stringWidth = font_renderer.width(text_wissen);

        gui.drawString(Minecraft.getInstance().font, text_wissen, 120 - (stringWidth / 2), 65, 0xffffff);
    }
}
