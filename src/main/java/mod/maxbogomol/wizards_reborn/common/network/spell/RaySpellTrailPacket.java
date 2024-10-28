package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.TwoPositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class RaySpellTrailPacket extends TwoPositionColorClientPacket {

    public RaySpellTrailPacket(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a) {
        super(x1, y1, z1, x2, y2, z2, r, g, b, a);
    }

    public RaySpellTrailPacket(Vec3 vec1, Vec3 vec2, Color color, float a) {
        super(vec1, vec2, color, a);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();

        if (random.nextFloat() < 0.2f) {
            ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setColorData(ColorParticleData.create(r, g, b).build())
                    .setTransparencyData(GenericParticleData.create(0.15f, 0.2f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                    .setScaleData(GenericParticleData.create(0.1f, 0.15f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                    .setLifetime(25)
                    .randomVelocity(0.025f)
                    .spawn(level, x1, y1, z1);
        }

        for (int i = 0; i < 10; i++) {
            if (random.nextFloat() < 0.1f) {
                double lerp = random.nextDouble();
                double lx = Mth.lerp(lerp, x1, x2);
                double ly = Mth.lerp(lerp, y1, y2);
                double lz = Mth.lerp(lerp, z1, z2);

                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setColorData(ColorParticleData.create(r, g, b).build())
                        .setTransparencyData(GenericParticleData.create(0.2f, 0).build())
                        .setScaleData(GenericParticleData.create(0.1f, 0.15f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                        .setLifetime(10)
                        .randomVelocity(0.015f)
                        .spawn(level, lx, ly, lz);

                if (random.nextFloat() < 0.1f) {
                    boolean square = random.nextBoolean();
                    ParticleBuilder.create(square ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(r, g, b).build())
                            .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                            .setScaleData(GenericParticleData.create(0, square ? 0.1f : 0.2f, 0).setEasing(Easing.SINE_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.05f).build())
                            .setLifetime(10)
                            .randomVelocity(0.015f)
                            .spawn(level, lx, ly, lz);
                }
            }
        }

        if (a > 0) {
            ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setColorData(ColorParticleData.create(r, g, b).build())
                    .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                    .setScaleData(GenericParticleData.create(0.2f, 0).build())
                    .setLifetime(40)
                    .randomVelocity(0.035f)
                    .repeat(level, x2, y2, z2, 5, 0.1f);
            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                    .setColorData(ColorParticleData.create(r, g, b).build())
                    .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                    .setScaleData(GenericParticleData.create(0, 0.075f, 0).setEasing(Easing.SINE_IN_OUT).build())
                    .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                    .setLifetime(50)
                    .randomVelocity(0.035f)
                    .repeat(level, x2, y2, z2, 4, 0.1f);
            ParticleBuilder.create(FluffyFurParticles.SQUARE)
                    .setColorData(ColorParticleData.create(r, g, b).build())
                    .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                    .setScaleData(GenericParticleData.create(0, 0.05f, 0).setEasing(Easing.SINE_IN_OUT).build())
                    .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                    .setLifetime(50)
                    .randomVelocity(0.035f)
                    .repeat(level, x2, y2, z2, 1, 0.1f);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, RaySpellTrailPacket.class, RaySpellTrailPacket::encode, RaySpellTrailPacket::decode, RaySpellTrailPacket::handle);
    }

    public static RaySpellTrailPacket decode(FriendlyByteBuf buf) {
        return decode(RaySpellTrailPacket::new, buf);
    }
}