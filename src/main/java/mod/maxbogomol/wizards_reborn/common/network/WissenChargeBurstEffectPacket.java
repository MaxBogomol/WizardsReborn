package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class WissenChargeBurstEffectPacket {
    private final float posX;
    private final float posY;
    private final float posZ;

    private final float r;
    private final float g;
    private final float b;

    private final float charge;

    private static final Random random = new Random();

    public WissenChargeBurstEffectPacket(float posX, float posY, float posZ, float r, float g, float b, float charge) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.r = r;
        this.g = g;
        this.b = b;

        this.charge = charge;
    }

    public static WissenChargeBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new WissenChargeBurstEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);

        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);

        buf.writeFloat(charge);
    }

    public static void handle(WissenChargeBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                            .setColorData(ColorParticleData.create(msg.r, msg.g, msg.b).build())
                            .setTransparencyData(GenericParticleData.create(0.2f * msg.charge, 0).build())
                            .setScaleData(GenericParticleData.create(0.15f * msg.charge, 0).build())
                            .randomSpin(0.3f)
                            .setLifetime(40)
                            .randomVelocity(0.035f)
                            .randomOffset(0.025f)
                            .repeat(world, msg.posX, msg.posY, msg.posZ, 30);
                    ParticleBuilder.create(FluffyFur.SPARKLE_PARTICLE)
                            .setColorData(ColorParticleData.create(msg.r, msg.g, msg.b).build())
                            .setTransparencyData(GenericParticleData.create(0.3f * msg.charge, 0).build())
                            .setScaleData(GenericParticleData.create(0.15f * msg.charge, 0.3f * msg.charge, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .randomSpin(0.3f)
                            .setLifetime(40)
                            .randomVelocity(0.035f)
                            .randomOffset(0.025f)
                            .repeat(world, msg.posX, msg.posY, msg.posZ, 15, 0.6f);
                    ParticleBuilder.create(FluffyFur.SQUARE_PARTICLE)
                            .setColorData(ColorParticleData.create(msg.r, msg.g, msg.b).build())
                            .setTransparencyData(GenericParticleData.create(0.3f * msg.charge, 0).build())
                            .setScaleData(GenericParticleData.create(0.05f * msg.charge, 0.1f * msg.charge, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .randomSpin(0.3f)
                            .setLifetime(40)
                            .randomVelocity(0.035f)
                            .randomOffset(0.025f)
                            .repeat(world, msg.posX, msg.posY, msg.posZ, 15, 0.6f);
                    ParticleBuilder.create(FluffyFur.SMOKE_PARTICLE)
                            .setColorData(ColorParticleData.create(msg.r, msg.g, msg.b).build())
                            .setTransparencyData(GenericParticleData.create(0.3f * msg.charge, 0).build())
                            .setScaleData(GenericParticleData.create(0.5f * msg.charge, 0).build())
                            .randomSpin(0.1f)
                            .setLifetime(40)
                            .randomVelocity(0.035f)
                            .randomOffset(0.025f)
                            .repeat(world, msg.posX, msg.posY, msg.posZ, 30, 0.3f);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
