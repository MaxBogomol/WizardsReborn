package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.network.TwoPositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class SmokePacket extends TwoPositionColorClientPacket {

    public SmokePacket(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a) {
        super(x1, y1, z1, x2, y2, z2, r, g, b, a);
    }

    public SmokePacket(Vec3 pos, Vec3 vec, Color color) {
        super(pos, vec, color);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        for (int i = 0; i < 40; i++) {
            ParticleBuilder.create(FluffyFurParticles.SMOKE)
                    .setColorData(ColorParticleData.create(r, g, b).build())
                    .setTransparencyData(GenericParticleData.create(0.05f, 0).build())
                    .setScaleData(GenericParticleData.create(0.1f, 2).build())
                    .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                    .setLifetime(500, 100)
                    .randomVelocity(0.5f / (20 - (5 * random.nextDouble())), 0.5f / (20 - (5 * random.nextDouble())), 0.5f / (20 - (5 * random.nextDouble())))
                    .addVelocity(x2, y2, z2)
                    .randomOffset(0.05f)
                    .spawn(level, x1, y1, z1);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SmokePacket.class, SmokePacket::encode, SmokePacket::decode, SmokePacket::handle);
    }

    public static SmokePacket decode(FriendlyByteBuf buf) {
        return decode(SmokePacket::new, buf);
    }
}
