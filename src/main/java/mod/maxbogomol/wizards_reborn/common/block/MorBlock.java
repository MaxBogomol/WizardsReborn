package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.world.WorldGen;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class MorBlock extends MushroomBlock {
    public MorBlock(Properties properties) {
        super(properties);
    }

    public boolean grow(ServerWorld world, BlockPos pos, BlockState state, Random rand) {
        world.removeBlock(pos, false);
        ConfiguredFeature<?, ?> configuredfeature;
        if (rand.nextFloat() < 0.4) {
            if (this == WizardsReborn.MOR.get()) {
                configuredfeature = WorldGen.TALL_MOR;
            } else {
                configuredfeature = WorldGen.TALL_ELDER_MOR;
            }
        } else {
            if (this == WizardsReborn.MOR.get()) {
                configuredfeature = WorldGen.HUGE_MOR;
            } else {
                configuredfeature = WorldGen.HUGE_ELDER_MOR;
            }
        }

        if (configuredfeature.generate(world, world.getChunkProvider().getChunkGenerator(), rand, pos)) {
            return true;
        } else {
            world.setBlockState(pos, state, 3);
            return false;
        }
    }
}
