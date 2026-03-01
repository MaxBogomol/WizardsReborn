package mod.maxbogomol.wizards_reborn.common.block.sensor.item_sorter;

import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownBlockEntity;
import mod.maxbogomol.wizards_reborn.common.gui.menu.ItemSorterMenu;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemSorterBlockEntity extends SensorBlockEntity implements ICooldownBlockEntity {
    public final ItemStackHandler itemHandler = createHandler(27);
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public int cooldown = 0;

    public ItemSorterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ItemSorterBlockEntity(BlockPos pos, BlockState blockState) {
        this(WizardsRebornBlockEntities.ITEM_SORTER.get(), pos, blockState);
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

                BlockEntity outputBlock = level.getBlockEntity(outputBlockpos);
                BlockEntity inputBlock = level.getBlockEntity(inputBlockpos);

                boolean isTransfer = false;

                if (outputBlock != null && inputBlock != null) {
                    IItemHandler outputHandler = outputBlock.getCapability(ForgeCapabilities.ITEM_HANDLER, outputDirection.getOpposite()).orElse(null);
                    IItemHandler inputHandler = inputBlock.getCapability(ForgeCapabilities.ITEM_HANDLER, inputDirection.getOpposite()).orElse(null);

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
                                        if (outputHandler.isItemValid(ii, inputItemSort) && outputHandler.getStackInSlot(i).getCount() + 1 <= outputHandler.getSlotLimit(i)) {
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

            if (update) setChanged();
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
    public Component getDefaultName() {
        return Component.translatable("gui.wizards_reborn.item_sorter.title");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ItemSorterMenu(i, level, getBlockPos(), inventory, player);
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
