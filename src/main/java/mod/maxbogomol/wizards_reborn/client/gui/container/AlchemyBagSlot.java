package mod.maxbogomol.wizards_reborn.client.gui.container;

import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyBottleItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyPotionItem;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AlchemyBagSlot extends SlotItemHandler {
    public AlchemyBagSlot(IItemHandler itemHandler, int pSlot, int pXPosition, int pYPosition) {
        super(itemHandler, pSlot, pXPosition, pYPosition);
    }

    public boolean mayPlace(ItemStack pStack) {
        return (pStack.getItem() instanceof PotionItem || pStack.getItem() instanceof BottleItem || pStack.getItem() instanceof AlchemyPotionItem || pStack.getItem() instanceof AlchemyBottleItem);
    }
}
