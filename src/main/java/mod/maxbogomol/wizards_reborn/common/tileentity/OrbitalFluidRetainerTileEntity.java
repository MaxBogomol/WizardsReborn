package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.block.PipeConnection;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class OrbitalFluidRetainerTileEntity extends PipeBaseTileEntity implements TickableBlockEntity {
    protected FluidTank fluidTank = new FluidTank(10) {
        @Override
        public void onContentsChanged() {
            OrbitalFluidRetainerTileEntity.this.setChanged();
        }
    };
    public LazyOptional<IFluidHandler> fluidHolder = LazyOptional.of(() -> fluidTank);

    public static Direction[] directions = {
            Direction.NORTH,
            Direction.SOUTH,
            Direction.WEST,
            Direction.EAST
    };

    public Random random = new Random();

    public OrbitalFluidRetainerTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public OrbitalFluidRetainerTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.ORBITAL_FLUID_RETAINER_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        initConnections();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            if (side != Direction.UP && side != Direction.DOWN) {
                return ForgeCapabilities.FLUID_HANDLER.orEmpty(cap, fluidHolder);
            }
        }

        return super.getCapability(cap, side);
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

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("fluidTank", fluidTank.writeToNBT(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        fluidTank.readFromNBT(tag.getCompound("fluidTank"));
    }

    public void initConnections() {
        Block block = level.getBlockState(worldPosition).getBlock();
        for (Direction direction : directions) {
            BlockState facingState = level.getBlockState(worldPosition.relative(direction));
            BlockEntity facingBE = level.getBlockEntity(worldPosition.relative(direction));
            if (facingState.is(WizardsReborn.FLUID_PIPE_CONNECTION_BLOCK_TAG)) {
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
}
