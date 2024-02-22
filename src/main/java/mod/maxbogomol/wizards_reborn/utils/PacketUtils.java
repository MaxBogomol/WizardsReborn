package mod.maxbogomol.wizards_reborn.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;

public class PacketUtils {
    public static void SUpdateTileEntityPacket(BlockEntity tile) {
        if (tile != null && tile.getLevel() instanceof ServerLevel) {
            Packet<?> packet = tile.getUpdatePacket();
            if (packet != null) {
                BlockPos pos = tile.getBlockPos();
                ((ServerChunkCache) tile.getLevel().getChunkSource()).chunkMap
                        .getPlayers(new ChunkPos(pos), false)
                        .forEach(e -> e.connection.send(packet));
            }
        }
    }
}
