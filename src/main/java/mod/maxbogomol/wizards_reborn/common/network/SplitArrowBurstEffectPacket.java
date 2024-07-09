package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class SplitArrowBurstEffectPacket {
    private final float posX;
    private final float posY;
    private final float posZ;

    private final float r;
    private final float g;
    private final float b;

    private static final Random random = new Random();

    public SplitArrowBurstEffectPacket(float posX, float posY, float posZ, float r, float g, float b) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static SplitArrowBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new SplitArrowBurstEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);

        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
    }

    public static void handle(SplitArrowBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 5; i++) {
                        Particles.create(WizardsReborn.WISP_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 15), ((random.nextDouble() - 0.5D) / 15), ((random.nextDouble() - 0.5D) / 15))
                                .setAlpha(0.2f, 0).setScale(0.05f, 0)
                                .setColor(msg.r, msg.g, msg.b)
                                .setLifetime(40)
                                .setSpin((0.3f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, msg.posX + ((random.nextDouble() - 0.5D) / 20), msg.posY + ((random.nextDouble() - 0.5D) / 20), msg.posZ + ((random.nextDouble() - 0.5D) / 20));

                        if (random.nextFloat() < 0.6f) {
                            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 15), ((random.nextDouble() - 0.5D) / 15), ((random.nextDouble() - 0.5D) / 15))
                                    .setAlpha(0.3f, 0).setScale(0.1f, 0)
                                    .setColor(msg.r, msg.g, msg.b)
                                    .setLifetime(40)
                                    .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                    .spawn(world, msg.posX + ((random.nextDouble() - 0.5D) / 20), msg.posY + ((random.nextDouble() - 0.5D) / 20), msg.posZ + ((random.nextDouble() - 0.5D) / 20));
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
