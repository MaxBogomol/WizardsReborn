package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalTileEntity;
import mod.maxbogomol.wizards_reborn.common.block.SensorBaseBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class WissenActivatorTileEntity extends SensorTileEntity {
    public WissenActivatorTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public WissenActivatorTileEntity(BlockPos pPos, BlockState pBlockState) {
        super(WizardsReborn.WISSEN_ACTIVATOR_TILE_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean active = level.hasNeighborSignal(getBlockPos());

            if (active) {
                Direction direction = getBlockState().getValue(SensorBaseBlock.FACING);
                BlockPos blockpos = getBlockPos().relative(direction);

                switch (getBlockState().getValue(SensorBaseBlock.FACE)) {
                    case FLOOR:
                        blockpos = getBlockPos().above();
                        break;
                    case WALL:
                        break;
                    case CEILING:
                        blockpos = getBlockPos().below();
                        break;
                }

                if (level.getBlockEntity(blockpos) instanceof IWissenWandFunctionalTileEntity functionalTile) {
                    functionalTile.wissenWandFuction();
                }
            }
        }
    }
}
