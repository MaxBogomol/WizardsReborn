package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class InnocentWoodLeavesBlock extends LeavesBlock {
    private static Random random = new Random();

    public InnocentWoodLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (world.isClientSide()) {
            if (!player.isCreative()) {
                for (int i = 0; i < 5; i++) {
                    Particles.create(WizardsReborn.WISP_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 40), (random.nextDouble() / 40), ((random.nextDouble() - 0.5D) / 40))
                            .setAlpha(0.2f, 0).setScale(0.2f, 0)
                            .setColor(0.968f, 0.968f, 0.968f)
                            .setLifetime(20)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(world, pos.getX() + 0.5F + (random.nextDouble() - 0.5D), pos.getY() + 0.5F + (random.nextDouble() - 0.5D), pos.getZ() + 0.5F + (random.nextDouble() - 0.5D));
                }
            }
        }

        super.playerWillDestroy(world, pos, state, player);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextInt(10) == 0) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = world.getBlockState(blockpos);
            if (!isFaceFull(blockstate.getCollisionShape(world, blockpos), Direction.UP)) {
                Particles.create(WizardsReborn.INNOCENT_WOOD_LEAVES_PARTICLE)
                        .spawn(world, pos.getX() + 0.5F + (random.nextFloat() - 0.5f), pos.getY() - 0.05f, pos.getZ() + 0.5F + (random.nextFloat() - 0.5f));
            }
        }
    }
}
