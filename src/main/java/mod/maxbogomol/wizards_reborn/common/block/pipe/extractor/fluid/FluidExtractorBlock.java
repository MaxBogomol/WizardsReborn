package mod.maxbogomol.wizards_reborn.common.block.pipe.extractor.fluid;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.pipe.extractor.ExtractorBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.fluid.FluidPipeBaseBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockTags;
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

public class FluidExtractorBlock extends ExtractorBaseBlock {
    
    public FluidExtractorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TagKey<Block> getConnectionTag() {
        return WizardsRebornBlockTags.FLUID_PIPE_CONNECTION;
    }

    @Override
    public TagKey<Block> getToggleConnectionTag() {
        return WizardsRebornBlockTags.FLUID_PIPE_CONNECTION_TOGGLE;
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
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return WizardsRebornBlockEntities.FLUID_EXTRACTOR.get().create(pos, state);
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
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        FluidPipeBaseBlockEntity tile = (FluidPipeBaseBlockEntity) level.getBlockEntity(pos);
        return Mth.floor(((float) tile.tank.getFluidAmount() / tile.getCapacity()) * 14.0F);
    }
}
