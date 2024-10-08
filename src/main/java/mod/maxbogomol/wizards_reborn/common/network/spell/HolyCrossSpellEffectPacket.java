package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornParticles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class HolyCrossSpellEffectPacket {
    private final float X, Y, Z;
    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

    public HolyCrossSpellEffectPacket(float X, float Y, float Z, float colorR, float colorG, float colorB) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static HolyCrossSpellEffectPacket decode(FriendlyByteBuf buf) {
        return new HolyCrossSpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(X);
        buf.writeFloat(Y);
        buf.writeFloat(Z);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(HolyCrossSpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level level = WizardsReborn.proxy.getLevel();

                    for (int i = 0; i < 4; i++) {
                        if (random.nextFloat() < 0.6f) {
                            double x = (random.nextDouble() - 0.5D);
                            double y = (random.nextDouble() - 0.5D);
                            double z = (random.nextDouble() - 0.5D);

                            ParticleBuilder.create(WizardsRebornParticles.KARMA)
                                    .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                    .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.05f, 0.2f).build())
                                    .setLifetime(35)
                                    .addVelocity(-x / 16, -y / 16, -z / 16)
                                    .spawn(level, msg.X + x, msg.Y + y, msg.Z + z);
                        }
                    }

                    ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                            .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.3f).build())
                            .setLifetime(60)
                            .randomVelocity(0.0125f)
                            .repeat(level, msg.X, msg.Y, msg.Z, 10, 0.6f);

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}