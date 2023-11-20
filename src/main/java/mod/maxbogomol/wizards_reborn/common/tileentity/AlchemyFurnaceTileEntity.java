package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidTileEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.JewelerTableBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.JewelerTableRecipe;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Optional;
import java.util.Random;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class AlchemyFurnaceTileEntity extends BlockEntity implements TickableBlockEntity, IFluidTileEntity, ISteamTileEntity {
    public final ItemStackHandler itemHandler = createHandler(2);
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public final ItemStackHandler itemOutputHandler = createHandler(1);
    public final LazyOptional<IItemHandler> outputHandler = LazyOptional.of(() -> itemOutputHandler);

    protected FluidTank fluidTank = new FluidTank(10000) {
        @Override
        public void onContentsChanged() {
            AlchemyFurnaceTileEntity.this.setChanged();
        }
    };
    public LazyOptional<IFluidHandler> fluidHolder = LazyOptional.of(() -> fluidTank);

    public int wissenInCraft= 0;
    public int wissenIsCraft = 0;
    public boolean startCraft = false;

    public int steam = 0;


    public Random random = new Random();

    public AlchemyFurnaceTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public AlchemyFurnaceTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.ALCHEMY_FURNACE_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {

    }

    private ItemStackHandler createHandler(int size) {
        return new ItemStackHandler(size) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!isItemValid(slot, stack)) {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                CombinedInvWrapper item = new CombinedInvWrapper(itemHandler, itemOutputHandler);
                return LazyOptional.of(() -> item).cast();
            }

            if (side == Direction.DOWN) {
                return outputHandler.cast();
            } else {
                return handler.cast();
            }
        }

        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            if (side == this.getBlockState().getValue(HORIZONTAL_FACING).getOpposite()) {
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

    public boolean isCanCraft(SimpleContainer inv, ItemStack output) {
        if (inv.getItem(2).isEmpty()) {
            return true;
        }

        if ((ItemHandlerHelper.canItemStacksStack(output, inv.getItem(2))) && (inv.getItem(2).getCount() + output.getCount() <= output.getMaxStackSize())) {
            return true;
        }

        return false;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inv", itemHandler.serializeNBT());
        tag.put("output", itemOutputHandler.serializeNBT());

        tag.putInt("wissenInCraft", wissenInCraft);
        tag.putInt("wissenIsCraft", wissenIsCraft);
        tag.putBoolean("startCraft", startCraft);

        tag.putInt("steam", steam);

        tag.put("fluidTank", fluidTank.writeToNBT(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        itemOutputHandler.deserializeNBT(tag.getCompound("output"));

        wissenInCraft = tag.getInt("wissenInCraft");
        wissenIsCraft = tag.getInt("wissenIsCraft");
        startCraft = tag.getBoolean("startCraft");

        steam = tag.getInt("steam");

        fluidTank.readFromNBT(tag.getCompound("fluidTank"));
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

    @Override
    public int getSteam() {
        return steam;
    }

    @Override
    public int getMaxSteam() {
        return 350;
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
        return (side == Direction.UP);
    }
}
