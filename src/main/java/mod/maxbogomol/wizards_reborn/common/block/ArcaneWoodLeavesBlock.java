package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornParticles;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArcaneWoodLeavesBlock extends LeavesBlock {

    public ArcaneWoodLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.isClientSide()) {
            if (!player.isCreative()) {
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                        .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                        .setScaleData(GenericParticleData.create(0.2f, 0).build())
                        .setLifetime(20)
                        .randomVelocity(0.05f)
                        .randomOffset(0.05)
                        .repeat(level, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 5);
                ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                        .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                        .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                        .setScaleData(GenericParticleData.create(0.2f, 0).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                        .setLifetime(30)
                        .randomVelocity(0.035f)
                        .randomOffset(0.25f)
                        .repeat(level, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 5);
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(20) == 0) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!isFaceFull(blockstate.getCollisionShape(level, blockpos), Direction.UP)) {
                ParticleBuilder.create(WizardsRebornParticles.ARCANE_WOOD_LEAVES)
                        .setParticleRenderType(ParticleRenderType.PARTICLE_SHEET_OPAQUE)
                        .flatRandomOffset(1f, 0, 1f)
                        .spawn(level, pos.getX() + 0.5F, pos.getY() - 0.05f, pos.getZ() + 0.5F);
            }
        }
    }
}
