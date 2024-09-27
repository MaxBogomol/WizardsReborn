package mod.maxbogomol.wizards_reborn.common.network.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class TotemOfDisenchantBurstPacket extends PositionClientPacket {

    public TotemOfDisenchantBurstPacket(double x, double y, double z) {
        super(x, y, z);
    }

    public TotemOfDisenchantBurstPacket(BlockPos pos) {
        super(pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColorR(), WizardsRebornConfig.wissenColorG(), WizardsRebornConfig.wissenColorB()).build())
                .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                .setScaleData(GenericParticleData.create(0.3f, 0).build())
                .setLifetime(20)
                .randomVelocity(0.025f)
                .repeat(level, x + 0.5f, y + 0.725f, z + 0.5f, 10);
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColorR(), WizardsRebornConfig.wissenColorG(), WizardsRebornConfig.wissenColorB()).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setLifetime(30)
                .randomVelocity(0.025f)
                .repeat(level, x + 0.5f, y + 0.725f, z + 0.5f, 5);
        ParticleBuilder.create(FluffyFurParticles.SQUARE)
                .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColorR(), WizardsRebornConfig.wissenColorG(), WizardsRebornConfig.wissenColorB()).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setLifetime(30)
                .randomVelocity(0.025f)
                .repeat(level, x + 0.5f, y + 0.725f, z + 0.5f, 5);
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(0.784f, 1f, 0.560f).build())
                .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                .setScaleData(GenericParticleData.create(0.1f, 0).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                .setLifetime(30)
                .randomVelocity(0.025f)
                .repeat(level, x + 0.5f, y + 0.725f, z + 0.5f, 10);
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(0.784f, 1f, 0.560f).build())
                .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                .setScaleData(GenericParticleData.create(0.1f, 0).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setLifetime(30)
                .randomVelocity(0.025f)
                .repeat(level, x + 0.5f, y + 0.725f, z + 0.5f, 10);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, TotemOfDisenchantBurstPacket.class, TotemOfDisenchantBurstPacket::encode, TotemOfDisenchantBurstPacket::decode, TotemOfDisenchantBurstPacket::handle);
    }

    public static TotemOfDisenchantBurstPacket decode(FriendlyByteBuf buf) {
        return decode(TotemOfDisenchantBurstPacket::new, buf);
    }
}
