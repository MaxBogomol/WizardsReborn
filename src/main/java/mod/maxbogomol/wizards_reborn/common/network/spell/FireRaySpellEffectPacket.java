package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class FireRaySpellEffectPacket {
    private static float X, Y, Z;
    private static float colorR, colorG, colorB;

    private static Random random = new Random();

    public FireRaySpellEffectPacket(float X, float Y, float Z, float colorR, float colorG, float colorB) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static FireRaySpellEffectPacket decode(FriendlyByteBuf buf) {
        return new FireRaySpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(X);
        buf.writeFloat(Y);
        buf.writeFloat(Z);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(FireRaySpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 15; i++) {
                        if (random.nextFloat() < 0.6f) {
                            Particles.create(WizardsReborn.WISP_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 10), ((random.nextDouble() - 0.5D) / 10) + 0.05f, ((random.nextDouble() - 0.5D) / 10))
                                    .setAlpha(0.3f, 0).setScale(0.3f, 0)
                                    .setColor(colorR, colorG, colorB)
                                    .setLifetime(40)
                                    .spawn(world, X, Y, Z);
                        }

                        if (random.nextFloat() < 0.4f) {
                            world.addParticle(ParticleTypes.LARGE_SMOKE,
                                    X + ((random.nextDouble() - 0.5D) / 4), Y + ((random.nextDouble() - 0.5D) / 4), Z + ((random.nextDouble() - 0.5D) / 4),
                                    ((random.nextDouble() - 0.5D) / 10), ((random.nextDouble() - 0.5D) / 10) + 0.05f, ((random.nextDouble() - 0.5D) / 10));
                        }

                        if (random.nextFloat() < 0.1f) {
                            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 6), ((random.nextDouble() - 0.5D) / 8) + 0.3D, ((random.nextDouble() - 0.5D) / 6))
                                    .setAlpha(0.4f, 0).setScale(0.2f, 0)
                                    .setColor(0.979f, 0.912f, 0.585f)
                                    .setLifetime(60)
                                    .enableGravity()
                                    .spawn(world, X, Y, Z);
                        }
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}