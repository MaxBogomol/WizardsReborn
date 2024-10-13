package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class WitheringSpellPacket extends PositionColorClientPacket {

    public WitheringSpellPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public WitheringSpellPacket(Vec3 vec, Color color) {
        super(vec, color);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0.5f, 0).setEasing(Easing.ELASTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.25f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setLifetime(20)
                .randomVelocity(0.075f)
                .repeat(level, x, y, z, 10);
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                .setColorData(ColorParticleData.create(Color.BLACK).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0.5f, 0).setEasing(Easing.ELASTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.25f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setLifetime(20)
                .randomVelocity(0.075f)
                .repeat(level, x, y, z, 10);
        ParticleBuilder.create(FluffyFurParticles.SKULL)
                .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.075f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.05f).build())
                .setLifetime(30)
                .randomVelocity(0.05f)
                .repeat(level, x, y, z, 10);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, WitheringSpellPacket.class, WitheringSpellPacket::encode, WitheringSpellPacket::decode, WitheringSpellPacket::handle);
    }

    public static WitheringSpellPacket decode(FriendlyByteBuf buf) {
        return decode(WitheringSpellPacket::new, buf);
    }
}