package mod.maxbogomol.wizards_reborn.common.block.flammable;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class FlammableFenceBlock extends FenceBlock {
    public int fireSpeed;
    public int flammability;

    public FlammableFenceBlock(Properties properties, int fireSpeed, int flammability) {
        super(properties);
        this.fireSpeed = fireSpeed;
        this.flammability = flammability;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return flammability;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return fireSpeed;
    }
}
