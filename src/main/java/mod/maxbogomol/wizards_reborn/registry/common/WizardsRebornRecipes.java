package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.recipe.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WizardsRebornRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, WizardsReborn.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, WizardsReborn.MOD_ID);

    public static final RegistryObject<ArcanumDustTransmutationRecipe.Serializer> ARCANUM_DUST_TRANSMUTATION_SERIALIZER = RECIPE_SERIALIZERS.register("arcanum_dust_transmutation", ArcanumDustTransmutationRecipe.Serializer::new);
    public static final RegistryObject<WissenAltarRecipe.Serializer> WISSEN_ALTAR_SERIALIZER = RECIPE_SERIALIZERS.register("wissen_altar", WissenAltarRecipe.Serializer::new);
    public static final RegistryObject<WissenCrystallizerRecipe.Serializer> WISSEN_CRYSTALLIZER_SERIALIZER = RECIPE_SERIALIZERS.register("wissen_crystallizer", WissenCrystallizerRecipe.Serializer::new);
    public static final RegistryObject<ArcaneWorkbenchRecipe.Serializer> ARCANE_WORKBENCH_SERIALIZER = RECIPE_SERIALIZERS.register("arcane_workbench", ArcaneWorkbenchRecipe.Serializer::new);
    public static final RegistryObject<MortarRecipe.Serializer> MORTAR_SERIALIZER = RECIPE_SERIALIZERS.register("mortar", MortarRecipe.Serializer::new);
    public static final RegistryObject<JewelerTableRecipe.Serializer> JEWELER_TABLE_SERIALIZER = RECIPE_SERIALIZERS.register("jeweler_table", JewelerTableRecipe.Serializer::new);
    public static final RegistryObject<AlchemyMachineRecipe.Serializer> ALCHEMY_MACHINE_SERIALIZER = RECIPE_SERIALIZERS.register("alchemy_machine", AlchemyMachineRecipe.Serializer::new);
    public static final RegistryObject<CenserRecipe.Serializer> CENSER_SERIALIZER = RECIPE_SERIALIZERS.register("censer", CenserRecipe.Serializer::new);
    public static final RegistryObject<ArcaneIteratorRecipe.Serializer> ARCANE_ITERATOR_SERIALIZER = RECIPE_SERIALIZERS.register("arcane_iterator", ArcaneIteratorRecipe.Serializer::new);
    public static final RegistryObject<CrystalRitualRecipe.Serializer> CRYSTAL_RITUAL_SERIALIZER = RECIPE_SERIALIZERS.register("crystal_ritual", CrystalRitualRecipe.Serializer::new);
    public static final RegistryObject<CrystalInfusionRecipe.Serializer> CRYSTAL_INFUSION_SERIALIZER = RECIPE_SERIALIZERS.register("crystal_infusion", CrystalInfusionRecipe.Serializer::new);

    public static RegistryObject<RecipeType<ArcanumDustTransmutationRecipe>> ARCANUM_DUST_TRANSMUTATION = RECIPES.register("arcanum_dust_transmutation", () -> RecipeType.simple(ArcanumDustTransmutationRecipe.TYPE_ID));
    public static final RegistryObject<RecipeType<WissenAltarRecipe>> WISSEN_ALTAR = RECIPES.register("wissen_altar", () -> RecipeType.simple(WissenAltarRecipe.TYPE_ID));
    public static final RegistryObject<RecipeType<WissenCrystallizerRecipe>> WISSEN_CRYSTALLIZER = RECIPES.register("wissen_crystallizer", () -> RecipeType.simple(WissenCrystallizerRecipe.TYPE_ID));
    public static final RegistryObject<RecipeType<ArcaneWorkbenchRecipe>> ARCANE_WORKBENCH = RECIPES.register("arcane_workbench", () -> RecipeType.simple(ArcaneWorkbenchRecipe.TYPE_ID));
    public static final RegistryObject<RecipeType<MortarRecipe>> MORTAR = RECIPES.register("mortar", () -> RecipeType.simple(MortarRecipe.TYPE_ID));
    public static final RegistryObject<RecipeType<JewelerTableRecipe>> JEWELER_TABLE = RECIPES.register("jeweler_table", () -> RecipeType.simple(JewelerTableRecipe.TYPE_ID));
    public static final RegistryObject<RecipeType<AlchemyMachineRecipe>> ALCHEMY_MACHINE = RECIPES.register("alchemy_machine", () -> RecipeType.simple(AlchemyMachineRecipe.TYPE_ID));
    public static final RegistryObject<RecipeType<CenserRecipe>> CENSER = RECIPES.register("censer", () -> RecipeType.simple(CenserRecipe.TYPE_ID));
    public static final RegistryObject<RecipeType<ArcaneIteratorRecipe>> ARCANE_ITERATOR = RECIPES.register("arcane_iterator", () -> RecipeType.simple(ArcaneIteratorRecipe.TYPE_ID));
    public static final RegistryObject<RecipeType<CrystalRitualRecipe>> CRYSTAL_RITUAL = RECIPES.register("crystal_ritual", () -> RecipeType.simple(CrystalRitualRecipe.TYPE_ID));
    public static final RegistryObject<RecipeType<CrystalInfusionRecipe>> CRYSTAL_INFUSION = RECIPES.register("crystal_infusion", () -> RecipeType.simple(CrystalInfusionRecipe.TYPE_ID));

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPES.register(eventBus);
    }
}
