package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;;

public class WissenTranslatorBurstEffectPacket {
    private static float posX;
    private static float posY;
    private static float posZ;

    private static float colorR, colorG, colorB;

    private static Random random = new Random();

    public WissenTranslatorBurstEffectPacket(float posX, float posY, float posZ, float colorR, float colorG, float colorB) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public WissenTranslatorBurstEffectPacket(float posX, float posY, float posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.colorR = 0.466f;
        this.colorG = 0.643f;
        this.colorB = 0.815f;
    }

    public static WissenTranslatorBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new WissenTranslatorBurstEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(WissenTranslatorBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    ClientLevel world = Minecraft.getInstance().level;

                    for (int i = 0; i < 10; i++) {
                        Particles.create(WizardsReborn.WISP_PARTICLE)
                              .addVelocity(((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20))
                              .setAlpha(0.125f, 0).setScale(0.2f, 0)
                              .setColor(colorR, colorG, colorB)
                              .setLifetime(20)
                              .spawn(world, posX, posY, posZ);
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                              .addVelocity(((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20))
                              .setAlpha(0.25f, 0).setScale(0.075f, 0)
                              .setColor(colorR, colorG, colorB)
                              .setLifetime(30)
                              .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                              .spawn(world, posX, posY, posZ);
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
