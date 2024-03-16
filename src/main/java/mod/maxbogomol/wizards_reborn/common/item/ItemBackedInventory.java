package mod.maxbogomol.wizards_reborn.common.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class ItemBackedInventory extends SimpleContainer {
    private static final String TAG_ITEMS = "Items";
    private final ItemStack stack;

    public ItemBackedInventory(ItemStack stack, int expectedSize) {
        super(expectedSize);
        this.stack = stack;

        CompoundTag nbt = stack.getTag();
        ListTag lst = new ListTag();
        if (nbt!=null) {
            if (nbt.contains(TAG_ITEMS)) {
                lst = nbt.getList(TAG_ITEMS, 10);
            }
        }
        int i = 0;
        for (; i < expectedSize && i < lst.size(); i++) {
            setItem(i, ItemStack.of(lst.getCompound(i)));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return !stack.isEmpty();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        ListTag list = new ListTag();
        for (int i = 0; i < getContainerSize(); i++) {
            list.add(getItem(i).save(new CompoundTag()));
        }
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.put(TAG_ITEMS,list);
    }
}
