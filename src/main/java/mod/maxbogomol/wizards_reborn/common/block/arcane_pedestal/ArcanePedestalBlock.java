package mod.maxbogomol.wizards_reborn.common.block.arcane_pedestal;

import mod.maxbogomol.fluffy_fur.common.block.entity.BlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.book.CustomBook;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.common.item.ArcanemiconItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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

public class ArcanePedestalBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(5, 13, 5, 11, 14, 11),
            Block.box(2, 11, 2, 14, 13, 14),
            Block.box(4, 2, 4, 12, 11, 12),
            Block.box(2, 0, 2, 14, 2, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public ArcanePedestalBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
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

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BlockSimpleInventory blockSimpleInventory) {
                Containers.dropContents(level, pos, blockSimpleInventory.getItemHandler());
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ArcanePedestalBlockEntity blockEntity = (ArcanePedestalBlockEntity) level.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();

        if (player.isShiftKeyDown()) {
            if (stack.getItem() instanceof ArcanemiconItem) {
                return InteractionResult.PASS;
            }
        }

        Container container = blockEntity.getItemHandler();
        CustomBook book = blockEntity.getBook();
        if (book != null) {
            if (player.isShiftKeyDown() && !container.getItem(0).isEmpty()) {
                BlockSimpleInventory.addHandPlayerItem(level, player, hand, stack, container.getItem(0));
                container.removeItem(0, 1);
                level.updateNeighbourForOutputSignal(pos, this);
                BlockEntityUpdate.packet(blockEntity);
                level.playSound(null, pos, WizardsRebornSounds.PEDESTAL_REMOVE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            }
            if (level.isClientSide()) {
                book.openGui(level, pos.getCenter(), container.getItem(0));
            }
            return InteractionResult.SUCCESS;
        }

        if ((!stack.isEmpty()) && (container.getItem(0).isEmpty())) {
            if (stack.getCount() > 1) {
                player.getItemInHand(hand).shrink(1);
                stack.setCount(1);
                container.setItem(0, stack);
            } else {
                container.setItem(0, stack);
                player.getInventory().removeItem(player.getItemInHand(hand));
            }
            level.updateNeighbourForOutputSignal(pos, this);
            BlockEntityUpdate.packet(blockEntity);
            level.playSound(null, pos, WizardsRebornSounds.PEDESTAL_INSERT.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            return InteractionResult.SUCCESS;
        }

        if (!container.getItem(0).isEmpty()) {
            BlockSimpleInventory.addHandPlayerItem(level, player, hand, stack, container.getItem(0));
            container.removeItem(0, 1);
            level.updateNeighbourForOutputSignal(pos, this);
            BlockEntityUpdate.packet(blockEntity);
            level.playSound(null, pos, WizardsRebornSounds.PEDESTAL_REMOVE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ArcanePedestalBlockEntity(pos, state);
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
        BlockSimpleInventory blockEntity = (BlockSimpleInventory) level.getBlockEntity(pos);
        return AbstractContainerMenu.getRedstoneSignalFromContainer(blockEntity.getItemHandler());
    }
}
