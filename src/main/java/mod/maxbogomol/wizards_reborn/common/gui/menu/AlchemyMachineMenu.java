package mod.maxbogomol.wizards_reborn.common.gui.menu;

import mod.maxbogomol.fluffy_fur.common.gui.menu.ContainerMenuBase;
import mod.maxbogomol.fluffy_fur.common.gui.slot.InputSlot;
import mod.maxbogomol.fluffy_fur.common.gui.slot.ResultSlot;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.wrapper.InvWrapper;

public class AlchemyMachineMenu extends ContainerMenuBase {
    public final BlockEntity blockEntity;

    public AlchemyMachineMenu(int containerId, Level level, BlockPos pos, Inventory playerInventory, Player player) {
        super(WizardsRebornMenuTypes.ALCHEMY_MACHINE_CONTAINER.get(), containerId);
        this.blockEntity = level.getBlockEntity(pos);
        playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.layoutPlayerInventorySlots(8, 127);

        if (blockEntity != null) {
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                addSlot(new InputSlot(h, 0, 29, 18));
                addSlot(new InputSlot(h, 1, 47, 18));
                addSlot(new InputSlot(h, 2, 65, 18));
                addSlot(new InputSlot(h, 3, 29, 36));
                addSlot(new InputSlot(h, 4, 47, 36));
                addSlot(new InputSlot(h, 5, 65, 36));

                addSlot(new ResultSlot(h, 6, 132, 54));
            });
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, blockEntity.getBlockState().getBlock());
    }

    @Override
    public int getInventorySize() {
        return 7;
    }
}