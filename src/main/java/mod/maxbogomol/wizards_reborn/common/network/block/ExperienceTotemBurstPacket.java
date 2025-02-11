package mod.maxbogomol.wizards_reborn.common.network.block;

import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.TwoPositionClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExperienceTotemBurstPacket extends TwoPositionClientPacket {

    public ExperienceTotemBurstPacket(double x1, double y1, double z1, double x2, double y2, double z2) {
        super(x1, y1, z1, x2, y2, z2);
    }

    public ExperienceTotemBurstPacket(BlockPos pos, double x2, double y2, double z2) {
        super(pos, x2, y2, z2);
    }

    public ExperienceTotemBurstPacket(BlockPos pos, Vec3 vec) {
        super(pos, vec);
    }

    public ExperienceTotemBurstPacket(Vec3 pos, Vec3 vec) {
        super(pos, vec);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        final Consumer<GenericParticle> blockTarget = p -> {
            Vec3 pos = p.getPosition();

            double dX = x1 - pos.x();
            double dY = y1 + 0.25f - pos.y();
            double dZ = z1 - pos.z();

            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

            float speed = 0.01f;
            double x = Math.sin(pitch) * Math.cos(yaw) * speed;
            double y = Math.cos(pitch) * speed;
            double z = Math.sin(pitch) * Math.sin(yaw) * speed;

            p.setSpeed(p.getSpeed().subtract(x, y, z));
        };
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(0.784f, 1f, 0.560f).build())
                .setTransparencyData(GenericParticleData.create(0.3f, 0.6f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.15f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .addTickActor(blockTarget)
                .setLifetime(50)
                .randomVelocity(0.5f)
                .disablePhysics()
                .setFriction(0.9f)
                .repeat(level, x2, y2, z2, 5);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ExperienceTotemBurstPacket.class, ExperienceTotemBurstPacket::encode, ExperienceTotemBurstPacket::decode, ExperienceTotemBurstPacket::handle);
    }

    public static ExperienceTotemBurstPacket decode(FriendlyByteBuf buf) {
        return decode(ExperienceTotemBurstPacket::new, buf);
    }
}