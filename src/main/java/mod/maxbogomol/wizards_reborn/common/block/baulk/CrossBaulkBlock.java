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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

public class CrossBaulkBlock extends CrossBaulkBaseBlock {
    public CrossBaulkBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TagKey<Block> getConnectionTag() {
        return WizardsRebornBlockTags.FLUID_PIPE_CONNECTION;
    }

    @Override
    public TagKey<Block> getToggleConnectionTag() {
        return WizardsRebornBlockTags.FLUID_PIPE_CONNECTION;
    }

    @Override
    public boolean connected(Direction direction, BlockState state) {
        return false;
    }

    @Override
    public boolean connectToBlockEntity(BlockEntity blockEntity, Direction direction) {
        return blockEntity != null && blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, direction.getOpposite()).isPresent();
    }

    @Override
    public boolean unclog(BlockEntity blockEntity, Level level, BlockPos pos) {
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
