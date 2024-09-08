package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ArcanumOreBlock extends Block {

    public ArcanumOreBlock(Properties properties) {
        super(properties);
    }

    protected int getExperience(RandomSource random) {
        return Mth.nextInt(random, 3, 6);
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader level, RandomSource random, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(random) : 0;
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
                        .randomVelocity(0.1)
                        .randomOffset(0.05)
                        .repeat(level, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 15);
                ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                        .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                        .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                        .setScaleData(GenericParticleData.create(0.2f, 0).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                        .setLifetime(30)
                        .randomVelocity(0.035f)
                        .randomOffset(0.25f)
                        .repeat(level, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 15);
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }
}
