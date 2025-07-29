package mod.maxbogomol.wizards_reborn.client.gui.container;

import mod.maxbogomol.fluffy_fur.client.gui.screen.ContainerMenuBase;
import mod.maxbogomol.wizards_reborn.common.block.cargo_carpet.CargoCarpetBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.CargoCarpetItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CargoCarpetContainer extends ContainerMenuBase {
    public final BlockEntity blockEntity;

    public CargoCarpetContainer(int windowId, Level level, BlockPos pos, Inventory playerInventory, Player player) {
        super(WizardsRebornMenuTypes.CARGO_CARPET_CONTAINER.get(), windowId);
        this.blockEntity = level.getBlockEntity(pos);
        playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.layoutPlayerInventorySlots(8, 112);

        if (blockEntity instanceof CargoCarpetBlockEntity cargoCarpetBlockEntity) {
            ItemStack stack = cargoCarpetBlockEntity.getItemHandler().getItem(0);
            if (stack.getItem() instanceof CargoCarpetItem) {
                SimpleContainer container = CargoCarpetItem.getInventory(stack);
                IItemHandler inventoryContainer = new InvWrapper(container);
                int c = 0;
                for (int i = 0; i < 4; i++) {
                    for (int ii = 0; ii < 5; ii++) {
                        addSlot(new SlotItemHandler(inventoryContainer, c, 44 + (ii * 18), 18 + (i * 18)));
                        c++;
                    }
                }
            }
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, blockEntity.getBlockState().getBlock());
    }

    @Override
    public int getInventorySize() {
        return 20;
    }
}