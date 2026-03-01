package mod.maxbogomol.wizards_reborn.common.gui.slot;

import mod.maxbogomol.wizards_reborn.common.gui.menu.AlchemyFurnaceMenu;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_furnace.AlchemyFurnaceBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class AlchemyFurnaceResultSlot extends SlotItemHandler {
    private final Player player;
    private int removeCount;

    private final AlchemyFurnaceMenu menu;

    public AlchemyFurnaceResultSlot(Player player, AlchemyFurnaceMenu furnaceMenu, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.player = player;
        this.menu = furnaceMenu;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return false;
    }

    public ItemStack remove(int pAmount) {
        if (this.hasItem()) {
            this.removeCount += Math.min(pAmount, this.getItem().getCount());
        }

        return super.remove(pAmount);
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        this.checkTakeAchievements(stack);
        super.onTake(player, stack);
    }

    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player.level(), this.player, this.removeCount);
        Player player = this.player;
        if (player instanceof ServerPlayer serverplayer) {
            if (menu.blockEntity instanceof AlchemyFurnaceBlockEntity furnace) {
                furnace.popExperience(serverplayer);
            }
        }

        this.removeCount = 0;
        net.minecraftforge.event.ForgeEventFactory.firePlayerSmeltedEvent(this.player, stack);
    }
}
