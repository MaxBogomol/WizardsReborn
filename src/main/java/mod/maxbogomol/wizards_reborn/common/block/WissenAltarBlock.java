package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.recipe.WissenAltarRecipe;
import mod.maxbogomol.wizards_reborn.common.tileentity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.TileSimpleInventory;
import mod.maxbogomol.wizards_reborn.common.tileentity.WissenAltarTileEntity;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.Containers;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;

import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WissenAltarBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock  {

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(5, 13, 5, 11, 14, 11),
            Block.box(2, 11, 2, 14, 13, 14),
            Block.box(4, 2, 4, 12, 11, 12),
            Block.box(2, 0, 2, 14, 2, 14),
            Block.box(1, 9, 1, 3, 16, 3),
            Block.box(13, 9, 1, 15, 16, 3),
            Block.box(1, 9, 13, 3, 16, 15),
            Block.box(13, 9, 13, 15, 16, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public WissenAltarBlock(Properties properties) {
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
        builder.add(FACING);
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileSimpleInventory) {
                ((TileSimpleInventory) tile).getItemHandler().removeItemNoUpdate(2);
                Containers.dropContents(world, pos, ((TileSimpleInventory) tile).getItemHandler());
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        WissenAltarTileEntity altar = (WissenAltarTileEntity) world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();

        if (stack.getItem() instanceof WissenWandItem) {
            if (WissenWandItem.getMode(stack) != 4) {
                world.updateNeighbourForOutputSignal(pos, this);
                PacketUtils.SUpdateTileEntityPacket(altar);
                return InteractionResult.SUCCESS;
            }
        }

        if (stack.getItem() instanceof IWissenItem) {
            if (altar.getItemHandler().getItem(0).isEmpty()) {
                altar.getItemHandler().setItem(0, stack);
                player.getInventory().removeItem(player.getItemInHand(hand));
                world.updateNeighbourForOutputSignal(pos, this);
                PacketUtils.SUpdateTileEntityPacket(altar);
                return InteractionResult.SUCCESS;
            }
        }

        SimpleContainer inv = new SimpleContainer(1);
        inv.setItem(0, stack);
        int wissenInItem = world.getRecipeManager().getRecipeFor(WizardsReborn.WISSEN_ALTAR_RECIPE.get(), inv, world)
                .map(WissenAltarRecipe::getRecipeWissen)
                .orElse(0);
        if (wissenInItem > 0) {
            if (altar.getItemHandler().getItem(1).isEmpty()) {
                altar.getItemHandler().setItem(1, stack);
                player.getInventory().removeItem(player.getItemInHand(hand));
                world.updateNeighbourForOutputSignal(pos, this);
                PacketUtils.SUpdateTileEntityPacket(altar);
                return InteractionResult.SUCCESS;
            } else {
                if (altar.getItemHandler().getItem(1).equals(stack)
                        && altar.getItemHandler().getItem(1).getCount() + stack.getCount() <= altar.getItemHandler().getItem(1).getMaxStackSize()) {
                    altar.getItemHandler().getItem(1).setCount(altar.getItemHandler().getItem(1).getCount() + stack.getCount());
                    world.updateNeighbourForOutputSignal(pos, this);
                    PacketUtils.SUpdateTileEntityPacket(altar);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        if (!altar.getItemHandler().getItem(0).isEmpty()) {
            player.getInventory().add(altar.getItemHandler().getItem(0).copy());
            altar.getItemHandler().removeItemNoUpdate(0);
            world.updateNeighbourForOutputSignal(pos, this);
            PacketUtils.SUpdateTileEntityPacket(altar);
            return InteractionResult.SUCCESS;
        } else {
            if (!altar.getItemHandler().getItem(1).isEmpty()) {
                player.getInventory().add(altar.getItemHandler().getItem(1).copy());
                altar.getItemHandler().removeItemNoUpdate(1);
                world.updateNeighbourForOutputSignal(pos, this);
                PacketUtils.SUpdateTileEntityPacket(altar);
                return InteractionResult.SUCCESS;
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
        return new WissenAltarTileEntity(pPos, pState);
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
