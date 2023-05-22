package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class SetCrystalPacket {
    private static boolean hand;
    private static ItemStack crystal;

    public SetCrystalPacket(boolean hand, ItemStack crystal) {
        this.hand = hand;
        this.crystal = crystal;
    }

    public static SetCrystalPacket decode(PacketBuffer buf) {
        return new SetCrystalPacket(buf.readBoolean(), buf.readItemStack());
    }

    public void encode(PacketBuffer buf) {
        buf.writeBoolean(hand);
        buf.writeItemStack(crystal);
    }

    public static void handle(SetCrystalPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();

                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                if (!hand) {
                    stack = player.getHeldItem(Hand.OFF_HAND);
                } else {
                    player.setActiveHand(Hand.MAIN_HAND);
                }

                Inventory stack_inv = ArcaneWandItem.getInventory(stack);
                CompoundNBT nbt = stack.getTag();

                List<ItemStack> items = player.container.getInventory();
                for (ItemStack item : items) {
                    if (item.isItemEqual(crystal)) {
                        if (item.getOrCreateTag().toString().equals(crystal.getOrCreateTag().toString())) {
                            player.inventory.deleteStack(item);
                            if (nbt.getBoolean("crystal")) {
                                player.inventory.addItemStackToInventory(stack_inv.getStackInSlot(0));
                            }
                            stack_inv.setInventorySlotContents(0, crystal);
                            nbt.putBoolean("crystal", true);
                            break;
                        }
                    }
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
