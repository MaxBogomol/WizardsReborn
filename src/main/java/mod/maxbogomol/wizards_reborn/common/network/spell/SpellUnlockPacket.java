package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class SpellUnlockPacket extends ServerPacket {
    protected final String id;

    public SpellUnlockPacket(Spell spell) {
        this.id = spell.getId();
    }

    public SpellUnlockPacket(String id) {
        this.id = id;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();

        if (SpellHandler.getSpell(id) != null) {
            KnowledgeUtil.addSpell(player, SpellHandler.getSpell(id));
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SpellUnlockPacket.class, SpellUnlockPacket::encode, SpellUnlockPacket::decode, SpellUnlockPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(id);
    }

    public static SpellUnlockPacket decode(FriendlyByteBuf buf) {
        return new SpellUnlockPacket(buf.readUtf());
    }
}
