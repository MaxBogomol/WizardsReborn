package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetCurrentSpellSetPacket {
    private final int setId;

    public SetCurrentSpellSetPacket(int setId) {
        this.setId = setId;
    }

    public static SetCurrentSpellSetPacket decode(FriendlyByteBuf buf) {
        return new SetCurrentSpellSetPacket(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(setId);
    }

    public static void handle(SetCurrentSpellSetPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                KnowledgeUtil.setCurrentSpellSet(player, msg.setId);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
