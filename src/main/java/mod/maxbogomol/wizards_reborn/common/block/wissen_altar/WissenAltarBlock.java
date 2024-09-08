package mod.maxbogomol.wizards_reborn.common.block.wissen_altar;

import mod.maxbogomol.fluffy_fur.common.block.entity.BlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.recipe.WissenAltarRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
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
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof BlockSimpleInventory) {
                ((BlockSimpleInventory) tile).getItemHandler().removeItem(2, 64);
                Containers.dropContents(level, pos, ((BlockSimpleInventory) tile).getItemHandler());
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        WissenAltarBlockEntity altar = (WissenAltarBlockEntity) level.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();

        if (stack.getItem() instanceof WissenWandItem) {
            if (WissenWandItem.getMode(stack) != 4) {
                level.updateNeighbourForOutputSignal(pos, this);
                BlockEntityUpdate.packet(altar);
                return InteractionResult.SUCCESS;
            }
        }

        if (stack.getItem() instanceof IWissenItem) {
            if (altar.getItemHandler().getItem(0).isEmpty()) {
                altar.getItemHandler().setItem(0, stack);
                player.getInventory().removeItem(player.getItemInHand(hand));
                level.updateNeighbourForOutputSignal(pos, this);
                BlockEntityUpdate.packet(altar);
                level.playSound(null, pos, WizardsRebornSounds.PEDESTAL_INSERT.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            }
        }

        SimpleContainer inv = new SimpleContainer(1);
        inv.setItem(0, stack);
        int wissenInItem = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.WISSEN_ALTAR.get(), inv, level)
                .map(WissenAltarRecipe::getRecipeWissen)
                .orElse(0);
        if (wissenInItem > 0) {
            if (altar.getItemHandler().getItem(1).isEmpty()) {
                altar.getItemHandler().setItem(1, stack.copy());
                player.getInventory().removeItem(player.getItemInHand(hand));
                level.updateNeighbourForOutputSignal(pos, this);
                BlockEntityUpdate.packet(altar);
                level.playSound(null, pos, WizardsRebornSounds.PEDESTAL_INSERT.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            } else {
                if (altar.getItemHandler().getItem(1).equals(stack)
                        && altar.getItemHandler().getItem(1).getCount() + stack.getCount() <= altar.getItemHandler().getItem(1).getMaxStackSize()) {
                    altar.getItemHandler().getItem(1).setCount(altar.getItemHandler().getItem(1).getCount() + stack.getCount());
                    level.updateNeighbourForOutputSignal(pos, this);
                    BlockEntityUpdate.packet(altar);
                    level.playSound(null, pos, WizardsRebornSounds.PEDESTAL_INSERT.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        if (!altar.getItemHandler().getItem(0).isEmpty()) {
            if (player.getInventory().getSlotWithRemainingSpace(altar.getItemHandler().getItem(0)) != -1 || player.getInventory().getFreeSlot() > -1) {
                player.getInventory().add(altar.getItemHandler().getItem(0).copy());
            } else {
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5F, pos.getY() + 1.0F, pos.getZ() + 0.5F, altar.getItemHandler().getItem(0).copy()));
            }
            altar.getItemHandler().removeItem(0, 1);
            level.updateNeighbourForOutputSignal(pos, this);
            BlockEntityUpdate.packet(altar);
            level.playSound(null, pos, WizardsRebornSounds.PEDESTAL_REMOVE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            return InteractionResult.SUCCESS;
        } else {
            if (!altar.getItemHandler().getItem(1).isEmpty()) {
                if (player.getInventory().getSlotWithRemainingSpace(altar.getItemHandler().getItem(1)) != -1 || player.getInventory().getFreeSlot() > -1) {
                    player.getInventory().add(altar.getItemHandler().getItem(1).copy());
                } else {
                    level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5F, pos.getY() + 1.0F, pos.getZ() + 0.5F, altar.getItemHandler().getItem(1).copy()));
                }
                altar.getItemHandler().removeItem(1, 64);
                level.updateNeighbourForOutputSignal(pos, this);
                BlockEntityUpdate.packet(altar);
                level.playSound(null, pos, WizardsRebornSounds.PEDESTAL_REMOVE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
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
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WissenAltarBlockEntity(pos, state);
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
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockSimpleInventory tile = (BlockSimpleInventory) level.getBlockEntity(pos);
        return AbstractContainerMenu.getRedstoneSignalFromContainer(tile.getItemHandler());
    }
}
