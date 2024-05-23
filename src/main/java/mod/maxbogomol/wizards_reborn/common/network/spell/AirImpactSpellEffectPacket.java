package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
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
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 20; i++) {
                        Particles.create(WizardsReborn.WISP_PARTICLE)
                                .addVelocity(msg.velX + ((random.nextDouble() - 0.5D) / 40), msg.velY + ((random.nextDouble() - 0.5D) / 40), msg.velZ + ((random.nextDouble() - 0.5D) / 40))
                                .setAlpha(0.05f, 0).setScale(0.1f, 1.0f)
                                .setColor(msg.colorR, msg.colorG, msg.colorB)
                                .setLifetime(15)
                                .setSpin(3f)
                                .spawn(world, msg.X, msg.Y, msg.Z);
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}