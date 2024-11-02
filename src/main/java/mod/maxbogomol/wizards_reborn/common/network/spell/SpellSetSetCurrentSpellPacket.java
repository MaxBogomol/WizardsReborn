package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class SpellSetSetCurrentSpellPacket extends ServerPacket {
    protected final int setId;

    public SpellSetSetCurrentSpellPacket(int setId) {
        this.setId = setId;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        KnowledgeUtil.setCurrentSpellInSet(player, setId);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SpellSetSetCurrentSpellPacket.class, SpellSetSetCurrentSpellPacket::encode, SpellSetSetCurrentSpellPacket::decode, SpellSetSetCurrentSpellPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(setId);
    }

    public static SpellSetSetCurrentSpellPacket decode(FriendlyByteBuf buf) {
        return new SpellSetSetCurrentSpellPacket(buf.readInt());
    }
}
