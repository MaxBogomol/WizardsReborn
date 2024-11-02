package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpellSetsRemovePacket {
    private final int setId;

    public SpellSetsRemovePacket(int setId) {
        this.setId = setId;
    }

    public static SpellSetsRemovePacket decode(FriendlyByteBuf buf) {
        return new SpellSetsRemovePacket(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(setId);
    }

    public static void handle(SpellSetsRemovePacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                KnowledgeUtil.removeSpellSet(player, msg.setId);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
