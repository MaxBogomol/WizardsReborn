package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class SplitArrowBurstEffectPacket {
    private final float posX;
    private final float posY;
    private final float posZ;

    private final float r;
    private final float g;
    private final float b;

    private static final Random random = new Random();

    public SplitArrowBurstEffectPacket(float posX, float posY, float posZ, float r, float g, float b) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static SplitArrowBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new SplitArrowBurstEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);

        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
    }

    public static void handle(SplitArrowBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getLevel();
                    ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                            .setColorData(ColorParticleData.create(msg.r, msg.g, msg.b).build())
                            .setTransparencyData(GenericParticleData.create(0.2f, 0).build())
                            .setScaleData(GenericParticleData.create(0.05f, 0).build())
                            .randomSpin(0.3f)
                            .setLifetime(40)
                            .randomVelocity(0.035f)
                            .randomOffset(0.025f)
                            .repeat(world, msg.posX, msg.posY, msg.posZ, 5);
                    ParticleBuilder.create(FluffyFur.SPARKLE_PARTICLE)
                            .setColorData(ColorParticleData.create(msg.r, msg.g, msg.b).build())
                            .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 0).build())
                            .randomSpin(0.5f)
                            .setLifetime(40)
                            .randomVelocity(0.035f)
                            .randomOffset(0.025f)
                            .repeat(world, msg.posX, msg.posY, msg.posZ, 5, 0.6f);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
