package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.api.alchemy.IPipeConnection;
import mod.maxbogomol.wizards_reborn.common.block.PipeBaseBlock;
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
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public class PipeBaseTileEntity extends BlockEntity {
    public PipeConnection[] connections = {
            PipeConnection.NONE,
            PipeConnection.NONE,
            PipeConnection.NONE,
            PipeConnection.NONE,
            PipeConnection.NONE,
            PipeConnection.NONE
    };

    boolean loaded = false;

    public static final ModelProperty<int[]> DATA_TYPE = new ModelProperty<int[]>();

    public PipeBaseTileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public void initConnections() {
        Block block = level.getBlockState(worldPosition).getBlock();
        for (Direction direction : Direction.values()) {
            if (block instanceof PipeBaseBlock pipeBlock) {
                BlockState facingState = level.getBlockState(worldPosition.relative(direction));
                BlockEntity facingBE = level.getBlockEntity(worldPosition.relative(direction));
                if (!(facingBE instanceof PipeBaseTileEntity) || ((PipeBaseTileEntity) facingBE).getConnection(direction.getOpposite()) != PipeConnection.DISABLED) {
                    if (facingState.is(pipeBlock.getConnectionTag())) {
                        if (facingBE instanceof PipeBaseTileEntity && ((PipeBaseTileEntity) facingBE).getConnection(direction.getOpposite()) == PipeConnection.DISABLED) {
                            connections[direction.get3DDataValue()] = PipeConnection.DISABLED;
                        } else {
                            connections[direction.get3DDataValue()] = PipeConnection.PIPE;
                        }
                    } else {
                        if (pipeBlock.connected(direction, facingState)) {
                            connections[direction.get3DDataValue()] = PipeConnection.LEVER;
                        } else if (pipeBlock.connectToTile(facingBE, direction)) {
                            if (facingState.getBlock() instanceof IPipeConnection) {
                                connections[direction.get3DDataValue()] = ((IPipeConnection) facingState.getBlock()).getPipeConnection(facingState, direction.getOpposite());
                            } else {
                                connections[direction.get3DDataValue()] = PipeConnection.END;
                            }
                        } else {
                            connections[direction.get3DDataValue()] = PipeConnection.NONE;
                        }
                    }
                }
            }
        }
        loaded = true;
        setChanged();
        level.getChunkAt(worldPosition).setUnsaved(true);
        level.updateNeighbourForOutputSignal(worldPosition, block);
    }

    @Override
    public ModelData getModelData() {
        int[] data = {
                connections[0].visualIndex,
                connections[1].visualIndex,
                connections[2].visualIndex,
                connections[3].visualIndex,
                connections[4].visualIndex,
                connections[5].visualIndex
        };
        return ModelData.builder().with(DATA_TYPE, data).build();
    }

    public void setConnection(Direction direction, PipeConnection connection) {
        connections[direction.get3DDataValue()] = connection;
        requestModelDataUpdate();
        setChanged();
    }

    public PipeConnection getConnection(Direction direction) {
        return connections[direction.get3DDataValue()];
    }

    public void setConnections(PipeConnection[] connections) {
        this.connections = connections;
        requestModelDataUpdate();
        setChanged();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, (e) -> e.getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
        if (level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        loadConnections(nbt);
        loaded = true;
    }

    public void loadConnections(CompoundTag nbt) {
        for (Direction direction : Direction.values()) {
            if (nbt.contains("connection" + direction.get3DDataValue()))
                connections[direction.get3DDataValue()] = PipeConnection.values()[nbt.getInt("connection" + direction.get3DDataValue())];
        }
        requestModelDataUpdate();
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        writeConnections(nbt);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        writeConnections(nbt);
        return nbt;
    }

    public void writeConnections(CompoundTag nbt) {
        for (Direction direction : Direction.values()) {
            nbt.putInt("connection" + direction.get3DDataValue(), getConnection(direction).index);
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide) {
            PacketUtils.SUpdateTileEntityPacket(this);
        }
    }
}
