package mod.maxbogomol.wizards_reborn.common.block.casing;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public class CasingBlock extends Block implements SimpleWaterloggedBlock {
    public CasingBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    private static final VoxelShape SHAPE_SHELL = Block.box(0, 0, 0, 16, 16, 16);
    private static final VoxelShape SHAPE_CENTER = Block.box(3, 3, 3, 13, 13, 13);

    private static final VoxelShape SHAPE_HOLE = Stream.of(
            Block.box(3, 0, 3, 13, 16, 13),
            Block.box(3, 3, 0, 13, 13, 16),
            Block.box(0, 3, 3, 16, 13, 13)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_C = Shapes.join(SHAPE_SHELL, SHAPE_HOLE, BooleanOp.ONLY_FIRST);
    public static final VoxelShape SHAPE = Shapes.join(SHAPE_C, SHAPE_CENTER, BooleanOp.OR);

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pState.getValue(BlockStateProperties.WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }
}
