package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.common.block.PipeConnection;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public interface IPipeConnection {
    PipeConnection getPipeConnection(BlockState state, Direction direction);
}