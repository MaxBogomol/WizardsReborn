package mod.maxbogomol.wizards_reborn.client.gui.container;

import mod.maxbogomol.fluffy_fur.client.gui.screen.ContainerMenuBase;
import mod.maxbogomol.fluffy_fur.client.gui.screen.ResultSlot;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ArcaneWorkbenchContainer extends ContainerMenuBase {
    public final BlockEntity blockEntity;

    public ArcaneWorkbenchContainer(int windowId, Level level, BlockPos pos, Inventory playerInventory, Player player) {
        super(WizardsReborn.ARCANE_WORKBENCH_CONTAINER.get(), windowId);
        this.blockEntity = level.getBlockEntity(pos);
        playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.layoutPlayerInventorySlots(8, 138);

        if (blockEntity != null) {
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 30, 40));
                addSlot(new SlotItemHandler(h, 1, 48, 40));
                addSlot(new SlotItemHandler(h, 2, 66, 40));
                addSlot(new SlotItemHandler(h, 3, 30, 58));
                addSlot(new SlotItemHandler(h, 4, 48, 58));
                addSlot(new SlotItemHandler(h, 5, 66, 58));
                addSlot(new SlotItemHandler(h, 6, 30, 76));
                addSlot(new SlotItemHandler(h, 7, 48, 76));
                addSlot(new SlotItemHandler(h, 8, 66, 76));

                addSlot(new SlotItemHandler(h, 9, 48, 18));
                addSlot(new SlotItemHandler(h, 10, 88, 58));
                addSlot(new SlotItemHandler(h, 11, 48, 98));
                addSlot(new SlotItemHandler(h, 12, 8, 58));

                addSlot(new ResultSlot(h, 13, 146, 58));
            });
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, WizardsReborn.ARCANE_WORKBENCH.get());
    }

    @Override
    public int getInventorySize() {
        return 14;
    }
}