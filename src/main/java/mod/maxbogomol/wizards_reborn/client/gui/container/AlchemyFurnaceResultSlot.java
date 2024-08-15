package mod.maxbogomol.wizards_reborn.client.gui.container;

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

    private final AlchemyFurnaceContainer menu;

    public AlchemyFurnaceResultSlot(Player player, AlchemyFurnaceContainer pFurnaceMenu, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.player = player;
        this.menu = pFurnaceMenu;
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

    public void onTake(Player pPlayer, ItemStack pStack) {
        this.checkTakeAchievements(pStack);
        super.onTake(pPlayer, pStack);
    }

    protected void checkTakeAchievements(ItemStack pStack) {
        pStack.onCraftedBy(this.player.level(), this.player, this.removeCount);
        Player player = this.player;
        if (player instanceof ServerPlayer serverplayer) {
            if (menu.tileEntity instanceof AlchemyFurnaceBlockEntity furnace) {
                furnace.popExperience(serverplayer);
            }
        }

        this.removeCount = 0;
        net.minecraftforge.event.ForgeEventFactory.firePlayerSmeltedEvent(this.player, pStack);
    }
}
