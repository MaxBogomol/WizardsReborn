package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.client.gui.container.ArcaneWorkbenchContainer;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcaneWorkbenchTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class ArcaneWorkbenchBlock extends HorizontalBlock implements ITileEntityProvider, IWaterLoggable  {

    private static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 4, 16),
            Block.makeCuboidShape(4, 4, 4, 12, 11, 12),
            Block.makeCuboidShape(0, 11, 0, 16, 16, 16),
            Block.makeCuboidShape(0, 9, 0, 4, 11, 4),
            Block.makeCuboidShape(12, 9, 0, 16, 11, 4),
            Block.makeCuboidShape(12, 9, 12, 16, 11, 16),
            Block.makeCuboidShape(0, 9, 12, 4, 11, 16)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public ArcaneWorkbenchBlock(Properties properties) {
        super(properties);
        setDefaultState(getDefaultState().with(WATERLOGGED, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {
        return SHAPE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidState = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Nonnull
    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader world) {
        return new ArcaneWorkbenchTileEntity();
    }

    @Override
    public void onReplaced(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof ArcaneWorkbenchTileEntity) {
                ArcaneWorkbenchTileEntity workbench = (ArcaneWorkbenchTileEntity) tile;
                Inventory inv = new Inventory(workbench.itemHandler.getSlots());
                for (int i = 0; i < workbench.itemHandler.getSlots(); i++) {
                    inv.setInventorySlotContents(i, workbench.itemHandler.getStackInSlot(i));
                }
                net.minecraft.inventory.InventoryHelper.dropInventoryItems(world, pos, inv);
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            ItemStack stack = player.getHeldItem(hand).copy();
            boolean isWand = false;

            if (stack.getItem() instanceof WissenWandItem) {
                CompoundNBT nbt = stack.getTag();
                if (nbt == null) {
                    nbt = new CompoundNBT();
                    stack.setTag(nbt);
                }

                if (!nbt.contains("block")) {
                    nbt.putBoolean("block", false);
                }
                if (!nbt.contains("mode")) {
                    nbt.putInt("mode", 0);
                }

                if (nbt.getInt("mode") != 4) {
                    isWand = true;
                }
            }

            if (!isWand) {
                TileEntity tileEntity = world.getTileEntity(pos);

                INamedContainerProvider containerProvider = createContainerProvider(world, pos);
                NetworkHooks.openGui(((ServerPlayerEntity) player), containerProvider, tileEntity.getPos());
                return ActionResultType.CONSUME;
            }
        }

        return ActionResultType.PASS;
    }

    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("");
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                return new ArcaneWorkbenchContainer(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction side, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return stateIn;
    }

    @Override
    public boolean eventReceived(BlockState state, World world, BlockPos pos, int id, int param) {
        super.eventReceived(state, world, pos, id, param);
        TileEntity tileentity = world.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
