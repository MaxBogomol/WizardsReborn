package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class FlowerFertilizerEffectPacket {
    private final float posX;
    private final float posY;
    private final float posZ;

    private static final Random random = new Random();

    public FlowerFertilizerEffectPacket(float posX, float posY, float posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static FlowerFertilizerEffectPacket decode(FriendlyByteBuf buf) {
        return new FlowerFertilizerEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);
    }

    public static void handle(FlowerFertilizerEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 10; i++) {
                        Particles.create(WizardsReborn.WISP_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 10), (random.nextDouble() / 10) + 0.2F, ((random.nextDouble() - 0.5D) / 10))
                                .setAlpha(0.4f, 0).setScale(0.1f, 0.5f)
                                .setColor(0.545F, 0.875F, 0.522F)
                                .setLifetime(20)
                                .setSpin((0.01f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .enableGravity()
                                .spawn(world, msg.posX + ((random.nextDouble() - 0.5D) / 4), msg.posY + ((random.nextDouble() - 0.5D) / 4), msg.posZ + ((random.nextDouble() - 0.5D) / 4));
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 10), (random.nextDouble() / 10) + 0.2F, ((random.nextDouble() - 0.5D) / 10))
                                .setAlpha(0.4f, 0).setScale(0.1f, 0.5f)
                                .setColor(0.545F, 0.875F, 0.522F)
                                .setLifetime(10)
                                .setSpin((0.01f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .enableGravity()
                                .spawn(world, msg.posX + ((random.nextDouble() - 0.5D) / 4), msg.posY + ((random.nextDouble() - 0.5D) / 4), msg.posZ + ((random.nextDouble() - 0.5D) / 4));
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
