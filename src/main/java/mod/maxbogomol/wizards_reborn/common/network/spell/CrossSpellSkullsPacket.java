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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class CrossSpellSkullsPacket extends PositionColorClientPacket {

    public CrossSpellSkullsPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public CrossSpellSkullsPacket(Vec3 vec, Color color) {
        super(vec, color);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.1f, 0.125f, 0).setEasing(Easing.ELASTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.25f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setLifetime(20)
                .randomVelocity(0.075f)
                .repeat(level, x, y, z, 5);
        ParticleBuilder.create(FluffyFurParticles.SKULL)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.075f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.05f).build())
                .setLifetime(30)
                .randomVelocity(0.05f)
                .repeat(level, x, y, z, 10);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, CrossSpellSkullsPacket.class, CrossSpellSkullsPacket::encode, CrossSpellSkullsPacket::decode, CrossSpellSkullsPacket::handle);
    }

    public static CrossSpellSkullsPacket decode(FriendlyByteBuf buf) {
        return decode(CrossSpellSkullsPacket::new, buf);
    }
}