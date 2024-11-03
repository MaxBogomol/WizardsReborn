package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeHandler;
import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeInstance;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.ClientPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class CrystalCrushingSpellScreenshakePacket extends ClientPacket {

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(40).setIntensity(0.6f, 0).setEasing(Easing.QUINTIC_IN_OUT));
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, CrystalCrushingSpellScreenshakePacket.class, CrystalCrushingSpellScreenshakePacket::encode, CrystalCrushingSpellScreenshakePacket::decode, CrystalCrushingSpellScreenshakePacket::handle);
    }

    public static CrystalCrushingSpellScreenshakePacket decode(FriendlyByteBuf buf) {
        return new CrystalCrushingSpellScreenshakePacket();
    }
}