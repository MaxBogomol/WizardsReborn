package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class DeleteCrystalPacket {
    private static boolean hand;

    public DeleteCrystalPacket(boolean hand) {
        this.hand = hand;
    }

    public static DeleteCrystalPacket decode(PacketBuffer buf) {
        return new DeleteCrystalPacket(buf.readBoolean());
    }

    public void encode(PacketBuffer buf) {
        buf.writeBoolean(hand);
    }

    public static void handle(DeleteCrystalPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();

                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                if (!hand) {
                    stack = player.getHeldItem(Hand.OFF_HAND);
                }

                Inventory stack_inv = ArcaneWandItem.getInventory(stack);
                CompoundNBT nbt = stack.getTag();

                player.inventory.addItemStackToInventory(stack_inv.getStackInSlot(0));

                stack_inv.clear();
                nbt.putBoolean("crystal", false);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
