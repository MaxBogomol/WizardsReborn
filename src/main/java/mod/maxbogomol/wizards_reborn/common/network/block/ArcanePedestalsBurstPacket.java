package mod.maxbogomol.wizards_reborn.common.network.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.ClientNBTPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class ArcanePedestalsBurstPacket extends ClientNBTPacket {
    
    public ArcanePedestalsBurstPacket(CompoundTag nbt) {
        super(nbt);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();

        for (int i = 0; i < nbt.size(); i++) {
            CompoundTag pos = nbt.getCompound(String.valueOf(i));
            ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setColorData(ColorParticleData.create().setRandomColor().build())
                    .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                    .setScaleData(GenericParticleData.create(0.2f, 0.3f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                    .setLifetime(20)
                    .randomVelocity(0.025f)
                    .repeat(level, pos.getFloat("x") + 0.5f, pos.getFloat("y") + 1.3F, pos.getFloat("z") + 0.5f, 10);
            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                    .setColorData(ColorParticleData.create().setRandomColor().build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                    .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                    .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                    .setLifetime(30)
                    .randomVelocity(0.035f)
                    .repeat(level, pos.getFloat("x") + 0.5f, pos.getFloat("y") + 1.3F, pos.getFloat("z") + 0.5f, 5);
            ParticleBuilder.create(FluffyFurParticles.SQUARE)
                    .setColorData(ColorParticleData.create().setRandomColor().build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                    .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                    .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                    .setLifetime(30)
                    .randomVelocity(0.035f)
                    .repeat(level, pos.getFloat("x") + 0.5f, pos.getFloat("y") + 1.3F, pos.getFloat("z") + 0.5f, 5);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ArcanePedestalsBurstPacket.class, ArcanePedestalsBurstPacket::encode, ArcanePedestalsBurstPacket::decode, ArcanePedestalsBurstPacket::handle);
    }

    public static ArcanePedestalsBurstPacket decode(FriendlyByteBuf buf) {
        return decode(ArcanePedestalsBurstPacket::new, buf);
    }
}
