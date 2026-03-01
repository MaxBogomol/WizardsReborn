package mod.maxbogomol.wizards_reborn.integration.client.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.registration.*;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtil;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.common.gui.menu.AlchemyFurnaceMenu;
import mod.maxbogomol.wizards_reborn.common.gui.menu.AlchemyMachineMenu;
import mod.maxbogomol.wizards_reborn.common.gui.menu.ArcaneWorkbenchMenu;
import mod.maxbogomol.wizards_reborn.common.gui.menu.JewelerTableMenu;
import mod.maxbogomol.wizards_reborn.client.gui.screen.AlchemyFurnaceScreen;
import mod.maxbogomol.wizards_reborn.client.gui.screen.AlchemyMachineScreen;
import mod.maxbogomol.wizards_reborn.client.gui.screen.ArcaneWorkbenchScreen;
import mod.maxbogomol.wizards_reborn.client.gui.screen.JewelerTableScreen;
import mod.maxbogomol.wizards_reborn.common.item.equipment.DrinkBottleItem;
import mod.maxbogomol.wizards_reborn.integration.common.farmers_delight.WizardsRebornFarmersDelight;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMenuTypes;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
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
        registration.addRecipeCategories(new ArcanumDustTransmutationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new WissenAltarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new WissenCrystallizerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ArcaneWorkbenchRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MortarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new AlchemyMachineRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CenserRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ArcaneIteratorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new JewelerTableRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CrystalRitualRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CrystalInfusionRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(ArcanumDustTransmutationRecipeCategory.TYPE, sortRecipes(WizardsRebornRecipes.ARCANUM_DUST_TRANSMUTATION.get(), BY_ID));
        registration.addRecipes(WissenAltarRecipeCategory.TYPE, sortRecipes(WizardsRebornRecipes.WISSEN_ALTAR.get(), BY_ID));
        registration.addRecipes(WissenCrystallizerRecipeCategory.TYPE, sortRecipes(WizardsRebornRecipes.WISSEN_CRYSTALLIZER.get(), BY_ID));
        registration.addRecipes(ArcaneWorkbenchRecipeCategory.TYPE, sortRecipes(WizardsRebornRecipes.ARCANE_WORKBENCH.get(), BY_ID));
        registration.addRecipes(MortarRecipeCategory.TYPE, sortRecipes(WizardsRebornRecipes.MORTAR.get(), BY_ID));
        registration.addRecipes(AlchemyMachineRecipeCategory.TYPE, sortRecipes(WizardsRebornRecipes.ALCHEMY_MACHINE.get(), BY_ID));
        registration.addRecipes(CenserRecipeCategory.TYPE, sortRecipes(WizardsRebornRecipes.CENSER.get(), BY_ID));
        registration.addRecipes(ArcaneIteratorRecipeCategory.TYPE, sortRecipes(WizardsRebornRecipes.ARCANE_ITERATOR.get(), BY_ID));
        registration.addRecipes(JewelerTableRecipeCategory.TYPE, sortRecipes(WizardsRebornRecipes.JEWELER_TABLE.get(), BY_ID));
        registration.addRecipes(CrystalRitualRecipeCategory.TYPE, sortRecipes(WizardsRebornRecipes.CRYSTAL_RITUAL.get(), BY_ID));
        registration.addRecipes(CrystalInfusionRecipeCategory.TYPE, sortRecipes(WizardsRebornRecipes.CRYSTAL_INFUSION.get(), BY_ID));

        if (WizardsRebornFarmersDelight.isLoaded()) {
            WizardsRebornFarmersDelight.JeiLoadedOnly.addKnifeJEIInfo(registration);
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.ARCANUM_DUST.get()), ArcanumDustTransmutationRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.WISSEN_ALTAR.get()), WissenAltarRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.WISSEN_CRYSTALLIZER.get()), WissenCrystallizerRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.ARCANE_WORKBENCH.get()), ArcaneWorkbenchRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.ARCANE_WOOD_MORTAR.get()), MortarRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_MORTAR.get()), MortarRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.ALCHEMY_MACHINE.get()), AlchemyMachineRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.ALCHEMY_BOILER.get()), AlchemyMachineRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.ARCANE_CENSER.get()), CenserRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.ARCANE_WOOD_SMOKING_PIPE.get()), CenserRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.INNOCENT_WOOD_SMOKING_PIPE.get()), CenserRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.BAMBOO_SMOKING_PIPE.get()), CenserRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.CORK_BAMBOO_SMOKING_PIPE.get()), CenserRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.ARCANE_ITERATOR.get()), ArcaneIteratorRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.JEWELER_TABLE.get()), JewelerTableRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.RUNIC_PEDESTAL.get()), CrystalRitualRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.RUNIC_PEDESTAL.get()), CrystalInfusionRecipeCategory.TYPE);

        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.ALCHEMY_FURNACE.get()), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.ALCHEMY_FURNACE.get()), RecipeTypes.FUELING);

        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.ARCANE_SALT_CAMPFIRE.get()), RecipeTypes.CAMPFIRE_COOKING);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.INNOCENT_SALT_CAMPFIRE.get()), RecipeTypes.CAMPFIRE_COOKING);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.CORK_BAMBOO_SALT_CAMPFIRE.get()), RecipeTypes.CAMPFIRE_COOKING);
        registration.addRecipeCatalyst(new ItemStack(WizardsRebornItems.WISESTONE_SALT_CAMPFIRE.get()), RecipeTypes.CAMPFIRE_COOKING);
    }

    @Override
    public void registerItemSubtypes(@NotNull ISubtypeRegistration registry) {
        IIngredientSubtypeInterpreter<ItemStack> interpreterPotion = (stack, ctx) -> String.valueOf(AlchemyPotionUtil.getPotion(stack).getId());
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.ALCHEMY_VIAL_POTION.get(), interpreterPotion);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.ALCHEMY_FLASK_POTION.get(), interpreterPotion);

        IIngredientSubtypeInterpreter<ItemStack> interpreterRitual = (stack, ctx) -> String.valueOf(CrystalRitualUtil.getCrystalRitual(stack).getId());
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.RUNIC_WISESTONE_PLATE.get(), interpreterRitual);

        IIngredientSubtypeInterpreter<ItemStack> interpreterArcaneEnchantment = (stack, ctx) -> String.valueOf(ArcaneEnchantmentUtil.getAllArcaneEnchantments(stack).toString());
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.ARCANE_ENCHANTED_BOOK.get(), interpreterArcaneEnchantment);

        IIngredientSubtypeInterpreter<ItemStack> interpreterDrinks = (stack, ctx) -> String.valueOf(DrinkBottleItem.getStageFromItem(stack));
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.VODKA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.BOURBON_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.WHISKEY_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.WHITE_WINE_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.RED_WINE_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.PORT_WINE_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.PALM_LIQUEUR_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.MEAD_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.SBITEN_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.SLIVOVITZ_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.SAKE_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.SOJU_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.CHICHA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.CHACHA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.APPLEJACK_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.RAKIA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.KIRSCH_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.BOROVICHKA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.PALINKA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.TEQUILA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.PULQUE_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.ARKHI_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.TEJ_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.WISSEN_BEER_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.MOR_TINCTURE_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.INNOCENT_WINE_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.TARKHUNA_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.BAIKAL_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.KVASS_BOTTLE.get(), interpreterDrinks);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, WizardsRebornItems.KISSEL_BOTTLE.get(), interpreterDrinks);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(ArcaneWorkbenchScreen.class, 110, 55, 28, 23, ArcaneWorkbenchRecipeCategory.TYPE);
        registration.addRecipeClickArea(AlchemyMachineScreen.class, 96, 51, 28, 23, AlchemyMachineRecipeCategory.TYPE);
        registration.addRecipeClickArea(AlchemyFurnaceScreen.class, 96, 33, 28, 23, RecipeTypes.SMELTING, RecipeTypes.FUELING);
        registration.addRecipeClickArea(JewelerTableScreen.class, 96, 33, 28, 23, JewelerTableRecipeCategory.TYPE);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(ArcaneWorkbenchMenu.class, WizardsRebornMenuTypes.ARCANE_WORKBENCH_CONTAINER.get(), ArcaneWorkbenchRecipeCategory.TYPE, 36, 13, 0, 36);
        registration.addRecipeTransferHandler(AlchemyMachineMenu.class, WizardsRebornMenuTypes.ALCHEMY_MACHINE_CONTAINER.get(), AlchemyMachineRecipeCategory.TYPE, 36, 6, 0, 36);
        registration.addRecipeTransferHandler(AlchemyFurnaceMenu.class, WizardsRebornMenuTypes.ALCHEMY_FURNACE_CONTAINER.get(), RecipeTypes.SMELTING, 36, 1, 0, 36);
        registration.addRecipeTransferHandler(AlchemyFurnaceMenu.class, WizardsRebornMenuTypes.ALCHEMY_FURNACE_CONTAINER.get(), RecipeTypes.FUELING, 37, 1, 0, 36);
        registration.addRecipeTransferHandler(JewelerTableMenu.class, WizardsRebornMenuTypes.JEWELER_TABLE_CONTAINER.get(), JewelerTableRecipeCategory.TYPE, 36, 2, 0, 36);
    }

    private static <T extends Recipe<C>, C extends Container> List<T> sortRecipes(RecipeType<T> type, Comparator<? super T> comparator) {
        @SuppressWarnings("unchecked")
        Collection<T> recipes = (Collection<T>) Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(type);
        List<T> list = new ArrayList<>(recipes);
        list.sort(comparator);
        return list;
    }
}