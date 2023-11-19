package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
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
import java.util.Random;

public class FluidPipeTileEntity extends FluidPipeBaseTileEntity {
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

    public FluidPipeTileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public FluidPipeTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.FLUID_PIPE_TILE_ENTITY.get(), pos, state);
    }

    public void tick() {
        super.tick();
        if (level.isClientSide()) {
            if (clogged && isAnySideUnclogged()) {
                Random posRand = new Random(getBlockPos().asLong());
                double angleA = posRand.nextDouble() * Math.PI * 2;
                double angleB = posRand.nextDouble() * Math.PI * 2;
                float xOffset = (float) (Math.cos(angleA) * Math.cos(angleB));
                float yOffset = (float) (Math.sin(angleA) * Math.cos(angleB));
                float zOffset = (float) Math.sin(angleB);
                float speed = 0.03f;
                float vx = xOffset * speed + posRand.nextFloat() * speed * 0.3f;
                float vy = yOffset * speed + posRand.nextFloat() * speed * 0.3f;
                float vz = zOffset * speed + posRand.nextFloat() * speed * 0.3f;
                Particles.create(WizardsReborn.WISP_PARTICLE)
                        .addVelocity(vx, vy, vz)
                        .setAlpha(0.4f, 0).setScale(0.05f, 0.15f)
                        .setColor(1F, 1F, 1F)
                        .setLifetime(20)
                        .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                        .spawn(level, getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5);
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
    public int getFluidAmount() {
        return tank.getFluidAmount();
    }

    @Override
    public int getFluidMaxAmount() {
        return getCapacity();
    }
}
