package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SparkParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class MagicBladeEffectPacket {
    private final float posX;
    private final float posY;
    private final float posZ;

    private static final Random random = new Random();

    public MagicBladeEffectPacket(float posX, float posY, float posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static MagicBladeEffectPacket decode(FriendlyByteBuf buf) {
        return new MagicBladeEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);
    }

    public static void handle(MagicBladeEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level level = WizardsReborn.proxy.getLevel();
                    ParticleBuilder builder = ParticleBuilder.create(FluffyFurParticles.TINY_WISP);
                    builder.setBehavior(SparkParticleBehavior.create()
                                    .enableSecondColor()
                                    .setColorData(ColorParticleData.create().setRandomColor().build())
                                    .setTransparencyData(GenericParticleData.create(0.2f, 0.2f, 0).setEasing(Easing.QUARTIC_OUT).build())
                                    .build())
                            .setColorData(ColorParticleData.create(0.431f, 0.305f, 0.662f).build())
                            .setTransparencyData(GenericParticleData.create(0.6f, 0.6f, 0).setEasing(Easing.QUARTIC_OUT).build())
                            .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.ELASTIC_OUT).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                            .setLifetime(20)
                            .randomVelocity(0.5f)
                            .addVelocity(0, 0.1f, 0)
                            .randomOffset(0.05f)
                            .setFriction(0.9f)
                            .enablePhysics()
                            .setGravity(1f)
                            .repeat(level, msg.posX, msg.posY, msg.posZ, 15, 0.8f);
                    builder.setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                            .repeat(level, msg.posX, msg.posY, msg.posZ, 5, 0.5f);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
