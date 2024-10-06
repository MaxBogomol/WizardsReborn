package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.TrailParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.TwoPositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AirFlowSpellPacket extends TwoPositionColorClientPacket {

    public AirFlowSpellPacket(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a) {
        super(x1, y1, z1, x2, y2, z2, r, g, b, a);
    }

    public AirFlowSpellPacket(Vec3 vec1, Vec3 vec2, Color color) {
        super(vec1, vec2, color);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        Vec3 vel = new Vec3(x2, y2, z2);
        final Consumer<GenericParticle> target = p -> {
            double dX = vel.x();
            double dY = vel.y();
            double dZ = vel.z();

            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

            float speed = 0.1f + (p.getAge() * 0.001f);
            double x = Math.sin(pitch) * Math.cos(yaw) * speed;
            double y = Math.cos(pitch) * speed;
            double z = Math.sin(pitch) * Math.sin(yaw) * speed;

            p.setSpeed(p.getSpeed().add(-x, -y, -z));
        };
        ParticleBuilder.create(FluffyFurParticles.TRAIL)
                .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                .setBehavior(TrailParticleBehavior.create()
                        .setColorData(ColorParticleData.create().build())
                        .setTransparencyData(GenericParticleData.create(0).build())
                        .enableSecondColor()
                        .build())
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.6f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.5f).setEasing(Easing.QUARTIC_OUT).build())
                .addTickActor(target)
                .setLifetime(20)
                .randomVelocity(0.35f)
                .setVelocity(x2, y2, z2)
                .setFriction(0.8f)
                .repeat(level, x1, y1, z1, 25);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, AirFlowSpellPacket.class, AirFlowSpellPacket::encode, AirFlowSpellPacket::decode, AirFlowSpellPacket::handle);
    }

    public static AirFlowSpellPacket decode(FriendlyByteBuf buf) {
        return decode(AirFlowSpellPacket::new, buf);
    }
}