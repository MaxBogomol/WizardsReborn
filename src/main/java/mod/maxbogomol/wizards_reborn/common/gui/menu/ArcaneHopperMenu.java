package mod.maxbogomol.wizards_reborn.common.gui.menu;

import mod.maxbogomol.fluffy_fur.common.gui.menu.ContainerMenuBase;
import mod.maxbogomol.wizards_reborn.common.block.arcane_hopper.ArcaneHopperBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ArcaneHopperMenu extends ContainerMenuBase {
    public final BlockEntity blockEntity;

    public ArcaneHopperMenu(int containerId, Level level, BlockPos pos, Inventory playerInventory, Player player) {
        super(WizardsRebornMenuTypes.ARCANE_HOPPER_CONTAINER.get(), containerId);
        this.blockEntity = level.getBlockEntity(pos);
        playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.layoutPlayerInventorySlots(8, 58);

        if (blockEntity instanceof ArcaneHopperBlockEntity hopperTile) {
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
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, blockEntity.getBlockState().getBlock());
    }

    @Override
    public int getInventorySize() {
        return 7;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (blockEntity instanceof ArcaneHopperBlockEntity hopperTile) {
            hopperTile.stopOpen(playerEntity);
        }
    }
}