package mod.maxbogomol.wizards_reborn.common.network.item;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.network.PositionClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class FlowerFertilizerPacket extends PositionClientPacket {

    public FlowerFertilizerPacket(double x, double y, double z) {
        super(x, y, z);
    }

    public FlowerFertilizerPacket(Vec3 vec) {
        super(vec);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(0.545F, 0.875F, 0.522F).build())
                .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                .setScaleData(GenericParticleData.create(0.1f, 0.5f).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.01f).build())
                .setLifetime(20)
                .setGravity(1f)
                .randomVelocity(0.05f)
                .addVelocity(0, 0.2F, 0)
                .randomOffset(0.125f)
                .repeat(level, x, y, z, 10);
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(0.545F, 0.875F, 0.522F).build())
                .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                .setScaleData(GenericParticleData.create(0.1f, 0.5f).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.01f).build())
                .setLifetime(10)
                .setGravity(1f)
                .randomVelocity(0.05f)
                .addVelocity(0, 0.2F, 0)
                .randomOffset(0.125f)
                .repeat(level, x, y, z, 10);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, FlowerFertilizerPacket.class, FlowerFertilizerPacket::encode, FlowerFertilizerPacket::decode, FlowerFertilizerPacket::handle);
    }

    public static FlowerFertilizerPacket decode(FriendlyByteBuf buf) {
        return decode(FlowerFertilizerPacket::new, buf);
    }
}
