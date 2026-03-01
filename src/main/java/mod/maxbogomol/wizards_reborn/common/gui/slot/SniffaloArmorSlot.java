package mod.maxbogomol.wizards_reborn.common.gui.slot;

import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.SniffaloArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SniffaloArmorSlot extends SlotItemHandler {

    public final SniffaloEntity sniffalo;

    public SniffaloArmorSlot(SniffaloEntity sniffalo, IItemHandler itemHandler, int slot, int xPosition, int yPosition) {
        super(itemHandler, slot, xPosition, yPosition);
        this.sniffalo = sniffalo;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof SniffaloArmorItem && !sniffalo.isArmored();
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
