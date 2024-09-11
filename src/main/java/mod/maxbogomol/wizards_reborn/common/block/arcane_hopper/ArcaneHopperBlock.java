package mod.maxbogomol.wizards_reborn.common.block.arcane_hopper;

import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
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
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class ArcaneHopperBlock extends HopperBlock implements SimpleWaterloggedBlock {

    private static final VoxelShape INSIDE = Block.box(2.0D, 13.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    private static final VoxelShape TOP = Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape MID = Block.box(2.0D, 10.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    private static final VoxelShape FUNNEL = Block.box(4.0D, 4.0D, 4.0D, 12.0D, 10.0D, 12.0D);
    private static final VoxelShape MID_BASE = Shapes.or(TOP, MID);
    private static final VoxelShape CONVEX_BASE = Shapes.or(FUNNEL, MID_BASE);
    private static final VoxelShape BASE = Shapes.join(CONVEX_BASE, INSIDE, BooleanOp.ONLY_FIRST);
    private static final VoxelShape DOWN_SHAPE = Shapes.or(BASE, Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D));
    private static final VoxelShape EAST_SHAPE = Shapes.or(BASE, Block.box(12.0D, 4.0D, 6.0D, 16.0D, 8.0D, 10.0D));
    private static final VoxelShape NORTH_SHAPE = Shapes.or(BASE, Block.box(6.0D, 4.0D, 0.0D, 10.0D, 8.0D, 4.0D));
    private static final VoxelShape SOUTH_SHAPE = Shapes.or(BASE, Block.box(6.0D, 4.0D, 12.0D, 10.0D, 8.0D, 16.0D));
    private static final VoxelShape WEST_SHAPE = Shapes.or(BASE, Block.box(0.0D, 4.0D, 6.0D, 4.0D, 8.0D, 10.0D));
    private static final VoxelShape DOWN_INTERACTION_SHAPE = INSIDE;
    private static final VoxelShape EAST_INTERACTION_SHAPE = Shapes.or(INSIDE, Block.box(12.0D, 8.0D, 6.0D, 16.0D, 10.0D, 10.0D));
    private static final VoxelShape NORTH_INTERACTION_SHAPE = Shapes.or(INSIDE, Block.box(6.0D, 8.0D, 0.0D, 10.0D, 10.0D, 4.0D));
    private static final VoxelShape SOUTH_INTERACTION_SHAPE = Shapes.or(INSIDE, Block.box(6.0D, 8.0D, 12.0D, 10.0D, 10.0D, 16.0D));
    private static final VoxelShape WEST_INTERACTION_SHAPE = Shapes.or(INSIDE, Block.box(0.0D, 8.0D, 6.0D, 4.0D, 10.0D, 10.0D));

    public ArcaneHopperBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN).setValue(ENABLED, Boolean.valueOf(true)).setValue(BlockStateProperties.WATERLOGGED, false));
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext pContext) {
        return switch (state.getValue(FACING)) {
            case DOWN -> DOWN_SHAPE;
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> BASE;
        };
    }

    public VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return switch (state.getValue(FACING)) {
            case DOWN -> DOWN_INTERACTION_SHAPE;
            case NORTH -> NORTH_INTERACTION_SHAPE;
            case SOUTH -> SOUTH_INTERACTION_SHAPE;
            case WEST -> WEST_INTERACTION_SHAPE;
            case EAST -> EAST_INTERACTION_SHAPE;
            default -> INSIDE;
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace().getOpposite();
        return this.defaultBlockState().setValue(FACING, direction.getAxis() == Direction.Axis.Y ? Direction.DOWN : direction).setValue(ENABLED, Boolean.valueOf(true)).setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ArcaneHopperBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> pBlockEntityType) {
        return level.isClientSide ? null : createTickerHelper(pBlockEntityType, WizardsRebornBlockEntities.ARCANE_HOPPER.get(), ArcaneHopperBlockEntity::pushItemsTick);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof ArcaneHopperBlockEntity) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                NetworkHooks.openScreen(((ServerPlayer) player), (MenuProvider) blockEntity, blockEntity.getBlockPos());
                player.awardStat(Stats.INSPECT_HOPPER);
            }

            return InteractionResult.CONSUME;
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ArcaneHopperBlockEntity hopperBlock) {
                hopperBlock.setCustomName(stack.getHoverName());
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState pNewState, boolean pIsMoving) {
        if (!state.is(pNewState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof ArcaneHopperBlockEntity) {
                Containers.dropContents(level, pos, (ArcaneHopperBlockEntity)blockentity);
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, pNewState, pIsMoving);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity pEntity) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof ArcaneHopperBlockEntity) {
            ArcaneHopperBlockEntity.entityInside(level, pos, state, pEntity, (ArcaneHopperBlockEntity)blockentity);
        }
    }
}