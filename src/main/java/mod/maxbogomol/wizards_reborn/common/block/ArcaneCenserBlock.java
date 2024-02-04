package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamTileEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.SteamUtils;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.recipe.CenserRecipe;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcaneCenserTileEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.TileSimpleInventory;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
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
import java.util.Random;
import java.util.stream.Stream;

public class ArcaneCenserBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock  {
    private static Random random = new Random();

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
            if (tile instanceof ArcaneCenserTileEntity censerTile) {
                SimpleContainer inv = new SimpleContainer(censerTile.getInventorySize());
                for (int i = 0; i < censerTile.getInventorySize(); i++) {
                    ItemStack item = censerTile.getItem(i);
                    if (ArcaneCenserTileEntity.getItemBurnCenser(item) <= 0) {
                        inv.addItem(item);
                    }
                }
                Containers.dropContents(world, pos, inv);
            }

            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ArcaneCenserTileEntity tile = (ArcaneCenserTileEntity) world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();

        int invSize = tile.getInventorySize();

        SimpleContainer inv = new SimpleContainer(1);
        inv.setItem(0, stack);
        Optional<CenserRecipe> recipe = world.getRecipeManager().getRecipeFor(WizardsReborn.CENSER_RECIPE.get(), inv, world);

        if (!player.isShiftKeyDown()) {
            if (recipe.isPresent()) {
                if (invSize < 8) {
                    int slot = invSize;
                    if ((!stack.isEmpty()) && (tile.getItemHandler().getItem(slot).isEmpty())) {
                        if (stack.getCount() > 1) {
                            player.getItemInHand(hand).setCount(stack.getCount() - 1);
                            stack.setCount(1);
                            tile.getItemHandler().setItem(slot, stack);
                            world.updateNeighbourForOutputSignal(pos, this);
                            PacketUtils.SUpdateTileEntityPacket(tile);
                            return InteractionResult.SUCCESS;
                        } else {
                            tile.getItemHandler().setItem(slot, stack);
                            player.getInventory().removeItem(player.getItemInHand(hand));
                            world.updateNeighbourForOutputSignal(pos, this);
                            PacketUtils.SUpdateTileEntityPacket(tile);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        } else {
            if (invSize > 0) {
                for (int i = 0; i < invSize; i++) {
                    int slot = invSize - i - 1;
                    if (!tile.getItemHandler().getItem(slot).isEmpty()) {
                        if (ArcaneCenserTileEntity.getItemBurnCenser(tile.getItemHandler().getItem(slot)) <= 0) {
                            player.getInventory().add(tile.getItemHandler().getItem(slot).copy());
                            tile.getItemHandler().removeItem(slot, 1);
                            world.updateNeighbourForOutputSignal(pos, this);
                            tile.sortItems();
                            PacketUtils.SUpdateTileEntityPacket(tile);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }

        if (tile.cooldown <= 0 && SteamUtils.canRemoveSteam(tile.steam, 150) && player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && !player.isShiftKeyDown()) {
            if (!world.isClientSide) {
                tile.smoke(player);
            }
            return InteractionResult.SUCCESS;
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
        return new ArcaneCenserTileEntity(pPos, pState);
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
