package mod.maxbogomol.wizards_reborn.client.gui.container;

import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.CrystalBagItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CrystalBagInventorySlot extends SlotItemHandler {
    public CrystalBagInventorySlot(IItemHandler itemHandler, int pSlot, int pXPosition, int pYPosition) {
        super(itemHandler, pSlot, pXPosition, pYPosition);
    }

    public boolean mayPlace(ItemStack pStack) {
        return !(getItem().getItem() instanceof CrystalBagItem);
    }

    public boolean mayPickup(Player player) {
        return !(getItem().getItem() instanceof CrystalBagItem);
    }
}
