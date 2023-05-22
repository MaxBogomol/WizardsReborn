package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.common.data.recipes.WissenAltarRecipe;
import mod.maxbogomol.wizards_reborn.common.tileentity.TileSimpleInventory;
import mod.maxbogomol.wizards_reborn.common.tileentity.WissenAltarTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
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

public class WissenAltarBlock extends HorizontalBlock implements ITileEntityProvider, IWaterLoggable  {

    private static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(5, 13, 5, 11, 14, 11),
            Block.makeCuboidShape(2, 11, 2, 14, 13, 14),
            Block.makeCuboidShape(4, 2, 4, 12, 11, 12),
            Block.makeCuboidShape(2, 0, 2, 14, 2, 14),
            Block.makeCuboidShape(1, 9, 1, 3, 16, 3),
            Block.makeCuboidShape(13, 9, 1, 15, 16, 3),
            Block.makeCuboidShape(1, 9, 13, 3, 16, 15),
            Block.makeCuboidShape(13, 9, 13, 15, 16, 15)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public WissenAltarBlock(Properties properties) {
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
        return new WissenAltarTileEntity();
    }

    @Override
    public void onReplaced(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileSimpleInventory) {
                ((TileSimpleInventory) tile).getItemHandler().removeStackFromSlot(2);
                net.minecraft.inventory.InventoryHelper.dropInventoryItems(world, pos, ((TileSimpleInventory) tile).getItemHandler());
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        WissenAltarTileEntity altar = (WissenAltarTileEntity) world.getTileEntity(pos);
        ItemStack stack = player.getHeldItem(hand).copy();

        if (stack.getItem() instanceof IWissenItem) {
            if (altar.getItemHandler().getStackInSlot(0).isEmpty()) {
                altar.getItemHandler().setInventorySlotContents(0, stack);
                player.inventory.deleteStack(player.getHeldItem(hand));
                return ActionResultType.SUCCESS;
            }
        }

        Inventory inv = new Inventory(1);
        inv.setInventorySlotContents(0, stack);
        int wissenInItem = world.getRecipeManager().getRecipe(WizardsReborn.WISSEN_ALTAR_RECIPE, inv, world)
                .map(WissenAltarRecipe::getRecipeWissen)
                .orElse(0);
        if (wissenInItem > 0) {
            if (altar.getItemHandler().getStackInSlot(1).isEmpty()) {
                altar.getItemHandler().setInventorySlotContents(1, stack);
                player.inventory.deleteStack(player.getHeldItem(hand));
                return ActionResultType.SUCCESS;
            } else {
                if (altar.getItemHandler().getStackInSlot(1).equals(stack)
                        && altar.getItemHandler().getStackInSlot(1).getCount() + stack.getCount() <= altar.getItemHandler().getStackInSlot(1).getMaxStackSize()) {
                    altar.getItemHandler().getStackInSlot(1).setCount(altar.getItemHandler().getStackInSlot(1).getCount() + stack.getCount());
                    return ActionResultType.SUCCESS;
                }
            }
        }

        if (stack != null) {
            if (!altar.getItemHandler().getStackInSlot(0).isEmpty()) {
                player.inventory.addItemStackToInventory(altar.getItemHandler().getStackInSlot(0).copy());
                altar.getItemHandler().removeStackFromSlot(0);
                return ActionResultType.SUCCESS;
            } else {
                if (!altar.getItemHandler().getStackInSlot(1).isEmpty()) {
                    player.inventory.addItemStackToInventory(altar.getItemHandler().getStackInSlot(1).copy());
                    altar.getItemHandler().removeStackFromSlot(1);
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
