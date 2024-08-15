package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.config.Config;
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

public class ArcaneWoodLeavesBlock extends LeavesBlock {
    private static Random random = new Random();

    public ArcaneWoodLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (world.isClientSide()) {
            if (!player.isCreative()) {
                for (int i = 0; i < 5; i++) {
                    Particles.create(WizardsReborn.WISP_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 5), ((random.nextDouble() - 0.5D) / 5), ((random.nextDouble() - 0.5D) / 5))
                            .setAlpha(0.125f, 0).setScale(0.2f, 0)
                            .setColor(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB())
                            .setLifetime(20)
                            .spawn(world, pos.getX() + 0.5F + ((random.nextDouble() - 0.5D) * 0.1), pos.getY() + 0.5F + ((random.nextDouble() - 0.5D) * 0.1), pos.getZ() + 0.5F + ((random.nextDouble() - 0.5D) * 0.1));
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 15), ((random.nextDouble() - 0.5D) / 15), ((random.nextDouble() - 0.5D) / 15))
                            .setAlpha(0.25f, 0).setScale(0.2f, 0)
                            .setColor(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB())
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(world, pos.getX() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5), pos.getY() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5), pos.getZ() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5));
                }
            }
        }

        super.playerWillDestroy(world, pos, state, player);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextInt(20) == 0) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = world.getBlockState(blockpos);
            if (!isFaceFull(blockstate.getCollisionShape(world, blockpos), Direction.UP)) {
                Particles.create(WizardsReborn.ARCANE_WOOD_LEAVES_PARTICLE)
                        .spawn(world, pos.getX() + 0.5F + (random.nextFloat() - 0.5f), pos.getY() - 0.05f, pos.getZ() + 0.5F + (random.nextFloat() - 0.5f));
            }
        }
    }
}
