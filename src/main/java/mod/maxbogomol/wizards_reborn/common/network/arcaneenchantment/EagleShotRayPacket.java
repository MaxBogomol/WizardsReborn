package mod.maxbogomol.wizards_reborn.common.network.arcaneenchantment;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.ThreePositionColorClientPacket;
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

public class EagleShotRayPacket extends ThreePositionColorClientPacket {

    public EagleShotRayPacket(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, float r, float g, float b, float a) {
        super(x1, y1, z1, x2, y2, z2, x3, y3, z3, r, g, b, a);
    }

    public EagleShotRayPacket(Vec3 vec1, Vec3 vec2, Vec3 vec3, Color color) {
        super(vec1, vec2, vec3, color);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();

        int distance = (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2)) * 8;
        for (int i = 0; i < distance; i++) {
            float ii = (float) i / distance;
            double lx = Mth.lerp(ii, x1, x2);
            double ly = Mth.lerp(ii, y1, y2);
            double lz = Mth.lerp(ii, z1, z2);

            if (random.nextFloat() < 0.15f) {
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setColorData(ColorParticleData.create(r, g, b).build())
                        .setTransparencyData(GenericParticleData.create(0.15f, 0.2f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f, 0.15f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                        .setLifetime(20, 5)
                        .randomVelocity(0.001f)
                        .addVelocity(x3, y3, z3)
                        .spawn(level, lx, ly, lz);
            }

            if (random.nextFloat() < 0.05f) {
                boolean square = random.nextBoolean();
                ParticleBuilder.create(square ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                        .setColorData(ColorParticleData.create(r, g, b).build())
                        .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                        .setScaleData(GenericParticleData.create(0, square ? 0.1f : 0.2f, 0).setEasing(Easing.SINE_IN_OUT).build())
                        .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.05f).build())
                        .setLifetime(30, 5)
                        .randomVelocity(0.002f)
                        .addVelocity(x3, y3, z3)
                        .spawn(level, lx, ly, lz);
            }
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, EagleShotRayPacket.class, EagleShotRayPacket::encode, EagleShotRayPacket::decode, EagleShotRayPacket::handle);
    }

    public static EagleShotRayPacket decode(FriendlyByteBuf buf) {
        return decode(EagleShotRayPacket::new, buf);
    }
}
