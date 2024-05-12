package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
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
                    Level world = WizardsReborn.proxy.getWorld();

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

                        Particles.create(WizardsReborn.WISP_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 30) + X, ((random.nextDouble() - 0.5D) / 30) + Y, ((random.nextDouble() - 0.5D) / 30) + Z)
                                .setAlpha(0.4f, 0).setScale(0.25f, 0)
                                .setColor(msg.colorR, msg.colorG, msg.colorB)
                                .setLifetime(80)
                                .setSpin((0.25f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, lerpX + ((random.nextDouble() - 0.5D) / 3), lerpY + ((random.nextDouble() - 0.5D) / 3), lerpZ + ((random.nextDouble() - 0.5D) / 3));

                        if (random.nextFloat() < 0.5f) {
                            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 20) + X, ((random.nextDouble() - 0.5D) / 20) + Y, ((random.nextDouble() - 0.5D) / 20) + Z)
                                    .setAlpha(0.2f, 0).setScale(0.3f, 0)
                                    .setColor(msg.colorR, msg.colorG, msg.colorB)
                                    .setLifetime(70)
                                    .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                    .spawn(world, lerpX + ((random.nextDouble() - 0.5D) / 3), lerpY + ((random.nextDouble() - 0.5D) / 3), lerpZ + ((random.nextDouble() - 0.5D) / 3));
                        }
                        if (msg.burst) {
                            if (random.nextFloat() < 0.75) {
                                Particles.create(WizardsReborn.CUBE_PARTICLE)
                                        .addVelocity(((random.nextDouble() - 0.5D) / 30) + X, ((random.nextDouble() - 0.5D) / 30) + Y, ((random.nextDouble() - 0.5D) / 30) + Z)
                                        .setAlpha(0.2f, 0).setScale(0.1f, 0)
                                        .setColor(msg.colorR, msg.colorG, msg.colorB)
                                        .setLifetime(70)
                                        .spawn(world, lerpX + ((random.nextDouble() - 0.5D) / 2), lerpY + ((random.nextDouble() - 0.5D) / 2), lerpZ + ((random.nextDouble() - 0.5D) / 2));
                            }
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}