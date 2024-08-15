package mod.maxbogomol.wizards_reborn.common.block.sensor.fluid;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBlockEntity;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class FluidSensorBlockEntity extends SensorBlockEntity {
    protected FluidTank fluidTank = new FluidTank(1) {
        @Override
        public void onContentsChanged() {
            FluidSensorBlockEntity.this.setChanged();
        }
    };
    public LazyOptional<IFluidHandler> fluidHolder = LazyOptional.of(() -> fluidTank);

    public FluidSensorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public FluidSensorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(WizardsReborn.FLUID_SENSOR_TILE_ENTITY.get(), pPos, pBlockState);
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

    public FluidTank getTank() {
        return fluidTank;
    }
}
