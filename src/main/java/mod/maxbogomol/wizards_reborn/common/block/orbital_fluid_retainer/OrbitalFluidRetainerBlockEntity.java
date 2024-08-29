package mod.maxbogomol.wizards_reborn.common.block.orbital_fluid_retainer;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipeConnection;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OrbitalFluidRetainerBlockEntity extends PipeBaseBlockEntity implements TickableBlockEntity, IFluidBlockEntity {
    protected FluidTank fluidTank = new FluidTank(getMaxCapacity()) {
        @Override
        public void onContentsChanged() {
            OrbitalFluidRetainerBlockEntity.this.setChanged();
            OrbitalFluidRetainerBlockEntity.this.fluidLastTick = OrbitalFluidRetainerBlockEntity.this.ticksExisted + 1;
        }
    };
    public LazyOptional<IFluidHandler> fluidHolder = LazyOptional.of(() -> fluidTank);

    public static Direction[] directions = {
            Direction.NORTH,
            Direction.SOUTH,
            Direction.WEST,
            Direction.EAST
    };

    public int ticksExisted = 0;
    public int fluidLastAmount = 0;
    public int fluidLastTick = 0;

    public OrbitalFluidRetainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public OrbitalFluidRetainerBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.ORBITAL_FLUID_RETAINER_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            initConnections();
        }

        if (level.isClientSide()) {
            ticksExisted++;

            if (fluidLastAmount != getTank().getFluidAmount()) {
                if (fluidLastTick < ticksExisted) {
                    fluidLastAmount = getTank().getFluidAmount();
                }
            }
        }
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
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("fluidTank", fluidTank.writeToNBT(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        fluidTank.readFromNBT(tag.getCompound("fluidTank"));
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 2f, pos.getZ() + 1.5f);
    }

    public void initConnections() {
        Block block = level.getBlockState(worldPosition).getBlock();
        for (Direction direction : directions) {
            BlockState facingState = level.getBlockState(worldPosition.relative(direction));
            BlockEntity facingBE = level.getBlockEntity(worldPosition.relative(direction));
            if (facingState.is(WizardsReborn.FLUID_PIPE_CONNECTION_BLOCK_TAG)) {
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
        level.getChunkAt(worldPosition).setUnsaved(true);
        level.updateNeighbourForOutputSignal(worldPosition, block);
    }

    public int getMaxCapacity() {
        return 10000;
    }

    public int getCapacity() {
        return fluidTank.getCapacity();
    }

    public FluidStack getFluidStack() {
        return fluidTank.getFluid();
    }

    public FluidTank getTank() {
        return fluidTank;
    }

    @Override
    public int getFluidAmount() {
        return getFluidStack().getAmount();
    }

    @Override
    public int getFluidMaxAmount() {
        return getMaxCapacity();
    }
}
