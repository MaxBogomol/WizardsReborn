package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.util.RenderUtils;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornParticles;
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
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                        .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                        .setScaleData(GenericParticleData.create(0.2f, 0).build())
                        .setLifetime(20)
                        .randomVelocity(0.05f)
                        .randomOffset(0.05)
                        .repeat(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 5);
                ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                        .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                        .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                        .setScaleData(GenericParticleData.create(0.2f, 0).build())
                        .randomSpin(0.5f)
                        .setLifetime(30)
                        .randomVelocity(0.035f)
                        .randomOffset(0.25f)
                        .repeat(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 5);
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
                ParticleBuilder.create(WizardsRebornParticles.ARCANE_WOOD_LEAVES)
                        .setRenderType(RenderUtils.DELAYED_PARTICLE)
                        .flatRandomOffset(1f, 0, 1f)
                        .spawn(world, pos.getX() + 0.5F, pos.getY() - 0.05f, pos.getZ() + 0.5F);
            }
        }
    }
}
