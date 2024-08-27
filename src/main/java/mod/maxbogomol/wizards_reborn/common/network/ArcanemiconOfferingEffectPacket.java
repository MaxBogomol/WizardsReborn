package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class ArcanemiconOfferingEffectPacket {
    private final float posX;
    private final float posY;
    private final float posZ;

    private static final Random random = new Random();

    public ArcanemiconOfferingEffectPacket(float posX, float posY, float posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static ArcanemiconOfferingEffectPacket decode(FriendlyByteBuf buf) {
        return new ArcanemiconOfferingEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);
    }

    public static void handle(ArcanemiconOfferingEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getLevel();

                    for (int i = 0; i < 36; i++) {
                        float distance = 0.5f + (0.25f * random.nextFloat());
                        double yaw = Math.toRadians(10 * i);
                        double pitch = 90;

                        double X = Math.sin(pitch) * Math.cos(yaw) * distance;
                        double Y = Math.cos(pitch);
                        double Z = Math.sin(pitch) * Math.sin(yaw) * distance;

                        ParticleBuilder.create(FluffyFur.SMOKE_PARTICLE)
                                .setColorData(ColorParticleData.create(251 / 255f, 179 / 255f, 176 / 255f).build())
                                .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                                .setScaleData(GenericParticleData.create(0.4f, 0f).build())
                                .randomSpin(0.1f)
                                .setLifetime(100)
                                .addVelocity(X / 10, 0, Z / 10)
                                .randomOffset(0.05f)
                                .spawn(world, msg.posX, msg.posY, msg.posZ);
                        ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                                .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                                .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                                .setScaleData(GenericParticleData.create(0.4f, 0f).build())
                                .randomSpin(0.1f)
                                .setLifetime(100)
                                .addVelocity(X / 12, 0, Z / 12)
                                .randomOffset(0.05f)
                                .spawn(world, msg.posX, msg.posY, msg.posZ);
                    }

                    for (int i = 0; i < 30; i++) {
                        ParticleBuilder.create(FluffyFur.SPARKLE_PARTICLE)
                                .setColorData(ColorParticleData.create(123 / 255f, 73 / 255f, 109 / 255f).build())
                                .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                                .setScaleData(GenericParticleData.create(0.4f, 0).build())
                                .randomSpin(0.1f)
                                .setLifetime(100)
                                .randomVelocity(0.02f, 0, 0.02f)
                                .randomOffset(0.1f)
                                .addVelocity(0, random.nextDouble() / 10, 0)
                                .spawn(world, msg.posX, msg.posY, msg.posZ);
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
