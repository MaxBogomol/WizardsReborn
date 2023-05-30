package mod.maxbogomol.wizards_reborn.common.world;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class WorldGen {

    static List<ConfiguredFeature<?, ?>> ORES = new ArrayList<>();
    static ConfiguredFeature<?, ?> ARCANUM_ORE_GEN;
    static RuleTest IN_STONE = new TagMatchRuleTest(Tags.Blocks.STONE);

    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> ARCANE_WOOD_TREE;

    static IStructurePieceType register(IStructurePieceType type, String name) {
        net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.STRUCTURE_PIECE, new ResourceLocation(WizardsReborn.MOD_ID, name), type);
        return type;
    }

    static <C extends IFeatureConfig, F extends Feature<C>> ConfiguredFeature<C, F> register(ConfiguredFeature<C, F> feature, String name) {
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(WizardsReborn.MOD_ID, name), feature);
        return feature;
    }

    static <C extends IFeatureConfig, S extends Structure<C>> StructureFeature<C, S> register(StructureFeature<C, S> feature, String name) {
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(WizardsReborn.MOD_ID, name), feature);
        return feature;
    }

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }

    public static void init() {
        ARCANUM_ORE_GEN = register(Feature.ORE.withConfiguration(new OreFeatureConfig(IN_STONE,
                        WizardsReborn.ARCANUM_ORE.get().getDefaultState(), Config.ARCANUM_VEIN_SIZE.get()))
                .square()
                .count(Config.ARCANUM_VEIN_COUNT.get())
                .range(Config.ARCANUM_MAX_Y.get()
                ), "arcanum_ore");
        if (Config.ARCANUM_ENABLED.get()) ORES.add(ARCANUM_ORE_GEN);

        ARCANE_WOOD_TREE = register("arcane_wood", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(WizardsReborn.ARCANE_WOOD_LOG.get().getDefaultState()),
                new SimpleBlockStateProvider(WizardsReborn.ARCANE_WOOD_LEAVES.get().getDefaultState()),
                new FancyFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(4), 4),
                new FancyTrunkPlacer(6, 9, 1),
                new TwoLayerFeature(2, 0, 2))).setMaxWaterDepth(Integer.MAX_VALUE).setHeightmap(Heightmap.Type.MOTION_BLOCKING).setIgnoreVines().build()));;
    }

    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        for (ConfiguredFeature<?, ?> feature : ORES) {
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, feature);
        }
    }
}
