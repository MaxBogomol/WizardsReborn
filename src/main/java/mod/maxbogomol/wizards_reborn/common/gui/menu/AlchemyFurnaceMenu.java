package mod.maxbogomol.wizards_reborn.common.gui.menu;

import mod.maxbogomol.fluffy_fur.common.gui.menu.ContainerMenuBase;
import mod.maxbogomol.fluffy_fur.common.gui.slot.InputSlot;
import mod.maxbogomol.wizards_reborn.common.gui.slot.AlchemyFurnaceFuelSlot;
import mod.maxbogomol.wizards_reborn.common.gui.slot.AlchemyFurnaceResultSlot;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.wrapper.InvWrapper;

public class AlchemyFurnaceMenu extends ContainerMenuBase {
    public final BlockEntity blockEntity;

    public AlchemyFurnaceMenu(int containerId, Level level, BlockPos pos, Inventory playerInventory, Player player) {
        super(WizardsRebornMenuTypes.ALCHEMY_FURNACE_CONTAINER.get(), containerId);
        this.blockEntity = level.getBlockEntity(pos);
        playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.layoutPlayerInventorySlots(8, 94);

        if (blockEntity != null) {
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                addSlot(new InputSlot(h, 0, 74, 18));
                addSlot(new AlchemyFurnaceFuelSlot(this, h, 1, 74, 54));

                addSlot(new AlchemyFurnaceResultSlot(playerEntity, this, h, 2, 132, 36));
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

    public boolean isFuel(ItemStack pStack) {
        return ForgeHooks.getBurnTime(pStack, RecipeType.SMELTING) > 0;
    }
}