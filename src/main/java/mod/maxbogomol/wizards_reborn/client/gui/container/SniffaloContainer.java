package mod.maxbogomol.wizards_reborn.client.gui.container;

import mod.maxbogomol.fluffy_fur.client.gui.screen.ContainerMenuBase;
import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

public class SniffaloContainer extends ContainerMenuBase {
    public final Container sniffaloContainer;
    public final SniffaloEntity sniffalo;
    public static SimpleContainer blankContainer = new SimpleContainer(1);

    public SniffaloContainer(int windowId, Inventory playerInventory, Container sniffaloContainer, Player player, final SniffaloEntity sniffalo) {
        super(null, windowId);
        this.sniffaloContainer = sniffaloContainer;
        this.sniffalo = sniffalo;
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

        this.layoutPlayerInventorySlots(8, 112);

        sniffaloContainer.startOpen(playerInventory.player);
        IItemHandler inventoryContainer = new InvWrapper(sniffaloContainer);

        this.addSlot(new Slot(sniffaloContainer, 0, 8, 18) {
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(Items.SADDLE);
            }
        });

        this.addSlot(new SniffaloCargoCarpetSlot(sniffalo, inventoryContainer, 1, 8, 36));
        this.addSlot(new SniffaloBannerSlot(sniffalo, inventoryContainer, 2, 8, 54));
        this.addSlot(new SniffaloArmorSlot(sniffalo, inventoryContainer, 3, 8, 72));

        int c = 4;
        for (int i = 0; i < 4; i++) {
            for (int ii = 0; ii < 5; ii++) {
                addSlot(new SniffaloCargoCarpetInventorySlot(sniffalo, inventoryContainer, c, 80 + (ii * 18), 18 + (i * 18)));
                c++;
            }
        }
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.sniffaloContainer.stopOpen(player);
    }

    @Override
    public boolean stillValid(Player player) {
        return !sniffalo.hasInventoryChanged(this.sniffaloContainer) && this.sniffaloContainer.stillValid(player) && this.sniffalo.isAlive() && this.sniffalo.distanceTo(player) < 8.0F;
    }

    @Override
    public int getInventorySize() {
        return 24;
    }
}