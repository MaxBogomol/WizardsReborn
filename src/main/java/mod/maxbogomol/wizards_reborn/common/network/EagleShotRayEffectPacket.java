package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class EagleShotRayEffectPacket {
    private final float posFromX;
    private final float posFromY;
    private final float posFromZ;

    private final float posToX;
    private final float posToY;
    private final float posToZ;

    private final float motionX;
    private final float motionY;
    private final float motionZ;

    private final float r;
    private final float g;
    private final float b;

    private static final Random random = new Random();

    public EagleShotRayEffectPacket(float posFromX, float posFromY, float posFromZ, float posToX, float posToY, float posToZ, float motionX, float motionY, float motionZ, float r, float g, float b) {
        this.posFromX = posFromX;
        this.posFromY = posFromY;
        this.posFromZ = posFromZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;

        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static EagleShotRayEffectPacket decode(FriendlyByteBuf buf) {
        return new EagleShotRayEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posFromX);
        buf.writeFloat(posFromY);
        buf.writeFloat(posFromZ);

        buf.writeFloat(posToX);
        buf.writeFloat(posToY);
        buf.writeFloat(posToZ);

        buf.writeFloat(motionX);
        buf.writeFloat(motionY);
        buf.writeFloat(motionZ);

        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
    }

    public static void handle(EagleShotRayEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    Vec3 pos = new Vec3(msg.posToX, msg.posToY, msg.posToZ);
                    Vec3 norm = new Vec3(msg.motionX, msg.motionY, msg.motionZ).normalize().scale(0.025f);

                    int distance = (int) Math.sqrt(Math.pow(msg.posFromX - pos.x(), 2) + Math.pow(msg.posFromY - pos.y(), 2) + Math.pow(msg.posFromZ - pos.z(), 2)) * 8;
                    for (int i = 0; i < distance; i++) {
                        double lerpX = Mth.lerp((double) i / distance, msg.posFromX, pos.x);
                        double lerpY = Mth.lerp((double) i / distance, msg.posFromY, pos.y);
                        double lerpZ = Mth.lerp((double) i / distance, msg.posFromZ, pos.z);

                        if (random.nextFloat() < 0.15f) {
                            Particles.create(WizardsReborn.WISP_PARTICLE)
                                    .addVelocity(-norm.x + ((random.nextDouble() - 0.5D) / 50), -norm.y + ((random.nextDouble() - 0.5D) / 50), -norm.z + ((random.nextDouble() - 0.5D) / 50))
                                    .setAlpha(0.2f, 0).setScale(0.05f, 0)
                                    .setColor(msg.r, msg.g, msg.b)
                                    .setLifetime(60)
                                    .setSpin((0.3f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                    .spawn(world, lerpX, lerpY, lerpZ);
                        }

                        if (random.nextFloat() < 0.05f) {
                            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                    .addVelocity(-norm.x + ((random.nextDouble() - 0.5D) / 25), -norm.y + ((random.nextDouble() - 0.5D) / 25), -norm.z + ((random.nextDouble() - 0.5D) / 25))
                                    .setAlpha(0.125f, 0).setScale(0.1f, 0)
                                    .setColor(msg.r, msg.g, msg.b)
                                    .setLifetime(80)
                                    .setSpin((0.3f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                    .spawn(world, lerpX, lerpY, lerpZ);
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
