package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.CubeParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.TrailParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.TwoPositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BlinkSpellPacket extends TwoPositionColorClientPacket {

    public BlinkSpellPacket(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a) {
        super(x1, y1, z1, x2, y2, z2, r, g, b, a);
    }

    public BlinkSpellPacket(Vec3 vec1, Vec3 vec2, Color color, float a) {
        super(vec1, vec2, color, a);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        final Consumer<GenericParticle> target = p -> {
            Vec3 pos = p.getPosition();

            double dX = x2 - pos.x();
            double dY = y2 - pos.y();
            double dZ = z2 - pos.z();

            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

            float speed = 0.4f;
            double x = Math.sin(pitch) * Math.cos(yaw) * speed;
            double y = Math.cos(pitch) * speed;
            double z = Math.sin(pitch) * Math.sin(yaw) * speed;

            p.setSpeed(p.getSpeed().subtract(x, y, z));
        };
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                .setBehavior(TrailParticleBehavior.create().setWidthFunction(RenderUtil.LINEAR_IN_ROUND_WIDTH_FUNCTION).build())
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.6f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.5f).setEasing(Easing.QUARTIC_OUT).build())
                .addTickActor(target)
                .setLifetime(30)
                .randomVelocity(0.5f)
                .setFriction(0.8f)
                .repeat(level, x1, y1, z1, 25);

        for (int i = 0; i < 25; i++) {
            double lerp = random.nextDouble();
            double lx = Mth.lerp(lerp, x1, x2);
            double ly = Mth.lerp(lerp, y1, y2);
            double lz = Mth.lerp(lerp, z1, z2);

            double dX = x1 - x2;
            double dY = y1 - y2;
            double dZ = z1 - z2;

            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

            float speed = 0.1f;
            double X = Math.sin(pitch) * Math.cos(yaw) * speed;
            double Y = Math.cos(pitch) * speed;
            double Z = Math.sin(pitch) * Math.sin(yaw) * speed;

            if (a > 0) {
                if (random.nextFloat() < 0.75) {
                    ParticleBuilder.create(FluffyFurParticles.SQUARE)
                            .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                            .setBehavior(CubeParticleBehavior.create().build())
                            .setColorData(ColorParticleData.create(r, g, b).build())
                            .setTransparencyData(GenericParticleData.create(0.2f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 0.2f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.1f).build())
                            .setLifetime(70)
                            .randomVelocity(0.015f)
                            .addVelocity(X, Y, Z)
                            .randomOffset(0.25f)
                            .spawn(level, lx, ly, lz);
                }
            }
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BlinkSpellPacket.class, BlinkSpellPacket::encode, BlinkSpellPacket::decode, BlinkSpellPacket::handle);
    }

    public static BlinkSpellPacket decode(FriendlyByteBuf buf) {
        return decode(BlinkSpellPacket::new, buf);
    }
}