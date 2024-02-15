package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
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
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 15; i++) {
                        if (random.nextFloat() < 0.6f) {
                            double x = ((random.nextDouble() - 0.5D) * 2);
                            double y = ((random.nextDouble() - 0.5D) * 2);
                            double z = ((random.nextDouble() - 0.5D) * 2);

                            Particles.create(WizardsReborn.WISP_PARTICLE)
                                    .addVelocity(-x / 8, -y / 8, -z / 8)
                                    .setAlpha(0.3f, 0).setScale(0.1f, 0.3f)
                                    .setColor(msg.colorR, msg.colorG, msg.colorB)
                                    .setLifetime(40)
                                    .spawn(world, msg.X + (x * 1.5f), msg.Y + (y * 1.5f), msg.Z + (z * 1.5f));
                        }
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}