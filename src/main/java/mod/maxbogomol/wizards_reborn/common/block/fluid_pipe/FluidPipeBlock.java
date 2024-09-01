package mod.maxbogomol.wizards_reborn.common.block.fluid_pipe;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlock;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class FluidPipeBlock extends PipeBaseBlock {
    public FluidPipeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public TagKey<Block> getConnectionTag() {
        return WizardsRebornTags.FLUID_PIPE_CONNECTION_BLOCK;
    }

    @Override
    public TagKey<Block> getToggleConnectionTag() {
        return WizardsRebornTags.FLUID_PIPE_CONNECTION_TOGGLE_BLOCK;
    }

    @Override
    public boolean connected(Direction direction, BlockState state) {
        return false;
    }

    @Override
    public boolean connectToTile(BlockEntity blockEntity, Direction direction) {
        return blockEntity != null && blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, direction.getOpposite()).isPresent();
    }

    @Override
    public boolean unclog(BlockEntity blockEntity, Level level, BlockPos pos) {
        if (blockEntity instanceof FluidPipeBaseBlockEntity pipeEntity && pipeEntity.clogged) {
            IFluidHandler handler = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, null).orElse(null);
            handler.drain(handler.getTankCapacity(0), IFluidHandler.FluidAction.EXECUTE);
            level.updateNeighbourForOutputSignal(pos, this);
            return true;
        }

        return false;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return WizardsRebornBlockEntities.FLUID_PIPE.get().create(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        FluidPipeBaseBlockEntity tile = (FluidPipeBaseBlockEntity) level.getBlockEntity(pos);
        return Mth.floor(((float) tile.tank.getFluidAmount() / tile.getCapacity()) * 14.0F);
    }
}
