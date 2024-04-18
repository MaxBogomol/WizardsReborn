package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetSpellInSetPacket {
    private final String spell;
    private final int setId;
    private final int spellId;

    public SetSpellInSetPacket(String spell, int setId, int spellId) {
        this.spell = spell;
        this.setId = setId;
        this.spellId = spellId;
    }

    public static SetSpellInSetPacket decode(FriendlyByteBuf buf) {
        return new SetSpellInSetPacket(buf.readUtf(), buf.readInt(), buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(spell);
        buf.writeInt(setId);
        buf.writeInt(spellId);
    }

    public static void handle(SetSpellInSetPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                KnowledgeUtils.addSpellInSet(player, msg.setId, msg.spellId, Spells.getSpell(msg.spell));
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
