package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamTileEntity;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.tileentity.SteamThermalStorageTileEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.TileSimpleInventory;
import mod.maxbogomol.wizards_reborn.common.tileentity.WissenTranslatorTileEntity;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.block.state.properties.AttachFace;
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
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileSimpleInventory) {
                net.minecraft.world.Containers.dropContents(world, pos, ((TileSimpleInventory) tile).getItemHandler());
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
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
        return new SteamThermalStorageTileEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    /*@Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        TileSimpleInventory tile = (TileSimpleInventory) level.getBlockEntity(pos);
        return AbstractContainerMenu.getRedstoneSignalFromContainer(tile.getItemHandler());
    }*/

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (world.isClientSide()) {
            if (!player.isCreative()) {
                if (world.getBlockEntity(pos) != null) {
                    if (world.getBlockEntity(pos) instanceof ISteamTileEntity tile) {
                        if (tile.getMaxSteam() > 0) {
                            float amount = (float) tile.getSteam() / (float) tile.getMaxSteam();
                            for (int i = 0; i < 15 * amount; i++) {
                                Particles.create(WizardsReborn.STEAM_PARTICLE)
                                        .addVelocity(((random.nextDouble() - 0.5D) / 30), (random.nextDouble() / 30) + 0.001, ((random.nextDouble() - 0.5D) / 30))
                                        .setAlpha(0.4f, 0).setScale(0.1f, 0.5f)
                                        .setColor(1f, 1f, 1f)
                                        .setLifetime(30)
                                        .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                        .spawn(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
                            }
                        }
                    }
                }
            }
        }

        super.playerWillDestroy(world, pos, state, player);
    }
}
