package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamTileEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipeConnection;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class CreativeSteamStorageTileEntity extends PipeBaseTileEntity implements TickableBlockEntity, ISteamTileEntity {
    public Random random = new Random();

    public CreativeSteamStorageTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public CreativeSteamStorageTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.CREATIVE_STEAM_STORAGE_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            initConnections();
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, (e) -> e.getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    @NotNull
    @Override
    public final CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide) {
            PacketUtils.SUpdateTileEntityPacket(this);
        }
    }

    public void initConnections() {
        Block block = level.getBlockState(worldPosition).getBlock();
        for (Direction direction : Direction.values()) {
            BlockState facingState = level.getBlockState(worldPosition.relative(direction));
            BlockEntity facingBE = level.getBlockEntity(worldPosition.relative(direction));
            if (facingState.is(WizardsReborn.STEAM_PIPE_CONNECTION_BLOCK_TAG)) {
                if (facingBE instanceof PipeBaseTileEntity && !((PipeBaseTileEntity) facingBE).getConnection(direction.getOpposite()).transfer) {
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
        level.getChunkAt(worldPosition).setUnsaved(true);
        level.updateNeighbourForOutputSignal(worldPosition, block);
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
