package mod.maxbogomol.wizards_reborn.client.gui.container;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AlchemyFurnaceFuelSlot extends SlotItemHandler {
    private final AlchemyFurnaceContainer menu;

    public AlchemyFurnaceFuelSlot(AlchemyFurnaceContainer pFurnaceMenu, IItemHandler itemHandler, int pSlot, int pXPosition, int pYPosition) {
        super(itemHandler, pSlot, pXPosition, pYPosition);
        this.menu = pFurnaceMenu;
    }

    public boolean mayPlace(ItemStack pStack) {
        return this.menu.isFuel(pStack) || isBucket(pStack);
    }

    public int getMaxStackSize(ItemStack pStack) {
        return isBucket(pStack) ? 1 : super.getMaxStackSize(pStack);
    }

    public static boolean isBucket(ItemStack pStack) {
        return pStack.is(Items.BUCKET);
    }
}
