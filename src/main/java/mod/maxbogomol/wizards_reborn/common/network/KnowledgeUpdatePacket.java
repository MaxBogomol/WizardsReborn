package mod.maxbogomol.wizards_reborn.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;;
import java.util.UUID;

public class KnowledgeUpdatePacket {
    UUID uuid;
    CompoundTag tag;

    //public KnowledgeUpdatePacket(UUID uuid, CompoundTag tag) {
    public KnowledgeUpdatePacket() {
        //this.uuid = uuid;
        //this.tag = tag;
    }

    public KnowledgeUpdatePacket(Player entity) {
        /*
        this.uuid = entity.getUUID();
        entity.getCapability(KnowledgeProvider.CAPABILITY, null).ifPresent((k) -> {
            this.tag = (CompoundTag)KnowledgeProvider.CAPABILITY.getStorage().writeNBT(KnowledgeProvider.CAPABILITY, k, null);
        });*/
    }

    public static void encode(KnowledgeUpdatePacket object, FriendlyByteBuf buffer) {
        //buffer.writeUUID(object.uuid);
        //buffer.writeNbt(object.tag);
    }

    public static KnowledgeUpdatePacket decode(FriendlyByteBuf buffer) {
       // return new KnowledgeUpdatePacket(buffer.readUUID(), buffer.readNbt());
        return new KnowledgeUpdatePacket();
    }

    public static void handle(KnowledgeUpdatePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            //Level world = Minecraft.getInstance().level;
            //Player player = world.getPlayerByUUID(packet.uuid);
            /*if (player != null) {
                player.getCapability(KnowledgeProvider.CAPABILITY, null).ifPresent((k) -> {
                    KnowledgeProvider.CAPABILITY.getStorage().readNBT(KnowledgeProvider.CAPABILITY, k, null, packet.tag);
                });
            }*/
        });
        ctx.get().setPacketHandled(true);
    }
}