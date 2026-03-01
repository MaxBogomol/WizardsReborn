package mod.maxbogomol.wizards_reborn.common.gui.slot;

import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SniffaloCargoCarpetInventorySlot extends SlotItemHandler {

    public final SniffaloEntity sniffalo;

    public SniffaloCargoCarpetInventorySlot(SniffaloEntity sniffalo, IItemHandler itemHandler, int slot, int xPosition, int yPosition) {
        super(itemHandler, slot, xPosition, yPosition);
        this.sniffalo = sniffalo;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return sniffalo.isCarpeted();
    }

    @Override
    public boolean isActive() {
        return sniffalo.isCarpeted();
    }

    @Override
    public boolean isHighlightable() {
        return sniffalo.isCarpeted();
    }
}
