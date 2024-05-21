package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
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
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 4; i++) {
                        if (random.nextFloat() < 0.6f) {
                            double x = (random.nextDouble() - 0.5D);
                            double y = (random.nextDouble() - 0.5D);
                            double z = (random.nextDouble() - 0.5D);

                            Particles.create(WizardsReborn.KARMA_PARTICLE)
                                    .addVelocity(-x / 16, -y / 16, -z / 16)
                                    .setAlpha(0.3f, 0).setScale(0.05f, 0.2f)
                                    .setColor(msg.colorR, msg.colorG, msg.colorB)
                                    .setLifetime(35)
                                    .spawn(world, msg.X + x, msg.Y + y, msg.Z + z);
                        }
                    }

                    for (int i = 0; i < 10; i++) {
                        if (random.nextFloat() < 0.6f) {
                            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 40), ((random.nextDouble() - 0.5D) / 40), ((random.nextDouble() - 0.5D) / 40))
                                    .setAlpha(0.3f, 0).setScale(0, 0.3f)
                                    .setColor(msg.colorR, msg.colorG, msg.colorB)
                                    .setLifetime(60)
                                    .spawn(world, msg.X, msg.Y, msg.Z);
                        }
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}