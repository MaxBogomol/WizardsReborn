package mod.maxbogomol.wizards_reborn.common.block.alchemy_boiler;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipeConnection;
import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IItemResultBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_machine.AlchemyMachineBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
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
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class AlchemyBoilerBlockEntity extends PipeBaseBlockEntity implements TickableBlockEntity, IFluidBlockEntity, ISteamBlockEntity, IWissenBlockEntity, IWissenWandFunctionalBlockEntity, ICooldownBlockEntity, IItemResultBlockEntity {
    protected FluidTank fluidTank = new FluidTank(getMaxCapacity()) {
        @Override
        public void onContentsChanged() {
            AlchemyBoilerBlockEntity.this.setChanged();
        }
    };
    public LazyOptional<IFluidHandler> fluidHolder = LazyOptional.of(() -> fluidTank);

    public static Direction[] directions = {
            Direction.NORTH,
            Direction.SOUTH,
            Direction.WEST,
            Direction.EAST
    };

    public int steam = 0;
    public int wissen = 0;

    public AlchemyBoilerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public AlchemyBoilerBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.ALCHEMY_BOILER.get(), pos, state);
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
            if (side != Direction.UP && side != Direction.DOWN && side == getBlockState().getValue(HORIZONTAL_FACING)) {
                return ForgeCapabilities.FLUID_HANDLER.orEmpty(cap, fluidHolder);
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("fluidTank", fluidTank.writeToNBT(new CompoundTag()));
        tag.putInt("steam", steam);
        tag.putInt("wissen", wissen);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        fluidTank.readFromNBT(tag.getCompound("fluidTank"));
        steam = tag.getInt("steam");
        wissen = tag.getInt("wissen");
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 1.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 1.5f, pos.getZ() + 1.5f);
    }

    public void initConnections() {
        Block block = level.getBlockState(getBlockPos()).getBlock();
        for (Direction direction : directions) {
            BlockState facingState = level.getBlockState(getBlockPos().relative(direction));
            BlockEntity facingBE = level.getBlockEntity(getBlockPos().relative(direction));
            if (facingState.is(WizardsRebornBlockTags.STEAM_PIPE_CONNECTION) || facingState.is(WizardsRebornBlockTags.FLUID_PIPE_CONNECTION)) {
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
        level.getChunkAt(getBlockPos()).setUnsaved(true);
        level.updateNeighbourForOutputSignal(getBlockPos(), block);
    }

    public int getMaxCapacity() {
        return 5000;
    }

    public int getCapacity() {
        return fluidTank.getCapacity();
    }

    @Override
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

    @Override
    public int getSteam() {
        return steam;
    }

    @Override
    public int getMaxSteam() {
        return 5000;
    }

    @Override
    public void setSteam(int steam) {
        this.steam = steam;
    }

    @Override
    public void addSteam(int steam) {
        this.steam = this.steam + steam;
        if (this.steam > getMaxSteam()) {
            this.steam = getMaxSteam();
        }
    }

    @Override
    public void removeSteam(int steam) {
        this.steam = this.steam - steam;
        if (this.steam < 0) {
            this.steam = 0;
        }
    }

    @Override
    public boolean canSteamTransfer(Direction side) {
        return true;
    }

    @Override
    public boolean canSteamConnection(Direction side) {
        return (side == getBlockState().getValue(HORIZONTAL_FACING).getOpposite());
    }

    @Override
    public int getWissen() {
        return wissen;
    }

    @Override
    public int getMaxWissen() {
        return 5000;
    }

    @Override
    public boolean canSendWissen() {
        return true;
    }

    @Override
    public boolean canReceiveWissen() {
        return true;
    }

    @Override
    public boolean canConnectSendWissen() {
        return true;
    }

    @Override
    public boolean canConnectReceiveWissen() {
        return true;
    }

    @Override
    public void setWissen(int wissen) {
        this.wissen = wissen;
    }

    @Override
    public void addWissen(int wissen) {
        this.wissen = this.wissen + wissen;
        if (this.wissen > getMaxWissen()) {
            this.wissen = getMaxWissen();
        }
    }

    @Override
    public void removeWissen(int wissen) {
        this.wissen = this.wissen - wissen;
        if (this.wissen < 0) {
            this.wissen = 0;
        }
    }

    @Override
    public void wissenWandFunction() {
        if (level.getBlockEntity(getBlockPos().below()) instanceof AlchemyMachineBlockEntity machine) {
            machine.startCraft = true;
        }
    }

    @Override
    public List<ItemStack> getItemsResult() {
        List<ItemStack> list = new ArrayList<>();
        if (level.getBlockEntity(getBlockPos().below()) instanceof AlchemyMachineBlockEntity machine) {
            return machine.getItemsResult();
        }

        return list;
    }

    @Override
    public float getCooldown() {
        if (level.getBlockEntity(getBlockPos().below()) instanceof AlchemyMachineBlockEntity machine) {
            return machine.getCooldown();
        }
        return 0;
    }
}
