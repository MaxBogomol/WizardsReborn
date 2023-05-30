package mod.maxbogomol.wizards_reborn.common.block.flammable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class FlammableStairsBlock extends StairsBlock {
    public int fireSpeed;
    public int flammability;

    public FlammableStairsBlock(java.util.function.Supplier<BlockState> state, AbstractBlock.Properties properties, int fireSpeed, int flammability) {
        super(state, properties);
        this.fireSpeed = fireSpeed;
        this.flammability = flammability;
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return flammability;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return fireSpeed;
    }
}
