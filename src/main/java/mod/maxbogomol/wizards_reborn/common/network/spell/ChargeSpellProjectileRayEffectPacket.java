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
                    Level world = WizardsReborn.proxy.getLevel();

                    Vec3 pos = new Vec3(msg.posToX, msg.posToY, msg.posToZ);
                    Vec3 norm = new Vec3(msg.motionX, msg.motionY, msg.motionZ).normalize().scale(0.025f);

                    for (int i = 0; i < 15; i++) {
                        double lerpX = Mth.lerp(i / 15.0f, msg.posFromX, pos.x);
                        double lerpY = Mth.lerp(i / 15.0f, msg.posFromY, pos.y);
                        double lerpZ = Mth.lerp(i / 15.0f, msg.posFromZ, pos.z);

                        if (random.nextFloat() < 0.25f) {
                            ParticleBuilder.create(FluffyFurParticles.WISP)
                                    .setColorData(ColorParticleData.create(msg.r, msg.g, msg.b).build())
                                    .setTransparencyData(GenericParticleData.create(0.2f * msg.charge, 0).build())
                                    .setScaleData(GenericParticleData.create(0.15f * msg.charge, 0).build())
                                    .randomSpin(0.3f)
                                    .setLifetime(20)
                                    .randomVelocity(0.002f)
                                    .addVelocity(-norm.x, -norm.y, -norm.z)
                                    .spawn(world, lerpX, lerpY, lerpZ);
                        }

                        if (random.nextFloat() < 0.1f) {
                            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                                    .setColorData(ColorParticleData.create(msg.r, msg.g, msg.b).build())
                                    .setTransparencyData(GenericParticleData.create(0.125f * msg.charge, 0).build())
                                    .setScaleData(GenericParticleData.create(0.2f * msg.charge, 0).build())
                                    .randomSpin(0.3f)
                                    .setLifetime(30)
                                    .randomVelocity(0.005f)
                                    .addVelocity(-norm.x, -norm.y, -norm.z)
                                    .spawn(world, lerpX, lerpY, lerpZ);
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
