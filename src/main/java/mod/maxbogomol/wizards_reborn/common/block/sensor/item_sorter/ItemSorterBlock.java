package mod.maxbogomol.wizards_reborn.common.block.sensor.item_sorter;

import mod.maxbogomol.wizards_reborn.client.gui.container.ItemSorterContainer;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBaseBlock;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
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
        return WizardsRebornModels.ITEM_SORTER_PIECE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ItemSorterBlockEntity(pos, state);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof ItemSorterBlockEntity sorter) {
                SimpleContainer inv = new SimpleContainer(sorter.itemHandler.getSlots());
                for (int i = 0; i < sorter.itemHandler.getSlots(); i++) {
                    inv.setItem(i, sorter.itemHandler.getStackInSlot(i));
                }
                Containers.dropContents(level, pos, inv);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity tileEntity = level.getBlockEntity(pos);

            MenuProvider containerProvider = createContainerProvider(level, pos);
            NetworkHooks.openScreen(((ServerPlayer) player), containerProvider, tileEntity.getBlockPos());
            return InteractionResult.CONSUME;
        }
    }

    private MenuProvider createContainerProvider(Level level, BlockPos pos) {
        return new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.translatable("gui.wizards_reborn.item_sorter.title");
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
                return new ItemSorterContainer(i, level, pos, playerInventory, playerEntity);
            }
        };
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        ItemSorterBlockEntity sorter = (ItemSorterBlockEntity) level.getBlockEntity(pos);
        SimpleContainer inv = new SimpleContainer(sorter.itemHandler.getSlots());
        for (int i = 0; i < sorter.itemHandler.getSlots(); i++) {
            inv.setItem(i, sorter.itemHandler.getStackInSlot(i));
        }
        return AbstractContainerMenu.getRedstoneSignalFromContainer(inv);
    }
}
