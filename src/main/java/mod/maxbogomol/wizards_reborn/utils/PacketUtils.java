package mod.maxbogomol.wizards_reborn.utils;

import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;

public class PacketUtils {
    public static void SUpdateTileEntityPacket(BlockEntity tile) {
        //if (tile.getLevel() instanceof ServerLevel) {
            /*ClientboundBlockEntityDataPacket packet = tile.getUpdatePacket();
            if (packet != null) {
                BlockPos poss = tile.getBlockPos();
                ((ServerChunkCache) tile.getLevel().getChunkSource()).chunkMap
                        .getPlayers(new ChunkPos(poss), false)
                        .forEach(e -> e.connection.send(packet));
            }*/
        //}
    }
}
