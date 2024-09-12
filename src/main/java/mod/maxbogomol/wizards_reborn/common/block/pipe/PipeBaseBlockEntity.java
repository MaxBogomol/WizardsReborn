package mod.maxbogomol.wizards_reborn.common.block.pipe;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.BlockEntityBase;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.api.alchemy.IPipeConnection;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipeConnection;
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

import java.awt.*;
import java.util.Random;

public class PipeBaseBlockEntity extends BlockEntityBase {
    public PipeConnection[] connections = {
            PipeConnection.NONE,
            PipeConnection.NONE,
            PipeConnection.NONE,
            PipeConnection.NONE,
            PipeConnection.NONE,
            PipeConnection.NONE
    };

    public boolean loaded = false;

    public static final ModelProperty<int[]> DATA_TYPE = new ModelProperty<int[]>();

    public PipeBaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public void initConnections() {
        Block block = level.getBlockState(getBlockPos()).getBlock();
        for (Direction direction : Direction.values()) {
            if (block instanceof PipeBaseBlock pipeBlock) {
                BlockState facingState = level.getBlockState(getBlockPos().relative(direction));
                BlockEntity facingBE = level.getBlockEntity(getBlockPos().relative(direction));
                if (!(facingBE instanceof PipeBaseBlockEntity) || ((PipeBaseBlockEntity) facingBE).getConnection(direction.getOpposite()) != PipeConnection.DISABLED) {
                    if (facingState.is(pipeBlock.getConnectionTag())) {
                        if (facingBE instanceof PipeBaseBlockEntity && ((PipeBaseBlockEntity) facingBE).getConnection(direction.getOpposite()) == PipeConnection.DISABLED) {
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
        level.getChunkAt(getBlockPos()).setUnsaved(true);
        level.updateNeighbourForOutputSignal(getBlockPos(), block);
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
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        if (level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
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

    public void cloggedEffect() {
        Random posRand = new Random(getBlockPos().asLong());
        double angleA = posRand.nextDouble() * Math.PI * 2;
        double angleB = posRand.nextDouble() * Math.PI * 2;
        float xOffset = (float) (Math.cos(angleA) * Math.cos(angleB));
        float yOffset = (float) (Math.sin(angleA) * Math.cos(angleB));
        float zOffset = (float) Math.sin(angleB);
        float speed = 0.02f;
        float vx = xOffset * speed + posRand.nextFloat() * speed * 0.3f;
        float vy = yOffset * speed + posRand.nextFloat() * speed * 0.3f;
        float vz = zOffset * speed + posRand.nextFloat() * speed * 0.3f;
        ParticleBuilder.create(FluffyFurParticles.SMOKE)
                .setColorData(ColorParticleData.create(Color.WHITE).build())
                .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.15f).build())
                .setSpinData(SpinParticleData.create(-0.1f).randomSpin(0.01f).build())
                .setLifetime(30)
                .addVelocity(vx, vy, vz)
                .spawn(level, getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5);
    }
}
