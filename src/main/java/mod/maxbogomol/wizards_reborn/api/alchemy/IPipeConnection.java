package mod.maxbogomol.wizards_reborn.api.alchemy;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public interface IPipeConnection {
    PipeConnection getPipeConnection(BlockState state, Direction direction);
}