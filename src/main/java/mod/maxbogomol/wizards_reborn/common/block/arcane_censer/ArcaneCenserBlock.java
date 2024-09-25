package mod.maxbogomol.wizards_reborn.common.block.arcane_censer;

import mod.maxbogomol.fluffy_fur.common.block.entity.BlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.SteamUtil;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.block.SteamBreakPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.CenserRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
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
import java.util.Optional;
import java.util.stream.Stream;

public class ArcaneCenserBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock  {

    private static final VoxelShape SHAPE_SHELL  = Stream.of(
            Block.box(6, 0, 6, 10, 1, 10),
            Block.box(4, 1, 4, 12, 3, 12),
            Block.box(5, 3, 5, 11, 10, 11),
            Block.box(7, 10, 7, 9, 11, 9),
            Block.box(6, 11, 6, 10, 15, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_HOLE = Block.box(6, 3, 6, 10, 9, 10);
    private static final VoxelShape SHAPE = Shapes.join(SHAPE_SHELL, SHAPE_HOLE, BooleanOp.ONLY_FIRST);

    public ArcaneCenserBlock(Properties properties) {
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
            if (blockEntity instanceof ArcaneCenserBlockEntity censerBlockEntity) {
                SimpleContainer inv = new SimpleContainer(censerBlockEntity.getInventorySize());
                for (int i = 0; i < censerBlockEntity.getInventorySize(); i++) {
                    ItemStack item = censerBlockEntity.getItem(i);
                    if (ArcaneCenserBlockEntity.getItemBurnCenser(item) <= 0) {
                        inv.addItem(item);
                    }
                }
                Containers.dropContents(level, pos, inv);
            }
            if (blockEntity instanceof ISteamBlockEntity steamBlockEntity) {
                if (steamBlockEntity.getMaxSteam() > 0) {
                    float amount = (float) steamBlockEntity.getSteam() / (float) steamBlockEntity.getMaxSteam();
                    PacketHandler.sendToTracking(level, pos, new SteamBreakPacket(pos, 15 * amount));
                }
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ArcaneCenserBlockEntity blockEntity = (ArcaneCenserBlockEntity) level.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();

        int size = blockEntity.getInventorySize();

        SimpleContainer inv = new SimpleContainer(1);
        inv.setItem(0, stack);
        Optional<CenserRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.CENSER.get(), inv, level);

        Container container = blockEntity.getItemHandler();
        if (!player.isShiftKeyDown()) {
            if (recipe.isPresent()) {
                if (size < 8) {
                    if ((!stack.isEmpty()) && (container).getItem(size).isEmpty()) {
                        if (stack.getCount() > 1) {
                            player.getItemInHand(hand).shrink(1);
                            stack.setCount(1);
                            container.setItem(size, stack);
                        } else {
                            container.setItem(size, stack);
                            player.getInventory().removeItem(player.getItemInHand(hand));
                        }
                        level.updateNeighbourForOutputSignal(pos, this);
                        BlockEntityUpdate.packet(blockEntity);
                        level.playSound(null, pos, WizardsRebornSounds.PEDESTAL_INSERT.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        } else {
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    int slot = size - i - 1;
                    if (!container.getItem(slot).isEmpty()) {
                        if (ArcaneCenserBlockEntity.getItemBurnCenser(container.getItem(slot)) <= 0) {
                            BlockSimpleInventory.addHandPlayerItem(level, player, hand, stack, container.getItem(slot));
                            container.removeItem(slot, 1);
                            level.updateNeighbourForOutputSignal(pos, this);
                            blockEntity.sortItems();
                            BlockEntityUpdate.packet(blockEntity);
                            level.playSound(null, pos, WizardsRebornSounds.PEDESTAL_REMOVE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }

        if (blockEntity.cooldown <= 0 && SteamUtil.canRemoveSteam(blockEntity.steam, 150) && player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && !player.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                blockEntity.smoke(player);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ArcaneCenserBlockEntity(pos, state);
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
