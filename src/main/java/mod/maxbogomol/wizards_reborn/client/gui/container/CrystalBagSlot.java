package mod.maxbogomol.wizards_reborn.client.gui.container;

import mod.maxbogomol.wizards_reborn.common.block.CrystalSeedBlock;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.FracturedCrystalItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CrystalBagSlot extends SlotItemHandler {
    public CrystalBagSlot(IItemHandler itemHandler, int pSlot, int pXPosition, int pYPosition) {
        super(itemHandler, pSlot, pXPosition, pYPosition);
    }

    public boolean mayPlace(ItemStack pStack) {
        if (pStack.getItem() instanceof FracturedCrystalItem || pStack.getItem() instanceof CrystalItem) {
            return true;
        }
        if (pStack.getItem() instanceof BlockItem blockItem) {
            if (blockItem.getBlock() instanceof CrystalSeedBlock) {
                return true;
            }
        }

        return false;
    }
}
