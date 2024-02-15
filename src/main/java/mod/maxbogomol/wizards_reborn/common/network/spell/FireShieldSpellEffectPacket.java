package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class FireShieldSpellEffectPacket {
    private final float X, Y, Z;
    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

    public FireShieldSpellEffectPacket(float X, float Y, float Z, float colorR, float colorG, float colorB) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static FireShieldSpellEffectPacket decode(FriendlyByteBuf buf) {
        return new FireShieldSpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(X);
        buf.writeFloat(Y);
        buf.writeFloat(Z);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(FireShieldSpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 15; i++) {
                        if (random.nextFloat() < 0.6f) {
                            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 10), ((random.nextDouble() - 0.5D) / 10), ((random.nextDouble() - 0.5D) / 10))
                                    .setAlpha(0.5f, 0).setScale(0.1f, 0.4f)
                                    .setColor(msg.colorR, msg.colorG, msg.colorB)
                                    .setLifetime(60)
                                    .spawn(world, msg.X, msg.Y, msg.Z);
                        }
                        if (random.nextFloat() < 0.6f) {
                            Particles.create(WizardsReborn.WISP_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 6), ((random.nextDouble() - 0.5D) / 8) + 0.3D, ((random.nextDouble() - 0.5D) / 6))
                                    .setAlpha(0.6f, 0).setScale(0.1f, 0.3f)
                                    .setColor(0.979f, 0.912f, 0.585f)
                                    .setLifetime(80)
                                    .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                    .enableGravity()
                                    .spawn(world, msg.X + ((random.nextDouble() - 0.5D) / 5), msg.Y + ((random.nextDouble() - 0.5D) / 5), msg.Z + ((random.nextDouble() - 0.5D) / 5));
                        }
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}