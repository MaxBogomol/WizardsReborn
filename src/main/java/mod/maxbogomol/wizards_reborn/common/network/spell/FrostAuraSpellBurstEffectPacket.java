package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class FrostAuraSpellBurstEffectPacket {
    private final float X, Y, Z;
    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

    public FrostAuraSpellBurstEffectPacket(float X, float Y, float Z, float colorR, float colorG, float colorB) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static FrostAuraSpellBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new FrostAuraSpellBurstEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(X);
        buf.writeFloat(Y);
        buf.writeFloat(Z);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(FrostAuraSpellBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getLevel();
                    ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                            .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                            .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                            .setScaleData(GenericParticleData.create(0.2f, 0).build())
                            .randomSpin(1f)
                            .setLifetime(40)
                            .setGravity(1f)
                            .randomVelocity(0.0625)
                            .addVelocity(0, 0.25f, 0)
                            .repeat(world, msg.X, msg.Y, msg.Z, 15, 0.3f);

                    for (int i = 0; i < 10; i++) {
                        if (random.nextFloat() < 0.5f) {
                            world.addParticle(ParticleTypes.SNOWFLAKE,
                                    msg.X + ((random.nextDouble() - 0.5D) / 2), msg.Y + ((random.nextDouble() - 0.5D) / 2), msg.Z + ((random.nextDouble() - 0.5D) / 2),
                                    ((random.nextDouble() - 0.5D) / 10), ((random.nextDouble() - 0.5D) / 10) + 0.05f, ((random.nextDouble() - 0.5D) / 10));
                        }
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}