package mod.maxbogomol.wizards_reborn.common.gui.menu;

import mod.maxbogomol.fluffy_fur.common.gui.menu.ContainerMenuBase;
import mod.maxbogomol.fluffy_fur.common.gui.slot.InputSlot;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.wrapper.InvWrapper;

public class JewelerTableMenu extends ContainerMenuBase {
    public final BlockEntity blockEntity;

    public JewelerTableMenu(int containerId, Level level, BlockPos pos, Inventory inventory, Player player) {
        super(WizardsRebornMenuTypes.JEWELER_TABLE_CONTAINER.get(), containerId);
        this.blockEntity = level.getBlockEntity(pos);
        playerEntity = player;
        this.playerInventory = new InvWrapper(inventory);
        this.layoutPlayerInventorySlots(8, 94);

        if (blockEntity != null) {
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                addSlot(new InputSlot(h, 0, 24, 36));
                addSlot(new InputSlot(h, 1, 74, 36));

                addSlot(new InputSlot(h, 2, 132, 36));
            });
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, blockEntity.getBlockState().getBlock());
    }

    @Override
    public int getInventorySize() {
        return 3;
    }
}