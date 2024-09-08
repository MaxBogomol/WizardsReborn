package mod.maxbogomol.wizards_reborn.common.block.alchemy_machine;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.IPipeConnection;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipeConnection;
import mod.maxbogomol.wizards_reborn.client.gui.container.AlchemyMachineContainer;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
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
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class AlchemyMachineBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock, IPipeConnection {

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(2, 0, 2, 14, 2, 14),
            Block.box(4, 2, 4, 12, 14, 12),
            Block.box(2, 14, 2, 14, 16, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_N_FACE = Block.box(5, 5, 3, 11, 11, 4);
    private static final VoxelShape SHAPE_S_FACE = Block.box(5, 5, 12, 11, 11, 13);
    private static final VoxelShape SHAPE_W_FACE = Block.box(3, 5, 5, 4, 11, 11);
    private static final VoxelShape SHAPE_E_FACE = Block.box(12, 5, 5, 13, 11, 11);

    private static final VoxelShape SHAPE_N = Shapes.join(SHAPE, SHAPE_N_FACE, BooleanOp.OR);
    private static final VoxelShape SHAPE_S = Shapes.join(SHAPE, SHAPE_S_FACE, BooleanOp.OR);
    private static final VoxelShape SHAPE_W = Shapes.join(SHAPE, SHAPE_W_FACE, BooleanOp.OR);
    private static final VoxelShape SHAPE_E = Shapes.join(SHAPE, SHAPE_E_FACE, BooleanOp.OR);

    public static VoxelShape[] SHAPES_N = new VoxelShape[729];
    public static VoxelShape[] SHAPES_S = new VoxelShape[729];
    public static VoxelShape[] SHAPES_W = new VoxelShape[729];
    public static VoxelShape[] SHAPES_E = new VoxelShape[729];

    static {
        PipeBaseBlock.makeShapes(SHAPE_N, SHAPES_N);
        PipeBaseBlock.makeShapes(SHAPE_S, SHAPES_S);
        PipeBaseBlock.makeShapes(SHAPE_W, SHAPES_W);
        PipeBaseBlock.makeShapes(SHAPE_E, SHAPES_E);
    }

    public AlchemyMachineBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return switch (state.getValue(HORIZONTAL_FACING)) {
            case NORTH -> PipeBaseBlock.getShapeWithConnection(state, level, pos, ctx, SHAPES_N);
            case SOUTH -> PipeBaseBlock.getShapeWithConnection(state, level, pos, ctx, SHAPES_S);
            case WEST -> PipeBaseBlock.getShapeWithConnection(state, level, pos, ctx, SHAPES_W);
            case EAST -> PipeBaseBlock.getShapeWithConnection(state, level, pos, ctx, SHAPES_E);
            default -> SHAPE;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(BlockStateProperties.WATERLOGGED);
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
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AlchemyMachineBlockEntity machine) {
                SimpleContainer inv = new SimpleContainer(machine.itemHandler.getSlots() + 1);
                for (int i = 0; i < machine.itemHandler.getSlots(); i++) {
                    inv.setItem(i, machine.itemHandler.getStackInSlot(i));
                }
                inv.setItem(machine.itemHandler.getSlots(), machine.itemOutputHandler.getStackInSlot(0));
                Containers.dropContents(level, pos, inv);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack stack = player.getItemInHand(hand).copy();
            boolean clickable = WissenWandItem.isClickable(stack);

            if (clickable) {
                BlockEntity blockEntity = level.getBlockEntity(pos);

                MenuProvider containerProvider = createContainerProvider(level, pos);
                NetworkHooks.openScreen(((ServerPlayer) player), containerProvider, blockEntity.getBlockPos());
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.PASS;
    }

    public MenuProvider createContainerProvider(Level level, BlockPos pos) {
        return new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.translatable("gui.wizards_reborn.alchemy_machine.title");
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                return new AlchemyMachineContainer(i, level, pos, inventory, player);
            }
        };
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

        if (level.getBlockEntity(currentPos) instanceof PipeBaseBlockEntity pipe) {
            BlockEntity facingBE = level.getBlockEntity(currentPos);
            if (neighborState.is(WizardsRebornBlockTags.FLUID_PIPE_CONNECTION)) {
                if (facingBE instanceof PipeBaseBlockEntity && ((PipeBaseBlockEntity) facingBE).getConnection(direction.getOpposite()) == PipeConnection.DISABLED) {
                    pipe.setConnection(direction, PipeConnection.NONE);
                } else {
                    pipe.setConnection(direction, PipeConnection.PIPE);
                }
            } else {
                pipe.setConnection(direction, PipeConnection.NONE);
            }
        }

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AlchemyMachineBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Override
    public PipeConnection getPipeConnection(BlockState state, Direction direction) {
        return PipeConnection.END;
    }
}
