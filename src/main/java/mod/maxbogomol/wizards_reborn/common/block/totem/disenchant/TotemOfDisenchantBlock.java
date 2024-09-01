package mod.maxbogomol.wizards_reborn.common.block.totem.disenchant;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.ITotemBlock;
import mod.maxbogomol.wizards_reborn.client.gui.container.TotemOfDisenchantContainer;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;

public class TotemOfDisenchantBlock extends Block implements EntityBlock, SimpleWaterloggedBlock, ITotemBlock {

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(4, 0, 4, 12, 4, 12),
            Block.box(5, 4, 5, 11, 6, 11),
            Block.box(10, 5, 10, 12, 10, 12),
            Block.box(4, 5, 10, 6, 10, 12),
            Block.box(10, 5, 4, 12, 10, 6),
            Block.box(4, 5, 4, 6, 10, 6)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public TotemOfDisenchantBlock(Properties properties) {
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
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TotemOfDisenchantBlockEntity totem) {
                SimpleContainer inv = new SimpleContainer(totem.itemHandler.getSlots());
                for (int i = 0; i < totem.itemHandler.getSlots(); i++) {
                    inv.setItem(i, totem.itemHandler.getStackInSlot(i));
                }
                Containers.dropContents(world, pos, inv);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack stack = player.getItemInHand(hand).copy();
            boolean isWand = false;

            if (stack.getItem() instanceof WissenWandItem) {
                if (WissenWandItem.getMode(stack) != 4) {
                    isWand = true;
                }
            }

            if (!isWand) {
                BlockEntity tileEntity = world.getBlockEntity(pos);

                MenuProvider containerProvider = createContainerProvider(world, pos);
                NetworkHooks.openScreen(((ServerPlayer) player), containerProvider, tileEntity.getBlockPos());
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.PASS;
    }

    private MenuProvider createContainerProvider(Level worldIn, BlockPos pos) {
        return new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.translatable("gui.wizards_reborn.totem_of_disenchant.title");
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
                return new TotemOfDisenchantContainer(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    @Override
    public InteractionResult useTotem(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return use(state, world, pos, player, hand, hit);
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

        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.getBlock() == WizardsRebornBlocks.ARCANE_PEDESTAL.get() || blockstate.getBlock() == WizardsRebornBlocks.TOTEM_BASE.get()) {
            return true;
        }

        return false;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (level.getBlockState(pos.below()).getBlock() == WizardsRebornBlocks.ARCANE_PEDESTAL.get()) {
            level.setBlockAndUpdate(pos.below(), WizardsRebornBlocks.TOTEM_BASE.get().defaultBlockState());
        }
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tileentity = world.getBlockEntity(pos);
        return tileentity != null && tileentity.triggerEvent(id, param);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TotemOfDisenchantBlockEntity(pPos, pState);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        TotemOfDisenchantBlockEntity totem = (TotemOfDisenchantBlockEntity) level.getBlockEntity(pos);
        SimpleContainer inv = new SimpleContainer(totem.itemHandler.getSlots());
        for (int i = 0; i < totem.itemHandler.getSlots(); i++) {
            inv.setItem(i, totem.itemHandler.getStackInSlot(i));
        }
        return AbstractContainerMenu.getRedstoneSignalFromContainer(inv);
    }
}
