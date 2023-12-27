package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class MagicBladeEffectPacket {
    private static float posX;
    private static float posY;
    private static float posZ;

    private static Random random = new Random();

    public MagicBladeEffectPacket(float posX, float posY, float posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static MagicBladeEffectPacket decode(FriendlyByteBuf buf) {
        return new MagicBladeEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);
    }

    public static void handle(MagicBladeEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 15; i++) {
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 10), ((random.nextDouble() - 0.5D) / 10), ((random.nextDouble() - 0.5D) / 10))
                                .setAlpha(0.4f, 0).setScale(0.1f, 0.5f)
                                .setColor(0.431F, 0.305F, 0.662F)
                                .setLifetime(30)
                                .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, posX + ((random.nextDouble() - 0.5D) / 10), posY + ((random.nextDouble() - 0.5D) / 10), posZ + ((random.nextDouble() - 0.5D) / 10));
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
