package mod.maxbogomol.wizards_reborn.common.network.spell;

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
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class MagicSproutSpellPacket extends PositionColorClientPacket {

    public MagicSproutSpellPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public MagicSproutSpellPacket(Vec3 pos, Color color) {
        super(pos, color);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                .setScaleData(GenericParticleData.create(0.1f, 0.3f).setEasing(Easing.ELASTIC_OUT).build())
                .setLifetime(40)
                .randomVelocity(0.05f)
                .flatRandomOffset(0.25f, 0.25f, 0.25f)
                .repeat(level, x, y, z, 10, 0.6f);
        ParticleBuilder.create(FluffyFurParticles.SMOKE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.15f, 0).build())
                .setScaleData(GenericParticleData.create(0.1f, 1.5f, 1f).setEasing(Easing.ELASTIC_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                .setLifetime(40)
                .randomVelocity(0.01f)
                .flatRandomOffset(0.25f, 0.25f, 0.25f)
                .repeat(level, x, y, z, 10, 0.3f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, MagicSproutSpellPacket.class, MagicSproutSpellPacket::encode, MagicSproutSpellPacket::decode, MagicSproutSpellPacket::handle);
    }

    public static MagicSproutSpellPacket decode(FriendlyByteBuf buf) {
        return decode(MagicSproutSpellPacket::new, buf);
    }
}