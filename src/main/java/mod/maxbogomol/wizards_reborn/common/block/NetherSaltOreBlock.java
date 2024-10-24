package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.block.NetherSaltOreBreakPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

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
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            WizardsRebornPacketHandler.sendToTracking(level, pos, new NetherSaltOreBreakPacket(pos));
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }
}
