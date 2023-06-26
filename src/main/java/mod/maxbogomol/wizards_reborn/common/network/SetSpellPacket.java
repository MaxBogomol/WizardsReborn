package mod.maxbogomol.wizards_reborn.common.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SetSpellPacket {
    private static boolean hand;
    private static String spell;

    public SetSpellPacket(boolean hand, String spell) {
        this.hand = hand;
        this.spell = spell;
    }

    public static SetSpellPacket decode(PacketBuffer buf) {
        return new SetSpellPacket(buf.readBoolean(), buf.readString());
    }

    public void encode(PacketBuffer buf) {
        buf.writeBoolean(hand);
        buf.writeString(spell);
    }

    public static void handle(SetSpellPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();

                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                if (!hand) {
                    stack = player.getHeldItem(Hand.OFF_HAND);
                } else {
                    player.setActiveHand(Hand.MAIN_HAND);
                }

                CompoundNBT nbt = stack.getTag();

                nbt.putString("spell", spell);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
