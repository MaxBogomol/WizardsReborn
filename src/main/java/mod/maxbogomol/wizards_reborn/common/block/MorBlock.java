package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.world.WorldGen;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MorBlock extends MushroomBlock {
    public MorBlock(Properties pProperties, ResourceKey<ConfiguredFeature<?, ?>> pFeature) {
        super(pProperties, pFeature);
    }
    //public MorBlock(Properties properties) {
    //    super(properties, (Supplier<Holder<? extends ConfiguredFeature<?, ?>>>) TreeFeatures.HUGE_RED_MUSHROOM);
    //}

    public boolean growMushroom(ServerLevel world, BlockPos pos, BlockState state, Random rand) {
        /*world.removeBlock(pos, false);
        ConfiguredFeature<?, ?> configuredfeature;
        if (rand.nextFloat() < 0.4) {
            if (this == WizardsReborn.FACETED_EARTH_CRYSTAL_BLOCK.get()) {
                //configuredfeature = WorldGen.TALL_MOR;
            } else {
                //configuredfeature = WorldGen.TALL_ELDER_MOR;
            }
        } else {
            if (this == WizardsReborn.FACETED_EARTH_CRYSTAL_BLOCK.get()) {
                //configuredfeature = WorldGen.HUGE_MOR;
            } else {
                //configuredfeature = WorldGen.HUGE_ELDER_MOR;
            }
        }

        //if (configuredfeature.place(world, world.getChunkSource().getGenerator(), rand, pos)) {
            return true;
        } else {
            world.setBlock(pos, state, 3);
            return false;
        }*/
        return false;
    }
}
