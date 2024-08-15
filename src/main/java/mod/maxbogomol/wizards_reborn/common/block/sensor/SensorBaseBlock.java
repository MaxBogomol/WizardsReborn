package mod.maxbogomol.wizards_reborn.common.block.sensor;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SensorBaseBlock extends DiodeBlock implements EntityBlock, SimpleWaterloggedBlock {

    private static final VoxelShape SHAPE = Block.box(6, 6, 6, 10, 10, 10);

    public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;
    public static final EnumProperty<ComparatorMode> MODE = BlockStateProperties.MODE_COMPARATOR;

    public SensorBaseBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false).setValue(BlockStateProperties.LIT, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(FACE);
        builder.add(BlockStateProperties.WATERLOGGED);
        builder.add(MODE, POWERED);
        builder.add(BlockStateProperties.LIT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        for(Direction direction : context.getNearestLookingDirections()) {
            BlockState blockstate;
            if (direction.getAxis() == Direction.Axis.Y) {
                blockstate = this.defaultBlockState().setValue(FACE, direction == Direction.UP ? AttachFace.FLOOR : AttachFace.CEILING).setValue(FACING, context.getHorizontalDirection()).setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
            } else {
                blockstate = this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, direction).setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
            }

            if (blockstate.canSurvive(context.getLevel(), context.getClickedPos())) {
                return blockstate;
            }
        }
        return this.defaultBlockState();
    }

    @Override
    protected int getDelay(BlockState pState) {
        return 2;
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

        return pState;
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
        return new SensorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Override
    protected int getInputSignal(Level pLevel, BlockPos pPos, BlockState pState) {
        return 0;
    }

    @Override
    protected int getAlternateSignal(SignalGetter pSignalGetter, BlockPos pPos, BlockState pState) {
        return 0;
    }


    @Override
    protected int getOutputSignal(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        return blockentity instanceof SensorBlockEntity ? ((SensorBlockEntity)blockentity).getOutputSignal() : 0;
    }

    @Override
    protected void checkTickOnNeighbor(Level pLevel, BlockPos pPos, BlockState pState) {
        if (!pLevel.getBlockTicks().willTickThisTick(pPos, this)) {
            int i = this.calculateOutputSignal(pLevel, pPos, pState);
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            int j = blockentity instanceof SensorBlockEntity ? ((SensorBlockEntity)blockentity).getOutputSignal() : 0;
            if (i != j || pState.getValue(POWERED) != this.shouldTurnOn(pLevel, pPos, pState)) {
                TickPriority tickpriority = this.shouldPrioritize(pLevel, pPos, pState) ? TickPriority.HIGH : TickPriority.NORMAL;
                pLevel.scheduleTick(pPos, this, 2, tickpriority);
            }

        }
    }

    public void refreshOutputState(Level pLevel, BlockPos pPos, BlockState pState) {
        int i = this.calculateOutputSignal(pLevel, pPos, pState);
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        int j = 0;
        if (blockentity instanceof SensorBlockEntity comparatorblockentity) {
            j = comparatorblockentity.getOutputSignal();
            comparatorblockentity.setOutputSignal(i);
        }

        if (j != i || pState.getValue(MODE) == ComparatorMode.COMPARE) {
            boolean flag1 = this.shouldTurnOn(pLevel, pPos, pState);
            boolean flag = pState.getValue(POWERED);
            if (flag && !flag1) {
                pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(false)), 2);
            } else if (!flag && flag1) {
                pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(true)), 2);
            }

            this.updateNeighborsInFront(pLevel, pPos, pState);
        }
    }

    protected void updateNeighborsInFront(Level pLevel, BlockPos pPos, BlockState pState) {
        Direction direction = pState.getValue(FACING);
        BlockPos blockpos = pPos.relative(direction.getOpposite());

        switch (pState.getValue(FACE)) {
            case FLOOR:
                blockpos = pPos.below();
                direction = Direction.UP;
                break;
            case WALL:
                break;
            case CEILING:
                blockpos = pPos.above();
                direction = Direction.DOWN;
                break;
        }

        if (net.minecraftforge.event.ForgeEventFactory.onNeighborNotify(pLevel, pPos, pLevel.getBlockState(pPos), java.util.EnumSet.of(direction.getOpposite()), false).isCanceled())
            return;
        pLevel.neighborChanged(blockpos, this, pPos);
        pLevel.updateNeighborsAtExceptFromFacing(blockpos, this, direction);
    }

    private int calculateOutputSignal(Level pLevel, BlockPos pPos, BlockState pState) {
        int i = this.getInputSignal(pLevel, pPos, pState);
        if (i == 0) {
            return 0;
        } else {
            int j = this.getAlternateSignal(pLevel, pPos, pState);
            if (j > i) {
                return 0;
            } else {
                return pState.getValue(MODE) == ComparatorMode.SUBTRACT ? i - j : i;
            }
        }
    }

    protected boolean shouldTurnOn(Level pLevel, BlockPos pPos, BlockState pState) {
        int i = this.getInputSignal(pLevel, pPos, pState);
        if (i == 0) {
            return false;
        } else {
            int j = this.getAlternateSignal(pLevel, pPos, pState);
            if (i > j) {
                return true;
            } else {
                return i == j && pState.getValue(MODE) == ComparatorMode.COMPARE;
            }
        }
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        this.refreshOutputState(pLevel, pPos, pState);
    }

    @Override
    public boolean getWeakChanges(BlockState state, net.minecraft.world.level.LevelReader world, BlockPos pos) {
        return state.is(Blocks.COMPARATOR);
    }

    @Override
    public void onNeighborChange(BlockState state, net.minecraft.world.level.LevelReader world, BlockPos pos, BlockPos neighbor) {
        state.neighborChanged((Level)world, pos, world.getBlockState(neighbor).getBlock(), neighbor, false);
    }

    @Nullable
    public ItemFrame getItemFrame(Level pLevel, Direction pFacing, BlockPos pPos) {
        List<ItemFrame> list = pLevel.getEntitiesOfClass(ItemFrame.class, new AABB((double)pPos.getX(), (double)pPos.getY(), (double)pPos.getZ(), (double)(pPos.getX() + 1), (double)(pPos.getY() + 1), (double)(pPos.getZ() + 1)), (p_289506_) -> {
            return p_289506_ != null && p_289506_.getDirection() == pFacing;
        });
        return list.size() == 1 ? list.get(0) : null;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return true;
    }

    @Nullable
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        if (!pBlockState.getValue(POWERED)) {
            return 0;
        } else {
            switch (pBlockState.getValue(FACE)) {
                case FLOOR:
                    return Direction.UP == pSide ? this.getOutputSignal(pBlockAccess, pPos, pBlockState) : 0;
                case WALL:
                    return pBlockState.getValue(FACING) == pSide ? this.getOutputSignal(pBlockAccess, pPos, pBlockState) : 0;
                case CEILING:
                    return Direction.DOWN == pSide ? this.getOutputSignal(pBlockAccess, pPos, pBlockState) : 0;
            }
            return pBlockState.getValue(FACING) == pSide ? this.getOutputSignal(pBlockAccess, pPos, pBlockState) : 0;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public ModelResourceLocation getModel(BlockState state) {
        return WizardsRebornClient.REDSTONE_SENSOR_PIECE_MODEL;
    }
}
