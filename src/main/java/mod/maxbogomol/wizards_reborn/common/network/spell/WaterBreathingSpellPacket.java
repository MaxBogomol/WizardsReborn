package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SparkParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.TwoPositionColorClientPacket;
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

public class WaterBreathingSpellPacket extends TwoPositionColorClientPacket {

    public WaterBreathingSpellPacket(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a) {
        super(x1, y1, z1, x2, y2, z2, r, g, b, a);
    }

    public WaterBreathingSpellPacket(Vec3 vec1, Vec3 vec2, Color color) {
        super(vec1, vec2, color);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setBehavior(SparkParticleBehavior.create().build())
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.1f, 0.2f,  0).setEasing(Easing.ELASTIC_OUT).build())
                .setLifetime(30)
                .randomVelocity(0.5f)
                .addVelocity(0, 0.1f, 0)
                .randomOffset(0.25f)
                .setFriction(0.9f)
                .setGravity(1f)
                .repeat(level, x1, y1, z1, 15, 0.6f);
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.1f, 0.2f,  0).setEasing(Easing.ELASTIC_OUT).build())
                .setLifetime(40)
                .randomVelocity(0.05f)
                .addVelocity(0, 0.06f, 0)
                .repeat(level, x1, y1, z1, 15, 0.6f);
        ParticleBuilder.create(FluffyFurParticles.SQUARE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.6f, 0).build())
                .setScaleData(GenericParticleData.create(0f, 0.12f, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.1f).build())
                .setLifetime(30)
                .randomVelocity(0.005f)
                .flatRandomOffset(x2, y2, z2)
                .repeat(level, x1, y1, z1, 15, 0.6f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, WaterBreathingSpellPacket.class, WaterBreathingSpellPacket::encode, WaterBreathingSpellPacket::decode, WaterBreathingSpellPacket::handle);
    }

    public static WaterBreathingSpellPacket decode(FriendlyByteBuf buf) {
        return decode(WaterBreathingSpellPacket::new, buf);
    }
}