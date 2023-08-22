package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CrystalGrowthTileEntity extends BlockEntity {
    public CrystalGrowthTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public CrystalGrowthTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.CRYSTAL_GROWTH_TILE_ENTITY.get(), pos, state);
    }
}
