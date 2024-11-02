package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class SpellSetSetCurrentPacket extends ServerPacket {
    protected final int setId;

    public SpellSetSetCurrentPacket(int setId) {
        this.setId = setId;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        KnowledgeUtil.setCurrentSpellSet(player, setId);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SpellSetSetCurrentPacket.class, SpellSetSetCurrentPacket::encode, SpellSetSetCurrentPacket::decode, SpellSetSetCurrentPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(setId);
    }

    public static SpellSetSetCurrentPacket decode(FriendlyByteBuf buf) {
        return new SpellSetSetCurrentPacket(buf.readInt());
    }
}
