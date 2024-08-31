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
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.common.recipe.CrystalRitualRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

public class CrystalRitualRecipeCategory implements IRecipeCategory<CrystalRitualRecipe> {
    public static final RecipeType<CrystalRitualRecipe> TYPE = RecipeType.create(WizardsReborn.MOD_ID, "crystal_ritual", CrystalRitualRecipe.class);
    public final static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jei/crystal_ritual.png");

    private final IDrawable background;
    private final IDrawable icon;

    public CrystalRitualRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 88, 88);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(WizardsReborn.RUNIC_PEDESTAL_ITEM.get()));
    }

    @NotNull
    @Override
    public RecipeType<CrystalRitualRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.nullToEmpty(WizardsReborn.RUNIC_PEDESTAL.get().getName().getString());
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
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull CrystalRitualRecipe recipe, @NotNull IFocusGroup focusGroup) {
        int index = 1;
        double angleBetweenEach = 360.0 / (recipe.getIngredients().size());
        Vec2 point = new Vec2(35, 0), center = new Vec2(35, 36);

        ItemStack item = new ItemStack(WizardsReborn.RUNIC_WISESTONE_PLATE.get());
        CrystalRitualUtil.setCrystalRitual(item, recipe.getRecipeRitual());

        builder.addSlot(RecipeIngredientRole.INPUT, 35, 36).addItemStack(item);

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
}
