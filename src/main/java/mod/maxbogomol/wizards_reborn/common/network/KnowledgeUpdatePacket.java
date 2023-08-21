package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.common.capability.KnowledgeProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class KnowledgeUpdatePacket {
    UUID uuid;
    CompoundNBT tag;

    public KnowledgeUpdatePacket(UUID uuid, CompoundNBT tag) {
        this.uuid = uuid;
        this.tag = tag;
    }

    public KnowledgeUpdatePacket(PlayerEntity entity) {
        this.uuid = entity.getUniqueID();
        entity.getCapability(KnowledgeProvider.CAPABILITY, null).ifPresent((k) -> {
            this.tag = (CompoundNBT)KnowledgeProvider.CAPABILITY.getStorage().writeNBT(KnowledgeProvider.CAPABILITY, k, null);
        });
    }

    public static void encode(KnowledgeUpdatePacket object, PacketBuffer buffer) {
        buffer.writeUniqueId(object.uuid);
        buffer.writeCompoundTag(object.tag);
    }

    public static KnowledgeUpdatePacket decode(PacketBuffer buffer) {
        return new KnowledgeUpdatePacket(buffer.readUniqueId(), buffer.readCompoundTag());
    }

    public static void handle(KnowledgeUpdatePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            World world = Minecraft.getInstance().world;
            PlayerEntity player = world.getPlayerByUuid(packet.uuid);
            if (player != null) {
                player.getCapability(KnowledgeProvider.CAPABILITY, null).ifPresent((k) -> {
                    KnowledgeProvider.CAPABILITY.getStorage().readNBT(KnowledgeProvider.CAPABILITY, k, null, packet.tag);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}