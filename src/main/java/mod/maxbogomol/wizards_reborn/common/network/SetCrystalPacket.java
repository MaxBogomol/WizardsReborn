package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;;

public class SetCrystalPacket {
    private static boolean hand;
    private static ItemStack crystal;

    public SetCrystalPacket(boolean hand, ItemStack crystal) {
        this.hand = hand;
        this.crystal = crystal;
    }

    public static SetCrystalPacket decode(FriendlyByteBuf buf) {
        return new SetCrystalPacket(buf.readBoolean(), buf.readItem());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(hand);
        buf.writeItem(crystal);
    }

    public static void handle(SetCrystalPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (!hand) {
                    stack = player.getItemInHand(InteractionHand.OFF_HAND);
                } else {
                    player.startUsingItem(InteractionHand.MAIN_HAND);
                }

                SimpleContainer stack_inv = ArcaneWandItem.getInventory(stack);
                CompoundTag nbt = stack.getTag();

                List<ItemStack> items = player.inventoryMenu.getItems();
                for (ItemStack item : items) {
                    //if (item.sameItem(crystal)) {
                    //    if (item.getOrCreateTag().toString().equals(crystal.getOrCreateTag().toString())) {
                    //        player.getInventory().removeItem(item);
                    //        if (nbt.getBoolean("crystal")) {
                    //            player.getInventory().add(stack_inv.getItem(0));
                    //        }
                    //        stack_inv.setItem(0, crystal);
                     //       nbt.putBoolean("crystal", true);
                    //        break;
                    //    }
                    //}
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
