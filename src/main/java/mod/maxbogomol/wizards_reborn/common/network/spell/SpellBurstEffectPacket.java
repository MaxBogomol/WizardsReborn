package mod.maxbogomol.wizards_reborn.common.network.spell;

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

public class SpellBurstEffectPacket {
    private final float posX;
    private final float posY;
    private final float posZ;
    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

    public SpellBurstEffectPacket(float posX, float posY, float posZ, float colorR, float colorG, float colorB) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static SpellBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new SpellBurstEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);
        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(SpellBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level level = WizardsReborn.proxy.getLevel();
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                            .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                            .setScaleData(GenericParticleData.create(0.15f, 0).build())
                            .setLifetime(20)
                            .randomVelocity(0.025f)
                            .repeat(level, msg.posX, msg.posY, msg.posZ, 10);
                    ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.075f, 0).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                            .setLifetime(30)
                            .randomVelocity(0.025f)
                            .repeat(level, msg.posX, msg.posY, msg.posZ, 10);
                    ParticleBuilder.create(FluffyFurParticles.TRAIL)
                            .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.2f, 0).build())
                            .setLifetime(30)
                            .randomVelocity(0.025f)
                            .repeat(level, msg.posX, msg.posY, msg.posZ, 5);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}