package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
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
    private final float posFromX;
    private final float posFromY;
    private final float posFromZ;

    private final float posToX;
    private final float posToY;
    private final float posToZ;

    private final float colorR, colorG, colorB;

    private final int particlePerBlock;

    private static final Random random = new Random();

    public WissenSendEffectPacket(float posFromX, float posFromY, float posFromZ, float posToX, float posToY, float posToZ, float colorR, float colorG, float colorB) {
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

    public WissenSendEffectPacket(float posFromX, float posFromY, float posFromZ, float posToX, float posToY, float posToZ) {
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

    public WissenSendEffectPacket(float posFromX, float posFromY, float posFromZ, float posToX, float posToY, float posToZ, float colorR, float colorG, float colorB, int particlePerBlock) {
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

    public WissenSendEffectPacket(float posFromX, float posFromY, float posFromZ, float posToX, float posToY, float posToZ, int particlePerBlock) {
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
        return new WissenSendEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt());
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

        buf.writeInt(particlePerBlock);
    }

    @SuppressWarnings("Convert2Lambda")
    public static void handle(WissenSendEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getLevel();

                    int particlePerBlock = msg.particlePerBlock;

                    double dX = msg.posFromX - msg.posToX;
                    double dY = msg.posFromY - msg.posToY;
                    double dZ = msg.posFromZ - msg.posToZ;

                    float x = (float) (dX / (particlePerBlock));
                    float y = (float) (dY / (particlePerBlock));
                    float z = (float) (dZ / (particlePerBlock));

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
                                    .spawn(world, msg.posFromX - (x * i), msg.posFromY - (y * i), msg.posFromZ - (z * i));
                            if (random.nextFloat() < 0.1) {
                                ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                                        .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                        .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                                        .setScaleData(GenericParticleData.create(0.1f, 0.2f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                                        .randomSpin(0.5f)
                                        .setLifetime(30)
                                        .randomVelocity(0.02f)
                                        .spawn(world, msg.posFromX - (x * i), msg.posFromY - (y * i), msg.posFromZ - (z * i));
                            }
                            if (random.nextFloat() < 0.1) {
                                ParticleBuilder.create(FluffyFurParticles.SQUARE)
                                        .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                        .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                                        .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                                        .randomSpin(0.5f)
                                        .setLifetime(30)
                                        .randomVelocity(0.02f)
                                        .spawn(world, msg.posFromX - (x * i), msg.posFromY - (y * i), msg.posFromZ - (z * i));
                            }
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
