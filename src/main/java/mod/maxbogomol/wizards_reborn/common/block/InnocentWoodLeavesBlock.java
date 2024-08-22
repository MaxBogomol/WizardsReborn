package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.utils.RenderUtils;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
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
                ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                        .setColorData(ColorParticleData.create(0.968f, 0.968f, 0.968f).build())
                        .setTransparencyData(GenericParticleData.create(0.2f, 0).build())
                        .setScaleData(GenericParticleData.create(0.2f, 0).build())
                        .randomSpin(0.5f)
                        .setLifetime(20)
                        .randomVelocity(0.0125f)
                        .randomOffset(0.5f)
                        .repeat(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 5);
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
                ParticleBuilder.create(WizardsReborn.INNOCENT_WOOD_LEAVES_PARTICLE)
                        .setRenderType(RenderUtils.DELAYED_PARTICLE)
                        .setLightData(LightParticleData.DEFAULT)
                        .flatRandomOffset(1f, 0, 1f)
                        .spawn(world, pos.getX() + 0.5F, pos.getY() - 0.05f, pos.getZ() + 0.5F);
            }
        }
    }
}
