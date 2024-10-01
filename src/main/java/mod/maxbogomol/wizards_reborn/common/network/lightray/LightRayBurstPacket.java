package mod.maxbogomol.wizards_reborn.common.network.lightray;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SparkParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class LightRayBurstPacket extends PositionColorClientPacket {

    public LightRayBurstPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public LightRayBurstPacket(Vec3 vec, Color color) {
        super(vec, color);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder builder = ParticleBuilder.create(FluffyFurParticles.TINY_WISP);
        builder.setBehavior(SparkParticleBehavior.create().build())
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.6f, 0.6f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.ELASTIC_OUT).build())
                .setLifetime(10)
                .randomVelocity(0.5f)
                .addVelocity(0, 0.1f, 0)
                .randomOffset(0.05f)
                .setFriction(0.87f)
                .enablePhysics()
                .setGravity(1f)
                .spawn(level, x, y, z);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, LightRayBurstPacket.class, LightRayBurstPacket::encode, LightRayBurstPacket::decode, LightRayBurstPacket::handle);
    }

    public static LightRayBurstPacket decode(FriendlyByteBuf buf) {
        return decode(LightRayBurstPacket::new, buf);
    }
}
