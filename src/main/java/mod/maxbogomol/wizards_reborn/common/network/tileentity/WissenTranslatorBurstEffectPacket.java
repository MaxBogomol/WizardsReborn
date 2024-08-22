package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class WissenTranslatorBurstEffectPacket {
    private final float posX;
    private final float posY;
    private final float posZ;

    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

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

        this.colorR = Config.wissenColorR();
        this.colorG = Config.wissenColorG();
        this.colorB = Config.wissenColorB();
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
                    Level world = WizardsReborn.proxy.getWorld();

                    ClientTickHandler.wissenCount++;

                    if (ClientTickHandler.wissenCount > 200) {
                        ClientTickHandler.wissenCount = 200;
                    }

                    int wissenCount = ClientTickHandler.wissenCountOld;

                    for (int i = 0; i < 10; i++) {
                        if (random.nextFloat() < (0.75f * (1f - (wissenCount / 200f))) + 0.05f) {
                            ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                                    .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                    .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.2f, 0).build())
                                    .setLifetime(20)
                                    .randomVelocity(0.025f)
                                    .spawn(world, msg.posX, msg.posY, msg.posZ);
                        }
                        if (random.nextFloat() < (0.75f * (1f - (wissenCount / 200f))) + 0.05f) {
                            ParticleBuilder.create(FluffyFur.SPARKLE_PARTICLE)
                                    .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                    .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.075f, 0).build())
                                    .randomSpin(0.5f)
                                    .setLifetime(30)
                                    .randomVelocity(0.025f)
                                    .spawn(world, msg.posX, msg.posY, msg.posZ);
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
