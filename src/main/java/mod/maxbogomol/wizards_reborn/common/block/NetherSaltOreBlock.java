package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;
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
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setRenderType(FluffyFurRenderTypes.DELAYED_PARTICLE)
                        .setColorData(ColorParticleData.create(Color.BLACK).build())
                        .setTransparencyData(GenericParticleData.create(1f, 0).build())
                        .setScaleData(GenericParticleData.create(0.2f, 0).build())
                        .setLightData(LightParticleData.DEFAULT)
                        .setLifetime(40)
                        .randomVelocity(0.025f)
                        .randomOffset(0.25f)
                        .repeat(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 25);
            }
        }

        super.playerWillDestroy(world, pos, state, player);
    }
}
