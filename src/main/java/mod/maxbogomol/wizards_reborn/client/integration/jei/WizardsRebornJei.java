package mod.maxbogomol.wizards_reborn.client.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtils;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtils;
import mod.maxbogomol.wizards_reborn.common.integration.farmersdelight.FarmersDelightIntegration;
import mod.maxbogomol.wizards_reborn.common.item.equipment.DrinkBottleItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@JeiPlugin
public class WizardsRebornJei implements IModPlugin {
    private static final Comparator<Recipe<?>> BY_ID = Comparator.comparing(Recipe::getId);

    @Nonnull
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
        registration.addRecipeCategories(
                new CenserRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(
                new ArcaneIteratorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(
                new JewelerTableRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(
                new CrystalRitualRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(
                new CrystalInfusionRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(ArcanumDustTransmutationRecipeCategory.TYPE, sortRecipes(WizardsReborn.ARCANUM_DUST_TRANSMUTATION_RECIPE.get(), BY_ID));
        registration.addRecipes(WissenAltarRecipeCategory.TYPE, sortRecipes(WizardsReborn.WISSEN_ALTAR_RECIPE.get(), BY_ID));
        registration.addRecipes(WissenCrystallizerRecipeCategory.TYPE, sortRecipes(WizardsReborn.WISSEN_CRYSTALLIZER_RECIPE.get(), BY_ID));
        registration.addRecipes(ArcaneWorkbenchRecipeCategory.TYPE, sortRecipes(WizardsReborn.ARCANE_WORKBENCH_RECIPE.get(), BY_ID));
        registration.addRecipes(MortarRecipeCategory.TYPE, sortRecipes(WizardsReborn.MORTAR_RECIPE.get(), BY_ID));
        registration.addRecipes(AlchemyMachineRecipeCategory.TYPE, sortRecipes(WizardsReborn.ALCHEMY_MACHINE_RECIPE.get(), BY_ID));
        registration.addRecipes(CenserRecipeCategory.TYPE, sortRecipes(WizardsReborn.CENSER_RECIPE.get(), BY_ID));
        registration.addRecipes(ArcaneIteratorRecipeCategory.TYPE, sortRecipes(WizardsReborn.ARCANE_ITERATOR_RECIPE.get(), BY_ID));
        registration.addRecipes(JewelerTableRecipeCategory.TYPE, sortRecipes(WizardsReborn.JEWELER_TABLE_RECIPE.get(), BY_ID));
        registration.addRecipes(CrystalRitualRecipeCategory.TYPE, sortRecipes(WizardsReborn.CRYSTAL_RITUAL_RECIPE.get(), BY_ID));
        registration.addRecipes(CrystalInfusionRecipeCategory.TYPE, sortRecipes(WizardsReborn.CRYSTAL_INFUSION_RECIPE.get(), BY_ID));

        if (FarmersDelightIntegration.isLoaded()) {
            FarmersDelightIntegration.JeiLoadedOnly.addKnifeJEIInfo(registration);
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ARCANUM_DUST.get()), ArcanumDustTransmutationRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.WISSEN_ALTAR_ITEM.get()), WissenAltarRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get()), WissenCrystallizerRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ARCANE_WORKBENCH_ITEM.get()), ArcaneWorkbenchRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ARCANE_WOOD_MORTAR.get()), MortarRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.INNOCENT_WOOD_MORTAR.get()), MortarRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ALCHEMY_MACHINE_ITEM.get()), AlchemyMachineRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ALCHEMY_BOILER_ITEM.get()), AlchemyMachineRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ARCANE_CENSER_ITEM.get()), CenserRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ARCANE_WOOD_SMOKING_PIPE.get()), CenserRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ARCANE_ITERATOR_ITEM.get()), ArcaneIteratorRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.JEWELER_TABLE_ITEM.get()), JewelerTableRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.RUNIC_PEDESTAL_ITEM.get()), CrystalRitualRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.RUNIC_PEDESTAL_ITEM.get()), CrystalInfusionRecipeCategory.TYPE);

        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ALCHEMY_FURNACE_ITEM.get()), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ALCHEMY_FURNACE_ITEM.get()), RecipeTypes.FUELING);

        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.ARCANE_SALT_CAMPFIRE_ITEM.get()), RecipeTypes.CAMPFIRE_COOKING);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.INNOCENT_SALT_CAMPFIRE_ITEM.get()), RecipeTypes.CAMPFIRE_COOKING);
        registration.addRecipeCatalyst(new ItemStack(WizardsReborn.WISESTONE_SALT_CAMPFIRE_ITEM.get()), RecipeTypes.CAMPFIRE_COOKING);
    }

    @Override
    public void registerItemSubtypes(@NotNull ISubtypeRegistration registry) {
        IIngredientSubtypeInterpreter<ItemStack> interpreterPotion = (stack, ctx) -> String.valueOf(AlchemyPotionUtils.getPotion(stack).getId());
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.ALCHEMY_VIAL_POTION.get(), interpreterPotion);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.ALCHEMY_FLASK_POTION.get(), interpreterPotion);

        IIngredientSubtypeInterpreter<ItemStack> interpreterRitual = (stack, ctx) -> String.valueOf(CrystalRitualUtils.getCrystalRitual(stack).getId());
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.RUNIC_WISESTONE_PLATE.get(), interpreterRitual);

        IIngredientSubtypeInterpreter<ItemStack> interpreterArcaneEnchantment = (stack, ctx) -> String.valueOf(ArcaneEnchantmentUtils.getAllArcaneEnchantments(stack).toString());
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.ARCANE_ENCHANTED_BOOK.get(), interpreterArcaneEnchantment);

        IIngredientSubtypeInterpreter<ItemStack> interpreterDrinks = (stack, ctx) -> String.valueOf(DrinkBottleItem.getStageS(stack));
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.VODKA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.BOURBON_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.WHISKEY_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.WHITE_WINE_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.RED_WINE_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.PORT_WINE_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.PALM_LIQUEUR_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.MEAD_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.SBITEN_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.SLIVOVITZ_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.SAKE_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.SOJU_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.CHICHA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.CHACHA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.APPLEJACK_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.RAKIA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.KIRSCH_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.BOROVICHKA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.PALINKA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.TEQUILA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.PULQUE_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.ARKHI_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.TEJ_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.WISSEN_BEER_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.MOR_TINCTURE_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.INNOCENT_WINE_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.TARKHUNA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.BAIKAL_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.KVASS_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsReborn.KISSEL_BOTTLE.get(), interpreterDrinks);
    }

    private static <T extends Recipe<C>, C extends Container> List<T> sortRecipes(RecipeType<T> type, Comparator<? super T> comparator) {
        @SuppressWarnings("unchecked")
        Collection<T> recipes = (Collection<T>) Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(type);
        List<T> list = new ArrayList<>(recipes);
        list.sort(comparator);
        return list;
    }
}