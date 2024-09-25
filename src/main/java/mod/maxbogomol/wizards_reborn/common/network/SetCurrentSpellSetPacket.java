package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class SetCurrentSpellSetPacket extends ServerPacket {
    protected final int setId;

    public SetCurrentSpellSetPacket(int setId) {
        this.setId = setId;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        KnowledgeUtil.setCurrentSpellSet(player, setId);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SetCurrentSpellSetPacket.class, SetCurrentSpellSetPacket::encode, SetCurrentSpellSetPacket::decode, SetCurrentSpellSetPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(setId);
    }

    public static SetCurrentSpellSetPacket decode(FriendlyByteBuf buf) {
        return new SetCurrentSpellSetPacket(buf.readInt());
    }
}
