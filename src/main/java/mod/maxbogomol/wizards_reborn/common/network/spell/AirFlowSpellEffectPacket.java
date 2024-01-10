package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class AirFlowSpellEffectPacket {
    private static float X, Y, Z;
    private static float velX, velY, velZ;
    private static float colorR, colorG, colorB;

    private static Random random = new Random();

    public AirFlowSpellEffectPacket(float X, float Y, float Z, float velX, float velY, float velZ, float colorR, float colorG, float colorB) {
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

    public static AirFlowSpellEffectPacket decode(FriendlyByteBuf buf) {
        return new AirFlowSpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
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

    public static void handle(AirFlowSpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 20; i++) {
                        Particles.create(WizardsReborn.STEAM_PARTICLE)
                                .addVelocity(velX + ((random.nextDouble() - 0.5D) / 20), velY + ((random.nextDouble() - 0.5D) / 20), velZ + ((random.nextDouble() - 0.5D) / 20))
                                .setAlpha(0.3f, 0).setScale(0.1f, 1.0f)
                                .setColor(colorR, colorG, colorB)
                                .setLifetime(70)
                                .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, X + ((random.nextDouble() - 0.5D) / 2), Y + ((random.nextDouble() - 0.5D) / 2), Z + ((random.nextDouble() - 0.5D) / 2));
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(velX + ((random.nextDouble() - 0.5D) / 10), velY + ((random.nextDouble() - 0.5D) / 10), velZ + ((random.nextDouble() - 0.5D) / 10))
                                .setAlpha(0.125f, 0).setScale(0.2f, 0)
                                .setColor(colorR, colorG, colorB)
                                .setLifetime(20)
                                .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, X + ((random.nextDouble() - 0.5D) / 2), Y + ((random.nextDouble() - 0.5D) / 2), Z + ((random.nextDouble() - 0.5D) / 2));
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}