package mod.maxbogomol.wizards_reborn.common.world.tree;

import mod.maxbogomol.wizards_reborn.common.world.WorldGen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class InnocentWoodTree extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return pHasFlowers ? WorldGen.INNOCENT_WOOD_BEES_TREE : WorldGen.INNOCENT_WOOD_TREE;
    }
}