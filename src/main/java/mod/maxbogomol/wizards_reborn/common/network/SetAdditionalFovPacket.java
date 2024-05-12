package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.client.event.ViewHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetAdditionalFovPacket {
    private final float fov;

    public SetAdditionalFovPacket(float fov) {
        this.fov = fov;
    }

    public static SetAdditionalFovPacket decode(FriendlyByteBuf buf) {
        return new SetAdditionalFovPacket(buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(fov);
    }

    public static void handle(SetAdditionalFovPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    if (msg.fov > ViewHandler.additionalFov) ViewHandler.additionalFov = msg.fov;
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
