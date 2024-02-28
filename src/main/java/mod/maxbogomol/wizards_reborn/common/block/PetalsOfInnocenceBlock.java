package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class PetalsOfInnocenceBlock extends PinkPetalsBlock {

    public PetalsOfInnocenceBlock(Properties properties) {
        super(properties);
    }

    private static Random random = new Random();

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.1f) {
            double X = (random.nextDouble() - 0.5D);
            double Z = (random.nextDouble() - 0.5D);
            Particles.create(WizardsReborn.WISP_PARTICLE)
                    .addVelocity(-(X / 100), (random.nextDouble() / 40), -(Z / 100))
                    .setAlpha(0.5f, 0).setScale(0.05f, 0)
                    .setColor(0.968f, 0.968f, 0.968f)
                    .setLifetime(10)
                    .spawn(world, pos.getX() + 0.5F + X, pos.getY() + 0.05F, pos.getZ() + 0.5F + Z);
        }
    }
}
