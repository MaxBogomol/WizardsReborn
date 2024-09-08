package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.capability.IKnowledge;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class KnowledgeUpdatePacket {
    private final UUID uuid;
    private CompoundTag tag;

    public KnowledgeUpdatePacket(UUID uuid, CompoundTag tag) {
        this.uuid = uuid;
        this.tag = tag;
    }

    public KnowledgeUpdatePacket(Player entity) {
        this.uuid = entity.getUUID();
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((k) -> {
            this.tag = ((INBTSerializable<CompoundTag>)k).serializeNBT();
        });
    }

    public static void encode(KnowledgeUpdatePacket object, FriendlyByteBuf buffer) {
        buffer.writeUUID(object.uuid);
        buffer.writeNbt(object.tag);
    }

    public static KnowledgeUpdatePacket decode(FriendlyByteBuf buffer) {
       return new KnowledgeUpdatePacket(buffer.readUUID(), buffer.readNbt());
    }

    public static void handle(KnowledgeUpdatePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level level = WizardsReborn.proxy.getLevel();
            Player player = level.getPlayerByUUID(packet.uuid);
            if (player != null) {
                player.getCapability(IKnowledge.INSTANCE, null).ifPresent((k) -> {
                    ((INBTSerializable<CompoundTag>)k).deserializeNBT(packet.tag);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}