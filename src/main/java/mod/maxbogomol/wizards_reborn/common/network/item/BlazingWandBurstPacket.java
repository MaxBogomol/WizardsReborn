package mod.maxbogomol.wizards_reborn.common.network.item;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SparkParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.block.salt.torch.SaltTorchBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class BlazingWandBurstPacket extends PositionClientPacket {

    public BlazingWandBurstPacket(double x, double y, double z) {
        super(x, y, z);
    }

    public BlazingWandBurstPacket(Vec3 vec) {
        super(vec);
    }

    public BlazingWandBurstPacket(BlockPos pos) {
        super(pos.getCenter());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.DOT)
                .setBehavior(SparkParticleBehavior.create().build())
                .setColorData(ColorParticleData.create(SaltTorchBlockEntity.colorFirst, SaltTorchBlockEntity.colorSecond).build())
                .setTransparencyData(GenericParticleData.create(0.125f, 0.25f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.ELASTIC_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                .setLifetime(15)
                .randomVelocity(0.3f)
                .addVelocity(0, 0.1f, 0)
                .randomOffset(0.5f)
                .setFriction(0.9f)
                .setGravity(1f)
                .repeat(level, x, y, z, 30);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BlazingWandBurstPacket.class, BlazingWandBurstPacket::encode, BlazingWandBurstPacket::decode, BlazingWandBurstPacket::handle);
    }

    public static BlazingWandBurstPacket decode(FriendlyByteBuf buf) {
        return decode(BlazingWandBurstPacket::new, buf);
    }
}
