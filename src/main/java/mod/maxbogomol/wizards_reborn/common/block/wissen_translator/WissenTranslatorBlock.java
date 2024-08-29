package mod.maxbogomol.wizards_reborn.common.block.wissen_translator;

import mod.maxbogomol.fluffy_fur.common.block.entity.BlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
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

public class WissenTranslatorBlock extends FaceAttachedHorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock  {

    private static final VoxelShape SHAPE_D = Stream.of(
            Block.box(5, 0, 5, 11, 1, 11),
            Block.box(6, 1, 6, 10, 2, 10),
            Block.box(7, 2, 7, 9, 5, 9),
            Block.box(6, 5, 6, 10, 6, 10),
            Block.box(7, 6, 7, 9, 7, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_T = Stream.of(
            Block.box(5, 15, 5, 11, 16, 11),
            Block.box(6, 14, 6, 10, 15, 10),
            Block.box(7, 11, 7, 9, 14, 9),
            Block.box(6, 10, 6, 10, 11, 10),
            Block.box(7, 9, 7, 9, 10, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(5, 5, 15, 11, 11, 16),
            Block.box(6, 6, 14, 10, 10, 15),
            Block.box(7, 7, 11, 9, 9, 14),
            Block.box(6, 6, 10, 10, 10, 11),
            Block.box(7, 7, 9, 9, 9, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(5, 5, 0, 11, 11, 1),
            Block.box(6, 6, 1, 10, 10, 2),
            Block.box(7, 7, 2, 9, 9, 5),
            Block.box(6, 6, 5, 10, 10, 6),
            Block.box(7, 7, 6, 9, 9, 7)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(15, 5, 5, 16, 11, 11),
            Block.box(14, 6, 6, 15, 10, 10),
            Block.box(11, 7, 7, 14, 9, 9),
            Block.box(10, 6, 6, 11, 10, 10),
            Block.box(9, 7, 7, 10, 9, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(0, 5, 5, 1, 11, 11),
            Block.box(1, 6, 6, 2, 10, 10),
            Block.box(2, 7, 7, 5, 9, 9),
            Block.box(5, 6, 6, 6, 10, 10),
            Block.box(6, 7, 7, 7, 9, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public WissenTranslatorBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        switch (state.getValue(FACE)) {
            case FLOOR:
                return SHAPE_D;
            case WALL:
                switch (state.getValue(FACING)) {
                    case NORTH:
                        return SHAPE_N;
                    case SOUTH:
                        return SHAPE_S;
                    case WEST:
                        return SHAPE_W;
                    case EAST:
                        return SHAPE_E;
                    default:
                        return SHAPE_N;
                }
            case CEILING:
                return SHAPE_T;
            default:
                return SHAPE_D;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(FACE);
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        for(Direction direction : context.getNearestLookingDirections()) {
            BlockState blockstate;
            if (direction.getAxis() == Direction.Axis.Y) {
                blockstate = this.defaultBlockState().setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).setValue(FACING, context.getHorizontalDirection()).setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
            } else {
                blockstate = this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, direction.getOpposite()).setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
            }

            if (blockstate.canSurvive(context.getLevel(), context.getClickedPos())) {
                return blockstate;
            }
        }
        return this.defaultBlockState();
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof BlockSimpleInventory) {
                net.minecraft.world.Containers.dropContents(world, pos, ((BlockSimpleInventory) tile).getItemHandler());
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        WissenTranslatorBlockEntity tile = (WissenTranslatorBlockEntity) world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();

        if (stack.getItem() instanceof WissenWandItem) {
            if (WissenWandItem.getMode(stack) != 4) {
                world.updateNeighbourForOutputSignal(pos, this);
                BlockEntityUpdate.packet(tile);
                return InteractionResult.SUCCESS;
            }
        }

        if ((!stack.isEmpty()) && (tile.getItemHandler().getItem(0).isEmpty())) {
            if (stack.is(WizardsReborn.ARCANE_LUMOS_ITEM_TAG)) {
                if (stack.getCount() > 1) {
                    player.getItemInHand(hand).setCount(stack.getCount() - 1);
                    stack.setCount(1);
                    tile.getItemHandler().setItem(0, stack);
                    world.updateNeighbourForOutputSignal(pos, this);
                    BlockEntityUpdate.packet(tile);
                    world.playSound(null, pos, WizardsReborn.PEDESTAL_INSERT_SOUND.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    return InteractionResult.SUCCESS;
                } else {
                    tile.getItemHandler().setItem(0, stack);
                    player.getInventory().removeItem(player.getItemInHand(hand));
                    world.updateNeighbourForOutputSignal(pos, this);
                    BlockEntityUpdate.packet(tile);
                    world.playSound(null, pos, WizardsReborn.PEDESTAL_INSERT_SOUND.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        if (!tile.getItemHandler().getItem(0).isEmpty()) {
            if (player.getInventory().getSlotWithRemainingSpace(tile.getItemHandler().getItem(0)) != -1 || player.getInventory().getFreeSlot() > -1) {
                player.getInventory().add(tile.getItemHandler().getItem(0).copy());
            } else {
                world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5F, pos.getY() + 1.0F, pos.getZ() + 0.5F, tile.getItemHandler().getItem(0).copy()));
            }
            tile.getItemHandler().removeItem(0, 1);
            world.updateNeighbourForOutputSignal(pos, this);
            BlockEntityUpdate.packet(tile);
            world.playSound(null, pos, WizardsReborn.PEDESTAL_REMOVE_SOUND.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            return InteractionResult.SUCCESS;
        }

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
        return new WissenTranslatorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        BlockSimpleInventory tile = (BlockSimpleInventory) level.getBlockEntity(pos);
        return AbstractContainerMenu.getRedstoneSignalFromContainer(tile.getItemHandler());
    }
}
