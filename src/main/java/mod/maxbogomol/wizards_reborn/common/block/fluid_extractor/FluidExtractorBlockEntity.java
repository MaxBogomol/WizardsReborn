package mod.maxbogomol.wizards_reborn.common.block.fluid_extractor;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.block.fluid_pipe.FluidPipeBaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FluidExtractorBlockEntity extends FluidPipeBaseBlockEntity {
    IFluidHandler[] sideHandlers;
    boolean active;
    public static final int MAX_DRAIN = 150;

    public FluidExtractorBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public FluidExtractorBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.FLUID_EXTRACTOR_TILE_ENTITY.get(), pos, state);
    }

    @Override
    protected void initFluidTank() {
        super.initFluidTank();
        sideHandlers = new IFluidHandler[Direction.values().length];
        for (Direction facing : Direction.values()) {
            sideHandlers[facing.get3DDataValue()] = new IFluidHandler() {

                @Override
                public int fill(FluidStack resource, FluidAction action) {
                    if (active)
                        return 0;
                    if (action.execute())
                        setFrom(facing,true);
                    return tank.fill(resource, action);
                }

                @Nullable
                @Override
                public FluidStack drain(FluidStack resource, FluidAction action) {
                    return tank.drain(resource, action);
                }

                @Nullable
                @Override
                public FluidStack drain(int maxDrain, FluidAction action) {
                    return tank.drain(maxDrain, action);
                }

                @Override
                public int getTanks() {
                    return tank.getTanks();
                }

                @Override
                public @NotNull FluidStack getFluidInTank(int tankNum) {
                    return tank.getFluidInTank(tankNum);
                }

                @Override
                public int getTankCapacity(int tankNum) {
                    return tank.getTankCapacity(tankNum);
                }

                @Override
                public boolean isFluidValid(int tankNum, @NotNull FluidStack stack) {
                    return tank.isFluidValid(tankNum, stack);
                }
            };
        }
    }

    public void tick() {
        if (!level.isClientSide()) {
            active = (level.hasNeighborSignal(getBlockPos()) != level.getBlockState(getBlockPos()).getValue(BlockStateProperties.POWERED));
            for (Direction facing : Direction.values()) {
                if (!getConnection(facing).transfer)
                    continue;
                BlockEntity tile = level.getBlockEntity(getBlockPos().relative(facing));
                if (tile != null && !(tile instanceof FluidPipeBaseBlockEntity)) {
                    if (active) {
                        IFluidHandler handler = tile.getCapability(ForgeCapabilities.FLUID_HANDLER, facing.getOpposite()).orElse(null);
                        if (handler != null && handler.drain(MAX_DRAIN, IFluidHandler.FluidAction.SIMULATE) != null) {
                            FluidStack extracted = handler.drain(MAX_DRAIN, IFluidHandler.FluidAction.SIMULATE);
                            int filled = tank.fill(extracted, IFluidHandler.FluidAction.SIMULATE);
                            if (filled > 0) {
                                tank.fill(extracted, IFluidHandler.FluidAction.EXECUTE);
                                handler.drain(filled, IFluidHandler.FluidAction.EXECUTE);
                            }
                        }
                        setFrom(facing, true);
                    } else {
                        setFrom(facing, false);
                    }
                }
            }
        }
        super.tick();

        if (level.isClientSide()) {
            if (clogged && isAnySideUnclogged()) {
                cloggedEffect();
            }
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (!this.remove && cap == ForgeCapabilities.FLUID_HANDLER) {
            if (side == null)
                return ForgeCapabilities.FLUID_HANDLER.orEmpty(cap, holder);
            else if (getConnection(side).transfer)
                return ForgeCapabilities.FLUID_HANDLER.orEmpty(cap, LazyOptional.of(() -> this.sideHandlers[side.get3DDataValue()]));
        }
        return super.getCapability(cap, side);
    }

    @Override
    public int getCapacity() {
        return 350;
    }

    @Override
    public FluidStack getFluidStack() {
        return tank.getFluid();
    }

    @Override
    public int getFluidAmount() {
        return tank.getFluidAmount();
    }

    @Override
    public int getFluidMaxAmount() {
        return getCapacity();
    }
}
