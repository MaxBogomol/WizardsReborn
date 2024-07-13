package mod.maxbogomol.wizards_reborn.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class BunchOfThingsItem extends PlacedItem {

    public BunchOfThingsItem(Properties properties) {
        super(properties);
    }

    @Override
    public CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag tileNbt) {
        if (!tileNbt.contains("things")) {
            tileNbt.putBoolean("things", true);
        }

        return tileNbt;
    }
}