package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.client.gui.container.ItemSorterContainer;
import mod.maxbogomol.wizards_reborn.common.tileentity.ItemSorterTileEntity;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemSorterBlock extends SensorBaseBlock {

    public ItemSorterBlock(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ModelResourceLocation getModel(BlockState state) {
        return WizardsRebornClient.ITEM_SORTER_PIECE_MODEl;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ItemSorterTileEntity(pPos, pState);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof ItemSorterTileEntity) {
                ItemSorterTileEntity sorter = (ItemSorterTileEntity) tile;
                SimpleContainer inv = new SimpleContainer(sorter.itemHandler.getSlots());
                for (int i = 0; i < sorter.itemHandler.getSlots(); i++) {
                    inv.setItem(i, sorter.itemHandler.getStackInSlot(i));
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
            BlockEntity tileEntity = world.getBlockEntity(pos);

            MenuProvider containerProvider = createContainerProvider(world, pos);
            NetworkHooks.openScreen(((ServerPlayer) player), containerProvider, tileEntity.getBlockPos());
            return InteractionResult.CONSUME;
        }
    }

    private MenuProvider createContainerProvider(Level worldIn, BlockPos pos) {
        return new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.empty();
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
                return new ItemSorterContainer(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        ItemSorterTileEntity sorter = (ItemSorterTileEntity) level.getBlockEntity(pos);
        SimpleContainer inv = new SimpleContainer(sorter.itemHandler.getSlots());
        for (int i = 0; i < sorter.itemHandler.getSlots(); i++) {
            inv.setItem(i, sorter.itemHandler.getStackInSlot(i));
        }
        return AbstractContainerMenu.getRedstoneSignalFromContainer(inv);
    }
}
