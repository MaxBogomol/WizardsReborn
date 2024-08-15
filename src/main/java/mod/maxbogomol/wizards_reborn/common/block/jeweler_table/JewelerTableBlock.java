package mod.maxbogomol.wizards_reborn.common.block.jeweler_table;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.client.gui.container.JewelerTableContainer;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
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
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;

public class JewelerTableBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock  {

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(5, 0, 5, 11, 2, 11),
            Block.box(6, 2, 6, 10, 8, 10),
            Block.box(2, 8, 2, 14, 11, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public JewelerTableBlock(Properties properties) {
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
            if (tile instanceof JewelerTableBlockEntity table) {
                SimpleContainer inv = new SimpleContainer(table.itemHandler.getSlots() + 1);
                for (int i = 0; i < table.itemHandler.getSlots(); i++) {
                    inv.setItem(i, table.itemHandler.getStackInSlot(i));
                }
                inv.setItem(table.itemHandler.getSlots(), table.itemOutputHandler.getStackInSlot(0));
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
                return Component.translatable("gui.wizards_reborn.jeweler_table.title");
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
                return new JewelerTableContainer(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
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
        return new JewelerTableBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        JewelerTableBlockEntity table = (JewelerTableBlockEntity) level.getBlockEntity(pos);
        SimpleContainer inv = new SimpleContainer(table.itemHandler.getSlots() + 1);
        for (int i = 0; i < table.itemHandler.getSlots(); i++) {
            inv.setItem(i, table.itemHandler.getStackInSlot(i));
        }
        inv.setItem(table.itemHandler.getSlots(), table.itemOutputHandler.getStackInSlot(0));
        return AbstractContainerMenu.getRedstoneSignalFromContainer(inv);
    }
}
