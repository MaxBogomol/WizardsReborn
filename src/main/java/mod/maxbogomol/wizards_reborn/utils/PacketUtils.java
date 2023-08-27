package mod.maxbogomol.wizards_reborn.utils;

import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.TESyncPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.PacketDistributor;

public class PacketUtils {
    public static void SUpdateTileEntityPacket(BlockEntity tile) {
        if (tile.getLevel() instanceof ServerLevel) {
            tile.setChanged();
            tile.saveWithoutMetadata();
            PacketHandler.HANDLER.send(PacketDistributor.TRACKING_CHUNK.with(() -> tile.getLevel().getChunkAt(tile.getBlockPos())), new TESyncPacket(tile.getBlockPos(), tile.getUpdateTag()));
        }
    }
}
