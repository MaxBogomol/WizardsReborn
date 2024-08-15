package mod.maxbogomol.wizards_reborn.common.block.sensor.item_sorter;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownTileEntity;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBlockEntity;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemSorterBlockEntity extends SensorBlockEntity implements ICooldownTileEntity {
    public final ItemStackHandler itemHandler = createHandler(27);
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public int cooldown = 0;

    public ItemSorterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ItemSorterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(WizardsReborn.ITEM_SORTER_TILE_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;
            boolean active = (!level.hasNeighborSignal(getBlockPos()));

            if (cooldown > 0) {
                cooldown = cooldown - 1;
                update = true;
            }

            if (active && cooldown == 0) {
                Direction outputDirection = getBlockState().getValue(SensorBaseBlock.FACING);
                BlockPos outputBlockpos = getBlockPos().relative(outputDirection);

                Direction inputDirection = getBlockState().getValue(SensorBaseBlock.FACING).getOpposite();
                BlockPos inputBlockpos = getBlockPos().relative(inputDirection);

                switch (getBlockState().getValue(SensorBaseBlock.FACE)) {
                    case FLOOR:
                        outputBlockpos = getBlockPos().above();
                        inputBlockpos = getBlockPos().below();
                        outputDirection = Direction.UP;
                        inputDirection = Direction.DOWN;
                        break;
                    case WALL:
                        break;
                    case CEILING:
                        outputBlockpos = getBlockPos().below();
                        inputBlockpos = getBlockPos().above();
                        outputDirection = Direction.DOWN;
                        inputDirection = Direction.UP;
                        break;
                }

                BlockEntity outputTile = level.getBlockEntity(outputBlockpos);
                BlockEntity inputTile = level.getBlockEntity(inputBlockpos);

                boolean isTransfer = false;

                if (outputTile != null && inputTile != null) {
                    IItemHandler outputHandler = outputTile.getCapability(ForgeCapabilities.ITEM_HANDLER, outputDirection).orElse(null);
                    IItemHandler inputHandler = inputTile.getCapability(ForgeCapabilities.ITEM_HANDLER, inputDirection).orElse(null);

                    if (outputHandler != null && inputHandler != null) {
                        for (int i = 0; i < itemHandler.getSlots(); i++) {
                            if (outputHandler.getSlots() <= i) {
                                break;
                            }

                            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                                ItemStack itemSort = itemHandler.getStackInSlot(i);
                                for (int ii = 0; ii < inputHandler.getSlots(); ii++) {
                                    ItemStack inputItemSort = inputHandler.extractItem(ii, 1, true);

                                    if (inputItemSort.equals(itemSort, false)) {
                                        if (outputHandler.getStackInSlot(i).getCount() + 1 <= outputHandler.getSlotLimit(i)) {
                                            inputHandler.extractItem(ii, 1, false);
                                            outputHandler.insertItem(i, inputItemSort, false);
                                            isTransfer = true;

                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (isTransfer) {
                    update = true;
                    cooldown = getMaxCooldown();
                }
            }

            if (update) {
                PacketUtils.SUpdateTileEntityPacket(this);
            }
        }
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
                return 1;
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
            return handler.cast();
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

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inv", itemHandler.serializeNBT());
        tag.putInt("cooldown", cooldown);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        cooldown = tag.getInt("cooldown");
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 1.5f, pos.getY() - 1.5f, pos.getZ() - 1.5f, pos.getX() + 2.5f, pos.getY() + 2.5f, pos.getZ() + 2.5f);
    }

    public int getMaxCooldown() {
        return 20;
    }

    @Override
    public float getCooldown() {
        if (cooldown > 0) {
            return (float) getMaxCooldown() / cooldown;
        }
        return 0;
    }
}
