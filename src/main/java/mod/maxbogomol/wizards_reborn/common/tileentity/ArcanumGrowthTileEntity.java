package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ArcanumGrowthTileEntity extends BlockEntity {
    public ArcanumGrowthTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ArcanumGrowthTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.ARCANUM_GROWTH_TILE_ENTITY.get(), pos, state);
    }
}
