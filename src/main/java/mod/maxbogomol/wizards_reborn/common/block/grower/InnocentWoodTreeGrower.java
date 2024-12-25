package mod.maxbogomol.wizards_reborn.common.block.grower;

import mod.maxbogomol.wizards_reborn.common.levelgen.WizardsRebornFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class InnocentWoodTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return pHasFlowers ? WizardsRebornFeatures.INNOCENT_WOOD_BEES_TREE : WizardsRebornFeatures.INNOCENT_WOOD_TREE;
    }
}