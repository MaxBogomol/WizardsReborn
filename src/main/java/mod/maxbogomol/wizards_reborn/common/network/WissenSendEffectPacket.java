package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.client.event.WissenLimitHandler;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class WissenSendEffectPacket {
    private final double posFromX;
    private final double posFromY;
    private final double posFromZ;

    private final double posToX;
    private final double posToY;
    private final double posToZ;

    private final float colorR, colorG, colorB;

    private final int particlePerBlock;

    private static final Random random = new Random();

    public WissenSendEffectPacket(double posFromX, double posFromY, double posFromZ, double posToX, double posToY, double posToZ, float colorR, float colorG, float colorB) {
        this.posFromX = posFromX;
        this.posFromY = posFromY;
        this.posFromZ = posFromZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.particlePerBlock = 4;
    }

    public WissenSendEffectPacket(double posFromX, double posFromY, double posFromZ, double posToX, double posToY, double posToZ) {
        this.posFromX = posFromX;
        this.posFromY = posFromY;
        this.posFromZ = posFromZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.colorR = Config.wissenColorR();
        this.colorG = Config.wissenColorG();
        this.colorB = Config.wissenColorB();
        this.particlePerBlock = 4;
    }

    public WissenSendEffectPacket(double posFromX, double posFromY, double posFromZ, double posToX, double posToY, double posToZ, float colorR, float colorG, float colorB, int particlePerBlock) {
        this.posFromX = posFromX;
        this.posFromY = posFromY;
        this.posFromZ = posFromZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.particlePerBlock = particlePerBlock;
    }

    public WissenSendEffectPacket(double posFromX, double posFromY, double posFromZ, double posToX, double posToY, double posToZ, int particlePerBlock) {
        this.posFromX = posFromX;
        this.posFromY = posFromY;
        this.posFromZ = posFromZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.colorR = Config.wissenColorR();
        this.colorG = Config.wissenColorG();
        this.colorB = Config.wissenColorB();
        this.particlePerBlock = particlePerBlock;
    }

    public static WissenSendEffectPacket decode(FriendlyByteBuf buf) {
        return new WissenSendEffectPacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(posFromX);
        buf.writeDouble(posFromY);
        buf.writeDouble(posFromZ);

        buf.writeDouble(posToX);
        buf.writeDouble(posToY);
        buf.writeDouble(posToZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);

        buf.writeInt(particlePerBlock);
    }

    @SuppressWarnings("Convert2Lambda")
    public static void handle(WissenSendEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level level = WizardsReborn.proxy.getLevel();

                    int particlePerBlock = msg.particlePerBlock;

                    double dX = msg.posFromX - msg.posToX;
                    double dY = msg.posFromY - msg.posToY;
                    double dZ = msg.posFromZ - msg.posToZ;

                    double x = (dX / (particlePerBlock));
                    double y = (dY / (particlePerBlock));
                    double z = (dZ / (particlePerBlock));

                    WissenLimitHandler.wissenCount++;

                    if (WissenLimitHandler.wissenCount > ClientConfig.WISSEN_RAYS_LIMIT.get()) {
                        WissenLimitHandler.wissenCount = ClientConfig.WISSEN_RAYS_LIMIT.get();
                    }

                    int wissenCount = WissenLimitHandler.wissenCountOld;

                    for (int i = 0; i < particlePerBlock; i++) {
                        if (random.nextFloat() < (0.45f * (1f - ((float) wissenCount / ClientConfig.WISSEN_RAYS_LIMIT.get()))) + 0.05f) {
                            ParticleBuilder.create(FluffyFurParticles.WISP)
                                    .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                    .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.15f, 0).build())
                                    .setLifetime(20)
                                    .randomVelocity(0.01f)
                                    .spawn(level, msg.posFromX - (x * i), msg.posFromY - (y * i), msg.posFromZ - (z * i));
                            if (random.nextFloat() < 0.1) {
                                ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                                        .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                        .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                                        .setScaleData(GenericParticleData.create(0.1f, 0.2f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                                        .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                                        .setLifetime(30)
                                        .randomVelocity(0.02f)
                                        .spawn(level, msg.posFromX - (x * i), msg.posFromY - (y * i), msg.posFromZ - (z * i));
                            }
                            if (random.nextFloat() < 0.1) {
                                ParticleBuilder.create(FluffyFurParticles.SQUARE)
                                        .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                        .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                                        .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                                        .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                                        .setLifetime(30)
                                        .randomVelocity(0.02f)
                                        .spawn(level, msg.posFromX - (x * i), msg.posFromY - (y * i), msg.posFromZ - (z * i));
                            }
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
