package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.tileentity.LightEmitterTileEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.TileSimpleInventory;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;

public class LightEmitterBlock extends Block implements EntityBlock, SimpleWaterloggedBlock  {

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(3, 0, 3, 13, 5, 13),
            Block.box(4, 5, 4, 12, 6, 12),
            Block.box(12, 4, 12, 14, 7, 14),
            Block.box(2, 4, 12, 4, 7, 14),
            Block.box(12, 4, 2, 14, 7, 4),
            Block.box(2, 4, 2, 4, 7, 4)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public LightEmitterBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
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
        LightEmitterTileEntity tile = (LightEmitterTileEntity) world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();

        if (stack.getItem() instanceof WissenWandItem) {
            if (WissenWandItem.getMode(stack) != 4) {
                world.updateNeighbourForOutputSignal(pos, this);
                PacketUtils.SUpdateTileEntityPacket(tile);
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
                    PacketUtils.SUpdateTileEntityPacket(tile);
                    return InteractionResult.SUCCESS;
                } else {
                    tile.getItemHandler().setItem(0, stack);
                    player.getInventory().removeItem(player.getItemInHand(hand));
                    world.updateNeighbourForOutputSignal(pos, this);
                    PacketUtils.SUpdateTileEntityPacket(tile);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        if (!tile.getItemHandler().getItem(0).isEmpty()) {
            System.out.println(player.getInventory().getSlotWithRemainingSpace(tile.getItemHandler().getItem(0)));
            if (player.getInventory().getSlotWithRemainingSpace(tile.getItemHandler().getItem(0)) != -1 || player.getInventory().getFreeSlot() > 0) {
                player.getInventory().add(tile.getItemHandler().getItem(0).copy());
            } else {
                world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5F, pos.getY() + 1.0F, pos.getZ() + 0.5F, tile.getItemHandler().getItem(0).copy()));
            }
            tile.getItemHandler().removeItem(0, 1);
            world.updateNeighbourForOutputSignal(pos, this);
            PacketUtils.SUpdateTileEntityPacket(tile);
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
        return new LightEmitterTileEntity(pPos, pState);
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
        TileSimpleInventory tile = (TileSimpleInventory) level.getBlockEntity(pos);
        return AbstractContainerMenu.getRedstoneSignalFromContainer(tile.getItemHandler());
    }
}
