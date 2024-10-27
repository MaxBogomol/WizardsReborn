package mod.maxbogomol.wizards_reborn.common.network.arcaneenchantment;

import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeHandler;
import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeInstance;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.ClientPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class EagleShotScreenshakePacket extends ClientPacket {

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(20).setIntensity(0.35f, 0).setEasing(Easing.QUINTIC_IN_OUT));
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, EagleShotScreenshakePacket.class, EagleShotScreenshakePacket::encode, EagleShotScreenshakePacket::decode, EagleShotScreenshakePacket::handle);
    }

    public static EagleShotScreenshakePacket decode(FriendlyByteBuf buf) {
        return new EagleShotScreenshakePacket();
    }
}
