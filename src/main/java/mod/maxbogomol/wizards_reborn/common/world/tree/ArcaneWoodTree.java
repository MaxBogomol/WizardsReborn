package mod.maxbogomol.wizards_reborn.common.world.tree;

import mod.maxbogomol.wizards_reborn.common.world.WorldGen;
import net.minecraft.block.trees.BigTree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class ArcaneWoodTree extends BigTree {
    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
        return WorldGen.ARCANE_WOOD_TREE;
    }

    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getHugeTreeFeature(Random rand) {
        return WorldGen.FANCY_ARCANE_WOOD_TREE;
    }
}