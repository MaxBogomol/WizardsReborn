package mod.maxbogomol.wizards_reborn.common.block.baulk;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipeConnection;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class CrossBaulkBlock extends CrossBaulkBaseBlock {

    public CrossBaulkBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TagKey<Block> getConnectionTag() {
        return WizardsRebornBlockTags.CROSS_BAULKS;
    }

    @Override
    public TagKey<Block> getToggleConnectionTag() {
        return WizardsRebornBlockTags.CROSS_BAULKS;
    }

    @Override
    public boolean connected(Direction direction, BlockState state) {
        if (state.is(WizardsRebornBlockTags.CROSS_BAULKS_CONNECTION)) {
            if (state.hasProperty(BlockStateProperties.AXIS)) {
                return state.getValue(BlockStateProperties.AXIS) == direction.getAxis();
            }
        }

        return false;
    }

    @Override
    public boolean connectToBlockEntity(BlockEntity blockEntity, Direction direction) {
        return false;
    }

    @Override
    public boolean unclog(BlockEntity blockEntity, Level level, BlockPos pos) {
        if (blockEntity instanceof CrossBaulkBlockEntity pipe) {
            for (Direction facing : Direction.values()) {
                pipe.setConnection(facing, PipeConnection.NONE);
            }
            return true;
        }
        return false;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return WizardsRebornBlockEntities.CROSS_BAULK.get().create(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    public static boolean canConnectLantern(LevelReader level, BlockPos pos, Direction direction) {
        if (level.getBlockEntity(pos) instanceof CrossBaulkBlockEntity blockEntity) {
            return blockEntity.getConnection(direction) == PipeConnection.END;
        }
        return false;
    }
}
