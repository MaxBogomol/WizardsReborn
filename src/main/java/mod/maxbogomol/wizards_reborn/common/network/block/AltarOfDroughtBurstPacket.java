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

public class AltarOfDroughtBurstPacket extends PositionClientPacket {

    public AltarOfDroughtBurstPacket(double x, double y, double z) {
        super(x, y, z);
    }

    public AltarOfDroughtBurstPacket(BlockPos pos) {
        super(pos);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColor()).build())
                .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                .setScaleData(GenericParticleData.create(0.2f, 0).build())
                .setLifetime(20)
                .randomVelocity(0.05f)
                .repeat(level, x + 0.5f, y + 0.625f, z + 0.5f, 20);
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColor()).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setLifetime(30)
                .randomVelocity(0.05f)
                .repeat(level, x + 0.5f, y + 0.625f, z + 0.5f, 10);
        ParticleBuilder.create(FluffyFurParticles.SQUARE)
                .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColor()).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setLifetime(30)
                .randomVelocity(0.05f)
                .repeat(level, x + 0.5f, y + 0.625f, z + 0.5f, 10);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, AltarOfDroughtBurstPacket.class, AltarOfDroughtBurstPacket::encode, AltarOfDroughtBurstPacket::decode, AltarOfDroughtBurstPacket::handle);
    }

    public static AltarOfDroughtBurstPacket decode(FriendlyByteBuf buf) {
        return decode(AltarOfDroughtBurstPacket::new, buf);
    }
}
