package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.sound.ArcaneIteratorSoundInstance;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcaneIteratorTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ArcaneIteratorSoundPacket {
    private final BlockPos pos;

    public ArcaneIteratorSoundPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static ArcaneIteratorSoundPacket decode(FriendlyByteBuf buf) {
        return new ArcaneIteratorSoundPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(ArcaneIteratorSoundPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    if (world.getBlockEntity(msg.pos) instanceof ArcaneIteratorTileEntity tile) {
                        ArcaneIteratorSoundInstance.playSound(tile);
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
