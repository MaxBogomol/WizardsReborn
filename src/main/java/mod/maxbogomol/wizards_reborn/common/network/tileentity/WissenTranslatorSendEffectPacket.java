package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class WissenTranslatorSendEffectPacket {
    private final BlockPos pos;
    private static final Random random = new Random();

    public WissenTranslatorSendEffectPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static WissenTranslatorSendEffectPacket decode(FriendlyByteBuf buf) {
        return new WissenTranslatorSendEffectPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(WissenTranslatorSendEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getLevel();

                    ClientTickHandler.wissenCount++;

                    if (ClientTickHandler.wissenCount > ClientConfig.WISSEN_RAYS_LIMIT.get()) {
                        ClientTickHandler.wissenCount = ClientConfig.WISSEN_RAYS_LIMIT.get();
                    }

                    int wissenCount = ClientTickHandler.wissenCountOld;

                    for (int i = 0; i < 15; i++) {
                        if (random.nextFloat() < (0.75f * (1f - ((float) wissenCount / ClientConfig.WISSEN_RAYS_LIMIT.get()))) + 0.05f) {
                            ParticleBuilder.create(FluffyFur.SPARKLE_PARTICLE)
                                    .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                                    .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.15f, 0).build())
                                    .randomSpin(0.5f)
                                    .setLifetime(30)
                                    .randomVelocity(0.015f)
                                    .flatRandomOffset(0.375f, 0.375f, 0.375f)
                                    .spawn(world, msg.pos.getX() + 0.5F, msg.pos.getY() + 0.5F, msg.pos.getZ() + 0.5F);
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
