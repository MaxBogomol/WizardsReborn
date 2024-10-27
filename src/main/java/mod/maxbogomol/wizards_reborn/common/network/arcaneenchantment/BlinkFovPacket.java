package mod.maxbogomol.wizards_reborn.common.network.arcaneenchantment;

import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeHandler;
import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeInstance;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.ClientPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class BlinkFovPacket extends ClientPacket {

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(35).setIntensity(0.5f, 0).setEasing(Easing.QUINTIC_IN_OUT).disableRotation().enableFov());
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BlinkFovPacket.class, BlinkFovPacket::encode, BlinkFovPacket::decode, BlinkFovPacket::handle);
    }

    public static BlinkFovPacket decode(FriendlyByteBuf buf) {
        return new BlinkFovPacket();
    }
}
