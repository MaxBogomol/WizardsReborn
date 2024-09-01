package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class BlinkSpellEffectPacket {
    private final float posFromX;
    private final float posFromY;
    private final float posFromZ;

    private final float posToX;
    private final float posToY;
    private final float posToZ;

    private final float colorR, colorG, colorB;

    private final boolean burst;

    private static final Random random = new Random();

    public BlinkSpellEffectPacket(float posFromX, float posFromY, float posFromZ, float posToX, float posToY, float posToZ, float colorR, float colorG, float colorB, boolean burst) {
        this.posFromX = posFromX;
        this.posFromY = posFromY;
        this.posFromZ = posFromZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;

        this.burst = burst;
    }

    public static BlinkSpellEffectPacket decode(FriendlyByteBuf buf) {
        return new BlinkSpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posFromX);
        buf.writeFloat(posFromY);
        buf.writeFloat(posFromZ);

        buf.writeFloat(posToX);
        buf.writeFloat(posToY);
        buf.writeFloat(posToZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);

        buf.writeBoolean(burst);
    }

    public static void handle(BlinkSpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getLevel();

                    Vec3 pos = new Vec3(msg.posToX, msg.posToY, msg.posToZ);
                    for (int i = 0; i < 25; i++) {
                        double lerp = random.nextDouble();
                        double lerpX = Mth.lerp(lerp, msg.posFromX, pos.x);
                        double lerpY = Mth.lerp(lerp, msg.posFromY, pos.y);
                        double lerpZ = Mth.lerp(lerp, msg.posFromZ, pos.z);

                        double dX = msg.posFromX - pos.x;
                        double dY = msg.posFromY - pos.y ;
                        double dZ = msg.posFromZ - pos.z;

                        double yaw = Math.atan2(dZ, dX);
                        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                        float speed = 0.1f;
                        double X = Math.sin(pitch) * Math.cos(yaw) * speed;
                        double Y = Math.cos(pitch) * speed;
                        double Z = Math.sin(pitch) * Math.sin(yaw) * speed;

                        ParticleBuilder.create(FluffyFurParticles.WISP)
                                .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                                .setScaleData(GenericParticleData.create(0.25f, 0).build())
                                .randomSpin(0.25f)
                                .setLifetime(80)
                                .randomVelocity(0.015f)
                                .addVelocity(X, Y, Z)
                                .randomOffset(0.15f)
                                .spawn(world, lerpX, lerpY, lerpZ);

                        if (random.nextFloat() < 0.5f) {
                            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                                    .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                    .setTransparencyData(GenericParticleData.create(0.2f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.3f, 0).build())
                                    .randomSpin(0.1f)
                                    .setLifetime(70)
                                    .randomVelocity(0.015f)
                                    .addVelocity(X, Y, Z)
                                    .randomOffset(0.15f)
                                    .spawn(world, lerpX, lerpY, lerpZ);
                        }
                        if (msg.burst) {
                            if (random.nextFloat() < 0.75) {
                                ParticleBuilder.create(FluffyFurParticles.CUBE)
                                        .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                        .setTransparencyData(GenericParticleData.create(0.2f, 0).build())
                                        .setScaleData(GenericParticleData.create(0.1f, 0).build())
                                        .setLifetime(70)
                                        .randomVelocity(0.015f)
                                        .addVelocity(X, Y, Z)
                                        .randomOffset(0.15f)
                                        .spawn(world, lerpX, lerpY, lerpZ);
                            }
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}