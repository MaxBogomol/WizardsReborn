package mod.maxbogomol.wizards_reborn.common.network.item;

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

public class InnocentWoodToolsPacket {
    private final float posX;
    private final float posY;
    private final float posZ;

    private static final Random random = new Random();

    public InnocentWoodToolsPacket(float posX, float posY, float posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static InnocentWoodToolsPacket decode(FriendlyByteBuf buf) {
        return new InnocentWoodToolsPacket(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);
    }

    public static void handle(InnocentWoodToolsPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level level = WizardsReborn.proxy.getLevel();
                    ParticleBuilder.create(FluffyFurParticles.SMOKE)
                            .setColorData(ColorParticleData.create(0.694F, 0.274F, 0.309F).build())
                            .setTransparencyData(GenericParticleData.create(0, 0.5f).build())
                            .setScaleData(GenericParticleData.create(0.5f, 0f).build())
                            .setLifetime(30)
                            .randomVelocity(0.05f)
                            .randomOffset(0.125f)
                            .repeat(level, msg.posX, msg.posY, msg.posZ, 3);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
