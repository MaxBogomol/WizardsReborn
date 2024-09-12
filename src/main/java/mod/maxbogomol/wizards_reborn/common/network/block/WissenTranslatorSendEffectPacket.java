package mod.maxbogomol.wizards_reborn.common.network.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.client.event.WissenLimitHandler;
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
                    Level level = WizardsReborn.proxy.getLevel();

                    WissenLimitHandler.wissenCount++;

                    if (WissenLimitHandler.wissenCount > ClientConfig.WISSEN_RAYS_LIMIT.get()) {
                        WissenLimitHandler.wissenCount = ClientConfig.WISSEN_RAYS_LIMIT.get();
                    }

                    int wissenCount = WissenLimitHandler.wissenCountOld;

                    for (int i = 0; i < 15; i++) {
                        if (random.nextFloat() < (0.75f * (1f - ((float) wissenCount / ClientConfig.WISSEN_RAYS_LIMIT.get()))) + 0.05f) {
                            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                                    .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                                    .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.15f, 0).build())
                                    .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                                    .setLifetime(30)
                                    .randomVelocity(0.015f)
                                    .flatRandomOffset(0.375f, 0.375f, 0.375f)
                                    .spawn(level, msg.pos.getX() + 0.5F, msg.pos.getY() + 0.5F, msg.pos.getZ() + 0.5F);
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
