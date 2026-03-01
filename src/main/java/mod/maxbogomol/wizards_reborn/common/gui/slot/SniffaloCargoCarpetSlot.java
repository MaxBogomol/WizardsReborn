package mod.maxbogomol.wizards_reborn.common.gui.slot;

import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.CargoCarpetItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SniffaloCargoCarpetSlot extends SlotItemHandler {

    public final SniffaloEntity sniffalo;

    public SniffaloCargoCarpetSlot(SniffaloEntity sniffalo, IItemHandler itemHandler, int slot, int xPosition, int yPosition) {
        super(itemHandler, slot, xPosition, yPosition);
        this.sniffalo = sniffalo;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof CargoCarpetItem && !sniffalo.isCarpeted();
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
