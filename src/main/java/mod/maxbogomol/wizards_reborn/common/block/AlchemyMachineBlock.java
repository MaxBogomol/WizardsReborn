package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.IPipeConnection;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipeConnection;
import mod.maxbogomol.wizards_reborn.common.tileentity.AlchemyMachineTileEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.PipeBaseTileEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class AlchemyMachineBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock, IPipeConnection {

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(2, 0, 2, 14, 2, 14),
            Block.box(4, 2, 4, 12, 14, 12),
            Block.box(2, 14, 2, 14, 16, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_N_FACE = Block.box(5, 5, 3, 11, 11, 4);
    private static final VoxelShape SHAPE_S_FACE = Block.box(5, 5, 12, 11, 11, 13);
    private static final VoxelShape SHAPE_W_FACE = Block.box(3, 5, 5, 4, 11, 11);
    private static final VoxelShape SHAPE_E_FACE = Block.box(12, 5, 5, 13, 11, 11);

    private static final VoxelShape SHAPE_N = Shapes.join(SHAPE, SHAPE_N_FACE, BooleanOp.OR);
    private static final VoxelShape SHAPE_S = Shapes.join(SHAPE, SHAPE_S_FACE, BooleanOp.OR);
    private static final VoxelShape SHAPE_W = Shapes.join(SHAPE, SHAPE_W_FACE, BooleanOp.OR);
    private static final VoxelShape SHAPE_E = Shapes.join(SHAPE, SHAPE_E_FACE, BooleanOp.OR);

    public AlchemyMachineBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        switch (state.getValue(HORIZONTAL_FACING)) {
            case NORTH:
                return SHAPE_N;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            case EAST:
                return SHAPE_E;
            default:
                return SHAPE;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        return InteractionResult.PASS;
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

        if (pLevel.getBlockEntity(pCurrentPos) instanceof PipeBaseTileEntity pipe) {
            BlockEntity facingBE = pLevel.getBlockEntity(pCurrentPos);
            if (pNeighborState.is(WizardsReborn.FLUID_PIPE_CONNECTION_BLOCK_TAG)) {
                if (facingBE instanceof PipeBaseTileEntity && ((PipeBaseTileEntity) facingBE).getConnection(pDirection.getOpposite()) == PipeConnection.DISABLED) {
                    pipe.setConnection(pDirection, PipeConnection.NONE);
                } else {
                    pipe.setConnection(pDirection, PipeConnection.PIPE);
                }
            } else {
                pipe.setConnection(pDirection, PipeConnection.NONE);
            }
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tileentity = world.getBlockEntity(pos);
        return tileentity != null && tileentity.triggerEvent(id, param);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AlchemyMachineTileEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Override
    public PipeConnection getPipeConnection(BlockState state, Direction direction) {
        return PipeConnection.END;
    }

    /*@Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        OrbitalFluidRetainerTileEntity tile = (OrbitalFluidRetainerTileEntity) level.getBlockEntity(pos);
        return Mth.floor(((float) tile.getTank().getFluidAmount() / tile.getMaxCapacity()) * 14.0F);
    }*/
}
