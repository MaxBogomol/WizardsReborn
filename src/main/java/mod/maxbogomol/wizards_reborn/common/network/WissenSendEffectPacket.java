package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;;

public class WissenSendEffectPacket {
    private static float posFromX;
    private static float posFromY;
    private static float posFromZ;

    private static float posToX;
    private static float posToY;
    private static float posToZ;

    private static float colorR, colorG, colorB;

    private static Random random = new Random();

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
    }

    public WissenSendEffectPacket(FriendlyByteBuf friendlyByteBuf) {
    }

    public static WissenSendEffectPacket decode(FriendlyByteBuf buf) {
        return new WissenSendEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
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
    }

    @SuppressWarnings("Convert2Lambda")
    public static void handle(WissenSendEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    ClientLevel world = Minecraft.getInstance().level;

                    int particlePerBlock = 4;

                    double dX = posFromX - posToX;
                    double dY = posFromY - posToY;
                    double dZ = posFromZ - posToZ;

                    float x = (float) (dX / (particlePerBlock));
                    float y = (float) (dY / (particlePerBlock));
                    float z = (float) (dZ / (particlePerBlock));

                    for (int i = 0; i < particlePerBlock; i++) {
                        Particles.create(WizardsReborn.WISP_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50))
                                .setAlpha(0.3f, 0).setScale(0.15f, 0)
                                .setColor(colorR, colorG, colorB)
                                .setLifetime(20)
                                .spawn(world, posFromX - (x * i), posFromY - (y * i), posFromZ - (z * i));
                        if (random.nextFloat() < 0.1) {
                            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 25), ((random.nextDouble() - 0.5D) / 25), ((random.nextDouble() - 0.5D) / 25))
                                    .setAlpha(0.125f, 0).setScale(0.2f, 0)
                                    .setColor(colorR, colorG, colorB)
                                    .setLifetime(30)
                                    .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                    .spawn(world, posFromX - (x * i), posFromY - (y * i), posFromZ - (z * i));
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
