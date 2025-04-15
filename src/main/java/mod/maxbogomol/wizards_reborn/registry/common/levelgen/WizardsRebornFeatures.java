package mod.maxbogomol.wizards_reborn.registry.common.levelgen;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class WizardsRebornFeatures {
    public static ResourceKey<ConfiguredFeature<?, ?>> ARCANE_WOOD_TREE = registerKey("arcane_wood");
    public static ResourceKey<ConfiguredFeature<?, ?>> FANCY_ARCANE_WOOD_TREE = registerKey("fancy_arcane_wood");
    public static ResourceKey<ConfiguredFeature<?, ?>> TALL_MOR = registerKey("tall_mor");
    public static ResourceKey<ConfiguredFeature<?, ?>> TALL_ELDER_MOR = registerKey("tall_elder_mor");
    public static ResourceKey<ConfiguredFeature<?, ?>> HUGE_MOR = registerKey("huge_mor");
    public static ResourceKey<ConfiguredFeature<?, ?>> HUGE_ELDER_MOR = registerKey("huge_elder_mor");
    public static ResourceKey<ConfiguredFeature<?, ?>> INNOCENT_WOOD_TREE = registerKey("innocent_wood");
    public static ResourceKey<ConfiguredFeature<?, ?>> INNOCENT_WOOD_BEES_TREE = registerKey("innocent_wood_bees");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(WizardsReborn.MOD_ID, name));
    }
}