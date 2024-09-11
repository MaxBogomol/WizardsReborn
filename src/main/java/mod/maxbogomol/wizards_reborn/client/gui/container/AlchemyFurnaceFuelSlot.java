package mod.maxbogomol.wizards_reborn.client.gui.container;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AlchemyFurnaceFuelSlot extends SlotItemHandler {
    private final AlchemyFurnaceContainer menu;

    public AlchemyFurnaceFuelSlot(AlchemyFurnaceContainer furnaceMenu, IItemHandler itemHandler, int slot, int xPosition, int yPosition) {
        super(itemHandler, slot, xPosition, yPosition);
        this.menu = furnaceMenu;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return this.menu.isFuel(stack) || isBucket(stack);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return isBucket(stack) ? 1 : super.getMaxStackSize(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.is(Items.BUCKET);
    }

    @Override
    public boolean allowModification(Player player) {
        if (this.getItem().isEmpty()) {
            return true;
        }
        return this.mayPickup(player) && this.mayPlace(this.getItem());
    }
}
