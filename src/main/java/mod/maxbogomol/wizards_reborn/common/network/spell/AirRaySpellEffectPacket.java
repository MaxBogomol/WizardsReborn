package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class AirRaySpellEffectPacket {
    private static float X, Y, Z;
    private static float velX, velY, velZ;
    private static float colorR, colorG, colorB;

    private static Random random = new Random();

    public AirRaySpellEffectPacket(float X, float Y, float Z, float velX, float velY, float velZ, float colorR, float colorG, float colorB) {
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

    public static AirRaySpellEffectPacket decode(FriendlyByteBuf buf) {
        return new AirRaySpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
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

    public static void handle(AirRaySpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 25; i++) {
                        Particles.create(WizardsReborn.WISP_PARTICLE)
                                .addVelocity(velX + ((random.nextDouble() - 0.5D) / 30), velY + ((random.nextDouble() - 0.5D) / 30), velZ + ((random.nextDouble() - 0.5D) / 30))
                                .setAlpha(0.125f, 0).setScale(0.2f, 0)
                                .setColor(colorR, colorG, colorB)
                                .setLifetime(20)
                                .spawn(world, X + ((random.nextDouble() - 0.5D) / 3), Y + ((random.nextDouble() - 0.5D) / 3), Z + ((random.nextDouble() - 0.5D) / 3));
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(velX + ((random.nextDouble() - 0.5D) / 30), velY + ((random.nextDouble() - 0.5D) / 30), velZ + ((random.nextDouble() - 0.5D) / 30))
                                .setAlpha(0.125f, 0).setScale(0.2f, 0)
                                .setColor(colorR, colorG, colorB)
                                .setLifetime(20)
                                .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, X + ((random.nextDouble() - 0.5D) / 3), Y + ((random.nextDouble() - 0.5D) / 3), Z + ((random.nextDouble() - 0.5D) / 3));
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}