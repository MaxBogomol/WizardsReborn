package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.tileentity.SaltLanternTileEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.TileSimpleInventory;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
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
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;

public class SaltLanternBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(5, 0, 5, 11, 2, 11),
            Block.box(10, 2, 10, 11, 7, 11),
            Block.box(5, 2, 10, 6, 7, 11),
            Block.box(10, 2, 5, 11, 7, 6),
            Block.box(5, 2, 5, 6, 7, 6),
            Block.box(5, 7, 5, 11, 8, 11),
            Block.box(6, 8, 6, 10, 10, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape HANGING_SHAPE = Stream.of(
            Block.box(5, 3, 5, 11, 5, 11),
            Block.box(10, 5, 10, 11, 10, 11),
            Block.box(5, 5, 10, 6, 10, 11),
            Block.box(10, 5, 5, 11, 10, 6),
            Block.box(5, 5, 5, 6, 10, 6),
            Block.box(5, 10, 5, 11, 11, 11),
            Block.box(6, 11, 6, 10, 13, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public SaltLanternBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false).setValue(HANGING, Boolean.valueOf(false)));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return state.getValue(HANGING) ? HANGING_SHAPE : SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED).add(HANGING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());

        for(Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockstate = this.defaultBlockState().setValue(HANGING, Boolean.valueOf(direction == Direction.UP));
                if (blockstate.canSurvive(context.getLevel(), context.getClickedPos())) {
                    return blockstate.setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileSimpleInventory) {
                net.minecraft.world.Containers.dropContents(world, pos, ((TileSimpleInventory) tile).getItemHandler());
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        SaltLanternTileEntity tile = (SaltLanternTileEntity) world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();

        int invSize = tile.getInventorySize();

        if (stack.getItem() instanceof WissenWandItem) {
            if (WissenWandItem.getMode(stack) != 4) {
                world.updateNeighbourForOutputSignal(pos, this);
                PacketUtils.SUpdateTileEntityPacket(tile);
                return InteractionResult.SUCCESS;
            }
        }

        if (!player.isShiftKeyDown()) {
            if (invSize < 2) {
                int slot = invSize;
                if (stack.is(WizardsReborn.ARCANE_LUMOS_ITEM_TAG)) {
                    if ((!stack.isEmpty()) && (tile.getItemHandler().getItem(slot).isEmpty())) {
                        if (stack.getCount() > 1) {
                            player.getItemInHand(hand).setCount(stack.getCount() - 1);
                            stack.setCount(1);
                            tile.getItemHandler().setItem(slot, stack);
                        } else {
                            tile.getItemHandler().setItem(slot, stack);
                            player.getInventory().removeItem(player.getItemInHand(hand));
                        }
                        world.updateNeighbourForOutputSignal(pos, this);
                        PacketUtils.SUpdateTileEntityPacket(tile);
                        world.playSound(null, pos, WizardsReborn.PEDESTAL_INSERT_SOUND.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        } else {
            if (invSize > 0) {
                int slot = invSize - 1;
                if (!tile.getItemHandler().getItem(slot).isEmpty()) {
                    if (player.getInventory().getSlotWithRemainingSpace(tile.getItemHandler().getItem(slot)) != -1 || player.getInventory().getFreeSlot() > -1) {
                        player.getInventory().add(tile.getItemHandler().getItem(slot).copy());
                    } else {
                        world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5F, pos.getY() + 1.0F, pos.getZ() + 0.5F, tile.getItemHandler().getItem(slot).copy()));
                    }
                    tile.getItemHandler().removeItem(slot, 1);
                    world.updateNeighbourForOutputSignal(pos, this);
                    PacketUtils.SUpdateTileEntityPacket(tile);
                    world.playSound(null, pos, WizardsReborn.PEDESTAL_REMOVE_SOUND.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    return InteractionResult.SUCCESS;
                }
            }
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

        return getConnectedDirection(pState).getOpposite() == pDirection && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        Direction direction = getConnectedDirection(pState).getOpposite();
        return Block.canSupportCenter(pLevel, pPos.relative(direction), direction.getOpposite());
    }

    protected static Direction getConnectedDirection(BlockState pState) {
        return pState.getValue(HANGING) ? Direction.DOWN : Direction.UP;
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SaltLanternTileEntity(pPos, pState);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        TileSimpleInventory tile = (TileSimpleInventory) level.getBlockEntity(pos);
        return AbstractContainerMenu.getRedstoneSignalFromContainer(tile.getItemHandler());
    }
}