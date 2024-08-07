package mod.maxbogomol.wizards_reborn.client.gui.container;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AlchemyBagSlot extends SlotItemHandler {
    public AlchemyBagSlot(IItemHandler itemHandler, int pSlot, int pXPosition, int pYPosition) {
        super(itemHandler, pSlot, pXPosition, pYPosition);
    }

    public boolean mayPlace(ItemStack stack) {
        return stack.is(WizardsReborn.ALCHEMY_BAG_SLOTS_ITEM_TAG);
    }
}
