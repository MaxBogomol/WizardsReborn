package mod.maxbogomol.wizards_reborn.api.alchemy;

import net.minecraft.core.Direction;

public interface IFluidPipePriority {
    int getPriority(Direction facing);
}