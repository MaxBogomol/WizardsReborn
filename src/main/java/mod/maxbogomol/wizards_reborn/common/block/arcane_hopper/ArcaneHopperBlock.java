package mod.maxbogomol.wizards_reborn.common.block.arcane_hopper;

import mod.maxbogomol.wizards_reborn.client.gui.container.ArcaneHopperContainer;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch ((Direction)pState.getValue(FACING)) {
            case DOWN:
                return DOWN_SHAPE;
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            case EAST:
                return EAST_SHAPE;
            default:
                return BASE;
        }
    }

    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        switch ((Direction)pState.getValue(FACING)) {
            case DOWN:
                return DOWN_INTERACTION_SHAPE;
            case NORTH:
                return NORTH_INTERACTION_SHAPE;
            case SOUTH:
                return SOUTH_INTERACTION_SHAPE;
            case WEST:
                return WEST_INTERACTION_SHAPE;
            case EAST:
                return EAST_INTERACTION_SHAPE;
            default:
                return INSIDE;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace().getOpposite();
        return this.defaultBlockState().setValue(FACING, direction.getAxis() == Direction.Axis.Y ? Direction.DOWN : direction).setValue(ENABLED, Boolean.valueOf(true)).setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER));
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
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ArcaneHopperBlockEntity(pPos, pState);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, WizardsRebornBlockEntities.ARCANE_HOPPER.get(), ArcaneHopperBlockEntity::pushItemsTick);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof ArcaneHopperBlockEntity) {
                MenuProvider containerProvider = createContainerProvider(pLevel, pPos);
                NetworkHooks.openScreen(((ServerPlayer) pPlayer), containerProvider, blockentity.getBlockPos());
                pPlayer.awardStat(Stats.INSPECT_HOPPER);
            }

            return InteractionResult.CONSUME;
        }
    }

    private MenuProvider createContainerProvider(Level worldIn, BlockPos pos) {
        return new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.translatable("gui.wizards_reborn.arcane_hopper.title");
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
                return new ArcaneHopperContainer(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof ArcaneHopperBlockEntity) {
                Containers.dropContents(pLevel, pPos, (ArcaneHopperBlockEntity)blockentity);
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof ArcaneHopperBlockEntity) {
            ArcaneHopperBlockEntity.entityInside(pLevel, pPos, pState, pEntity, (ArcaneHopperBlockEntity)blockentity);
        }
    }
}