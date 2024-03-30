package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class NetherSaltOreBlock extends Block {
    public NetherSaltOreBlock(Properties properties) {
        super(properties);
    }
    private static Random random = new Random();

    protected int getExperience(RandomSource rand) {
        return Mth.nextInt(rand, 3, 7);
    }

    @Override
    public void spawnAfterBreak(BlockState pState, ServerLevel pLevel, BlockPos pPos, ItemStack pStack, boolean pDropExperience) {
        super.spawnAfterBreak(pState, pLevel, pPos, pStack, pDropExperience);
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.level.LevelReader world, RandomSource randomSource, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(randomSource) : 0;
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (world.isClientSide()) {
            if (!player.isCreative()) {
                for (int i = 0; i < 25; i++) {
                    Particles.create(WizardsReborn.SMOKE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20) + 0.02f, ((random.nextDouble() - 0.5D) / 20))
                            .setAlpha(1f, 0).setScale(0.2f, 0)
                            .setColor(0, 0, 0)
                            .setLifetime(40)
                            .spawn(world, pos.getX() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5), pos.getY() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5), pos.getZ() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5));
                }
            }
        }

        super.playerWillDestroy(world, pos, state, player);
    }
}
