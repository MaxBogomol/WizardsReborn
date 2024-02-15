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

public class RaySpellEffectPacket {
    private final float posFromX;
    private final float posFromY;
    private final float posFromZ;

    private final float posToX;
    private final float posToY;
    private final float posToZ;

    private final float colorR, colorG, colorB;

    private final boolean burst;

    private static final Random random = new Random();

    public RaySpellEffectPacket(float posFromX, float posFromY, float posFromZ, float posToX, float posToY, float posToZ, float colorR, float colorG, float colorB, boolean burst) {
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

    public static RaySpellEffectPacket decode(FriendlyByteBuf buf) {
        return new RaySpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readBoolean());
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

    public static void handle(RaySpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    if (random.nextFloat() < 0.2f) {
                        Particles.create(WizardsReborn.WISP_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20))
                                .setAlpha(0.2f, 0).setScale(0.15f, 0)
                                .setColor(msg.colorR, msg.colorG, msg.colorB)
                                .setLifetime(25)
                                .spawn(world, msg.posFromX, msg.posFromY, msg.posFromZ);

                        if (random.nextFloat() < 0.1f) {
                            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20))
                                    .setAlpha(0.125f, 0).setScale(0.2f, 0)
                                    .setColor(msg.colorR, msg.colorG, msg.colorB)
                                    .setLifetime(20)
                                    .spawn(world, msg.posFromX, msg.posFromY, msg.posFromZ);
                        }
                    }

                    Vec3 pos = new Vec3(msg.posToX, msg.posToY, msg.posToZ);
                    for (int i = 0; i < 10; i++) {
                        if (random.nextFloat() < 0.1f) {
                            double lerp = random.nextDouble();
                            double lerpX = Mth.lerp(lerp, msg.posFromX, pos.x);
                            double lerpY = Mth.lerp(lerp, msg.posFromY, pos.y);
                            double lerpZ = Mth.lerp(lerp, msg.posFromZ, pos.z);

                            Particles.create(WizardsReborn.WISP_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30))
                                    .setAlpha(0.2f, 0).setScale(0.15f, 0)
                                    .setColor(msg.colorR, msg.colorG, msg.colorB)
                                    .setLifetime(10)
                                    .spawn(world, lerpX, lerpY, lerpZ);

                            if (random.nextFloat() < 0.1f) {
                                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                        .addVelocity(((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30))
                                        .setAlpha(0.125f, 0).setScale(0.2f, 0)
                                        .setColor(msg.colorR, msg.colorG, msg.colorB)
                                        .setLifetime(5)
                                        .spawn(world, lerpX, lerpY, lerpZ);
                            }
                        }
                    }

                    if (msg.burst) {
                        for (int i = 0; i < 5; i++) {
                            if (random.nextFloat() < 0.1) {
                                Particles.create(WizardsReborn.WISP_PARTICLE)
                                        .addVelocity(((random.nextDouble() - 0.5D) / 15), ((random.nextDouble() - 0.5D) / 15), ((random.nextDouble() - 0.5D) / 15))
                                        .setAlpha(0.125f, 0).setScale(0.2f, 0)
                                        .setColor(msg.colorR, msg.colorG, msg.colorB)
                                        .setLifetime(40)
                                        .spawn(world, msg.posToX, msg.posToY, msg.posToZ);
                                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                        .addVelocity(((random.nextDouble() - 0.5D) / 15), ((random.nextDouble() - 0.5D) / 15), ((random.nextDouble() - 0.5D) / 15))
                                        .setAlpha(0.25f, 0).setScale(0.075f, 0)
                                        .setColor(msg.colorR, msg.colorG, msg.colorB)
                                        .setLifetime(50)
                                        .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                        .spawn(world, msg.posToX, msg.posToY, msg.posToZ);
                            }
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}