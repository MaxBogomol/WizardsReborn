package mod.maxbogomol.wizards_reborn.client.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@JeiPlugin
public class WizardsRebornJei implements IModPlugin {
    private static final Comparator<Recipe<?>> BY_ID = Comparator.comparing(Recipe::getId);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new ArcanumDustTransmutationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(
                new WissenAltarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(
                new WissenCrystallizerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(
                new ArcaneWorkbenchRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(
                new MortarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(
                new AlchemyMachineRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(ArcanumDustTransmutationRecipeCategory.TYPE, sortRecipes(WizardsReborn.ARCANUM_DUST_TRANSMUTATION_RECIPE.get(), BY_ID));
        registration.addRecipes(WissenAltarRecipeCategory.TYPE, sortRecipes(WizardsReborn.WISSEN_ALTAR_RECIPE.get(), BY_ID));
        registration.addRecipes(WissenCrystallizerRecipeCategory.TYPE, sortRecipes(WizardsReborn.WISSEN_CRYSTALLIZER_RECIPE.get(), BY_ID));
        registration.addRecipes(ArcaneWorkbenchRecipeCategory.TYPE, sortRecipes(WizardsReborn.ARCANE_WORKBENCH_RECIPE.get(), BY_ID));
        registration.addRecipes(MortarRecipeCategory.TYPE, sortRecipes(WizardsReborn.MORTAR_RECIPE.get(), BY_ID));
        registration.addRecipes(AlchemyMachineRecipeCategory.TYPE, sortRecipes(WizardsReborn.ALCHEMY_MACHINE_RECIPE.get(), BY_ID));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ARCANUM_DUST.get()), ArcanumDustTransmutationRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()), WissenAltarRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get()), WissenCrystallizerRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ARCANE_WORKBENCH_ITEM.get()), ArcaneWorkbenchRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ARCANE_WOOD_MORTAR.get()), MortarRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ALCHEMY_MACHINE_ITEM.get()), AlchemyMachineRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ALCHEMY_BOILER_ITEM.get()), AlchemyMachineRecipeCategory.TYPE);
    }

    private static <T extends Recipe<C>, C extends Container> List<T> sortRecipes(RecipeType<T> type, Comparator<? super T> comparator) {
        @SuppressWarnings("unchecked")
        Collection<T> recipes = (Collection<T>) Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(type);
        List<T> list = new ArrayList<>(recipes);
        list.sort(comparator);
        return list;
    }
}