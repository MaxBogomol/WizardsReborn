package mod.maxbogomol.wizards_reborn.common.network.entity;

import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeHandler;
import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeInstance;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionClientPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class ThrownScytheScreenshakePacket extends PositionClientPacket {

    public ThrownScytheScreenshakePacket(double x, double y, double z) {
        super(x, y, z);
    }

    public ThrownScytheScreenshakePacket(Vec3 vec) {
        super(vec);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        double yaw = Math.atan2(z, x);
        double pitch = Math.atan2(Math.sqrt(z * z + x * x), y) + Math.PI;
        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.cos(pitch);
        double z = Math.sin(pitch) * Math.sin(yaw);
        ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(10).setIntensity(0, 0.25f, 0).setEasing(Easing.EXPO_OUT, Easing.QUINTIC_IN_OUT).disableRotation().enableVector().setVector(new Vec3(x, y, z)));
        ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(10).setIntensity(0.5f, 0).setEasing(Easing.QUINTIC_IN_OUT).disableNormalize());
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ThrownScytheScreenshakePacket.class, ThrownScytheScreenshakePacket::encode, ThrownScytheScreenshakePacket::decode, ThrownScytheScreenshakePacket::handle);
    }

    public static ThrownScytheScreenshakePacket decode(FriendlyByteBuf buf) {
        return decode(ThrownScytheScreenshakePacket::new, buf);
    }
}
