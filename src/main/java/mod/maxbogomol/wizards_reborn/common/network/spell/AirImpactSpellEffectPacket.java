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

public class AirImpactSpellEffectPacket {
    private final float X, Y, Z;
    private final float velX, velY, velZ;
    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

    public AirImpactSpellEffectPacket(float X, float Y, float Z, float velX, float velY, float velZ, float colorR, float colorG, float colorB) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;

        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static AirImpactSpellEffectPacket decode(FriendlyByteBuf buf) {
        return new AirImpactSpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(X);
        buf.writeFloat(Y);
        buf.writeFloat(Z);

        buf.writeFloat(velX);
        buf.writeFloat(velY);
        buf.writeFloat(velZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(AirImpactSpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getLevel();
                    ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                            .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                            .setTransparencyData(GenericParticleData.create(0.05f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 1.0f).build())
                            .randomSpin(3f)
                            .setLifetime(15)
                            .randomVelocity(0.0125f)
                            .addVelocity(msg.velX, msg.velY, msg.velZ)
                            .repeat(world, msg.X, msg.Y, msg.Z, 20);

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}