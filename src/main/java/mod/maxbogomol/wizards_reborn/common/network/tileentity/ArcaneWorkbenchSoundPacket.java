package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.sound.ArcaneWorkbenchSoundInstance;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcaneWorkbenchTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ArcaneWorkbenchSoundPacket {
    private final BlockPos pos;

    public ArcaneWorkbenchSoundPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static ArcaneWorkbenchSoundPacket decode(FriendlyByteBuf buf) {
        return new ArcaneWorkbenchSoundPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(ArcaneWorkbenchSoundPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    if (world.getBlockEntity(msg.pos) instanceof ArcaneWorkbenchTileEntity tile) {
                        ArcaneWorkbenchSoundInstance.playSound(tile);
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
