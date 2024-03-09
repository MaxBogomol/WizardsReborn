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

public class ChargeSpellProjectileRayEffectPacket {
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

    private final float charge;

    private static final Random random = new Random();

    public ChargeSpellProjectileRayEffectPacket(float posFromX, float posFromY, float posFromZ, float posToX, float posToY, float posToZ, float motionX, float motionY, float motionZ, float r, float g, float b, float charge) {
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

        this.charge = charge;
    }

    public static ChargeSpellProjectileRayEffectPacket decode(FriendlyByteBuf buf) {
        return new ChargeSpellProjectileRayEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
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

        buf.writeFloat(charge);
    }

    public static void handle(ChargeSpellProjectileRayEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    Vec3 pos = new Vec3(msg.posToX, msg.posToY, msg.posToZ);
                    Vec3 norm = new Vec3(msg.motionX, msg.motionY, msg.motionZ).normalize().scale(0.025f);

                    for (int i = 0; i < 15; i++) {
                        double lerpX = Mth.lerp(i / 15.0f, msg.posFromX, pos.x);
                        double lerpY = Mth.lerp(i / 15.0f, msg.posFromY, pos.y);
                        double lerpZ = Mth.lerp(i / 15.0f, msg.posFromZ, pos.z);

                        if (random.nextFloat() < 0.25f) {
                            Particles.create(WizardsReborn.WISP_PARTICLE)
                                    .addVelocity(-norm.x + ((random.nextDouble() - 0.5D) / 250), -norm.y + ((random.nextDouble() - 0.5D) / 250), -norm.z + ((random.nextDouble() - 0.5D) / 250))
                                    .setAlpha(0.2f * msg.charge, 0).setScale(0.15f * msg.charge, 0)
                                    .setColor(msg.r, msg.g, msg.b)
                                    .setLifetime(20)
                                    .setSpin((0.3f * (float) ((random.nextDouble() - 0.5D) * 2)) * msg.charge)
                                    .spawn(world, lerpX, lerpY, lerpZ);
                        }

                        if (random.nextFloat() < 0.1f) {
                            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                    .addVelocity(-norm.x + ((random.nextDouble() - 0.5D) / 100), -norm.y + ((random.nextDouble() - 0.5D) / 100), -norm.z + ((random.nextDouble() - 0.5D) / 100))
                                    .setAlpha(0.125f * msg.charge, 0).setScale(0.2f * msg.charge, 0)
                                    .setColor(msg.r, msg.g, msg.b)
                                    .setLifetime(30)
                                    .setSpin((0.3f * (float) ((random.nextDouble() - 0.5D) * 2)) * msg.charge)
                                    .spawn(world, lerpX, lerpY, lerpZ);
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
