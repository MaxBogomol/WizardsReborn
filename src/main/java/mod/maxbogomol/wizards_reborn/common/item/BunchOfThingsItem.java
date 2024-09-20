package mod.maxbogomol.wizards_reborn.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;

public class BunchOfThingsItem extends PlacedItem {

    public BunchOfThingsItem(Properties properties) {
        super(properties);
    }

    @Override
    public CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag nbt) {
        if (!nbt.contains("things")) {
            nbt.putBoolean("things", true);
        }

        return nbt;
    }

    @Override
    public boolean canPlaceBlock(BlockPlaceContext context) {
        return true;
    }
}