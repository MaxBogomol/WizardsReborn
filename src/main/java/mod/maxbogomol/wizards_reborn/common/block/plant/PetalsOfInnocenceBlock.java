package mod.maxbogomol.wizards_reborn.common.block.plant;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PetalsOfInnocenceBlock extends PinkPetalsBlock {

    public PetalsOfInnocenceBlock(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.1f) {
            double X = (random.nextDouble() - 0.5D);
            double Z = (random.nextDouble() - 0.5D);
            ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setColorData(ColorParticleData.create(0.968f, 0.968f, 0.968f).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                    .setScaleData(GenericParticleData.create(0.05f, 0).build())
                    .setLifetime(15)
                    .addVelocity(-(X / 100), (random.nextDouble() / 40), -(Z / 100))
                    .randomOffset(0.25f, 0, 0.25f)
                    .spawn(level, pos.getX() + 0.5f, pos.getY() + 0.05f, pos.getZ() + 0.5f);
        }
    }
}
