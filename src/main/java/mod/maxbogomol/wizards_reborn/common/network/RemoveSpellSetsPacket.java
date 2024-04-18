package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveSpellSetsPacket {
    private final int setId;

    public RemoveSpellSetsPacket(int setId) {
        this.setId = setId;
    }

    public static RemoveSpellSetsPacket decode(FriendlyByteBuf buf) {
        return new RemoveSpellSetsPacket(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(setId);
    }

    public static void handle(RemoveSpellSetsPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                KnowledgeUtils.removeSpellSet(player, msg.setId);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
