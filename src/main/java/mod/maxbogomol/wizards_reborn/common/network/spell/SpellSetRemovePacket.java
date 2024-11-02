package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class SpellSetRemovePacket extends ServerPacket {
    protected final int setId;

    public SpellSetRemovePacket(int setId) {
        this.setId = setId;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();

        for (int i = 0; i < 10; i++) {
            KnowledgeUtil.addSpellInSet(player, setId, i, null);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SpellSetRemovePacket.class, SpellSetRemovePacket::encode, SpellSetRemovePacket::decode, SpellSetRemovePacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(setId);
    }

    public static SpellSetRemovePacket decode(FriendlyByteBuf buf) {
        return new SpellSetRemovePacket(buf.readInt());
    }
}
