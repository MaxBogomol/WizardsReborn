package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class ArcanemiconOfferingEffectPacket {
    private final float posX;
    private final float posY;
    private final float posZ;

    private static final Random random = new Random();

    public ArcanemiconOfferingEffectPacket(float posX, float posY, float posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static ArcanemiconOfferingEffectPacket decode(FriendlyByteBuf buf) {
        return new ArcanemiconOfferingEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);
    }

    public static void handle(ArcanemiconOfferingEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 36; i++) {
                        float distance = 0.5f + (0.25f * random.nextFloat());
                        double yaw = Math.toRadians(10 * i);
                        double pitch = 90;

                        double X = Math.sin(pitch) * Math.cos(yaw) * distance;
                        double Y = Math.cos(pitch);
                        double Z = Math.sin(pitch) * Math.sin(yaw) * distance;

                        Particles.create(WizardsReborn.STEAM_PARTICLE)
                                .addVelocity(X / 10, 0, Z / 10)
                                .setAlpha(0.4f, 0).setScale(0.4f, 0f)
                                .setColor(251 / 255f, 179 / 255f, 176 / 255f)
                                .setLifetime(100)
                                .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, msg.posX + ((random.nextDouble() - 0.5D) / 10), msg.posY + ((random.nextDouble() - 0.5D) / 10), msg.posZ + ((random.nextDouble() - 0.5D) / 10));
                        Particles.create(WizardsReborn.WISP_PARTICLE)
                                .addVelocity(X / 12, 0, Z / 12)
                                .setAlpha(0.1f, 0).setScale(0.2f, 0f)
                                .setColor(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB())
                                .setLifetime(100)
                                .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, msg.posX + ((random.nextDouble() - 0.5D) / 10), msg.posY + ((random.nextDouble() - 0.5D) / 10), msg.posZ + ((random.nextDouble() - 0.5D) / 10));
                    }

                    for (int i = 0; i < 30; i++) {
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 25), (random.nextDouble() / 10), ((random.nextDouble() - 0.5D) / 25))
                                .setAlpha(0.3f, 0).setScale(0.4f, 0f)
                                .setColor(123 / 255f, 73 / 255f, 109 / 255f)
                                .setLifetime(100)
                                .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, msg.posX + ((random.nextDouble() - 0.5D) / 5), msg.posY + ((random.nextDouble() - 0.5D) / 5), msg.posZ + ((random.nextDouble() - 0.5D) / 5));
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
