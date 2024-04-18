package mod.maxbogomol.wizards_reborn.common.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetSpellPacket {
    private final boolean hand;
    private final String spell;

    public SetSpellPacket(boolean hand, String spell) {
        this.hand = hand;
        this.spell = spell;
    }

    public static SetSpellPacket decode(FriendlyByteBuf buf) {
        return new SetSpellPacket(buf.readBoolean(), buf.readUtf());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(hand);
        buf.writeUtf(spell);
    }

    public static void handle(SetSpellPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (!msg.hand) {
                    stack = player.getItemInHand(InteractionHand.OFF_HAND);
                }

                CompoundTag nbt = stack.getTag();

                nbt.putString("spell", msg.spell);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
