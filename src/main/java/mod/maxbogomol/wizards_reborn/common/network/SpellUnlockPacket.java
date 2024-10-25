package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpellUnlockPacket {
    private final Component spell;

    public SpellUnlockPacket(Spell spell) {
        this.spell = Component.literal(spell.getId());
    }

    public SpellUnlockPacket(String spell) {
        this.spell = Component.literal(spell);
    }

    public SpellUnlockPacket(Component spell) {
        this.spell = spell;
    }

    public static SpellUnlockPacket decode(FriendlyByteBuf buf) {
        return new SpellUnlockPacket(buf.readComponent());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeComponent(spell);
    }

    public static void handle(SpellUnlockPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                if (SpellHandler.getSpell(msg.spell.getString()) != null) {
                    KnowledgeUtil.addSpell(player, SpellHandler.getSpell(msg.spell.getString()));
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
