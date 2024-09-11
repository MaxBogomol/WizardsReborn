package mod.maxbogomol.wizards_reborn.common.block.sensor.item_sorter;

import mod.maxbogomol.fluffy_fur.common.block.entity.NameableBlockEntityBase;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBaseBlock;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
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
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ItemSorterBlockEntity sorter) {
                SimpleContainer inv = new SimpleContainer(sorter.itemHandler.getSlots());
                for (int i = 0; i < sorter.itemHandler.getSlots(); i++) {
                    inv.setItem(i, sorter.itemHandler.getStackInSlot(i));
                }
                Containers.dropContents(level, pos, inv);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            NetworkHooks.openScreen(((ServerPlayer) player), (MenuProvider) blockEntity, blockEntity.getBlockPos());
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof NameableBlockEntityBase blockEntityBase) {
                blockEntityBase.setCustomName(stack.getHoverName());
            }
        }
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
