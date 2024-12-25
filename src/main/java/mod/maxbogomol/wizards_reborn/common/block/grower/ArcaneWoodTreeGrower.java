package mod.maxbogomol.wizards_reborn.common.block.grower;

import mod.maxbogomol.wizards_reborn.common.levelgen.WizardsRebornFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class ArcaneWoodTreeGrower extends AbstractMegaTreeGrower {
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return WizardsRebornFeatures.ARCANE_WOOD_TREE;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource pRandom) {
        return WizardsRebornFeatures.FANCY_ARCANE_WOOD_TREE;
    }
}