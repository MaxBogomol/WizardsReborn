package mod.maxbogomol.wizards_reborn.client.gui.container;

import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.AlchemyBagItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AlchemyBagInventorySlot extends SlotItemHandler {
    public AlchemyBagInventorySlot(IItemHandler itemHandler, int pSlot, int pXPosition, int pYPosition) {
        super(itemHandler, pSlot, pXPosition, pYPosition);
    }

    public boolean mayPlace(ItemStack pStack) {
        return !(getItem().getItem() instanceof AlchemyBagItem);
    }

    public boolean mayPickup(Player player) {
        return !(getItem().getItem() instanceof AlchemyBagItem);
    }
}
