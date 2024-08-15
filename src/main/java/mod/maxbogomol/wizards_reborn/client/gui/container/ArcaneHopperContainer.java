package mod.maxbogomol.wizards_reborn.client.gui.container;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.block.arcane_hopper.ArcaneHopperBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public class ArcaneHopperContainer extends AbstractContainerMenu {
    public final BlockEntity tileEntity;
    public final Player playerEntity;
    public final IItemHandler playerInventory;

    protected ArcaneHopperContainer(@Nullable MenuType<?> pMenuType, int pContainerId, BlockEntity tileEntity, Player playerEntity, IItemHandler playerInventory) {
        super(pMenuType, pContainerId);
        this.tileEntity = tileEntity;
        this.playerEntity = playerEntity;
        this.playerInventory = playerInventory;
    }

    public ArcaneHopperContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(WizardsReborn.ARCANE_HOPPER_CONTAINER.get(), windowId);
        this.tileEntity = world.getBlockEntity(pos);
        playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.layoutPlayerInventorySlots(8, 58);

        if (tileEntity instanceof ArcaneHopperBlockEntity hopperTile) {
            hopperTile.startOpen(playerInventory.player);
            checkContainerSize(hopperTile, 5);
            int c = 0;
            for (int ii = 0; ii < 7; ii++) {
                addSlot(new Slot(hopperTile, c, 26 + (ii * 18), 18));
                c++;
            }
        }
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos()),
                playerIn, WizardsReborn.ARCANE_HOPPER.get());
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

    private static final int TE_INVENTORY_SLOT_COUNT = 7;

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

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        if (tileEntity instanceof ArcaneHopperBlockEntity hopperTile) {
            hopperTile.stopOpen(playerEntity);
        }
    }
}