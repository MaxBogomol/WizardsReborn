package mod.maxbogomol.wizards_reborn.client.gui.container;

import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.runic_pedestal.RunicPedestalBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class RunicPedestalSlot extends SlotItemHandler {

    private final RunicPedestalContainer menu;

    public RunicPedestalSlot(RunicPedestalContainer runicPedestalMenu, IItemHandler itemHandler, int slot, int xPosition, int yPosition) {
        super(itemHandler, slot, xPosition, yPosition);
        this.menu = runicPedestalMenu;
    }

    @Override
    public void set(ItemStack stack) {
        if (menu.blockEntity instanceof RunicPedestalBlockEntity runicPedestalBlock) {
            if (!runicPedestalBlock.getLevel().isClientSide()) {
                if (runicPedestalBlock.getLevel().getBlockEntity(runicPedestalBlock.getBlockPos().above()) instanceof CrystalBlockEntity crystalBlock) {
                    if (crystalBlock.startRitual) {
                        crystalBlock.reload();
                        BlockEntityUpdate.packet(crystalBlock);
                    }
                }
            }
        }
        super.set(stack);
    }
}
