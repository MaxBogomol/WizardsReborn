package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.sound.WissenCrystallizerSoundInstance;
import mod.maxbogomol.wizards_reborn.common.tileentity.WissenCrystallizerTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class WissenCrystallizerSoundPacket {
    private final BlockPos pos;

    public WissenCrystallizerSoundPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static WissenCrystallizerSoundPacket decode(FriendlyByteBuf buf) {
        return new WissenCrystallizerSoundPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(WissenCrystallizerSoundPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    if (world.getBlockEntity(msg.pos) instanceof WissenCrystallizerTileEntity tile) {
                        WissenCrystallizerSoundInstance.playSound(tile);
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
