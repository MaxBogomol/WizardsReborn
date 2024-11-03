package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.ClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import mod.maxbogomol.wizards_reborn.client.event.WissenLimitHandler;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class WissenSendEffectPacket extends ClientPacket {
    private final double posFromX;
    private final double posFromY;
    private final double posFromZ;

    private final double posToX;
    private final double posToY;
    private final double posToZ;

    private final float colorR, colorG, colorB;

    private final int particlePerBlock;

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

        this.colorR = WizardsRebornConfig.wissenColorR();
        this.colorG = WizardsRebornConfig.wissenColorG();
        this.colorB = WizardsRebornConfig.wissenColorB();
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

        this.colorR = WizardsRebornConfig.wissenColorR();
        this.colorG = WizardsRebornConfig.wissenColorG();
        this.colorB = WizardsRebornConfig.wissenColorB();
        this.particlePerBlock = particlePerBlock;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();

        double dX = posFromX - posToX;
        double dY = posFromY - posToY;
        double dZ = posFromZ - posToZ;

        double x = (dX / (particlePerBlock));
        double y = (dY / (particlePerBlock));
        double z = (dZ / (particlePerBlock));

        WissenLimitHandler.wissenCount++;

        if (WissenLimitHandler.wissenCount > WizardsRebornClientConfig.WISSEN_RAYS_LIMIT.get()) {
            WissenLimitHandler.wissenCount = WizardsRebornClientConfig.WISSEN_RAYS_LIMIT.get();
        }

        int wissenCount = WissenLimitHandler.wissenCountOld;

        ParticleBuilder wispBuilder = ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(colorR, colorG, colorB).build())
                .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                .setScaleData(GenericParticleData.create(0.15f, 0).build())
                .setLifetime(20)
                .randomVelocity(0.01f);
        ParticleBuilder sparkleBuilder = ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(colorR, colorG, colorB).build())
                .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                .setScaleData(GenericParticleData.create(0.1f, 0.2f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setLifetime(30)
                .randomVelocity(0.02f);
        ParticleBuilder squareBuilder = ParticleBuilder.create(FluffyFurParticles.SQUARE)
                .setColorData(ColorParticleData.create(colorR, colorG, colorB).build())
                .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setLifetime(30)
                .randomVelocity(0.02f);

        for (int i = 0; i < particlePerBlock; i++) {
            if (random.nextFloat() < (0.45f * (1f - ((float) wissenCount / WizardsRebornClientConfig.WISSEN_RAYS_LIMIT.get()))) + 0.05f) {
                wispBuilder.spawn(level, posFromX - (x * i), posFromY - (y * i), posFromZ - (z * i));
                if (random.nextFloat() < 0.1) {
                    sparkleBuilder.spawn(level, posFromX - (x * i), posFromY - (y * i), posFromZ - (z * i));
                }
                if (random.nextFloat() < 0.1) {
                    squareBuilder.spawn(level, posFromX - (x * i), posFromY - (y * i), posFromZ - (z * i));
                }
            }
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, WissenSendEffectPacket.class, WissenSendEffectPacket::encode, WissenSendEffectPacket::decode, WissenSendEffectPacket::handle);
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

    public static WissenSendEffectPacket decode(FriendlyByteBuf buf) {
        return new WissenSendEffectPacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt());
    }
}
