package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class SmokeEffectPacket {
    private final float posX;
    private final float posY;
    private final float posZ;

    private final float velX;
    private final float velY;
    private final float velZ;

    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

    public SmokeEffectPacket(float posX, float posY, float posZ, float velX, float velY, float velZ, float colorR, float colorG, float colorB) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static SmokeEffectPacket decode(FriendlyByteBuf buf) {
        return new SmokeEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);

        buf.writeFloat(velX);
        buf.writeFloat(velY);
        buf.writeFloat(velZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(SmokeEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 40; i++) {
                        Particles.create(WizardsReborn.STEAM_PARTICLE)
                                .addVelocity(msg.velX + ((random.nextDouble() - 0.5D) / (20 - (5 * random.nextDouble()))), msg.velY + ((random.nextDouble() - 0.5D) / (20 - (5 * random.nextDouble()))), msg.velZ + ((random.nextDouble() - 0.5D) / (20 - (5 * random.nextDouble()))))
                                .setAlpha(0.05f, 0).setScale(0.1f, 2)
                                .setColor(msg.colorR, msg.colorG, msg.colorB)
                                .setLifetime(500 + random.nextInt(100))
                                .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, msg.posX + ((random.nextDouble() - 0.5D) / 10), msg.posY + ((random.nextDouble() - 0.5D) / 10), msg.posZ + ((random.nextDouble() - 0.5D) / 10));
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
