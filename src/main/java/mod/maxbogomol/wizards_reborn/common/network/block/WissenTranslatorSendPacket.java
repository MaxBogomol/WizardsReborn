package mod.maxbogomol.wizards_reborn.common.network.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import mod.maxbogomol.wizards_reborn.client.event.WissenLimitHandler;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class WissenTranslatorSendPacket extends PositionClientPacket {

    public WissenTranslatorSendPacket(double x, double y, double z) {
        super(x, y, z);
    }

    public WissenTranslatorSendPacket(BlockPos pos) {
        super(pos);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();

        WissenLimitHandler.wissenCount++;
        if (WissenLimitHandler.wissenCount > WizardsRebornClientConfig.WISSEN_RAYS_LIMIT.get())
            WissenLimitHandler.wissenCount = WizardsRebornClientConfig.WISSEN_RAYS_LIMIT.get();
        int wissenCount = WissenLimitHandler.wissenCountOld;

        ParticleBuilder sparkleBuilder = ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColor()).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                .setScaleData(GenericParticleData.create(0.1f, 0.15f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setLifetime(30)
                .randomVelocity(0.015f)
                .flatRandomOffset(0.375f, 0.375f, 0.375f);

        for (int i = 0; i < 15; i++) {
            if (random.nextFloat() < (0.75f * (1f - ((float) wissenCount / WizardsRebornClientConfig.WISSEN_RAYS_LIMIT.get()))) + 0.05f) {
                sparkleBuilder.spawn(level, x + 0.5f, y + 0.5f, z + 0.5f);
            }
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, WissenTranslatorSendPacket.class, WissenTranslatorSendPacket::encode, WissenTranslatorSendPacket::decode, WissenTranslatorSendPacket::handle);
    }

    public static WissenTranslatorSendPacket decode(FriendlyByteBuf buf) {
        return decode(WissenTranslatorSendPacket::new, buf);
    }
}
