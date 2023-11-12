package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class SteamPipeTileEntity extends PipeBaseTileEntity implements TickableBlockEntity {
    public FluidTank tank;
    public LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

    protected void initFluidTank() {
        tank = new FluidTank(10) {
        };
    }

    public SteamPipeTileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        initFluidTank();
    }

    public SteamPipeTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.STEAM_PIPE_TILE_ENTITY.get(), pos, state);
        initFluidTank();
    }

    @Override
    public void tick() {
        if (!loaded) {
            initConnections();
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (!this.remove && cap == ForgeCapabilities.FLUID_HANDLER) {
            return ForgeCapabilities.FLUID_HANDLER.orEmpty(cap, holder);
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide) {
            PacketUtils.SUpdateTileEntityPacket(this);
        }
    }
}
