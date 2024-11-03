package mod.maxbogomol.wizards_reborn.common.network.spell;

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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class ChargeSpellTrailPacket extends ThreePositionColorClientPacket {

    public ChargeSpellTrailPacket(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, float r, float g, float b, float a) {
        super(x1, y1, z1, x2, y2, z2, x3, y3, z3, r, g, b, a);
    }

    public ChargeSpellTrailPacket(Vec3 vec1, Vec3 vec2, Vec3 vec3, Color color, float a) {
        super(vec1, vec2, vec3, color, a);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();

        for (int i = 0; i < 15; i++) {
            float ii = i / 15.0f;
            double lx = Mth.lerp(ii, x1, x2);
            double ly = Mth.lerp(ii, y1, y2);
            double lz = Mth.lerp(ii, z1, z2);

            if (random.nextFloat() < 0.25f) {
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setColorData(ColorParticleData.create(r, g, b).build())
                        .setTransparencyData(GenericParticleData.create(0.15f * a, 0.2f * a, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f * a, 0.15f * a, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                        .setLifetime(20, 5)
                        .randomVelocity(0.001f)
                        .addVelocity(-x3, -y3, -z3)
                        .spawn(level, lx, ly, lz);
            }

            if (random.nextFloat() < 0.05f) {
                boolean square = random.nextBoolean();
                ParticleBuilder.create(square ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                        .setColorData(ColorParticleData.create(r, g, b).build())
                        .setTransparencyData(GenericParticleData.create(0.125f * a, 0).build())
                        .setScaleData(GenericParticleData.create(0, square ? 0.1f * a : 0.2f * a, 0).setEasing(Easing.SINE_IN_OUT).build())
                        .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.05f).build())
                        .setLifetime(30, 5)
                        .randomVelocity(0.002f)
                        .addVelocity(-x3, -y3, -z3)
                        .spawn(level, lx, ly, lz);
            }
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ChargeSpellTrailPacket.class, ChargeSpellTrailPacket::encode, ChargeSpellTrailPacket::decode, ChargeSpellTrailPacket::handle);
    }

    public static ChargeSpellTrailPacket decode(FriendlyByteBuf buf) {
        return decode(ChargeSpellTrailPacket::new, buf);
    }
}
