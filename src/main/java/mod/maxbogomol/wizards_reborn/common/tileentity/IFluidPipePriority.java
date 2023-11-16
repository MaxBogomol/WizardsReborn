package mod.maxbogomol.wizards_reborn.common.tileentity;

import net.minecraft.core.Direction;

public interface IFluidPipePriority {
    int getPriority(Direction facing);
}