package mod.maxbogomol.wizards_reborn.common.network.spell;

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

public class StrikeSpellEffectPacket {
    private final float X, Y, Z;
    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

    public StrikeSpellEffectPacket(float X, float Y, float Z, float colorR, float colorG, float colorB) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static StrikeSpellEffectPacket decode(FriendlyByteBuf buf) {
        return new StrikeSpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(X);
        buf.writeFloat(Y);
        buf.writeFloat(Z);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(StrikeSpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getLevel();

                    for (int i = 0; i < 35; i++) {
                        if (random.nextFloat() < 0.4f) {
                            ParticleBuilder.create(FluffyFurParticles.WISP)
                                    .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                    .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.3f, 0f).build())
                                    .randomSpin(0.4f)
                                    .setLifetime(60)
                                    .randomVelocity(0.25f, 0, 0.25f)
                                    .addVelocity(0, random.nextDouble() / 6, 0)
                                    .spawn(world, msg.X, msg.Y, msg.Z);
                        }
                        if (random.nextFloat() < 0.4f) {
                            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                                    .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                    .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.3f, 0f).build())
                                    .randomSpin(2f)
                                    .setLifetime(80)
                                    .randomVelocity(0.25f, 0, 0.25f)
                                    .addVelocity(0, random.nextDouble() / 6, 0)
                                    .spawn(world, msg.X, msg.Y, msg.Z);
                        }
                        if (random.nextFloat() < 0.2f) {
                            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                                    .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                    .setTransparencyData(GenericParticleData.create(0.6f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.4f, 0f).build())
                                    .randomSpin(0.4f)
                                    .setLifetime(80)
                                    .randomVelocity(0.125f, 0.1f, 0.125f)
                                    .addVelocity(0, 0.5f, 0)
                                    .spawn(world, msg.X, msg.Y, msg.Z);
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}