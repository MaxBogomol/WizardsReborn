package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.api.spell.SpellHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class SpellSetSetSpellPacket extends ServerPacket {
    protected final String spell;
    protected final int setId;
    protected final int spellId;

    public SpellSetSetSpellPacket(String spell, int setId, int spellId) {
        this.spell = spell;
        this.setId = setId;
        this.spellId = spellId;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        KnowledgeUtil.addSpellInSet(player, setId, spellId, SpellHandler.getSpell(spell));
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SpellSetSetSpellPacket.class, SpellSetSetSpellPacket::encode, SpellSetSetSpellPacket::decode, SpellSetSetSpellPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(spell);
        buf.writeInt(setId);
        buf.writeInt(spellId);
    }

    public static SpellSetSetSpellPacket decode(FriendlyByteBuf buf) {
        return new SpellSetSetSpellPacket(buf.readUtf(), buf.readInt(), buf.readInt());
    }
}
