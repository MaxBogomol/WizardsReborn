package mod.maxbogomol.wizards_reborn.common.world.tree;

import mod.maxbogomol.wizards_reborn.common.world.WorldGen;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class ArcaneWoodTree extends Tree {
    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
        return WorldGen.ARCANE_WOOD_TREE;
    }
}