package mod.maxbogomol.wizards_reborn.common.block.fluid_pipe;

import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FluidPipeBlockEntity extends FluidPipeBaseBlockEntity {
    IFluidHandler[] sideHandlers;

    @Override
    protected void initFluidTank() {
        super.initFluidTank();
        sideHandlers = new IFluidHandler[Direction.values().length];
        for (Direction facing : Direction.values()) {
            sideHandlers[facing.get3DDataValue()] = new IFluidHandler() {

                @Override
                public int fill(FluidStack resource, FluidAction action) {
                    if(action.execute())
                        setFrom(facing, true);
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

    public FluidPipeBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public FluidPipeBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.FLUID_PIPE.get(), pos, state);
    }

    public void tick() {
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
