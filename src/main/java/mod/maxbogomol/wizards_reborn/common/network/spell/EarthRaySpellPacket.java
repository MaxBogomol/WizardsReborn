package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class EarthRaySpellPacket extends PositionColorClientPacket {

    public EarthRaySpellPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public EarthRaySpellPacket(Vec3 pos, Color color) {
        super(pos, color);
    }

    public EarthRaySpellPacket(BlockPos pos, Color color) {
        super(pos, color);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        BlockPos blockPos = BlockPos.containing(x, y, z);
        VoxelShape voxelShape = level.getBlockState(blockPos).getShape(level, blockPos);
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.2f, 0.3f, 0).setEasing(Easing.ELASTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.1f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.25f).build())
                .setLifetime(15)
                .randomVelocity(0.015f)
                .spawnVoxelShape(level, x, y, z, voxelShape, 2);
        ParticleBuilder.create(FluffyFurParticles.SQUARE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.1f, 0.2f, 0).setEasing(Easing.ELASTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.25f).build())
                .setLifetime(15)
                .randomVelocity(0.015f)
                .spawnVoxelShape(level, x, y, z, voxelShape, 1);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, EarthRaySpellPacket.class, EarthRaySpellPacket::encode, EarthRaySpellPacket::decode, EarthRaySpellPacket::handle);
    }

    public static EarthRaySpellPacket decode(FriendlyByteBuf buf) {
        return decode(EarthRaySpellPacket::new, buf);
    }
}