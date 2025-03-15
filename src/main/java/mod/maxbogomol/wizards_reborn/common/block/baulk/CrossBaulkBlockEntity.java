package mod.maxbogomol.wizards_reborn.common.block.baulk;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.IPipeConnection;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipeConnection;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CrossBaulkBlockEntity extends PipeBaseBlockEntity implements TickableBlockEntity {

    public CrossBaulkBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public CrossBaulkBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.CROSS_BAULK.get(), pos, state);
    }

    public void tick() {
        if (!level.isClientSide()) {
            if (!loaded) initConnections();
        }
    }

    @Override
    public void initConnections() {
        Block block = level.getBlockState(getBlockPos()).getBlock();
        for (Direction direction : Direction.values()) {
            if (block instanceof CrossBaulkBaseBlock pipeBlock) {
                BlockState facingState = level.getBlockState(getBlockPos().relative(direction));
                BlockEntity facingBE = level.getBlockEntity(getBlockPos().relative(direction));
                if (!(facingBE instanceof CrossBaulkBlockEntity) || ((CrossBaulkBlockEntity) facingBE).getConnection(direction.getOpposite()) != PipeConnection.DISABLED) {
                    if (facingState.is(pipeBlock.getConnectionTag())) {
                        if (facingBE instanceof CrossBaulkBlockEntity && ((CrossBaulkBlockEntity) facingBE).getConnection(direction.getOpposite()) == PipeConnection.DISABLED) {
                            connections[direction.get3DDataValue()] = PipeConnection.DISABLED;
                        } else {
                            connections[direction.get3DDataValue()] = PipeConnection.END;
                        }
                    } else {
                        if (pipeBlock.connected(direction, facingState) && ((CrossBaulkBlockEntity) facingBE).getConnection(direction.getOpposite()) == PipeConnection.NONE) {
                            connections[direction.get3DDataValue()] = PipeConnection.END;
                        }
                    }
                }
            }
        }
        loaded = true;
        setChanged();
        level.getChunkAt(getBlockPos()).setUnsaved(true);
        level.updateNeighbourForOutputSignal(getBlockPos(), block);
    }
}
