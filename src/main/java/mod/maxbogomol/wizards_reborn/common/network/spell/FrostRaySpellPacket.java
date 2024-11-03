package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.network.PositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class FrostRaySpellPacket extends PositionColorClientPacket {

    public FrostRaySpellPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public FrostRaySpellPacket(Vec3 pos, Color color) {
        super(pos, color);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                .setScaleData(GenericParticleData.create(0.3f, 0).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                .setLifetime(40)
                .randomVelocity(0.05f)
                .addVelocity(0, 0.05f, 0)
                .repeat(level, x, y, z, 10, 0.6f);

        for (int i = 0; i < 10; i++) {
            if (random.nextFloat() < 0.8f) {
                level.addParticle(ParticleTypes.SNOWFLAKE,
                        x + ((random.nextDouble() - 0.5D) / 2), y + ((random.nextDouble() - 0.5D) / 2), z + ((random.nextDouble() - 0.5D) / 2),
                        ((random.nextDouble() - 0.5D) / 10), ((random.nextDouble() - 0.5D) / 10) + 0.05f, ((random.nextDouble() - 0.5D) / 10));
            }
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, FrostRaySpellPacket.class, FrostRaySpellPacket::encode, FrostRaySpellPacket::decode, FrostRaySpellPacket::handle);
    }

    public static FrostRaySpellPacket decode(FriendlyByteBuf buf) {
        return decode(FrostRaySpellPacket::new, buf);
    }
}