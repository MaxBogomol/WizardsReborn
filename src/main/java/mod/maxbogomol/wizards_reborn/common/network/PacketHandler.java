package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public final class PacketHandler {
    private static final String PROTOCOL = "10";
    public static final SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(WizardsReborn.MOD_ID,"network"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public static void init() {
        int id = 0;
        HANDLER.registerMessage(id++, SetCrystalPacket.class, SetCrystalPacket::encode, SetCrystalPacket::decode, SetCrystalPacket::handle);
        HANDLER.registerMessage(id++, DeleteCrystalPacket.class, DeleteCrystalPacket::encode, DeleteCrystalPacket::decode, DeleteCrystalPacket::handle);

        HANDLER.registerMessage(id++, WissenAltarBurstEffectPacket.class, WissenAltarBurstEffectPacket::encode, WissenAltarBurstEffectPacket::decode, WissenAltarBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, WissenCrystallizerBurstEffectPacket.class, WissenCrystallizerBurstEffectPacket::encode, WissenCrystallizerBurstEffectPacket::decode, WissenCrystallizerBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, ArcaneWorkbenchBurstEffectPacket.class, ArcaneWorkbenchBurstEffectPacket::encode, ArcaneWorkbenchBurstEffectPacket::decode, ArcaneWorkbenchBurstEffectPacket::handle);

        HANDLER.registerMessage(id++, WissenTranslatorBurstEffectPacket.class, WissenTranslatorBurstEffectPacket::encode, WissenTranslatorBurstEffectPacket::decode, WissenTranslatorBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, WissenTranslatorSendEffectPacket.class, WissenTranslatorSendEffectPacket::encode, WissenTranslatorSendEffectPacket::decode, WissenTranslatorSendEffectPacket::handle);
        HANDLER.registerMessage(id++, WissenSendEffectPacket.class, WissenSendEffectPacket::encode, WissenSendEffectPacket::decode, WissenSendEffectPacket::handle);
    }

    public static void sendToNearby(World world, BlockPos pos, Object toSend) {
        if (world instanceof ServerWorld) {
            ServerWorld ws = (ServerWorld) world;

            ws.getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false)
                    .filter(p -> p.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64)
                    .forEach(p -> HANDLER.send(PacketDistributor.PLAYER.with(() -> p), toSend));
        }
    }

    public static void sendToNearby(World world, Entity e, Object toSend) {
        sendToNearby(world, e.getPosition(), toSend);
    }

    public static void sendTo(ServerPlayerEntity playerMP, Object toSend) {
        HANDLER.sendTo(toSend, playerMP.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendNonLocal(ServerPlayerEntity playerMP, Object toSend) {
        if (playerMP.server.isDedicatedServer() || !playerMP.getGameProfile().getName().equals(playerMP.server.getServerOwner())) {
            sendTo(playerMP, toSend);
        }
    }

    public static void sendToTracking(World world, BlockPos pos, Object msg) {
        HANDLER.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), msg);
    }

    public static void sendToServer(Object msg) {
        HANDLER.sendToServer(msg);
    }

    private PacketHandler() {}

}