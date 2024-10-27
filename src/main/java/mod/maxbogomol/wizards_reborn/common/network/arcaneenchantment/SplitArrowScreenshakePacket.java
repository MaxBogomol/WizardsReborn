package mod.maxbogomol.wizards_reborn.common.network.arcaneenchantment;

import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeHandler;
import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeInstance;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.ClientPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class SplitArrowScreenshakePacket extends ClientPacket {

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(20).setIntensity(0.5f, 0).setEasing(Easing.QUINTIC_IN_OUT));
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SplitArrowScreenshakePacket.class, SplitArrowScreenshakePacket::encode, SplitArrowScreenshakePacket::decode, SplitArrowScreenshakePacket::handle);
    }

    public static SplitArrowScreenshakePacket decode(FriendlyByteBuf buf) {
        return new SplitArrowScreenshakePacket();
    }
}
