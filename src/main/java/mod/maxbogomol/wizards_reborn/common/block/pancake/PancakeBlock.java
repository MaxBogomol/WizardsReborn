package mod.maxbogomol.wizards_reborn.common.block.pancake;

import mod.maxbogomol.fluffy_fur.common.block.entity.BlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.common.item.food.JamItem;
import mod.maxbogomol.wizards_reborn.common.item.food.PancakeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PancakeBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock {

    private static final VoxelShape SHAPE_1 = Block.box(1, 0, 1, 15, 2, 15);
    private static final VoxelShape SHAPE_2 = Block.box(1, 0, 1, 15, 4, 15);
    private static final VoxelShape SHAPE_3 = Block.box(1, 0, 1, 15, 6, 15);
    private static final VoxelShape SHAPE_4 = Block.box(1, 0, 1, 15, 8, 15);
    private static final VoxelShape SHAPE_5 = Block.box(1, 0, 1, 15, 10, 15);
    private static final VoxelShape SHAPE_6 = Block.box(1, 0, 1, 15, 12, 15);
    private static final VoxelShape SHAPE_7 = Block.box(1, 0, 1, 15, 14, 15);
    private static final VoxelShape SHAPE_8 = Block.box(1, 0, 1, 15, 16, 15);

    private static final List<VoxelShape> SHAPES = new ArrayList<>();

    public static final IntegerProperty AMOUNT = IntegerProperty.create("amount", 0, 7);

    public static Map<Item, Map<Item, Item>> jamMap = new HashMap<>();

    static {
        SHAPES.add(SHAPE_1);
        SHAPES.add(SHAPE_2);
        SHAPES.add(SHAPE_3);
        SHAPES.add(SHAPE_4);
        SHAPES.add(SHAPE_5);
        SHAPES.add(SHAPE_6);
        SHAPES.add(SHAPE_7);
        SHAPES.add(SHAPE_8);
    }

    public PancakeBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(AMOUNT));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(AMOUNT);
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(AMOUNT, 0);
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

        return getBlockConnected(state).getOpposite() == direction && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
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
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof PancakeBlockEntity pancake) {
            return pancake.getItemHandler().getItem(pancake.getInventorySize() - 1);
        }
        return super.getCloneItemStack(level, pos, state);
    }

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = getBlockConnected(state).getOpposite();
        BlockState blockState = level.getBlockState(pos.relative(direction));
        if (blockState.getBlock().equals(this)) {
            return blockState.getValue(AMOUNT) == 7;
        }
        return Block.canSupportCenter(level, pos.relative(direction), direction.getOpposite());
    }

    protected static Direction getBlockConnected(BlockState state) {
        return Direction.UP;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        PancakeBlockEntity blockEntity = (PancakeBlockEntity) level.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();

        int slot = blockEntity.getInventorySize();
        Container container = blockEntity.getItemHandler();

        if (!stack.isEmpty()) {
            if (slot < 8) {
                if (stack.getItem() instanceof PancakeItem pancakeItem) {
                    if (container.getItem(slot).isEmpty()) {
                        if (stack.getCount() > 1) {
                            player.getItemInHand(hand).shrink(1);
                            stack.setCount(1);
                            container.setItem(slot, stack);
                        } else {
                            container.setItem(slot, stack);
                            player.getInventory().removeItem(player.getItemInHand(hand));
                        }
                        state = state.setValue(AMOUNT, blockEntity.getInventorySize() - 1);
                        level.setBlock(pos, state, 3);
                        level.updateNeighbourForOutputSignal(pos, this);
                        BlockEntityUpdate.packet(blockEntity);
                        level.playSound(null, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            if (stack.getItem() instanceof JamItem jamItem) {
                slot = slot - 1;
                if (jamMap.containsKey(container.getItem(slot).getItem())) {
                    Map<Item, Item> map = jamMap.get(container.getItem(slot).getItem());
                    if (map.containsKey(jamItem)) {
                        container.setItem(slot, new ItemStack(map.get(jamItem)));
                        jamItem.useJam(player.getItemInHand(hand), level, player);
                        level.playSound(null, pos, SoundEvents.HONEY_BLOCK_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        } else {
            if (slot > 0) {
                slot = slot - 1;
                if (!container.getItem(slot).isEmpty()) {
                    boolean eat = !player.isShiftKeyDown();
                    boolean can = !eat || player.canEat(false);

                    if (eat) {
                        BlockState blockState = level.getBlockState(pos.above());
                        can =  !(blockState.getBlock().equals(this));
                    }

                    if (can) {
                        if (!eat) {
                            BlockSimpleInventory.addHandPlayerItem(level, player, hand, stack, container.getItem(slot));
                        } else {
                            if (container.getItem(slot).getItem() instanceof PancakeItem pancakeItem) {
                                pancakeItem.applyEffect(container.getItem(slot), level, player);
                                player.eat(level, container.getItem(slot));
                            }
                        }
                        container.removeItem(slot, 1);
                        if (blockEntity.getInventorySize() > 0) {
                            state = state.setValue(AMOUNT, blockEntity.getInventorySize() - 1);
                            level.setBlock(pos, state, 3);
                            level.updateNeighbourForOutputSignal(pos, this);
                            BlockEntityUpdate.packet(blockEntity);
                        } else {
                            level.destroyBlock(pos, true);
                        }
                        if (!eat) {
                            level.playSound(null, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        } else {
                            level.playSound(null, pos, SoundEvents.HONEY_BLOCK_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PancakeBlockEntity(pos, state);
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
