package mod.maxbogomol.wizards_reborn.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface ICustomBlockEntityDataItem {
    CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag tileNbt);
}
