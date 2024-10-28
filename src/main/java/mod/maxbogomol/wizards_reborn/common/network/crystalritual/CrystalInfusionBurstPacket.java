package mod.maxbogomol.wizards_reborn.common.network.crystalritual;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SparkParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class CrystalInfusionBurstPacket extends PositionColorClientPacket {

    public CrystalInfusionBurstPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public CrystalInfusionBurstPacket(BlockPos pos, Color color) {
        super(pos, color);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.TINY_WISP)
                .setBehavior(SparkParticleBehavior.create().build())
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.6f, 0.4f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.ELASTIC_OUT).build())
                .setLifetime(20)
                .randomVelocity(0.5f)
                .addVelocity(0, 0.1f, 0)
                .randomOffset(0.05f)
                .setFriction(0.9f)
                .setGravity(1f)
                .repeat(level, x + 0.5f, y + 1.25f, z + 0.5f, 25);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, CrystalInfusionBurstPacket.class, CrystalInfusionBurstPacket::encode, CrystalInfusionBurstPacket::decode, CrystalInfusionBurstPacket::handle);
    }

    public static CrystalInfusionBurstPacket decode(FriendlyByteBuf buf) {
        return decode(CrystalInfusionBurstPacket::new, buf);
    }
}