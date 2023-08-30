package mod.maxbogomol.wizards_reborn.common.world;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.OptionalInt;

public class WorldGen {


    /*
    static List<Holder<PlacedFeature>> ORES = new ArrayList<>();
    static Holder<PlacedFeature> ARCANUM_ORE_GEN;
    static RuleTest IN_STONE = new TagMatchTest(Tags.Blocks.STONE);
    */

    //public static ConfiguredFeature<TreeConfiguration, ?> ARCANE_WOOD_TREE, FANCY_ARCANE_WOOD_TREE;
    //public static ConfiguredFeature<?, ?> TALL_MOR, TALL_ELDER_MOR, HUGE_MOR, HUGE_ELDER_MOR;

    public static ResourceKey<ConfiguredFeature<?, ?>> ARCANE_WOOD_TREE = WorldGen.registerKey("arcane_wood");
    public static ResourceKey<ConfiguredFeature<?, ?>> FANCY_ARCANE_WOOD_TREE = WorldGen.registerKey("fancy_arcane_wood");
    public static ResourceKey<ConfiguredFeature<?, ?>> TALL_MOR = WorldGen.registerKey("tall_mor");
    public static ResourceKey<ConfiguredFeature<?, ?>> TALL_ELDER_MOR = WorldGen.registerKey("tall_elder_mor");
    public static ResourceKey<ConfiguredFeature<?, ?>> HUGE_MOR = WorldGen.registerKey("huge_mor");
    public static ResourceKey<ConfiguredFeature<?, ?>> HUGE_ELDER_MOR = WorldGen.registerKey("huge_elder_mor");

    //public static final TreeConfiguration ARCANE_WOOD_TREE = new TreeConfiguration.TreeConfigurationBuilder(
    //        BlockStateProvider.simple(WizardsReborn.ARCANE_WOOD_LOG.get().defaultBlockState()),
    //        new FancyTrunkPlacer(3, 11, 0),
    //        BlockStateProvider.simple(WizardsReborn.ARCANE_WOOD_LEAVES.get().defaultBlockState()),
    //        new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
    //        new TwoLayersFeatureSize(4, 1, 0, OptionalInt.of(4))
    //).build();

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(WizardsReborn.MOD_ID, name));
    }

        /*ARCANE_WOOD_TREE = register("arcane_wood", Feature.TREE.configuredCodec((new TreeConfiguration.TreeConfigurationBuilder(
                        new SimpleStateProvider(WizardsReborn.ARCANE_WOOD_LOG.get().defaultBlockState()),
                new FancyTrunkPlacer(6, 9, 1),
                new SimpleStateProvider(WizardsReborn.ARCANE_WOOD_LEAVES.get().defaultBlockState()),
                new SimpleStateProvider(WizardsReborn.ARCANE_WOOD_SAPLING.get().defaultBlockState()),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(2, 0, 2))).ignoreVines().build()));

    /*
        FANCY_ARCANE_WOOD_TREE = register("fancy_arcane_wood", Feature.TREE.configuredCodec((new TreeConfiguration.TreeConfigurationBuilder(
                        new SimpleStateProvider(WizardsReborn.ARCANE_WOOD_LOG.get().defaultBlockState()),
                new DarkOakTrunkPlacer(5, 7, 1),
                new SimpleStateProvider(WizardsReborn.ARCANE_WOOD_LEAVES.get().defaultBlockState()),
                new SimpleStateProvider(WizardsReborn.ARCANE_WOOD_SAPLING.get().defaultBlockState()),
                new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                new TwoLayersFeatureSize(2, 0, 2))).ignoreVines().build()));

        TALL_MOR = register("huge_mor", Feature.HUGE_RED_MUSHROOM.configuredCodec(new HugeMushroomFeatureConfiguration(
                new SimpleStateProvider(WizardsReborn.MOR_BLOCK.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, Boolean.valueOf(false))),
                new SimpleStateProvider(Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.valueOf(false)).setValue(HugeMushroomBlock.DOWN, Boolean.valueOf(false))), 1)));

        TALL_ELDER_MOR = register("huge_elder_mor", Feature.HUGE_RED_MUSHROOM.configuredCodec(new HugeMushroomFeatureConfiguration(
                new SimpleStateProvider(WizardsReborn.ELDER_MOR_BLOCK.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, Boolean.valueOf(false))),
                new SimpleStateProvider(Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.valueOf(false)).setValue(HugeMushroomBlock.DOWN, Boolean.valueOf(false))), 1)));

        HUGE_MOR = register("huge_mor", Feature.HUGE_RED_MUSHROOM.configuredCodec(new HugeMushroomFeatureConfiguration(
                new SimpleStateProvider(WizardsReborn.MOR_BLOCK.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, Boolean.valueOf(false))),
                new SimpleStateProvider(Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.valueOf(false)).setValue(HugeMushroomBlock.DOWN, Boolean.valueOf(false))), 2)));

        HUGE_ELDER_MOR = register("huge_elder_mor", Feature.HUGE_RED_MUSHROOM.configuredCodec(new HugeMushroomFeatureConfiguration(
                new SimpleStateProvider(WizardsReborn.ELDER_MOR_BLOCK.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, Boolean.valueOf(false))),
                new SimpleStateProvider(Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.valueOf(false)).setValue(HugeMushroomBlock.DOWN, Boolean.valueOf(false))), 2)));
    }

    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        for (Holder<PlacedFeature> feature : ORES) {
            List<Holder<PlacedFeature>> base = event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);
            base.add(feature);
        }
    }*/
}
