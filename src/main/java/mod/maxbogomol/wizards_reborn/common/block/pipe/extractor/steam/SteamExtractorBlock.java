package mod.maxbogomol.wizards_reborn.common.block.pipe.extractor.steam;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.pipe.extractor.TinyExtractorBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.steam.SteamPipeBaseBlockEntity;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.block.SteamBreakPacket;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class SteamExtractorBlock extends TinyExtractorBaseBlock {

    public SteamExtractorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TagKey<Block> getConnectionTag() {
        return WizardsRebornBlockTags.STEAM_PIPE_CONNECTION;
    }

    @Override
    public TagKey<Block> getToggleConnectionTag() {
        return WizardsRebornBlockTags.STEAM_PIPE_CONNECTION_TOGGLE;
    }

    @Override
    public boolean connectToBlockEntity(BlockEntity blockEntity, Direction direction) {
        boolean connect = false;
        if (blockEntity != null) {
            if (blockEntity instanceof ISteamBlockEntity steamBlockEntity) {
                connect = steamBlockEntity.canSteamConnection(direction.getOpposite());
            }
        }
        return connect;
    }

    @Override
    public boolean unclog(BlockEntity blockEntity, Level level, BlockPos pos) {
        if (blockEntity instanceof SteamPipeBaseBlockEntity pipeEntity && pipeEntity.clogged) {
            pipeEntity.setSteam(0);
            level.updateNeighbourForOutputSignal(pos, this);
            BlockEntityUpdate.packet(pipeEntity);
            return true;
        }

        return false;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return WizardsRebornBlockEntities.STEAM_EXTRACTOR.get().create(pos, state);
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
        SteamPipeBaseBlockEntity blockEntity = (SteamPipeBaseBlockEntity) level.getBlockEntity(pos);
        return Mth.floor(((float) blockEntity.getSteam() / blockEntity.getMaxSteam()) * 14.0F);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof ISteamBlockEntity blockEntity) {
                if (blockEntity.getMaxSteam() > 0) {
                    float amount = (float) blockEntity.getSteam() / (float) blockEntity.getMaxSteam();
                    WizardsRebornPacketHandler.sendToTracking(level, pos, new SteamBreakPacket(pos, 15 * amount));
                }
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }
}
