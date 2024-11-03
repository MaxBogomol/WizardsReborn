package mod.maxbogomol.wizards_reborn.common.network.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class NetherSaltOreBreakPacket extends PositionClientPacket {

    public NetherSaltOreBreakPacket(double x, double y, double z) {
        super(x, y, z);
    }

    public NetherSaltOreBreakPacket(BlockPos pos) {
        super(pos);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.SMOKE)
                .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                .setColorData(ColorParticleData.create(Color.BLACK).build())
                .setTransparencyData(GenericParticleData.create(1f, 0).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.2f, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.15f).build())
                .setLightData(LightParticleData.DEFAULT)
                .setLifetime(40)
                .randomVelocity(0.025f)
                .flatRandomOffset(0.4f, 0.4f, 0.4f)
                .repeat(level, x + 0.5f, y + 0.5f, z + 0.5f, 25);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, NetherSaltOreBreakPacket.class, NetherSaltOreBreakPacket::encode, NetherSaltOreBreakPacket::decode, NetherSaltOreBreakPacket::handle);
    }

    public static NetherSaltOreBreakPacket decode(FriendlyByteBuf buf) {
        return decode(NetherSaltOreBreakPacket::new, buf);
    }
}
