package mod.maxbogomol.wizards_reborn.common.gui.slot;

import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AlchemyBagSlot extends SlotItemHandler {

    public AlchemyBagSlot(IItemHandler itemHandler, int slot, int xPosition, int yPosition) {
        super(itemHandler, slot, xPosition, yPosition);
    }

    public boolean mayPlace(ItemStack stack) {
        return stack.is(WizardsRebornItemTags.ALCHEMY_BAG_SLOTS);
    }
}
