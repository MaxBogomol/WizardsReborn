package mod.maxbogomol.wizards_reborn.common.world;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class WorldGen {
    /*
    static List<Holder<PlacedFeature>> ORES = new ArrayList<>();
    static Holder<PlacedFeature> ARCANUM_ORE_GEN;
    static RuleTest IN_STONE = new TagMatchTest(Tags.Blocks.STONE);

    public static ConfiguredFeature<TreeConfiguration, ?> ARCANE_WOOD_TREE, FANCY_ARCANE_WOOD_TREE;
    public static ConfiguredFeature<?, ?> TALL_MOR, TALL_ELDER_MOR, HUGE_MOR, HUGE_ELDER_MOR;

    static StructurePieceType register(StructurePieceType type, String name) {
        net.minecraft.core.Registry.register(net.minecraft.core.Registry.STRUCTURE_PIECE, new ResourceLocation(WizardsReborn.MOD_ID, name), type);
        return type;
    }

    static <C extends FeatureConfiguration, F extends Feature<C>> ConfiguredFeature<C, F> register(ConfiguredFeature<C, F> feature, String name) {
        BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(WizardsReborn.MOD_ID, name), feature);
        return feature;
    }

    static <C extends FeatureConfiguration, S extends StructureFeature<C>> ConfiguredStructureFeature<C, S> register(ConfiguredStructureFeature<C, S> feature, String name) {
        BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(WizardsReborn.MOD_ID, name), feature);
        return feature;
    }

    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    public static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    public static void init() {
        /*RCANUM_ORE_GEN = register(Feature.ORE.configuredCodec(new OreConfiguration(IN_STONE,
                        WizardsReborn.ARCANUM_ORE.get().defaultBlockState(), Config.ARCANUM_VEIN_SIZE.get()))
                .squared()
                .count(Config.ARCANUM_VEIN_COUNT.get())
                .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(Config.ARCANUM_MAX_Y.get())
                ), "arcanum_ore");
;
        ConfiguredFeature<?, ?> ARCANUM_ORE = register(Feature.ORE.configuredCodec(new OreConfiguration(IN_STONE, WizardsReborn.ARCANUM_ORE.get().defaultBlockState(), Config.ARCANUM_VEIN_SIZE.get())), "arcanum_ore");
        ARCANUM_ORE_GEN = PlacementUtils.register("arcanum_ore", ARCANUM_ORE.(
    		InSquarePlacement.spread(), BiomeFilter.biome(), CountPlacement.of(Config.LEAD_VEIN_COUNT.get()),
    		HeightRangePlacement.uniform(VerticalAnchor.absolute(Math.max(0, Config.LEAD_MIN_Y.get())), VerticalAnchor.absolute(Config.LEAD_MAX_Y.get()))
		));*/

/*
        List<OreConfiguration.TargetBlockState> OVERWORLD_ARCANUM_ORE = List.of(
                OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, WizardsReborn.ARCANUM_ORE.get().defaultBlockState()));

        Holder<ConfiguredFeature<OreConfiguration, ?>> ARCANUM_ORE = FeatureUtils.register("arcanum_ore",
                Feature.ORE, new OreConfiguration(OVERWORLD_ARCANUM_ORE, Config.ARCANUM_VEIN_SIZE.get()));

        ARCANUM_ORE_GEN = PlacementUtils.register("arcanum_ore_gen",
                ARCANUM_ORE, commonOrePlacement(
                        Config.ARCANUM_VEIN_COUNT.get(),
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        if (Config.ARCANUM_ENABLED.get()) ORES.add(ARCANUM_ORE_GEN);

        /*
        ARCANE_WOOD_TREE = register("arcane_wood", Feature.TREE.configuredCodec((new TreeConfiguration.TreeConfigurationBuilder(
                        new SimpleStateProvider(WizardsReborn.ARCANE_WOOD_LOG.get().defaultBlockState()),
                new FancyTrunkPlacer(6, 9, 1),
                new SimpleStateProvider(WizardsReborn.ARCANE_WOOD_LEAVES.get().defaultBlockState()),
                new SimpleStateProvider(WizardsReborn.ARCANE_WOOD_SAPLING.get().defaultBlockState()),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(2, 0, 2))).ignoreVines().build()));

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
