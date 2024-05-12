package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.client.event.ScreenshakeHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AddScreenshakePacket {
    private final float shake;

    public AddScreenshakePacket(float shake) {
        this.shake = shake;
    }

    public static AddScreenshakePacket decode(FriendlyByteBuf buf) {
        return new AddScreenshakePacket(buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(shake);
    }

    public static void handle(AddScreenshakePacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    ScreenshakeHandler.addScreenshake(msg.shake);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
