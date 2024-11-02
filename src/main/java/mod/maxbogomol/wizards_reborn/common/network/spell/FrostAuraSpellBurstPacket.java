package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SparkParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class FrostAuraSpellBurstPacket extends PositionColorClientPacket {

    public FrostAuraSpellBurstPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public FrostAuraSpellBurstPacket(Vec3 pos, Color color) {
        super(pos, color);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.SQUARE)
                .setBehavior(SparkParticleBehavior.create().build())
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.1f,  0).setEasing(Easing.ELASTIC_OUT).build())
                .setLifetime(10)
                .randomVelocity(0.5f)
                .addVelocity(0, 0.2f, 0)
                .randomOffset(0.25f)
                .setFriction(0.9f)
                .setGravity(1f)
                .repeat(level, x, y, z, 10, 0.3f);
        for (int i = 0; i < 10; i++) {
            if (random.nextFloat() < 0.5f) {
                level.addParticle(ParticleTypes.SNOWFLAKE,
                        x + ((random.nextDouble() - 0.5D) / 2), y + ((random.nextDouble() - 0.5D) / 2), z + ((random.nextDouble() - 0.5D) / 2),
                        ((random.nextDouble() - 0.5D) / 10), ((random.nextDouble() - 0.5D) / 10) + 0.05f, ((random.nextDouble() - 0.5D) / 10));
            }
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, FrostAuraSpellBurstPacket.class, FrostAuraSpellBurstPacket::encode, FrostAuraSpellBurstPacket::decode, FrostAuraSpellBurstPacket::handle);
    }

    public static FrostAuraSpellBurstPacket decode(FriendlyByteBuf buf) {
        return decode(FrostAuraSpellBurstPacket::new, buf);
    }
}