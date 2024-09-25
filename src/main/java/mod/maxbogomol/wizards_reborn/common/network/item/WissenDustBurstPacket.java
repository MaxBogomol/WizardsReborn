package mod.maxbogomol.wizards_reborn.common.network.item;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.ThreePositionClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class WissenDustBurstPacket extends ThreePositionClientPacket {

    public WissenDustBurstPacket(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3) {
        super(x1, y1, z1, x2, y2, z2, x3, y3, z3);
    }

    public WissenDustBurstPacket(BlockPos pos, double x2, double y2, double z2, double x3, double y3, double z3) {
        super(pos, x2, y2, z2, x3, y3, z3);
    }

    public WissenDustBurstPacket(BlockPos pos, Vec3 vec1, Vec3 vec2) {
        super(pos, vec1, vec2);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                .setScaleData(GenericParticleData.create(0.2f, 0).build())
                .setLifetime(20)
                .randomVelocity(0.015f)
                .addVelocity(x3, y3, z3)
                .randomOffset(0.15f)
                .repeat(level, x2, y2, z2, 20);
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                .setScaleData(GenericParticleData.create(0.1f, 0.2f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setLifetime(20)
                .randomVelocity(0.015f)
                .addVelocity(x3, y3, z3)
                .randomOffset(0.15f)
                .repeat(level, x2, y2, z2, 20);
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                .setScaleData(GenericParticleData.create(0.2f, 0.3f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setLifetime(30)
                .randomVelocity(0.015f)
                .flatRandomOffset(0.625f, 0.625f, 0.625f)
                .repeat(level, x1 + 0.5f, y1 + 0.5f, z1 + 0.5f, 10);
        ParticleBuilder.create(FluffyFurParticles.SQUARE)
                .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setLifetime(30)
                .randomVelocity(0.015f)
                .flatRandomOffset(0.625f, 0.625f, 0.625f)
                .repeat(level, x1 + 0.5f, y1 + 0.5f, z1 + 0.5f, 10);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, WissenDustBurstPacket.class, WissenDustBurstPacket::encode, WissenDustBurstPacket::decode, WissenDustBurstPacket::handle);
    }

    public static WissenDustBurstPacket decode(FriendlyByteBuf buf) {
        return decode(WissenDustBurstPacket::new, buf);
    }
}
