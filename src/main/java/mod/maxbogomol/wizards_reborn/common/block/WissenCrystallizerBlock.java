package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.tileentity.WissenCrystallizerTileEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.TileSimpleInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class WissenCrystallizerBlock extends Block implements ITileEntityProvider, IWaterLoggable {

    private static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(5, 0, 5, 11, 2, 11),
            Block.makeCuboidShape(6, 2, 6, 10, 8, 10),
            Block.makeCuboidShape(4, 8, 4, 12, 10, 12),
            Block.makeCuboidShape(2, 10, 2, 14, 12, 14),
            Block.makeCuboidShape(6, 12, 6, 10, 15, 10),
            Block.makeCuboidShape(2, 12, 2, 5, 14, 5),
            Block.makeCuboidShape(11, 12, 2, 14, 14, 5),
            Block.makeCuboidShape(11, 12, 11, 14, 14, 14),
            Block.makeCuboidShape(2, 12, 11, 5, 14, 14)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public WissenCrystallizerBlock(Properties properties) {
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
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidState = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Nonnull
    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader world) {
        return new WissenCrystallizerTileEntity();
    }

    @Override
    public void onReplaced(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileSimpleInventory) {
                InventoryHelper.dropInventoryItems(world, pos, ((TileSimpleInventory) tile).getItemHandler());
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        WissenCrystallizerTileEntity tile = (WissenCrystallizerTileEntity) world.getTileEntity(pos);
        ItemStack stack = player.getHeldItem(hand).copy();

        int invSize = tile.getInventorySize();
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

        if (!player.isSneaking()) {
            if (invSize < 11 && !isWand) {
                int slot = invSize;
                if ((!stack.isEmpty()) && (tile.getItemHandler().getStackInSlot(slot).isEmpty())) {
                    if (stack.getCount() > 1) {
                        player.getHeldItemMainhand().setCount(stack.getCount() - 1);
                        stack.setCount(1);
                        tile.getItemHandler().setInventorySlotContents(slot, stack);
                        return ActionResultType.SUCCESS;
                    } else {
                        tile.getItemHandler().setInventorySlotContents(slot, stack);
                        player.inventory.deleteStack(player.getHeldItem(hand));
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        } else {
            if (invSize > 0) {
                int slot = invSize - 1;
                if (!tile.getItemHandler().getStackInSlot(slot).isEmpty()) {
                    player.inventory.addItemStackToInventory(tile.getItemHandler().getStackInSlot(slot).copy());
                    tile.getItemHandler().removeStackFromSlot(slot);
                    return ActionResultType.SUCCESS;
                }
            }
        }

        return ActionResultType.PASS;
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
