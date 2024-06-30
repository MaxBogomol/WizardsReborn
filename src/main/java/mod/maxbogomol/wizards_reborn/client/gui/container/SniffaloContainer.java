package mod.maxbogomol.wizards_reborn.client.gui.container;

import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import mod.maxbogomol.wizards_reborn.common.item.CargoCarpetItem;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

public class SniffaloContainer extends AbstractContainerMenu {
    public final Container sniffaloContainer;
    public final SniffaloEntity sniffalo;
    public final Player playerEntity;
    public final IItemHandler playerInventory;

    public SniffaloContainer(int windowId, Inventory playerInventory, Container sniffaloContainer, Player player, final SniffaloEntity sniffalo) {
        super(null, windowId);
        this.sniffaloContainer = sniffaloContainer;
        this.sniffalo = sniffalo;
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

        this.layoutPlayerInventorySlots(8, 112);

        sniffaloContainer.startOpen(playerInventory.player);
        this.addSlot(new Slot(sniffaloContainer, 0, 8, 18) {
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(Items.SADDLE);
            }
        });

        this.addSlot(new Slot(sniffaloContainer, 1, 8, 36) {
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof CargoCarpetItem;
            }
        });

        this.addSlot(new Slot(sniffaloContainer, 2, 8, 54) {
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof BannerItem;
            }
        });

        this.addSlot(new Slot(sniffaloContainer, 3, 8, 72) {
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof BannerItem;
            }
        });

        int c = 4;
        for (int i = 0; i < 4; i++) {
            for (int ii = 0; ii < 6; ii++) {
                this.addSlot(new Slot(sniffaloContainer, c, 80  + (ii * 18), 18 + (i * 18)) {
                    public boolean mayPlace(@NotNull ItemStack stack) {
                        return !sniffalo.getCarpet().isEmpty();
                    }
                });
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
    public boolean stillValid(Player playerIn) {
        return !sniffalo.hasInventoryChanged(this.sniffaloContainer) && this.sniffaloContainer.stillValid(playerIn) && this.sniffalo.isAlive() && this.sniffalo.distanceTo(playerIn) < 8.0F;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }

        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }

        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = 28;

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerEntity, sourceStack);
        return copyOfSourceStack;
    }
}