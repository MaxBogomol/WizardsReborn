package mod.maxbogomol.wizards_reborn.common.network.block;

import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExperienceTotemBurstEffectPacket {
    private final BlockPos pos;
    private final float X, Y, Z;

    private static final Random random = new Random();

    public ExperienceTotemBurstEffectPacket(BlockPos pos, float X, float Y, float Z) {
        this.pos = pos;

        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public ExperienceTotemBurstEffectPacket(BlockPos pos, Vec3 end) {
        this.pos = pos;

        this.X = (float) end.x();
        this.Y = (float) end.y();
        this.Z = (float) end.z();
    }

    public static ExperienceTotemBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new ExperienceTotemBurstEffectPacket(buf.readBlockPos(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);

        buf.writeFloat(X);
        buf.writeFloat(Y);
        buf.writeFloat(Z);
    }

    public static void handle(ExperienceTotemBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level level = WizardsReborn.proxy.getLevel();

                    final Consumer<GenericParticle> blockTarget = p -> {
                        Vec3 blockPos = msg.pos.getCenter().add(0, 0.25f, 0);
                        Vec3 pPos = p.getPosition();

                        double dX = blockPos.x() - pPos.x();
                        double dY = blockPos.y() - pPos.y();
                        double dZ = blockPos.z() - pPos.z();

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
                            .repeat(level, msg.X, msg.Y, msg.Z, 5);

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}