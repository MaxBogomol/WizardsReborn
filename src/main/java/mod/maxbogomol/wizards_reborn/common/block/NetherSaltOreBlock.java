package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;

public class NetherSaltOreBlock extends Block {

    public NetherSaltOreBlock(Properties properties) {
        super(properties);
    }

    protected int getExperience(RandomSource random) {
        return Mth.nextInt(random, 3, 7);
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader world, RandomSource random, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(random) : 0;
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.isClientSide()) {
            if (!player.isCreative()) {
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setRenderType(FluffyFurRenderTypes.DELAYED_PARTICLE)
                        .setColorData(ColorParticleData.create(Color.BLACK).build())
                        .setTransparencyData(GenericParticleData.create(1f, 0).build())
                        .setScaleData(GenericParticleData.create(0.2f, 0).build())
                        .setLightData(LightParticleData.DEFAULT)
                        .setLifetime(40)
                        .randomVelocity(0.025f)
                        .randomOffset(0.25f)
                        .repeat(level, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 25);
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }
}
