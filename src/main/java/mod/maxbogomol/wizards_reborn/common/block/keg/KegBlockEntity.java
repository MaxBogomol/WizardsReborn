package mod.maxbogomol.wizards_reborn.common.block.keg;

import mod.maxbogomol.fluffy_fur.common.block.entity.NameableBlockEntityBase;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.client.gui.container.KegContainer;
import mod.maxbogomol.wizards_reborn.common.item.equipment.DrinkBottleItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.RottenDrinkBottleItem;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItemTags;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class KegBlockEntity extends NameableBlockEntityBase implements TickableBlockEntity {
    public final ItemStackHandler itemHandler = createHandler(6);
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public long time = 0;
    public int doorTick = 0;
    public int oldDoorTick = 0;

    public KegBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public KegBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.KEG.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            long newTime = getLevel().getDayTime();
            long timeDifference = newTime - time;

            if (!getBlockState().getValue(BlockStateProperties.OPEN)) {
                if (timeDifference == 0) timeDifference = 1;
                if (timeDifference > 0) {
                    for (int i = 0; i < 6; i += 1) {
                        ItemStack itemStack = itemHandler.getStackInSlot(i);
                        if (itemStack.getItem() instanceof DrinkBottleItem bottle) {
                            DrinkBottleItem.setTicks(itemStack, DrinkBottleItem.getTicks(itemStack) + (int) timeDifference);
                            int ticks = bottle.ticksAged + bottle.ticksAgedOverexpose + bottle.ticksAgedRotten;
                            if (DrinkBottleItem.getTicks(itemStack) >= ticks) {
                                ItemStack rotten = new ItemStack(WizardsRebornItems.ROTTEN_DRINK_BOTTLE.get());
                                RottenDrinkBottleItem.getInventory(rotten).setItem(0, itemStack.copy());
                                itemHandler.setStackInSlot(i, rotten);
                            }
                        }
                    }
                    update = true;
                }
            } else {
                if (KegBlock.isBlockedByBlock(getLevel(), getBlockPos(), getBlockState().getValue(HORIZONTAL_FACING))) {
                    level.playSound(null, getBlockPos(), SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    level.setBlock(getBlockPos(), getBlockState().setValue(BlockStateProperties.OPEN, false), 3);
                }
            }

            if (newTime != time) {
                time = newTime;
                update = true;
            }

            if (update) setChanged();
        }

        if (level.isClientSide()) {
            oldDoorTick = doorTick;
            if (getBlockState().getValue(BlockStateProperties.OPEN)) {
                if (doorTick < 5) doorTick++;
            } else {
                if (doorTick > 0) doorTick--;
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
                return stack.is(WizardsRebornItemTags.KEG_SLOTS);
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
            if (getBlockState().getValue(BlockStateProperties.OPEN)) {
                return handler.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public Component getDefaultName() {
        return getBlockState().getBlock().getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new KegContainer(i, level, getBlockPos(), inventory, player);
    }

    public float getBlockRotate() {
        return switch (this.getBlockState().getValue(HORIZONTAL_FACING)) {
            case NORTH -> 0F;
            case SOUTH -> 180F;
            case WEST -> 90F;
            case EAST -> 270F;
            default -> 0F;
        };
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inv", itemHandler.serializeNBT());

        tag.putLong("time", time);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inv"));

        time = tag.getLong("time");
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 1f, pos.getY() - 1f, pos.getZ() - 1f, pos.getX() + 2f, pos.getY() + 2f, pos.getZ() + 2f);
    }
}