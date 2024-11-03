package mod.maxbogomol.wizards_reborn.common.network.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.event.WissenLimitHandler;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class WissenTranslatorBurstPacket extends PositionColorClientPacket {

    public WissenTranslatorBurstPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public WissenTranslatorBurstPacket(double x, double y, double z, Color color) {
        super(x, y, z, color);
    }

    public WissenTranslatorBurstPacket(double x, double y, double z) {
        super(x, y, z, WizardsRebornConfig.wissenColorR(), WizardsRebornConfig.wissenColorG(), WizardsRebornConfig.wissenColorB(), 1);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();

        WissenLimitHandler.wissenCount++;
        if (WissenLimitHandler.wissenCount > 200) WissenLimitHandler.wissenCount = 200;
        int wissenCount = WissenLimitHandler.wissenCountOld;

        ParticleBuilder wispBuilder = ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                .setScaleData(GenericParticleData.create(0.2f, 0).build())
                .setLifetime(20)
                .randomVelocity(0.025f);
        ParticleBuilder sparkleBuilder = ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.075f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setLifetime(30)
                .randomVelocity(0.025f);

        for (int i = 0; i < 10; i++) {
            if (random.nextFloat() < (0.75f * (1f - (wissenCount / 200f))) + 0.05f) {
                wispBuilder.spawn(level, x, y, z);
            }
            if (random.nextFloat() < (0.75f * (1f - (wissenCount / 200f))) + 0.05f) {
                sparkleBuilder.spawn(level, x, y, z);
            }
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, WissenTranslatorBurstPacket.class, WissenTranslatorBurstPacket::encode, WissenTranslatorBurstPacket::decode, WissenTranslatorBurstPacket::handle);
    }

    public static WissenTranslatorBurstPacket decode(FriendlyByteBuf buf) {
        return decode(WissenTranslatorBurstPacket::new, buf);
    }
}
