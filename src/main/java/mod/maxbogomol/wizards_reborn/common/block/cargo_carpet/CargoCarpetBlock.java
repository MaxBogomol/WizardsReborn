package mod.maxbogomol.wizards_reborn.common.block.cargo_carpet;

import mod.maxbogomol.fluffy_fur.common.block.entity.BlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.CargoCarpetItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CargoCarpetBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock {

    private static final VoxelShape SHAPE_X = Block.box(0, 0, 3, 16, 10, 13);
    private static final VoxelShape SHAPE_Z = Block.box(3, 0, 0, 13, 10, 16);
    private static final VoxelShape SHAPE_OPEN = Block.box(0, 0, 0, 16, 1, 16);

    private static final VoxelShape SHAPE_PART_X_UP = Block.box(8, 0, 3, 16, 10, 13);
    private static final VoxelShape SHAPE_PART_Z_UP = Block.box(3, 0, 8, 13, 10, 16);
    private static final VoxelShape SHAPE_PART_X_DOWN = Block.box(0, 0, 3, 8, 10, 13);
    private static final VoxelShape SHAPE_PART_Z_DOWN = Block.box(3, 0, 0, 13, 10, 8);

    private static final VoxelShape SHAPE_PART_OPEN_UP_UP = Block.box(7, 0, 7, 16, 1, 16);
    private static final VoxelShape SHAPE_PART_OPEN_UP_CENTER = Block.box(7, 0, 0, 16, 1, 16);
    private static final VoxelShape SHAPE_PART_OPEN_UP_DOWN = Block.box(7, 0, 0, 16, 1, 9);
    private static final VoxelShape SHAPE_PART_OPEN_CENTER_UP = Block.box(0, 0, 7, 16, 1, 16);
    private static final VoxelShape SHAPE_PART_OPEN_CENTER_DOWN = Block.box(0, 0, 0, 16, 1, 9);
    private static final VoxelShape SHAPE_PART_OPEN_DOWN_UP = Block.box(0, 0, 7, 9, 1, 16);
    private static final VoxelShape SHAPE_PART_OPEN_DOWN_CENTER = Block.box(0, 0, 0, 9, 1, 16);
    private static final VoxelShape SHAPE_PART_OPEN_DOWN_DOWN = Block.box(0, 0, 0, 9, 1, 9);

    public static final EnumProperty<CargoCarpedPart> CARGO_CARPET_PART_1 = EnumProperty.create("cargo_carpet_part_1", CargoCarpedPart.class);
    public static final EnumProperty<CargoCarpedPart> CARGO_CARPET_PART_2 = EnumProperty.create("cargo_carpet_part_2", CargoCarpedPart.class);

    public CargoCarpetBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false).setValue(CARGO_CARPET_PART_1, CargoCarpedPart.CENTER).setValue(CARGO_CARPET_PART_2, CargoCarpedPart.CENTER).setValue(BlockStateProperties.OPEN, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(BlockStateProperties.OPEN)) {
            CargoCarpedPart cargoCarpedPart1 = state.getValue(CARGO_CARPET_PART_1);
            CargoCarpedPart cargoCarpedPart2 = state.getValue(CARGO_CARPET_PART_2);
            switch (cargoCarpedPart1) {
                case UP -> {
                    return switch (cargoCarpedPart2) {
                        case UP -> SHAPE_PART_OPEN_UP_UP;
                        case CENTER -> SHAPE_PART_OPEN_UP_CENTER;
                        case DOWN -> SHAPE_PART_OPEN_UP_DOWN;
                    };
                }
                case CENTER -> {
                    return switch (cargoCarpedPart2) {
                        case UP -> SHAPE_PART_OPEN_CENTER_UP;
                        case CENTER -> SHAPE_OPEN;
                        case DOWN -> SHAPE_PART_OPEN_CENTER_DOWN;
                    };
                }
                case DOWN -> {
                    return switch (cargoCarpedPart2) {
                        case UP -> SHAPE_PART_OPEN_DOWN_UP;
                        case CENTER -> SHAPE_PART_OPEN_DOWN_CENTER;
                        case DOWN -> SHAPE_PART_OPEN_DOWN_DOWN;
                    };
                }
            }
            return SHAPE_OPEN;
        } else {
            CargoCarpedPart cargoCarpedPart1 = state.getValue(CARGO_CARPET_PART_1);
            switch (cargoCarpedPart1) {
                case UP -> {
                    return switch (state.getValue(FACING)) {
                        case NORTH -> SHAPE_PART_X_DOWN;
                        case SOUTH -> SHAPE_PART_X_UP;
                        case WEST -> SHAPE_PART_Z_UP;
                        case EAST -> SHAPE_PART_Z_DOWN;
                        default -> SHAPE_PART_X_UP;
                    };
                }
                case CENTER -> {
                    return switch (state.getValue(FACING)) {
                        case NORTH -> SHAPE_X;
                        case SOUTH -> SHAPE_X;
                        case WEST -> SHAPE_Z;
                        case EAST -> SHAPE_Z;
                        default -> SHAPE_X;
                    };
                }
                case DOWN -> {
                    return switch (state.getValue(FACING)) {
                        case NORTH -> SHAPE_PART_X_UP;
                        case SOUTH -> SHAPE_PART_X_DOWN;
                        case WEST -> SHAPE_PART_Z_DOWN;
                        case EAST -> SHAPE_PART_Z_UP;
                        default -> SHAPE_PART_X_DOWN;
                    };
                }
            }
        }
        return Shapes.block();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(CARGO_CARPET_PART_1);
        builder.add(CARGO_CARPET_PART_2);
        builder.add(BlockStateProperties.OPEN);
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        Direction direction = context.getHorizontalDirection();
        BlockPos blockPos = context.getClickedPos();
        BlockPos blockPosUp = blockPos.relative(direction.getClockWise());
        BlockPos blockPosDown = blockPos.relative(direction.getClockWise().getOpposite());
        Level level = context.getLevel();
        Direction directionDown = Direction.DOWN;
        boolean place = level.getBlockState(blockPosUp).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(blockPosUp) && Block.canSupportCenter(level, blockPosUp.relative(directionDown), directionDown.getOpposite())
                && level.getBlockState(blockPosDown).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(blockPosDown)  && Block.canSupportCenter(level, blockPosDown.relative(directionDown), directionDown.getOpposite());
        return place ? this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER) : null;
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

        return !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (!isCenter(state)) {
            BlockPos centerPos = getCenterPos(pos, state);
            BlockState centerState = level.getBlockState(centerPos);
            if (centerState.getBlock().equals(this)) {
                if (!isCenter(centerState)) return false;
            } else {
                return false;
            }
        }
        Direction direction = Direction.DOWN;
        return Block.canSupportCenter(level, pos.relative(direction), direction.getOpposite());
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CargoCarpetBlockEntity carpetBlockEntity) {
                if (!state.getValue(BlockStateProperties.OPEN)) {
                    Containers.dropContents(level, pos, (carpetBlockEntity).getItemHandler());
                } else {
                    ItemStack stack = carpetBlockEntity.getItemHandler().getItem(0);
                    if (stack.getItem() instanceof CargoCarpetItem) {
                        SimpleContainer container = CargoCarpetItem.getInventory(stack);
                        Containers.dropContents(level, pos, container);
                        for (int i = 0; i < 20; i++) {
                            container.setItem(i, ItemStack.EMPTY);
                        }
                        Containers.dropContents(level, pos, (carpetBlockEntity).getItemHandler());
                        level.playSound(null, pos, SoundEvents.BUNDLE_DROP_CONTENTS, SoundSource.BLOCKS, 1.0f, 1.0f);
                    }
                }
            }
            if (!isCenter(state)) {
                if (!state.getValue(BlockStateProperties.OPEN)) {
                    BlockPos blockPosUp = pos.relative(state.getValue(FACING).getClockWise().getOpposite());
                    BlockPos blockPosDown = pos.relative(state.getValue(FACING).getClockWise());
                    if (level.getBlockState(blockPosUp).getBlock().equals(this) && getCenterPos(pos, state).equals(blockPosUp)) level.destroyBlock(blockPosUp, true);
                    if (level.getBlockState(blockPosDown).getBlock().equals(this)&& getCenterPos(pos, state).equals(blockPosDown)) level.destroyBlock(blockPosDown, true);
                } else {
                    int x = -getPartOffset(state.getValue(CARGO_CARPET_PART_1));
                    int y = -getPartOffset(state.getValue(CARGO_CARPET_PART_2));
                    BlockState centerState = level.getBlockState(pos.offset(x, 0, y));
                    if (centerState.getBlock().equals(this) && centerState.getValue(BlockStateProperties.OPEN)) level.destroyBlock(pos.offset(x, 0, y), true);
                }
            } else {
                level.updateNeighborsAt(pos.offset(-1, 0, -1), this);
                level.updateNeighborsAt(pos.offset(-1, 0, 1), this);
                level.updateNeighborsAt(pos.offset(1, 0, -1), this);
                level.updateNeighborsAt(pos.offset(1, 0, 1), this);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof CargoCarpetBlockEntity carpet) {
            return carpet.getItemHandler().getItem(0);
        }
        return super.getCloneItemStack(level, pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        CargoCarpetBlockEntity blockEntity = (CargoCarpetBlockEntity) level.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();
        if (isCenter(state)) {
            if (stack.isEmpty() && player.isShiftKeyDown()) {
                if (!state.getValue(BlockStateProperties.OPEN)) {
                    if (canOpen(level, pos)) {
                        open(level, pos);
                        level.playSound(null, pos, SoundEvents.BUNDLE_REMOVE_ONE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        return InteractionResult.SUCCESS;
                    }
                } else {
                    close(level, pos);
                    level.playSound(null, pos, SoundEvents.BUNDLE_INSERT, SoundSource.BLOCKS, 1.0f, 1.0f);
                    return InteractionResult.SUCCESS;
                }
            } else {
                if (state.getValue(BlockStateProperties.OPEN)) {
                    if (level.isClientSide()) {
                        return InteractionResult.SUCCESS;
                    } else {
                        NetworkHooks.openScreen(((ServerPlayer) player), blockEntity, blockEntity.getBlockPos());
                        return InteractionResult.CONSUME;
                    }
                }
            }
        } else {
            BlockPos centerPos = getCenterPos(pos, state);
            BlockState centerState = level.getBlockState(centerPos);
            if (centerState.getBlock().equals(this)) return centerState.getBlock().use(centerState, level, centerPos, player, hand, hit);
        }

        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CargoCarpetBlockEntity(pos, state);
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

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.fallOn(level, state, pos, entity, fallDistance * 0.25F);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide()) {
            BlockPos blockPosUp = pos.relative(state.getValue(FACING).getClockWise());
            level.setBlock(blockPosUp, state.setValue(CARGO_CARPET_PART_1, CargoCarpedPart.UP), 3);
            BlockPos blockPosDown = pos.relative(state.getValue(FACING).getClockWise().getOpposite());
            level.setBlock(blockPosDown, state.setValue(CARGO_CARPET_PART_1, CargoCarpedPart.DOWN), 3);
            level.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, 3);
        }
    }

    public boolean isCenter(BlockState state) {
        return state.getValue(CARGO_CARPET_PART_1) == CargoCarpedPart.CENTER && state.getValue(CARGO_CARPET_PART_2) == CargoCarpedPart.CENTER;
    }

    public int getPartOffset(CargoCarpedPart part) {
        return switch (part) {
            case UP -> -1;
            case CENTER -> 0;
            case DOWN -> 1;
        };
    }

    public boolean canOpen(Level level, BlockPos pos) {
        if (!blockCanSupport(level, pos.offset(-1, 0, -1), pos)) return false;
        if (!blockCanSupport(level, pos.offset(-1, 0, 0), pos)) return false;
        if (!blockCanSupport(level, pos.offset(-1, 0, 1), pos)) return false;
        if (!blockCanSupport(level, pos.offset(0, 0, -1), pos)) return false;
        if (!blockCanSupport(level, pos.offset(0, 0, 1), pos)) return false;
        if (!blockCanSupport(level, pos.offset(1, 0, -1), pos)) return false;
        if (!blockCanSupport(level, pos.offset(1, 0, 0), pos)) return false;
        if (!blockCanSupport(level, pos.offset(1, 0, 1), pos)) return false;
        return true;
    }

    public void open(Level level, BlockPos pos) {
        openBlock(level, pos.offset(-1, 0, -1), CargoCarpedPart.UP, CargoCarpedPart.UP);
        openBlock(level, pos.offset(-1, 0, 0), CargoCarpedPart.UP, CargoCarpedPart.CENTER);
        openBlock(level, pos.offset(-1, 0, 1), CargoCarpedPart.UP, CargoCarpedPart.DOWN);
        openBlock(level, pos.offset(0, 0, -1), CargoCarpedPart.CENTER, CargoCarpedPart.UP);
        openBlock(level, pos, CargoCarpedPart.CENTER, CargoCarpedPart.CENTER);
        openBlock(level, pos.offset(0, 0, 1), CargoCarpedPart.CENTER, CargoCarpedPart.DOWN);
        openBlock(level, pos.offset(1, 0, -1), CargoCarpedPart.DOWN, CargoCarpedPart.UP);
        openBlock(level, pos.offset(1, 0, 0), CargoCarpedPart.DOWN, CargoCarpedPart.CENTER);
        openBlock(level, pos.offset(1, 0, 1), CargoCarpedPart.DOWN, CargoCarpedPart.DOWN);
    }

    public void openBlock(Level level, BlockPos pos, CargoCarpedPart part1, CargoCarpedPart part2) {
        BlockState state = level.getBlockState(pos);
        if (!state.getBlock().equals(this)) {
            level.setBlock(pos, defaultBlockState(), 3);
            state = level.getBlockState(pos);
        }
        level.setBlock(pos, state.setValue(BlockStateProperties.OPEN, true).setValue(CARGO_CARPET_PART_1, part1).setValue(CARGO_CARPET_PART_2, part2), 3);
        level.blockUpdated(pos, Blocks.AIR);
        state.updateNeighbourShapes(level, pos, 3);
    }

    public void close(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        level.setBlock(pos, state.setValue(BlockStateProperties.OPEN, false), 3);

        BlockPos blockPosUp = pos.relative(state.getValue(FACING).getClockWise());
        level.setBlock(blockPosUp, state.setValue(CARGO_CARPET_PART_1, CargoCarpedPart.UP).setValue(BlockStateProperties.OPEN, false), 3);
        BlockPos blockPosDown = pos.relative(state.getValue(FACING).getClockWise().getOpposite());
        level.setBlock(blockPosDown, state.setValue(CARGO_CARPET_PART_1, CargoCarpedPart.DOWN).setValue(BlockStateProperties.OPEN, false), 3);

        level.blockUpdated(pos, Blocks.AIR);
        state.updateNeighbourShapes(level, pos, 3);

        removeBlock(level, blockPosUp.relative(state.getValue(FACING).getClockWise().getClockWise()));
        removeBlock(level, blockPosUp.relative(state.getValue(FACING).getClockWise().getClockWise().getOpposite()));
        removeBlock(level, pos.relative(state.getValue(FACING).getClockWise().getClockWise()));
        removeBlock(level, pos.relative(state.getValue(FACING).getClockWise().getClockWise().getOpposite()));
        removeBlock(level, blockPosDown.relative(state.getValue(FACING).getClockWise().getClockWise()));
        removeBlock(level, blockPosDown.relative(state.getValue(FACING).getClockWise().getClockWise().getOpposite()));
    }

    public void removeBlock(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock().equals(this)) {
            level.removeBlock(pos, false);
            level.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, 3);
        }
    }

    public BlockPos getCenterPos(BlockPos pos, BlockState state) {
        if (!state.getValue(BlockStateProperties.OPEN)) {
            CargoCarpedPart cargoCarpedPart1 = state.getValue(CARGO_CARPET_PART_1);
            if (cargoCarpedPart1 == CargoCarpedPart.UP) {
                return pos.relative(state.getValue(FACING).getClockWise().getOpposite());
            }
            if (cargoCarpedPart1 == CargoCarpedPart.DOWN) {
                return pos.relative(state.getValue(FACING).getClockWise());
            }
        }
        int x = -getPartOffset(state.getValue(CARGO_CARPET_PART_1));
        int y = -getPartOffset(state.getValue(CARGO_CARPET_PART_2));
        return pos.offset(x, 0, y);
    }

    public boolean blockCanSupport(Level level, BlockPos pos, BlockPos centerPos) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock().equals(this)) {
            return getCenterPos(pos, state).equals(centerPos);
        }
        Direction direction = Direction.DOWN;
        return state.isAir() && level.getWorldBorder().isWithinBounds(pos) && Block.canSupportCenter(level, pos.relative(direction), direction.getOpposite());
    }
}
