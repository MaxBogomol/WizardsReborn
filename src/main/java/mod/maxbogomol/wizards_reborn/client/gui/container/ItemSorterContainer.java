package mod.maxbogomol.wizards_reborn.client.gui.container;

import mod.maxbogomol.fluffy_fur.client.gui.screen.ContainerMenuBase;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ItemSorterContainer extends ContainerMenuBase {
    public final BlockEntity blockEntity;

    public ItemSorterContainer(int windowId, Level level, BlockPos pos, Inventory playerInventory, Player player) {
        super(WizardsRebornMenuTypes.ITEM_SORTER_CONTAINER.get(), windowId);
        this.blockEntity = level.getBlockEntity(pos);
        playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.layoutPlayerInventorySlots(8, 94);

        if (blockEntity != null) {
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                int c = 0;
                for (int i = 0; i < 3; i++) {
                    for (int ii = 0; ii < 9; ii++) {
                        addSlot(new SlotItemHandler(h, c, 8  + (ii * 18), 18 + (i * 18)));
                        c++;
                    }
                }
            });
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, WizardsRebornBlocks.ITEM_SORTER.get());
    }

    @Override
    public int getInventorySize() {
        return 27;
    }
}