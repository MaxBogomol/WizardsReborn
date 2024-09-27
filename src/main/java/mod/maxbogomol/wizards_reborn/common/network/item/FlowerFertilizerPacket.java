package mod.maxbogomol.wizards_reborn.common.network.item;

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

public class FlowerFertilizerPacket {
    private final float posX;
    private final float posY;
    private final float posZ;

    private static final Random random = new Random();

    public FlowerFertilizerPacket(float posX, float posY, float posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static FlowerFertilizerPacket decode(FriendlyByteBuf buf) {
        return new FlowerFertilizerPacket(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);
    }

    public static void handle(FlowerFertilizerPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level level = WizardsReborn.proxy.getLevel();
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(0.545F, 0.875F, 0.522F).build())
                            .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 0.5f).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.01f).build())
                            .setLifetime(20)
                            .setGravity(1f)
                            .randomVelocity(0.05f)
                            .addVelocity(0, 0.2F, 0)
                            .randomOffset(0.125f)
                            .repeat(level, msg.posX, msg.posY, msg.posZ, 10);
                    ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(0.545F, 0.875F, 0.522F).build())
                            .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 0.5f).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.01f).build())
                            .setLifetime(10)
                            .setGravity(1f)
                            .randomVelocity(0.05f)
                            .addVelocity(0, 0.2F, 0)
                            .randomOffset(0.125f)
                            .repeat(level, msg.posX, msg.posY, msg.posZ, 10);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}