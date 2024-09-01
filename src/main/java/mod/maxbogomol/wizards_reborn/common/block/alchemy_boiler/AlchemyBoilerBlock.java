package mod.maxbogomol.wizards_reborn.common.block.alchemy_boiler;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.IPipeConnection;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipeConnection;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_machine.AlchemyMachineBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.pipe.TinyPipeBaseBlock;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
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

public class AlchemyBoilerBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock, IPipeConnection {

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(5, 0, 5, 11, 3, 11),
            Block.box(2, 3, 2, 14, 12, 14),
            Block.box(4, 12, 4, 12, 14, 12),
            Block.box(3, 14, 3, 13, 16, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_N_FACE = Stream.of(
            Block.box(5, 5, 1, 11, 11, 2),
            Block.box(6, 6, 14, 10, 10, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S_FACE = Stream.of(
            Block.box(5, 5, 14, 11, 11, 15),
            Block.box(6, 6, 1, 10, 10, 2)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W_FACE = Stream.of(
            Block.box(1, 5, 5, 2, 11, 11),
            Block.box(14, 6, 6, 15, 10, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E_FACE = Stream.of(
            Block.box(14, 5, 5, 15, 11, 11),
            Block.box(1, 6, 6, 2, 10, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_N = Shapes.join(SHAPE, SHAPE_N_FACE, BooleanOp.OR);
    private static final VoxelShape SHAPE_S = Shapes.join(SHAPE, SHAPE_S_FACE, BooleanOp.OR);
    private static final VoxelShape SHAPE_W = Shapes.join(SHAPE, SHAPE_W_FACE, BooleanOp.OR);
    private static final VoxelShape SHAPE_E = Shapes.join(SHAPE, SHAPE_E_FACE, BooleanOp.OR);

    public static VoxelShape[] SHAPES_N = new VoxelShape[729];
    public static VoxelShape[] SHAPES_S = new VoxelShape[729];
    public static VoxelShape[] SHAPES_W = new VoxelShape[729];
    public static VoxelShape[] SHAPES_E = new VoxelShape[729];

    static {
        TinyPipeBaseBlock.makeShapes(SHAPE_N, SHAPES_N);
        TinyPipeBaseBlock.makeShapes(SHAPE_S, SHAPES_S);
        TinyPipeBaseBlock.makeShapes(SHAPE_W, SHAPES_W);
        TinyPipeBaseBlock.makeShapes(SHAPE_E, SHAPES_E);
    }

    public AlchemyBoilerBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        switch (state.getValue(HORIZONTAL_FACING)) {
            case NORTH:
                return PipeBaseBlock.getShapeWithConnection(state, world, pos, ctx, SHAPES_N);
            case SOUTH:
                return PipeBaseBlock.getShapeWithConnection(state, world, pos, ctx, SHAPES_S);
            case WEST:
                return PipeBaseBlock.getShapeWithConnection(state, world, pos, ctx, SHAPES_W);
            case EAST:
                return PipeBaseBlock.getShapeWithConnection(state, world, pos, ctx, SHAPES_E);
            default:
                return SHAPE;
        }
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
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand).copy();
        boolean isWand = false;

        if (stack.getItem() instanceof WissenWandItem) {
            if (WissenWandItem.getMode(stack) != 4) {
                isWand = true;
            }
        }

        if (!isWand) {
            BlockEntity tileEntity = world.getBlockEntity(pos.below());

            if (world.getBlockState(pos.below()).getBlock() instanceof AlchemyMachineBlock block) {
                if (!world.isClientSide) {
                    MenuProvider containerProvider = block.createContainerProvider(world, pos.below());
                    NetworkHooks.openScreen(((ServerPlayer) player), containerProvider, tileEntity.getBlockPos());
                }
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

        if (pLevel.getBlockEntity(pCurrentPos) instanceof PipeBaseBlockEntity pipe) {
            BlockEntity facingBE = pLevel.getBlockEntity(pCurrentPos);
            if (pNeighborState.is(WizardsRebornBlockTags.STEAM_PIPE_CONNECTION) || pNeighborState.is(WizardsRebornBlockTags.FLUID_PIPE_CONNECTION)) {
                if (facingBE instanceof PipeBaseBlockEntity && ((PipeBaseBlockEntity) facingBE).getConnection(pDirection.getOpposite()) == PipeConnection.DISABLED) {
                    pipe.setConnection(pDirection, PipeConnection.NONE);
                } else {
                    pipe.setConnection(pDirection, PipeConnection.PIPE);
                }
            } else {
                pipe.setConnection(pDirection, PipeConnection.NONE);
            }
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
        return new AlchemyBoilerBlockEntity(pPos, pState);
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
