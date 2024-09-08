package mod.maxbogomol.wizards_reborn.common.block.sensor.wissen_activator;

import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class WissenActivatorBlockEntity extends SensorBlockEntity {
    public WissenActivatorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public WissenActivatorBlockEntity(BlockPos pos, BlockState blockState) {
        super(WizardsRebornBlockEntities.WISSEN_ACTIVATOR.get(), pos, blockState);
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

                if (level.getBlockEntity(blockpos) instanceof IWissenWandFunctionalBlockEntity functionalTile) {
                    functionalTile.wissenWandFunction();
                }
            }
        }
    }
}
