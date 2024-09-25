package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.block.ArcaneWoodLeavesBreakPacket;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornParticles;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class ArcaneWoodLeavesBlock extends LeavesBlock {

    public ArcaneWoodLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            PacketHandler.sendToTracking(level, pos, new ArcaneWoodLeavesBreakPacket(pos));
        }
        super.onRemove(state, level, pos, newState, isMoving);
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
