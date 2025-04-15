package mod.maxbogomol.wizards_reborn.common.network.arcaneenchantment;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class SonarResonatePacket extends PositionColorClientPacket {

    public SonarResonatePacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public SonarResonatePacket(Vec3 vec, Color color) {
        super(vec, color);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.05f, 0).build())
                .setScaleData(GenericParticleData.create(0.5f, 2f, 0).setEasing(Easing.BACK_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                .setLifetime(40, 20)
                .randomVelocity(0.035f)
                .repeat(level, x, y, z, 3);
        ParticleBuilder.create(FluffyFurParticles.STAR)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.1f, 0).build())
                .setScaleData(GenericParticleData.create(0.5f, 2f, 0).setEasing(Easing.BACK_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                .setLifetime(40, 20)
                .randomVelocity(0.035f)
                .spawn(level, x, y, z);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SonarResonatePacket.class, SonarResonatePacket::encode, SonarResonatePacket::decode, SonarResonatePacket::handle);
    }

    public static SonarResonatePacket decode(FriendlyByteBuf buf) {
        return decode(SonarResonatePacket::new, buf);
    }
}
