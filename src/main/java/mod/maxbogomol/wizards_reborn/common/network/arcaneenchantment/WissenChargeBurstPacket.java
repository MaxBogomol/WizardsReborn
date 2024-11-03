package mod.maxbogomol.wizards_reborn.common.network.arcaneenchantment;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class WissenChargeBurstPacket extends PositionColorClientPacket {

    public WissenChargeBurstPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public WissenChargeBurstPacket(Vec3 vec, Color color, float a) {
        super(vec, color, a);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.2f * a, 0).build())
                .setScaleData(GenericParticleData.create(0.15f * a, 0).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.3f).build())
                .setLifetime(40)
                .randomVelocity(0.035f)
                .randomOffset(0.025f)
                .repeat(level, x, y, z, 30);
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.3f * a, 0).build())
                .setScaleData(GenericParticleData.create(0.15f * a, 0.3f * a, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.3f).build())
                .setLifetime(40)
                .randomVelocity(0.035f)
                .randomOffset(0.025f)
                .repeat(level, x, y, z, 15, 0.6f);
        ParticleBuilder.create(FluffyFurParticles.SQUARE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.3f * a, 0).build())
                .setScaleData(GenericParticleData.create(0.05f * a, 0.1f * a, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.3f).build())
                .setLifetime(40)
                .randomVelocity(0.035f)
                .randomOffset(0.025f)
                .repeat(level, x, y, z, 15, 0.6f);
        ParticleBuilder.create(FluffyFurParticles.SMOKE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.3f * a, 0).build())
                .setScaleData(GenericParticleData.create(0.5f * a, 0).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                .setLifetime(40)
                .randomVelocity(0.035f)
                .randomOffset(0.025f)
                .repeat(level, x, y, z, 30, 0.3f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, WissenChargeBurstPacket.class, WissenChargeBurstPacket::encode, WissenChargeBurstPacket::decode, WissenChargeBurstPacket::handle);
    }

    public static WissenChargeBurstPacket decode(FriendlyByteBuf buf) {
        return decode(WissenChargeBurstPacket::new, buf);
    }
}
