package mod.maxbogomol.wizards_reborn.common.network.spell;

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

public class EarthRaySpellEffectPacket {
    private final float X, Y, Z;
    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

    public EarthRaySpellEffectPacket(float X, float Y, float Z, float colorR, float colorG, float colorB) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static EarthRaySpellEffectPacket decode(FriendlyByteBuf buf) {
        return new EarthRaySpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(X);
        buf.writeFloat(Y);
        buf.writeFloat(Z);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(EarthRaySpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getLevel();

                    for (int i = 0; i < 15; i++) {
                        if (random.nextFloat() < 0.6f) {
                            double x = ((random.nextDouble() - 0.5D) * 2);
                            double y = ((random.nextDouble() - 0.5D) * 2);
                            double z = ((random.nextDouble() - 0.5D) * 2);

                            ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                                    .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                    .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.1f, 0.3f).build())
                                    .setLifetime(40)
                                    .addVelocity(-x / 8, -y / 8, -z / 8)
                                    .flatRandomOffset(0.75f, 0.75f, 0.75f)
                                    .spawn(world, msg.X, msg.Y, msg.Z);
                        }
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}