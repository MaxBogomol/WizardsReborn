package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
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
                    Level world = WizardsReborn.proxy.getLevel();
                    ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(0.431F, 0.305F, 0.662F).build())
                            .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 0.5f).build())
                            .randomSpin(0.1f)
                            .setLifetime(30)
                            .randomVelocity(0.05f)
                            .randomOffset(0.05f)
                            .repeat(world, msg.posX, msg.posY, msg.posZ, 15);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
