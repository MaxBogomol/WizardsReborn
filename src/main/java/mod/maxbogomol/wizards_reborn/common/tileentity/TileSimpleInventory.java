package mod.maxbogomol.wizards_reborn.common.tileentity;

import com.google.common.base.Preconditions;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;

public abstract class TileSimpleInventory extends TileEntity {

    private final Inventory itemHandler = createItemHandler();

    public TileSimpleInventory(TileEntityType<?> type) {
        super(type);
        itemHandler.addListener(i -> markDirty());
    }

    private static void copyToInv(NonNullList<ItemStack> src, IInventory dest) {
        Preconditions.checkArgument(src.size() == dest.getSizeInventory());
        for (int i = 0; i < src.size(); i++) {
            dest.setInventorySlotContents(i, src.get(i));
        }
    }

    private static NonNullList<ItemStack> copyFromInv(IInventory inv) {
        NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ret.set(i, inv.getStackInSlot(i));
        }
        return ret;
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        NonNullList<ItemStack> tmp = NonNullList.withSize(inventorySize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(tag, tmp);
        copyToInv(tmp, itemHandler);
        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        ItemStackHelper.saveAllItems(tag, copyFromInv(itemHandler));
        CompoundNBT ret = super.write(tag);
        return ret;
    }

    public final int inventorySize() {
        return getItemHandler().getSizeInventory();
    }

    protected abstract Inventory createItemHandler();

    public final IInventory getItemHandler() {
        return itemHandler;
    }
}