package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveSpellSetPacket {
    private final int setId;

    public RemoveSpellSetPacket(int setId) {
        this.setId = setId;
    }

    public static RemoveSpellSetPacket decode(FriendlyByteBuf buf) {
        return new RemoveSpellSetPacket(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(setId);
    }

    public static void handle(RemoveSpellSetPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                for (int i = 0; i < 10; i++) {
                    KnowledgeUtil.addSpellInSet(player, msg.setId, i, null);
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
