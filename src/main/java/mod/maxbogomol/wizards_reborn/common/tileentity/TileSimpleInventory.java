package mod.maxbogomol.wizards_reborn.common.tileentity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.NonNullList;

public abstract class TileSimpleInventory extends BlockEntity {

    private final SimpleContainer itemHandler = createItemHandler();

    public TileSimpleInventory(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        itemHandler.addListener(i -> setChanged());
    }

    private static void copyToInv(NonNullList<ItemStack> src, Container dest) {
        Preconditions.checkArgument(src.size() == dest.getContainerSize());
        for (int i = 0; i < src.size(); i++) {
            dest.setItem(i, src.get(i));
        }
    }

    private static NonNullList<ItemStack> copyFromInv(Container inv) {
        NonNullList<ItemStack> ret = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ret.set(i, inv.getItem(i));
        }
        return ret;
    }

    @Override
    public void load(CompoundTag tag) {
        NonNullList<ItemStack> tmp = NonNullList.withSize(inventorySize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, tmp);
        copyToInv(tmp, itemHandler);
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, copyFromInv(itemHandler));
    }

    public final int inventorySize() {
        return getItemHandler().getContainerSize();
    }

    protected abstract SimpleContainer createItemHandler();

    public final Container getItemHandler() {
        return itemHandler;
    }
}