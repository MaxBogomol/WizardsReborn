package mod.maxbogomol.wizards_reborn.common.network;

import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

public class PacketUtils {
    public static void SUpdateTileEntityPacket(TileEntity tile) {
        if (tile.getWorld() instanceof ServerWorld) {
            SUpdateTileEntityPacket packet = tile.getUpdatePacket();
            if (packet != null) {
                BlockPos poss = tile.getPos();
                ((ServerChunkProvider) tile.getWorld().getChunkProvider()).chunkManager
                        .getTrackingPlayers(new ChunkPos(poss), false)
                        .forEach(e -> e.connection.sendPacket(packet));
            }
        }
    }
}
