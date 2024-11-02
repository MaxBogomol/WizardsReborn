package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SparkParticleBehavior;
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
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class StrikeSpellBurstPacket extends PositionColorClientPacket {

    public StrikeSpellBurstPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public StrikeSpellBurstPacket(Vec3 pos, Color color) {
        super(pos, color);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                .setScaleData(GenericParticleData.create(0.4f, 0.5f, 0f).setEasing(Easing.QUARTIC_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.4f).build())
                .setLifetime(50, 10)
                .randomVelocity(0.05f)
                .setFriction(0.9f)
                .repeat(level, x, y, z, 10);
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                .setScaleData(GenericParticleData.create(0.4f, 0.5f, 0f).setEasing(Easing.QUARTIC_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.4f).build())
                .setLifetime(50, 10)
                .randomVelocity(0.05f)
                .repeat(level, x, y, z, 10);
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                .setScaleData(GenericParticleData.create(0.1f, 0.25f, 0f).setEasing(Easing.QUARTIC_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.4f).build())
                .setLifetime(50, 10)
                .randomVelocity(0.05f)
                .repeat(level, x, y, z, 10);
        ParticleBuilder.create(FluffyFurParticles.SQUARE)
                .setBehavior(SparkParticleBehavior.create().build())
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.1f,  0).setEasing(Easing.ELASTIC_OUT).build())
                .setLifetime(20)
                .randomVelocity(0.5f)
                .addVelocity(0, 0.3f, 0)
                .randomOffset(0.25f)
                .setFriction(0.9f)
                .setGravity(1f)
                .repeat(level, x, y, z, 40, 0.8f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, StrikeSpellBurstPacket.class, StrikeSpellBurstPacket::encode, StrikeSpellBurstPacket::decode, StrikeSpellBurstPacket::handle);
    }

    public static StrikeSpellBurstPacket decode(FriendlyByteBuf buf) {
        return decode(StrikeSpellBurstPacket::new, buf);
    }
}