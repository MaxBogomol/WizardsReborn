package mod.maxbogomol.wizards_reborn.common.block.placed_items;

import mod.maxbogomol.fluffy_fur.common.block.entity.BlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.common.item.IPlacedItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornItems;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlacedItemsBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {

    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;

    private static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 8, 13);

    public PlacedItemsBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false).setValue(ROTATION, Integer.valueOf(0)));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED).add(ROTATION);;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(ROTATION, Integer.valueOf(RotationSegment.convertToSegment(context.getRotation() + 180.0F)));
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof PlacedItemsBlockEntity items) {
                SimpleContainer inv = new SimpleContainer(5);
                for (int i = 0; i < 4; i++) {
                    inv.setItem(i, items.getItemHandler().getItem(i));
                }
                if (items.things) {
                    inv.setItem(4, new ItemStack(WizardsRebornItems.BUNCH_OF_THINGS.get()));
                }
                Containers.dropContents(world, pos, inv);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        PlacedItemsBlockEntity tile = (PlacedItemsBlockEntity) world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();

        int invSize = tile.getInventorySize();

        if (stack.getItem() instanceof WissenWandItem) {
            if (WissenWandItem.getMode(stack) != 4) {
                world.updateNeighbourForOutputSignal(pos, this);
                BlockEntityUpdate.packet(tile);
                return InteractionResult.SUCCESS;
            }
        }

        if (!player.isShiftKeyDown()) {
            if (invSize < 4) {
                int slot = invSize;
                if ((!stack.isEmpty()) && (tile.getItemHandler().getItem(slot).isEmpty()) && (stack.getItem() instanceof IPlacedItem || tile.things)) {
                    if (stack.getCount() > 1) {
                        player.getItemInHand(hand).setCount(stack.getCount() - 1);
                        stack.setCount(1);
                        tile.getItemHandler().setItem(slot, stack);
                    } else {
                        tile.getItemHandler().setItem(slot, stack);
                        player.getInventory().removeItem(player.getItemInHand(hand));
                    }
                    world.updateNeighbourForOutputSignal(pos, this);
                    BlockEntityUpdate.packet(tile);
                    world.playSound(null, pos, WizardsRebornSounds.PEDESTAL_INSERT.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    return InteractionResult.SUCCESS;
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
                    BlockEntityUpdate.packet(tile);
                    world.playSound(null, pos, WizardsRebornSounds.PEDESTAL_REMOVE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    if (tile.getInventorySize() <= 0) {
                        world.removeBlock(pos, false);
                    }
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
        return new PlacedItemsBlockEntity(pPos, pState);
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

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(ROTATION, Integer.valueOf(pRotation.rotate(pState.getValue(ROTATION), 16)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.setValue(ROTATION, Integer.valueOf(pMirror.mirror(pState.getValue(ROTATION), 16)));
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(WizardsRebornItems.BUNCH_OF_THINGS.get());
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return Block.canSupportCenter(pLevel, pPos.below(), Direction.UP);
    }
}
