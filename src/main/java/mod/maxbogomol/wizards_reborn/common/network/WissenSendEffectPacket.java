package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class WissenSendEffectPacket {
    private final float posFromX;
    private final float posFromY;
    private final float posFromZ;

    private final float posToX;
    private final float posToY;
    private final float posToZ;

    private final float colorR, colorG, colorB;

    private final int particlePerBlock;

    private static final Random random = new Random();

    public WissenSendEffectPacket(float posFromX, float posFromY, float posFromZ, float posToX, float posToY, float posToZ, float colorR, float colorG, float colorB) {
        this.posFromX = posFromX;
        this.posFromY = posFromY;
        this.posFromZ = posFromZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.particlePerBlock = 4;
    }

    public WissenSendEffectPacket(float posFromX, float posFromY, float posFromZ, float posToX, float posToY, float posToZ) {
        this.posFromX = posFromX;
        this.posFromY = posFromY;
        this.posFromZ = posFromZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.colorR = 0.466f;
        this.colorG = 0.643f;
        this.colorB = 0.815f;
        this.particlePerBlock = 4;
    }

    public WissenSendEffectPacket(float posFromX, float posFromY, float posFromZ, float posToX, float posToY, float posToZ, float colorR, float colorG, float colorB, int particlePerBlock) {
        this.posFromX = posFromX;
        this.posFromY = posFromY;
        this.posFromZ = posFromZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.particlePerBlock = particlePerBlock;
    }

    public WissenSendEffectPacket(float posFromX, float posFromY, float posFromZ, float posToX, float posToY, float posToZ, int particlePerBlock) {
        this.posFromX = posFromX;
        this.posFromY = posFromY;
        this.posFromZ = posFromZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.colorR = 0.466f;
        this.colorG = 0.643f;
        this.colorB = 0.815f;
        this.particlePerBlock = particlePerBlock;
    }

    public static WissenSendEffectPacket decode(FriendlyByteBuf buf) {
        return new WissenSendEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posFromX);
        buf.writeFloat(posFromY);
        buf.writeFloat(posFromZ);

        buf.writeFloat(posToX);
        buf.writeFloat(posToY);
        buf.writeFloat(posToZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);

        buf.writeInt(particlePerBlock);
    }

    @SuppressWarnings("Convert2Lambda")
    public static void handle(WissenSendEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    int particlePerBlock = msg.particlePerBlock;

                    double dX = msg.posFromX - msg.posToX;
                    double dY = msg.posFromY - msg.posToY;
                    double dZ = msg.posFromZ - msg.posToZ;

                    float x = (float) (dX / (particlePerBlock));
                    float y = (float) (dY / (particlePerBlock));
                    float z = (float) (dZ / (particlePerBlock));

                    ClientTickHandler.wissenCount++;

                    if (ClientTickHandler.wissenCount > 200) {
                        ClientTickHandler.wissenCount = 200;
                    }

                    int wissenCount = ClientTickHandler.wissenCountOld;

                    for (int i = 0; i < particlePerBlock; i++) {
                        if (random.nextFloat() < (0.75f * (1f - (wissenCount / 200f))) + 0.05f) {
                            Particles.create(WizardsReborn.WISP_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50))
                                    .setAlpha(0.3f, 0).setScale(0.15f, 0)
                                    .setColor(msg.colorR, msg.colorG, msg.colorB)
                                    .setLifetime(20)
                                    .spawn(world, msg.posFromX - (x * i), msg.posFromY - (y * i), msg.posFromZ - (z * i));
                            if (random.nextFloat() < 0.1) {
                                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                        .addVelocity(((random.nextDouble() - 0.5D) / 25), ((random.nextDouble() - 0.5D) / 25), ((random.nextDouble() - 0.5D) / 25))
                                        .setAlpha(0.125f, 0).setScale(0.2f, 0)
                                        .setColor(msg.colorR, msg.colorG, msg.colorB)
                                        .setLifetime(30)
                                        .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                        .spawn(world, msg.posFromX - (x * i), msg.posFromY - (y * i), msg.posFromZ - (z * i));
                            }
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
