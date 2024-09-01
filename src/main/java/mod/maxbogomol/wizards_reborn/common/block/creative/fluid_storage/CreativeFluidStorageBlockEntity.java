package mod.maxbogomol.wizards_reborn.common.block.creative.fluid_storage;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipeConnection;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CreativeFluidStorageBlockEntity extends PipeBaseBlockEntity implements TickableBlockEntity, IFluidBlockEntity {
    protected FluidTank fluidTank = new FluidTank(getMaxCapacity()) {
        @Override
        public void onContentsChanged() {
            CreativeFluidStorageBlockEntity.this.full();
            CreativeFluidStorageBlockEntity.this.setChanged();
        }
    };
    public LazyOptional<IFluidHandler> fluidHolder = LazyOptional.of(() -> fluidTank);

    public CreativeFluidStorageBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public CreativeFluidStorageBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.CREATIVE_FLUID_STORAGE.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            initConnections();
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return ForgeCapabilities.FLUID_HANDLER.orEmpty(cap, fluidHolder);
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

    public void initConnections() {
        Block block = level.getBlockState(worldPosition).getBlock();
        for (Direction direction : Direction.values()) {
            BlockState facingState = level.getBlockState(worldPosition.relative(direction));
            BlockEntity facingBE = level.getBlockEntity(worldPosition.relative(direction));
            if (facingState.is(WizardsRebornTags.FLUID_PIPE_CONNECTION_BLOCK)) {
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
        return Integer.MAX_VALUE;
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

    public void full() {
        getFluidStack().setAmount(Integer.MAX_VALUE);
        fluidTank.setFluid(getFluidStack());
    }
}
