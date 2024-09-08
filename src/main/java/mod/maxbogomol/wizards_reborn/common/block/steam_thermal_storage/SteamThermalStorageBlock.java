package mod.maxbogomol.wizards_reborn.common.block.steam_thermal_storage;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class SteamThermalStorageBlock extends RotatedPillarBlock implements EntityBlock, SimpleWaterloggedBlock  {
    private static Random random = new Random();

    private static final VoxelShape SHAPE_X_SHELL  = Stream.of(
            Block.box(0, 6, 6, 1, 10, 10),
            Block.box(2, 3, 3, 4, 13, 13),
            Block.box(4, 4, 4, 15, 12, 12),
            Block.box(12, 3, 3, 14, 13, 13),
            Block.box(15, 6, 6, 16, 10, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_X_HOLE = Block.box(3, 5, 5, 13, 11, 11);
    private static final VoxelShape SHAPE_X = Shapes.join(SHAPE_X_SHELL, SHAPE_X_HOLE, BooleanOp.ONLY_FIRST);

    private static final VoxelShape SHAPE_Y_SHELL = Stream.of(
            Block.box(6, 0, 6, 10, 1, 10),
            Block.box(3, 2, 3, 13, 4, 13),
            Block.box(4, 4, 4, 12, 15, 12),
            Block.box(3, 12, 3, 13, 14, 13),
            Block.box(6, 15, 6, 10, 16, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_Y_HOLE = Block.box(5, 3, 5, 11, 13, 11);
    private static final VoxelShape SHAPE_Y = Shapes.join(SHAPE_Y_SHELL, SHAPE_Y_HOLE, BooleanOp.ONLY_FIRST);

    private static final VoxelShape SHAPE_Z_SHELL = Stream.of(
            Block.box(6, 6, 0, 10, 10, 1),
            Block.box(3, 3, 2, 13, 13, 4),
            Block.box(4, 4, 4, 12, 12, 15),
            Block.box(3, 3, 12, 13, 13, 14),
            Block.box(6, 6, 15, 10, 10, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_Z_HOLE = Block.box(5, 5, 3, 11, 11, 13);
    private static final VoxelShape SHAPE_Z = Shapes.join(SHAPE_Z_SHELL, SHAPE_Z_HOLE, BooleanOp.ONLY_FIRST);

    public SteamThermalStorageBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        switch (state.getValue(AXIS)) {
            case X:
                return SHAPE_X;
            case Y:
                return SHAPE_Y;
            case Z:
                return SHAPE_Z;
            default:
                return SHAPE_Y;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS).add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()).setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> items = super.getDrops(state, builder);
        BlockEntity tile = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (tile instanceof ISteamBlockEntity steamTile) {
            CompoundTag nbt = tile.getUpdateTag();
            if (nbt != null) {
                for (ItemStack stack : items) {
                    if (stack.getItem() == WizardsRebornItems.STEAM_THERMAL_STORAGE.get()) {
                        CompoundTag tag = stack.getOrCreateTag();
                        tag.putInt("steam", steamTile.getSteam());
                    }
                }
            }
        }
        return items;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
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
        return new SteamThermalStorageBlockEntity(pos, state);
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
        ISteamBlockEntity tile = (ISteamBlockEntity) level.getBlockEntity(pos);
        return Mth.floor(((float) tile.getSteam() / tile.getMaxSteam()) * 14.0F);
    }
}
