package mod.maxbogomol.wizards_reborn.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LuminalGlassBlock extends GlassBlock {
    public LuminalGlassBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.getBlock() instanceof LuminalGlassBlock || super.skipRendering(state, adjacentBlockState, side);
    }
}
