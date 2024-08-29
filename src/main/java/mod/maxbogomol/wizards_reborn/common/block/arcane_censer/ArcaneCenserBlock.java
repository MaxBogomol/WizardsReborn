package mod.maxbogomol.wizards_reborn.common.block.arcane_censer;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.BlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.SteamUtils;
import mod.maxbogomol.wizards_reborn.common.recipe.CenserRecipe;
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
import java.awt.*;
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
            if (tile instanceof ArcaneCenserBlockEntity censerTile) {
                SimpleContainer inv = new SimpleContainer(censerTile.getInventorySize());
                for (int i = 0; i < censerTile.getInventorySize(); i++) {
                    ItemStack item = censerTile.getItem(i);
                    if (ArcaneCenserBlockEntity.getItemBurnCenser(item) <= 0) {
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
        ArcaneCenserBlockEntity tile = (ArcaneCenserBlockEntity) world.getBlockEntity(pos);
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
                            BlockEntityUpdate.packet(tile);
                            world.playSound(null, pos, WizardsReborn.PEDESTAL_INSERT_SOUND.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                            return InteractionResult.SUCCESS;
                        } else {
                            tile.getItemHandler().setItem(slot, stack);
                            player.getInventory().removeItem(player.getItemInHand(hand));
                            world.updateNeighbourForOutputSignal(pos, this);
                            BlockEntityUpdate.packet(tile);
                            world.playSound(null, pos, WizardsReborn.PEDESTAL_INSERT_SOUND.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
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
                        if (ArcaneCenserBlockEntity.getItemBurnCenser(tile.getItemHandler().getItem(slot)) <= 0) {
                            if (player.getInventory().getSlotWithRemainingSpace(tile.getItemHandler().getItem(slot)) != -1 || player.getInventory().getFreeSlot() > -1) {
                                player.getInventory().add(tile.getItemHandler().getItem(slot).copy());
                            } else {
                                world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5F, pos.getY() + 1.0F, pos.getZ() + 0.5F, tile.getItemHandler().getItem(slot).copy()));
                            }
                            tile.getItemHandler().removeItem(slot, 1);
                            world.updateNeighbourForOutputSignal(pos, this);
                            tile.sortItems();
                            BlockEntityUpdate.packet(tile);
                            world.playSound(null, pos, WizardsReborn.PEDESTAL_REMOVE_SOUND.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
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
        return new ArcaneCenserBlockEntity(pPos, pState);
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
        BlockSimpleInventory tile = (BlockSimpleInventory) level.getBlockEntity(pos);
        return AbstractContainerMenu.getRedstoneSignalFromContainer(tile.getItemHandler());
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (world.isClientSide()) {
            if (!player.isCreative()) {
                if (world.getBlockEntity(pos) != null) {
                    if (world.getBlockEntity(pos) instanceof ISteamBlockEntity tile) {
                        if (tile.getMaxSteam() > 0) {
                            float amount = (float) tile.getSteam() / (float) tile.getMaxSteam();
                            ParticleBuilder.create(FluffyFur.SMOKE_PARTICLE)
                                    .setColorData(ColorParticleData.create(Color.WHITE).build())
                                    .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.1f, 0.5f).build())
                                    .randomSpin(0.1f)
                                    .setLifetime(30)
                                    .randomVelocity(0.015f)
                                    .repeat(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, (int) (15 * amount));
                        }
                    }
                }
            }
        }

        super.playerWillDestroy(world, pos, state, player);
    }
}
