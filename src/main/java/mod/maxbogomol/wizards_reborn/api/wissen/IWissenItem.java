package mod.maxbogomol.wizards_reborn.api.wissen;

import net.minecraft.world.item.ItemStack;

public interface IWissenItem {
    int getMaxWissen(ItemStack stack);
    WissenItemType getWissenItemType();
}
