package mod.maxbogomol.wizards_reborn.common.block.arcane_hopper;

import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArcaneHopperBlockEntity extends RandomizableContainerBlockEntity implements Hopper {
    public static final int MOVE_ITEM_SPEED = 4;
    public static final int HOPPER_CONTAINER_SIZE = 7;
    private NonNullList<ItemStack> items = NonNullList.withSize(getHopperContainerSize(), ItemStack.EMPTY);
    private int cooldownTime = -1;
    private long tickedGameTime;

    public static int getMoveItemSpeed() {
        return MOVE_ITEM_SPEED;
    }

    public static int getHopperContainerSize() {
        return HOPPER_CONTAINER_SIZE;
    }

    public ArcaneHopperBlockEntity(BlockPos pos, BlockState blockState) {
        super(WizardsRebornBlockEntities.ARCANE_HOPPER.get(), pos, blockState);
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(pTag)) {
            ContainerHelper.loadAllItems(pTag, this.items);
        }

        this.cooldownTime = pTag.getInt("TransferCooldown");
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (!this.trySaveLootTable(pTag)) {
            ContainerHelper.saveAllItems(pTag, this.items);
        }

        pTag.putInt("TransferCooldown", this.cooldownTime);
    }

    public int getContainerSize() {
        return this.items.size();
    }

    public ItemStack removeItem(int pIndex, int pCount) {
        this.unpackLootTable((Player)null);
        return ContainerHelper.removeItem(this.getItems(), pIndex, pCount);
    }

    public void setItem(int pIndex, ItemStack stack) {
        this.unpackLootTable((Player)null);
        this.getItems().set(pIndex, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
    }

    protected Component getDefaultName() {
        return Component.translatable("container.hopper");
    }

    public static void pushItemsTick(Level level, BlockPos pos, BlockState state, ArcaneHopperBlockEntity pBlockEntity) {
        --pBlockEntity.cooldownTime;
        pBlockEntity.tickedGameTime = level.getGameTime();
        if (!pBlockEntity.isOnCooldown()) {
            pBlockEntity.setCooldown(0);
            tryMoveItems(level, pos, state, pBlockEntity, () -> {
                return suckInItems(level, pBlockEntity);
            });
        }
    }

    private static boolean tryMoveItems(Level level, BlockPos pos, BlockState state, ArcaneHopperBlockEntity pBlockEntity, BooleanSupplier pValidator) {
        if (level.isClientSide) {
            return false;
        } else {
            if (!pBlockEntity.isOnCooldown() && state.getValue(HopperBlock.ENABLED)) {
                boolean flag = false;
                if (!pBlockEntity.isEmpty()) {
                    flag = ejectItems(level, pos, state, pBlockEntity);
                }

                if (!pBlockEntity.inventoryFull()) {
                    flag |= pValidator.getAsBoolean();
                }

                if (flag) {
                    pBlockEntity.setCooldown(getMoveItemSpeed());
                    setChanged(level, pos, state);
                    return true;
                }
            }

            return false;
        }
    }

    private boolean inventoryFull() {
        for(ItemStack itemstack : this.items) {
            if (itemstack.isEmpty() || itemstack.getCount() != itemstack.getMaxStackSize()) {
                return false;
            }
        }

        return true;
    }

    private static boolean ejectItems(Level level, BlockPos pos, BlockState state, ArcaneHopperBlockEntity pSourceContainer) {
        if (ArcaneHopperInventoryCodeHooks.insertHook(pSourceContainer)) return true;
        Container container = getAttachedContainer(level, pos, state);
        if (container == null) {
            return false;
        } else {
            Direction direction = state.getValue(HopperBlock.FACING).getOpposite();
            if (isFullContainer(container, direction)) {
                return false;
            } else {
                for(int i = 0; i < pSourceContainer.getContainerSize(); ++i) {
                    if (!pSourceContainer.getItem(i).isEmpty()) {
                        ItemStack itemstack = pSourceContainer.getItem(i).copy();
                        ItemStack itemstack1 = addItem(pSourceContainer, container, pSourceContainer.removeItem(i, 1), direction);
                        if (itemstack1.isEmpty()) {
                            container.setChanged();
                            return true;
                        }

                        pSourceContainer.setItem(i, itemstack);
                    }
                }

                return false;
            }
        }
    }

    private static IntStream getSlots(Container pContainer, Direction direction) {
        return pContainer instanceof WorldlyContainer ? IntStream.of(((WorldlyContainer)pContainer).getSlotsForFace(direction)) : IntStream.range(0, pContainer.getContainerSize());
    }

    private static boolean isFullContainer(Container pContainer, Direction direction) {
        return getSlots(pContainer, direction).allMatch((p_59379_) -> {
            ItemStack itemstack = pContainer.getItem(p_59379_);
            return itemstack.getCount() >= itemstack.getMaxStackSize();
        });
    }

    private static boolean isEmptyContainer(Container pContainer, Direction direction) {
        return getSlots(pContainer, direction).allMatch((p_59319_) -> {
            return pContainer.getItem(p_59319_).isEmpty();
        });
    }

    public static boolean suckInItems(Level level, Hopper pHopper) {
        Boolean ret = net.minecraftforge.items.VanillaInventoryCodeHooks.extractHook(level, pHopper);
        if (ret != null) return ret;
        Container container = getSourceContainer(level, pHopper);
        if (container != null) {
            Direction direction = Direction.DOWN;
            return isEmptyContainer(container, direction) ? false : getSlots(container, direction).anyMatch((p_59363_) -> {
                return tryTakeInItemFromSlot(pHopper, container, p_59363_, direction);
            });
        } else {
            for(ItemEntity itementity : getItemsAtAndAbove(level, pHopper)) {
                if (addItem(pHopper, itementity)) {
                    return true;
                }
            }

            return false;
        }
    }

    private static boolean tryTakeInItemFromSlot(Hopper pHopper, Container pContainer, int pSlot, Direction direction) {
        ItemStack itemstack = pContainer.getItem(pSlot);
        if (!itemstack.isEmpty() && canTakeItemFromContainer(pHopper, pContainer, itemstack, pSlot, direction)) {
            ItemStack itemstack1 = itemstack.copy();
            ItemStack itemstack2 = addItem(pContainer, pHopper, pContainer.removeItem(pSlot, 1), (Direction)null);
            if (itemstack2.isEmpty()) {
                pContainer.setChanged();
                return true;
            }

            pContainer.setItem(pSlot, itemstack1);
        }

        return false;
    }

    public static boolean addItem(Container pContainer, ItemEntity pItem) {
        boolean flag = false;
        ItemStack itemstack = pItem.getItem().copy();
        ItemStack itemstack1 = addItem((Container)null, pContainer, itemstack, (Direction)null);
        if (itemstack1.isEmpty()) {
            flag = true;
            pItem.discard();
        } else {
            pItem.setItem(itemstack1);
        }

        return flag;
    }

    public static ItemStack addItem(@Nullable Container pSource, Container pDestination, ItemStack stack, @Nullable Direction direction) {
        if (pDestination instanceof WorldlyContainer worldlycontainer) {
            if (direction != null) {
                int[] aint = worldlycontainer.getSlotsForFace(direction);

                for(int k = 0; k < aint.length && !stack.isEmpty(); ++k) {
                    stack = tryMoveInItem(pSource, pDestination, stack, aint[k], direction);
                }

                return stack;
            }
        }

        int i = pDestination.getContainerSize();

        for(int j = 0; j < i && !stack.isEmpty(); ++j) {
            stack = tryMoveInItem(pSource, pDestination, stack, j, direction);
        }

        return stack;
    }

    private static boolean canPlaceItemInContainer(Container pContainer, ItemStack stack, int pSlot, @Nullable Direction direction) {
        if (!pContainer.canPlaceItem(pSlot, stack)) {
            return false;
        } else {
            if (pContainer instanceof WorldlyContainer) {
                WorldlyContainer worldlycontainer = (WorldlyContainer)pContainer;
                if (!worldlycontainer.canPlaceItemThroughFace(pSlot, stack, direction)) {
                    return false;
                }
            }

            return true;
        }
    }

    private static boolean canTakeItemFromContainer(Container pSource, Container pDestination, ItemStack stack, int pSlot, Direction direction) {
        if (!pDestination.canTakeItem(pSource, pSlot, stack)) {
            return false;
        } else {
            if (pDestination instanceof WorldlyContainer) {
                WorldlyContainer worldlycontainer = (WorldlyContainer)pDestination;
                if (!worldlycontainer.canTakeItemThroughFace(pSlot, stack, direction)) {
                    return false;
                }
            }

            return true;
        }
    }

    private static ItemStack tryMoveInItem(@Nullable Container pSource, Container pDestination, ItemStack stack, int pSlot, @Nullable Direction direction) {
        ItemStack itemstack = pDestination.getItem(pSlot);
        if (canPlaceItemInContainer(pDestination, stack, pSlot, direction)) {
            boolean flag = false;
            boolean flag1 = pDestination.isEmpty();
            if (itemstack.isEmpty()) {
                pDestination.setItem(pSlot, stack);
                stack = ItemStack.EMPTY;
                flag = true;
            } else if (canMergeItems(itemstack, stack)) {
                int i = stack.getMaxStackSize() - itemstack.getCount();
                int j = Math.min(stack.getCount(), i);
                stack.shrink(j);
                itemstack.grow(j);
                flag = j > 0;
            }

            if (flag) {
                if (flag1 && pDestination instanceof ArcaneHopperBlockEntity) {
                    ArcaneHopperBlockEntity hopperblockentity1 = (ArcaneHopperBlockEntity)pDestination;
                    if (!hopperblockentity1.isOnCustomCooldown()) {
                        int k = 0;
                        if (pSource instanceof ArcaneHopperBlockEntity) {
                            ArcaneHopperBlockEntity hopperblockentity = (ArcaneHopperBlockEntity)pSource;
                            if (hopperblockentity1.tickedGameTime >= hopperblockentity.tickedGameTime) {
                                k = 1;
                            }
                        }

                        hopperblockentity1.setCooldown(getMoveItemSpeed() - k);
                    }
                }

                pDestination.setChanged();
            }
        }

        return stack;
    }

    @Nullable
    private static Container getAttachedContainer(Level level, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(HopperBlock.FACING);
        return getContainerAt(level, pos.relative(direction));
    }

    @Nullable
    private static Container getSourceContainer(Level level, Hopper pHopper) {
        return getContainerAt(level, pHopper.getLevelX(), pHopper.getLevelY() + 1.0D, pHopper.getLevelZ());
    }

    public static List<ItemEntity> getItemsAtAndAbove(Level level, Hopper pHopper) {
        return pHopper.getSuckShape().toAabbs().stream().flatMap((p_155558_) -> {
            return level.getEntitiesOfClass(ItemEntity.class, p_155558_.move(pHopper.getLevelX() - 0.5D, pHopper.getLevelY() - 0.5D, pHopper.getLevelZ() - 0.5D), EntitySelector.ENTITY_STILL_ALIVE).stream();
        }).collect(Collectors.toList());
    }

    @Nullable
    public static Container getContainerAt(Level level, BlockPos pos) {
        return getContainerAt(level, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D);
    }

    @Nullable
    private static Container getContainerAt(Level level, double pX, double pY, double pZ) {
        Container container = null;
        BlockPos blockpos = BlockPos.containing(pX, pY, pZ);
        BlockState blockstate = level.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (block instanceof WorldlyContainerHolder) {
            container = ((WorldlyContainerHolder)block).getContainer(blockstate, level, blockpos);
        } else if (blockstate.hasBlockEntity()) {
            BlockEntity blockentity = level.getBlockEntity(blockpos);
            if (blockentity instanceof Container) {
                container = (Container)blockentity;
                if (container instanceof ChestBlockEntity && block instanceof ChestBlock) {
                    container = ChestBlock.getContainer((ChestBlock)block, blockstate, level, blockpos, true);
                }
            }
        }

        if (container == null) {
            List<Entity> list = level.getEntities((Entity)null, new AABB(pX - 0.5D, pY - 0.5D, pZ - 0.5D, pX + 0.5D, pY + 0.5D, pZ + 0.5D), EntitySelector.CONTAINER_ENTITY_SELECTOR);
            if (!list.isEmpty()) {
                container = (Container)list.get(level.random.nextInt(list.size()));
            }
        }

        return container;
    }

    private static boolean canMergeItems(ItemStack stack1, ItemStack stack2) {
        return stack1.getCount() <= stack1.getMaxStackSize() && ItemStack.isSameItemSameTags(stack1, stack2);
    }

    public double getLevelX() {
        return (double)this.worldPosition.getX() + 0.5D;
    }

    public double getLevelY() {
        return (double)this.worldPosition.getY() + 0.5D;
    }

    public double getLevelZ() {
        return (double)this.worldPosition.getZ() + 0.5D;
    }

    public void setCooldown(int pCooldownTime) {
        this.cooldownTime = pCooldownTime;
    }

    private boolean isOnCooldown() {
        return this.cooldownTime > 0;
    }

    public boolean isOnCustomCooldown() {
        return this.cooldownTime > getMoveItemSpeed();
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> pItems) {
        this.items = pItems;
    }

    public static void entityInside(Level level, BlockPos pos, BlockState state, Entity pEntity, ArcaneHopperBlockEntity pBlockEntity) {
        if (pEntity instanceof ItemEntity && Shapes.joinIsNotEmpty(Shapes.create(pEntity.getBoundingBox().move((double)(-pos.getX()), (double)(-pos.getY()), (double)(-pos.getZ()))), pBlockEntity.getSuckShape(), BooleanOp.AND)) {
            tryMoveItems(level, pos, state, pBlockEntity, () -> {
                return addItem(pBlockEntity, (ItemEntity)pEntity);
            });
        }

    }

    protected AbstractContainerMenu createMenu(int pId, Inventory player) {
        return new HopperMenu(pId, player, this);
    }

    @Override
    protected net.minecraftforge.items.IItemHandler createUnSidedHandler() {
        return new ArcaneHopperItemHandler(this);
    }

    public long getLastUpdateTime() {
        return this.tickedGameTime;
    }
}
