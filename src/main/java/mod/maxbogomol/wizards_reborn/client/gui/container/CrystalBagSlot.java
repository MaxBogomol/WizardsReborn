package mod.maxbogomol.wizards_reborn.client.gui.container;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornTags;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CrystalBagSlot extends SlotItemHandler {

    public CrystalBagSlot(IItemHandler itemHandler, int slot, int xPosition, int yPosition) {
        super(itemHandler, slot, xPosition, yPosition);
    }

    public boolean mayPlace(ItemStack stack) {
        return stack.is(WizardsRebornTags.CRYSTAL_BAG_SLOTS_ITEM);
    }
}
