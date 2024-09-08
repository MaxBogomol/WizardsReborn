package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class SmokeEffectPacket {
    private final float posX;
    private final float posY;
    private final float posZ;

    private final float velX;
    private final float velY;
    private final float velZ;

    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

    public SmokeEffectPacket(float posX, float posY, float posZ, float velX, float velY, float velZ, float colorR, float colorG, float colorB) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static SmokeEffectPacket decode(FriendlyByteBuf buf) {
        return new SmokeEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);

        buf.writeFloat(velX);
        buf.writeFloat(velY);
        buf.writeFloat(velZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(SmokeEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level level = WizardsReborn.proxy.getLevel();

                    for (int i = 0; i < 40; i++) {
                        ParticleBuilder.create(FluffyFurParticles.SMOKE)
                                .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                .setTransparencyData(GenericParticleData.create(0.05f, 0).build())
                                .setScaleData(GenericParticleData.create(0.1f, 2).build())
                                .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                                .setLifetime(500, 100)
                                .randomVelocity(0.5f / (20 - (5 * random.nextDouble())), 0.5f / (20 - (5 * random.nextDouble())), 0.5f / (20 - (5 * random.nextDouble())))
                                .addVelocity(msg.velX, msg.velY, msg.velZ)
                                .randomOffset(0.05f)
                                .spawn(level, msg.posX, msg.posY, msg.posZ);
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
