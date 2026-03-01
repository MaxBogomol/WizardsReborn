package mod.maxbogomol.wizards_reborn.common.gui.menu;

import mod.maxbogomol.fluffy_fur.common.gui.menu.ContainerMenuBase;
import mod.maxbogomol.wizards_reborn.common.gui.slot.CrystalBagInventorySlot;
import mod.maxbogomol.wizards_reborn.common.gui.slot.CrystalBagSlot;
import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.CrystalBagItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMenuTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CrystalBagMenu extends ContainerMenuBase {
    public final ItemStack itemStack;
    private final Container inventory;

    public CrystalBagMenu(int containerId, Level level, ItemStack stack, Inventory playerInventory, Player player) {
        super(WizardsRebornMenuTypes.CRYSTAL_BAG_CONTAINER.get(), containerId);
        this.itemStack = stack;
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.inventory = playerInventory;
        inventory.startOpen(playerInventory.player);
        this.layoutPlayerInventorySlots(8, 94);

        if (stack != null && stack.getItem() instanceof CrystalBagItem) {
            SimpleContainer container = CrystalBagItem.getInventory(stack);
            IItemHandler inventoryContainer = new InvWrapper(container);
            int c = 0;
            for (int i = 0; i < 3; i++) {
                for (int ii = 0; ii < 5; ii++) {
                    addSlot(new CrystalBagSlot(inventoryContainer, c, 44 + (ii * 18), 18 + (i * 18)));
                    c++;
                }
            }
        }
    }

    @Override
    public void removed(Player player) {
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 1, 1);
        super.removed(player);
        this.inventory.stopOpen(player);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    @Override
    public int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new CrystalBagInventorySlot(handler, index, x, y));
            x += dx;
            index++;
        }

        return index;
    }

    @Override
    public int getInventorySize() {
        return 15;
    }
}