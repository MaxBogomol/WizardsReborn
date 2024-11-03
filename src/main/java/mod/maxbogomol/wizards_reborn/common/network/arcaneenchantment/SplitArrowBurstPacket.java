package mod.maxbogomol.wizards_reborn.common.network.arcaneenchantment;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
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

public class SplitArrowBurstPacket extends PositionColorClientPacket {

    public SplitArrowBurstPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public SplitArrowBurstPacket(Vec3 vec, Color color) {
        super(vec, color);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.2f, 0).build())
                .setScaleData(GenericParticleData.create(0.05f, 0).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.3f).build())
                .setLifetime(40)
                .randomVelocity(0.035f)
                .randomOffset(0.025f)
                .repeat(level, x, y, z, 5);
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                .setScaleData(GenericParticleData.create(0.1f, 0).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                .setLifetime(40)
                .randomVelocity(0.035f)
                .randomOffset(0.025f)
                .repeat(level, x, y, z, 5, 0.6f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SplitArrowBurstPacket.class, SplitArrowBurstPacket::encode, SplitArrowBurstPacket::decode, SplitArrowBurstPacket::handle);
    }

    public static SplitArrowBurstPacket decode(FriendlyByteBuf buf) {
        return decode(SplitArrowBurstPacket::new, buf);
    }
}
