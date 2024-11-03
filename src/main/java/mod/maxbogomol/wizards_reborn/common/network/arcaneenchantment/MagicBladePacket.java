package mod.maxbogomol.wizards_reborn.common.network.arcaneenchantment;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SparkParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class MagicBladePacket extends PositionClientPacket {

    public MagicBladePacket(double x, double y, double z) {
        super(x, y, z);
    }

    public MagicBladePacket(Vec3 vec) {
        super(vec);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder builder = ParticleBuilder.create(FluffyFurParticles.TINY_WISP);
        builder.setBehavior(SparkParticleBehavior.create()
                        .enableSecondColor()
                        .setColorData(ColorParticleData.create().setRandomColor().build())
                        .setTransparencyData(GenericParticleData.create(0.2f, 0.2f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .build())
                .setColorData(ColorParticleData.create(0.431f, 0.305f, 0.662f).build())
                .setTransparencyData(GenericParticleData.create(0.6f, 0.6f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.ELASTIC_OUT).build())
                .setLifetime(20)
                .randomVelocity(0.5f)
                .addVelocity(0, 0.1f, 0)
                .randomOffset(0.05f)
                .setFriction(0.9f)
                .setGravity(1f)
                .repeat(level, x, y, z, 15, 0.8f);
        builder.setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                .repeat(level, x, y, z, 5, 0.5f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, MagicBladePacket.class, MagicBladePacket::encode, MagicBladePacket::decode, MagicBladePacket::handle);
    }

    public static MagicBladePacket decode(FriendlyByteBuf buf) {
        return decode(MagicBladePacket::new, buf);
    }
}
