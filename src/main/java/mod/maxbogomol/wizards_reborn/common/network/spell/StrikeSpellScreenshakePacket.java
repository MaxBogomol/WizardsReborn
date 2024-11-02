package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.screenshake.PositionedScreenshakeInstance;
import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeHandler;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionClientPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class StrikeSpellScreenshakePacket extends PositionClientPacket {

    public StrikeSpellScreenshakePacket(double x, double y, double z) {
        super(x, y, z);
    }

    public StrikeSpellScreenshakePacket(Vec3 pos) {
        super(pos);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ScreenshakeHandler.addScreenshake(new PositionedScreenshakeInstance(80, new Vec3(x, y, z), 0, 40).setIntensity(1f, 0).setEasing(Easing.QUINTIC_IN_OUT).disableNormalize());
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, StrikeSpellScreenshakePacket.class, StrikeSpellScreenshakePacket::encode, StrikeSpellScreenshakePacket::decode, StrikeSpellScreenshakePacket::handle);
    }

    public static StrikeSpellScreenshakePacket decode(FriendlyByteBuf buf) {
        return decode(StrikeSpellScreenshakePacket::new, buf);
    }
}