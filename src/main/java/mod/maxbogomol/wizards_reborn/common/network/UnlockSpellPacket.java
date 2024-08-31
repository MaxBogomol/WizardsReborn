package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UnlockSpellPacket {
    private final Component spell;

    public UnlockSpellPacket(Spell spell) {
        this.spell = Component.literal(spell.getId());
    }

    public UnlockSpellPacket(String spell) {
        this.spell = Component.literal(spell);
    }

    public UnlockSpellPacket(Component spell) {
        this.spell = spell;
    }

    public static UnlockSpellPacket decode(FriendlyByteBuf buf) {
        return new UnlockSpellPacket(buf.readComponent());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeComponent(spell);
    }

    public static void handle(UnlockSpellPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                if (Spells.getSpell(msg.spell.getString()) != null) {
                    KnowledgeUtil.addSpell(player, Spells.getSpell(msg.spell.getString()));
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
