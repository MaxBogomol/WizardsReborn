package mod.maxbogomol.wizards_reborn.common.block.creative.steam_storage;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipeConnection;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CreativeSteamStorageBlockEntity extends PipeBaseBlockEntity implements TickableBlockEntity, ISteamBlockEntity {

    public CreativeSteamStorageBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public CreativeSteamStorageBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.CREATIVE_STEAM_STORAGE.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            initConnections();
        }
    }

    public void initConnections() {
        Block block = level.getBlockState(getBlockPos()).getBlock();
        for (Direction direction : Direction.values()) {
            BlockState facingState = level.getBlockState(getBlockPos().relative(direction));
            BlockEntity facingBE = level.getBlockEntity(getBlockPos().relative(direction));
            if (facingState.is(WizardsRebornBlockTags.STEAM_PIPE_CONNECTION)) {
                if (facingBE instanceof PipeBaseBlockEntity && !((PipeBaseBlockEntity) facingBE).getConnection(direction.getOpposite()).transfer) {
                    connections[direction.get3DDataValue()] = PipeConnection.NONE;
                } else {
                    connections[direction.get3DDataValue()] = PipeConnection.PIPE;
                }
            } else {
                connections[direction.get3DDataValue()] = PipeConnection.NONE;
            }
        }
        loaded = true;
        setChanged();
        level.getChunkAt(getBlockPos()).setUnsaved(true);
        level.updateNeighbourForOutputSignal(getBlockPos(), block);
    }

    @Override
    public int getSteam() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMaxSteam() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void setSteam(int steam) {

    }

    @Override
    public void addSteam(int steam) {

    }

    @Override
    public void removeSteam(int steam) {

    }

    @Override
    public boolean canSteamTransfer(Direction side) {
        return true;
    }

    @Override
    public boolean canSteamConnection(Direction side) {
        return true;
    }
}
