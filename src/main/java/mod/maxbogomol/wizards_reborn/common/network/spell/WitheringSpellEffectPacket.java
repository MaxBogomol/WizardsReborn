package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class WitheringSpellEffectPacket {
    private final float X, Y, Z;
    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

    public WitheringSpellEffectPacket(float X, float Y, float Z, float colorR, float colorG, float colorB) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static WitheringSpellEffectPacket decode(FriendlyByteBuf buf) {
        return new WitheringSpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(X);
        buf.writeFloat(Y);
        buf.writeFloat(Z);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(WitheringSpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 10; i++) {
                        if (random.nextFloat() < 0.3f) {
                            Particles.create(WizardsReborn.STEAM_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 70), ((random.nextDouble() - 0.5D) / 70), ((random.nextDouble() - 0.5D) / 70))
                                    .setAlpha(0.15f, 0).setScale(0.1f, 0.5f)
                                    .setColor(msg.colorR, msg.colorG, msg.colorB)
                                    .setLifetime(80)
                                    .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                    .spawn(world, msg.X + ((random.nextDouble() - 0.5D) / 2), msg.Y + ((random.nextDouble() - 0.5D) / 2), msg.Z + ((random.nextDouble() - 0.5D) / 2));
                        }
                        if (random.nextFloat() < 0.3f) {
                            Particles.create(WizardsReborn.SMOKE_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 60), ((random.nextDouble() - 0.5D) / 60), ((random.nextDouble() - 0.5D) / 60))
                                    .setAlpha(0.25f, 0).setScale(0.1f, 1.5f)
                                    .setColor(msg.colorR, msg.colorG, msg.colorB)
                                    .setLifetime(80)
                                    .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                    .spawn(world, msg.X + ((random.nextDouble() - 0.5D) / 2), msg.Y + ((random.nextDouble() - 0.5D) / 2), msg.Z + ((random.nextDouble() - 0.5D) / 2));
                        }
                        if (random.nextFloat() < 0.2f) {
                            Particles.create(WizardsReborn.SMOKE_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 60), ((random.nextDouble() - 0.5D) / 60), ((random.nextDouble() - 0.5D) / 60))
                                    .setAlpha(0.25f, 0).setScale(0.0f, 1f)
                                    .setColor(0, 0, 0)
                                    .setLifetime(80)
                                    .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                    .spawn(world, msg.X + ((random.nextDouble() - 0.5D) / 2), msg.Y + ((random.nextDouble() - 0.5D) / 2), msg.Z + ((random.nextDouble() - 0.5D) / 2));
                        }
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}