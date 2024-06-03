package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class BlockPlaceSpellEffectPacket {
    private final float X, Y, Z;
    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

    public BlockPlaceSpellEffectPacket(float X, float Y, float Z, float colorR, float colorG, float colorB) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static BlockPlaceSpellEffectPacket decode(FriendlyByteBuf buf) {
        return new BlockPlaceSpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(X);
        buf.writeFloat(Y);
        buf.writeFloat(Z);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(BlockPlaceSpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 20; i++) {
                        if (random.nextFloat() < 0.6f) {
                            Particles.create(WizardsReborn.WISP_PARTICLE)
                                    .addVelocity(0, 0.06F, 0)
                                    .setAlpha(0.4f, 0).setScale(0.2f, 0)
                                    .setColor(msg.colorR, msg.colorG, msg.colorB)
                                    .setLifetime(30)
                                    .spawn(world, msg.X + ((random.nextFloat() - 0.5f) * 1.1f), msg.Y + ((random.nextFloat() - 0.5f) * 1.1f), msg.Z + ((random.nextFloat() - 0.5f) * 1.1f));
                        }
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}