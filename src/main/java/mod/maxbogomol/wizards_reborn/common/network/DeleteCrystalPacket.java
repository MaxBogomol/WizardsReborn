package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;;

public class DeleteCrystalPacket {
    private static boolean hand;

    public DeleteCrystalPacket(boolean hand) {
        this.hand = hand;
    }

    public static DeleteCrystalPacket decode(FriendlyByteBuf buf) {
        return new DeleteCrystalPacket(buf.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(hand);
    }

    public static void handle(DeleteCrystalPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (!hand) {
                    stack = player.getItemInHand(InteractionHand.OFF_HAND);
                }

                SimpleContainer stack_inv = ArcaneWandItem.getInventory(stack);
                CompoundTag nbt = stack.getTag();

                player.getInventory().add(stack_inv.getItem(0));

                stack_inv.clearContent();
                nbt.putBoolean("crystal", false);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
