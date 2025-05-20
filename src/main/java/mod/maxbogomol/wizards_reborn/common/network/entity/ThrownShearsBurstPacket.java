package mod.maxbogomol.wizards_reborn.common.network.entity;

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

public class ThrownShearsBurstPacket extends PositionColorClientPacket {

    public ThrownShearsBurstPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public ThrownShearsBurstPacket(Vec3 vec, Color color) {
        super(vec, color);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.TINY_STAR)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                .setScaleData(GenericParticleData.create(0.4f, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                .setLifetime(30)
                .addVelocity(0f, 0.05, 0f)
                .randomVelocity(0.03f)
                .repeat(level, x, y + 0.1, z, 5);
        ParticleBuilder.create(FluffyFurParticles.TINY_STAR)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                .setScaleData(GenericParticleData.create(0.4f, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                .setLifetime(30)
                .addVelocity(0f, -0.05, 0f)
                .randomVelocity(0.03f)
                .repeat(level, x, y + 0.1, z, 5);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ThrownShearsBurstPacket.class, ThrownShearsBurstPacket::encode, ThrownShearsBurstPacket::decode, ThrownShearsBurstPacket::handle);
    }

    public static ThrownShearsBurstPacket decode(FriendlyByteBuf buf) {
        return decode(ThrownShearsBurstPacket::new, buf);
    }
}
