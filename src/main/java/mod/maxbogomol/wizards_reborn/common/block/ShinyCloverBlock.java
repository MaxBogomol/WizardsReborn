package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class ShinyCloverBlock extends BushBlock {
    private static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 3, 13);

    public ShinyCloverBlock(Properties builder) {
        super(builder);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.1f) {
            double X = (random.nextDouble() - 0.5D) * 0.75f;
            double Z = (random.nextDouble() - 0.5D) * 0.75f;
            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                    .addVelocity(-(X / 100), (random.nextDouble() / 20), -(Z / 100))
                    .setAlpha(0.5f, 0).setScale(0.1f, 0)
                    .setColor(0.427f, 0.612f, 0.423f, 0.968f, 0.941f, 0.549f)
                    .setLifetime(10)
                    .spawn(world, pos.getX() + 0.5F + X, pos.getY() + 0.05F, pos.getZ() + 0.5F + Z);
        }
    }
}
