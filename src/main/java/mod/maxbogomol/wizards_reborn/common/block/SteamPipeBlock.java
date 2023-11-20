package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamTileEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SteamPipeBlock extends TinyPipeBaseBlock {
    public SteamPipeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public TagKey<Block> getConnectionTag() {
        return WizardsReborn.STEAM_PIPE_CONNECTION_BLOCK_TAG;
    }

    @Override
    public TagKey<Block> getToggleConnectionTag() {
        return WizardsReborn.STEAM_PIPE_CONNECTION_TOGGLE_BLOCK_TAG;
    }

    @Override
    public boolean connected(Direction direction, BlockState state) {
        return false;
    }

    @Override
    public boolean connectToTile(BlockEntity blockEntity, Direction direction) {
        boolean connect = false;
        if (blockEntity != null) {
            if (blockEntity instanceof ISteamTileEntity steamTile) {
                connect = steamTile.canSteamConnection(direction.getOpposite());
            }
        }
        return connect;
    }

    @Override
    public boolean unclog(BlockEntity blockEntity, Level level, BlockPos pos) {
        /*if (blockEntity instanceof FluidPipeBaseTileEntity pipeEntity && pipeEntity.clogged) {
            IFluidHandler handler = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, null).orElse(null);
            handler.drain(handler.getTankCapacity(0), IFluidHandler.FluidAction.EXECUTE);
            level.updateNeighbourForOutputSignal(pos, this);
            return true;
        }*/

        return false;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return WizardsReborn.STEAM_PIPE_TILE_ENTITY.get().create(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    /*@Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        FluidPipeBaseTileEntity tile = (FluidPipeBaseTileEntity) level.getBlockEntity(pos);
        return Mth.floor(((float) tile.tank.getFluidAmount() / tile.getCapacity()) * 14.0F);
    }*/
}
