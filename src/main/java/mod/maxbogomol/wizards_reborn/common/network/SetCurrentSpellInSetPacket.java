package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class SetCurrentSpellInSetPacket extends ServerPacket {
    protected final int setId;

    public SetCurrentSpellInSetPacket(int setId) {
        this.setId = setId;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        KnowledgeUtil.setCurrentSpellInSet(player, setId);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SetCurrentSpellInSetPacket.class, SetCurrentSpellInSetPacket::encode, SetCurrentSpellInSetPacket::decode, SetCurrentSpellInSetPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(setId);
    }

    public static SetCurrentSpellInSetPacket decode(FriendlyByteBuf buf) {
        return new SetCurrentSpellInSetPacket(buf.readInt());
    }
}
