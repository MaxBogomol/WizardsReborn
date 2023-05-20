package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class ArcanumOreBlock extends Block {
    public ArcanumOreBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    protected int getExperience(Random rand) {
        return MathHelper.nextInt(rand, 3, 6);
    }

    public void spawnAdditionalDrops(BlockState state, ServerWorld worldIn, BlockPos pos, ItemStack stack) {
        super.spawnAdditionalDrops(state, worldIn, pos, stack);
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.isRemote()) {
            if (!player.isCreative()) {
                for (int i = 0; i < 15; i++) {
                    Particles.create(WizardsReborn.WISP_PARTICLE)
                            .addVelocity(((RANDOM.nextDouble() - 0.5D) / 5), ((RANDOM.nextDouble() - 0.5D) / 5), ((RANDOM.nextDouble() - 0.5D) / 5))
                            .setAlpha(0.125f, 0).setScale(0.2f, 0)
                            .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                            .setLifetime(20)
                            .spawn(world, pos.getX() + 0.5F + ((RANDOM.nextDouble() - 0.5D) * 0.1), pos.getY() + 0.5F + ((RANDOM.nextDouble() - 0.5D) * 0.1), pos.getZ() + 0.5F + ((RANDOM.nextDouble() - 0.5D) * 0.1));
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((RANDOM.nextDouble() - 0.5D) / 15), ((RANDOM.nextDouble() - 0.5D) / 15), ((RANDOM.nextDouble() - 0.5D) / 15))
                            .setAlpha(0.25f, 0).setScale(0.2f, 0)
                            .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((RANDOM.nextDouble() - 0.5D) * 2)))
                            .spawn(world, pos.getX() + 0.5F + ((RANDOM.nextDouble() - 0.5D) * 0.5), pos.getY() + 0.5F + ((RANDOM.nextDouble() - 0.5D) * 0.5), pos.getZ() + 0.5F + ((RANDOM.nextDouble() - 0.5D) * 0.5));
                }
            }
        }

        super.onBlockHarvested(world, pos, state, player);
    }
}
