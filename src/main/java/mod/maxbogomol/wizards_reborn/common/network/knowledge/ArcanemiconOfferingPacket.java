package mod.maxbogomol.wizards_reborn.common.network.knowledge;

import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.TrailParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ArcanemiconOfferingPacket extends PositionClientPacket {

    public ArcanemiconOfferingPacket(double x, double y, double z) {
        super(x, y, z);
    }

    public ArcanemiconOfferingPacket(Vec3 vec) {
        super(vec);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();

        ParticleBuilder sparkleBuilder = ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                .setBehavior(TrailParticleBehavior.create().setWidthFunction(RenderUtil.LINEAR_IN_ROUND_WIDTH_FUNCTION).build())
                .setColorData(ColorParticleData.create(new Color(123, 73, 109)).build())
                .setTransparencyData(GenericParticleData.create(0.4f, 0.4f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.2f, 0.4f, 0f).setEasing(Easing.ELASTIC_OUT).build())
                .setLifetime(100)
                .randomOffset(0.05f)
                .randomVelocity(0.05f);

        ParticleBuilder circleBuilder = ParticleBuilder.create(FluffyFurParticles.TINY_CIRCLE)
                .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                .setBehavior(TrailParticleBehavior.create().setWidthFunction(RenderUtil.LINEAR_IN_ROUND_WIDTH_FUNCTION).build())
                .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColor()).build())
                .setTransparencyData(GenericParticleData.create(0.4f, 0.4f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.2f, 0.4f, 0f).setEasing(Easing.ELASTIC_OUT).build())
                .setLifetime(100)
                .randomOffset(0.05f)
                .randomVelocity(0.05f);

        for (int i = 0; i < 36; i++) {
            float distance = 0.5f + (0.25f * random.nextFloat());
            double yaw = Math.toRadians(10 * i);
            double pitch = 90;

            double X = Math.sin(pitch) * Math.cos(yaw) * distance;
            double Z = Math.sin(pitch) * Math.sin(yaw) * distance;

            sparkleBuilder.setVelocity(X / 5, 0, Z / 5)
                    .spawn(level, x, y, z);
            circleBuilder.setVelocity(X / 6, 0, Z / 6)
                    .spawn(level, x, y, z);
        }

        ParticleBuilder.create(FluffyFurParticles.DOT)
                .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                .setBehavior(TrailParticleBehavior.create().setWidthFunction(RenderUtil.LINEAR_IN_ROUND_WIDTH_FUNCTION).build())
                .setColorData(ColorParticleData.create(new Color(123, 73, 109)).build())
                .setTransparencyData(GenericParticleData.create(0.3f, 0.3f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.3f, 0.4f, 0).setEasing(Easing.ELASTIC_OUT).build())
                .setLifetime(100)
                .randomVelocity(0.1f)
                .randomOffset(0.1f)
                .addVelocity(0, 0.3f, 0)
                .setGravity(0.5f)
                .repeat(level, x, y, z, 30);

        final Consumer<GenericParticle> playerTarget = p -> {
            Vec3 playerPos = WizardsReborn.proxy.getPlayer().position();
            Vec3 pos = p.getPosition();

            double dX = playerPos.x() - pos.x();
            double dY = playerPos.y() + 0.5f - pos.y();
            double dZ = playerPos.z() - pos.z();

            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

            float speed = 0.03f;
            double x = Math.sin(pitch) * Math.cos(yaw) * speed;
            double y = Math.cos(pitch) * speed;
            double z = Math.sin(pitch) * Math.sin(yaw) * speed;

            p.setSpeed(p.getSpeed().subtract(x, y, z));
        };

        ParticleBuilder starBuilder = ParticleBuilder.create(FluffyFurParticles.STAR)
                .setColorData(ColorParticleData.create().setRandomColor().build())
                .setTransparencyData(GenericParticleData.create(0.3f, 0.3f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.15f).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.2f).build())
                .addTickActor(playerTarget)
                .setLifetime(200)
                .randomVelocity(0.6f)
                .disablePhysics()
                .setFriction(0.95f);
        ParticleBuilder.create(FluffyFurParticles.TRAIL)
                .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                .setBehavior(TrailParticleBehavior.create()
                        .setColorData(ColorParticleData.create().setRandomColor().build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .enableSecondColor()
                        .setWidthFunction(RenderUtil.LINEAR_IN_SEMI_ROUND_WIDTH_FUNCTION)
                        .build())
                .setColorData(ColorParticleData.create().setRandomColor().build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.5f).build())
                .addTickActor(playerTarget)
                .setLifetime(200)
                .randomVelocity(0.7f)
                .disablePhysics()
                .setFriction(0.95f)
                .addAdditionalBuilder(starBuilder)
                .repeat(level, x, y, z, 10);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ArcanemiconOfferingPacket.class, ArcanemiconOfferingPacket::encode, ArcanemiconOfferingPacket::decode, ArcanemiconOfferingPacket::handle);
    }

    public static ArcanemiconOfferingPacket decode(FriendlyByteBuf buf) {
        return decode(ArcanemiconOfferingPacket::new, buf);
    }
}
