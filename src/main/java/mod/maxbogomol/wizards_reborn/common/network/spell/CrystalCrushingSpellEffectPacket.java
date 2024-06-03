package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class CrystalCrushingSpellEffectPacket {
    private final float X, Y, Z;
    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

    public CrystalCrushingSpellEffectPacket(float X, float Y, float Z, float colorR, float colorG, float colorB) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static CrystalCrushingSpellEffectPacket decode(FriendlyByteBuf buf) {
        return new CrystalCrushingSpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(X);
        buf.writeFloat(Y);
        buf.writeFloat(Z);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(CrystalCrushingSpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 40; i++) {
                        if (random.nextFloat() < 0.6f) {
                            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 5), ((random.nextDouble() - 0.5D) / 5), ((random.nextDouble() - 0.5D) / 5))
                                    .setAlpha(0.5f, 0).setScale(0.3f, 0.5f)
                                    .setColor(msg.colorR, msg.colorG, msg.colorB)
                                    .setLifetime(60)
                                    .setSpin((0.25f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                    .spawn(world, msg.X, msg.Y, msg.Z);
                        }
                        if (random.nextFloat() < 0.1f) {
                            Particles.create(WizardsReborn.WISP_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 5), ((random.nextDouble() - 0.5D) / 5), ((random.nextDouble() - 0.5D) / 5))
                                    .setAlpha(0.5f, 0).setScale(0.3f, 0.5f)
                                    .setColor(msg.colorR, msg.colorG, msg.colorB)
                                    .setLifetime(30)
                                    .setSpin((0.25f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                    .spawn(world, msg.X, msg.Y, msg.Z);
                        }
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}