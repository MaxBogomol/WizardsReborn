package mod.maxbogomol.wizards_reborn.common.block.arcane_hopper;

import mod.maxbogomol.wizards_reborn.client.gui.container.ArcaneHopperContainer;
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
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

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

    public void load(CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.items);
        }

        this.cooldownTime = tag.getInt("TransferCooldown");
    }

    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items);
        }

        tag.putInt("TransferCooldown", this.cooldownTime);
    }

    public int getContainerSize() {
        return this.items.size();
    }

    public ItemStack removeItem(int index, int count) {
        this.unpackLootTable((Player)null);
        return ContainerHelper.removeItem(this.getItems(), index, count);
    }

    public void setItem(int index, ItemStack stack) {
        this.unpackLootTable((Player)null);
        this.getItems().set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
    }

    public static void pushItemsTick(Level level, BlockPos pos, BlockState state, ArcaneHopperBlockEntity blockEntity) {
        --blockEntity.cooldownTime;
        blockEntity.tickedGameTime = level.getGameTime();
        if (!blockEntity.isOnCooldown()) {
            blockEntity.setCooldown(0);
            tryMoveItems(level, pos, state, blockEntity, () -> {
                return suckInItems(level, blockEntity);
            });
        }
    }

    private static boolean tryMoveItems(Level level, BlockPos pos, BlockState state, ArcaneHopperBlockEntity blockEntity, BooleanSupplier pValidator) {
        if (level.isClientSide) {
            return false;
        } else {
            if (!blockEntity.isOnCooldown() && state.getValue(HopperBlock.ENABLED)) {
                boolean flag = false;
                if (!blockEntity.isEmpty()) {
                    flag = ejectItems(level, pos, state, blockEntity);
                }

                if (!blockEntity.inventoryFull()) {
                    flag |= pValidator.getAsBoolean();
                }

                if (flag) {
                    blockEntity.setCooldown(getMoveItemSpeed());
                    setChanged(level, pos, state);
                    return true;
                }
            }

            return false;
        }
    }

    private boolean inventoryFull() {
        for (ItemStack itemstack : this.items) {
            if (itemstack.isEmpty() || itemstack.getCount() != itemstack.getMaxStackSize()) {
                return false;
            }
        }

        return true;
    }

    private static boolean ejectItems(Level level, BlockPos pos, BlockState state, ArcaneHopperBlockEntity sourceContainer) {
        if (ArcaneHopperInventoryCodeHooks.insertHook(sourceContainer)) return true;
        Container container = getAttachedContainer(level, pos, state);
        if (container == null) {
            return false;
        } else {
            Direction direction = state.getValue(HopperBlock.FACING).getOpposite();
            if (isFullContainer(container, direction)) {
                return false;
            } else {
                for (int i = 0; i < sourceContainer.getContainerSize(); ++i) {
                    if (!sourceContainer.getItem(i).isEmpty()) {
                        ItemStack itemstack = sourceContainer.getItem(i).copy();
                        ItemStack itemstack1 = addItem(sourceContainer, container, sourceContainer.removeItem(i, 1), direction);
                        if (itemstack1.isEmpty()) {
                            container.setChanged();
                            return true;
                        }

                        sourceContainer.setItem(i, itemstack);
                    }
                }

                return false;
            }
        }
    }

    private static IntStream getSlots(Container container, Direction direction) {
        return container instanceof WorldlyContainer ? IntStream.of(((WorldlyContainer)container).getSlotsForFace(direction)) : IntStream.range(0, container.getContainerSize());
    }

    private static boolean isFullContainer(Container container, Direction direction) {
        return getSlots(container, direction).allMatch((p_59379_) -> {
            ItemStack itemstack = container.getItem(p_59379_);
            return itemstack.getCount() >= itemstack.getMaxStackSize();
        });
    }

    private static boolean isEmptyContainer(Container container, Direction direction) {
        return getSlots(container, direction).allMatch((p_59319_) -> {
            return container.getItem(p_59319_).isEmpty();
        });
    }

    public static boolean suckInItems(Level level, Hopper hopper) {
        Boolean ret = net.minecraftforge.items.VanillaInventoryCodeHooks.extractHook(level, hopper);
        if (ret != null) return ret;
        Container container = getSourceContainer(level, hopper);
        if (container != null) {
            Direction direction = Direction.DOWN;
            return isEmptyContainer(container, direction) ? false : getSlots(container, direction).anyMatch((p_59363_) -> {
                return tryTakeInItemFromSlot(hopper, container, p_59363_, direction);
            });
        } else {
            for (ItemEntity itementity : getItemsAtAndAbove(level, hopper)) {
                if (addItem(hopper, itementity)) {
                    return true;
                }
            }

            return false;
        }
    }

    private static boolean tryTakeInItemFromSlot(Hopper hopper, Container container, int slot, Direction direction) {
        ItemStack itemstack = container.getItem(slot);
        if (!itemstack.isEmpty() && canTakeItemFromContainer(hopper, container, itemstack, slot, direction)) {
            ItemStack itemstack1 = itemstack.copy();
            ItemStack itemstack2 = addItem(container, hopper, container.removeItem(slot, 1), (Direction)null);
            if (itemstack2.isEmpty()) {
                container.setChanged();
                return true;
            }

            container.setItem(slot, itemstack1);
        }

        return false;
    }

    public static boolean addItem(Container container, ItemEntity item) {
        boolean flag = false;
        ItemStack itemstack = item.getItem().copy();
        ItemStack itemstack1 = addItem((Container)null, container, itemstack, (Direction)null);
        if (itemstack1.isEmpty()) {
            flag = true;
            item.discard();
        } else {
            item.setItem(itemstack1);
        }

        return flag;
    }

    public static ItemStack addItem(@Nullable Container source, Container destination, ItemStack stack, @Nullable Direction direction) {
        if (destination instanceof WorldlyContainer worldlycontainer) {
            if (direction != null) {
                int[] aint = worldlycontainer.getSlotsForFace(direction);

                for (int k = 0; k < aint.length && !stack.isEmpty(); ++k) {
                    stack = tryMoveInItem(source, destination, stack, aint[k], direction);
                }

                return stack;
            }
        }

        int i = destination.getContainerSize();

        for (int j = 0; j < i && !stack.isEmpty(); ++j) {
            stack = tryMoveInItem(source, destination, stack, j, direction);
        }

        return stack;
    }

    private static boolean canPlaceItemInContainer(Container container, ItemStack stack, int slot, @Nullable Direction direction) {
        if (!container.canPlaceItem(slot, stack)) {
            return false;
        } else {
            if (container instanceof WorldlyContainer) {
                WorldlyContainer worldlycontainer = (WorldlyContainer)container;
                if (!worldlycontainer.canPlaceItemThroughFace(slot, stack, direction)) {
                    return false;
                }
            }

            return true;
        }
    }

    private static boolean canTakeItemFromContainer(Container source, Container destination, ItemStack stack, int slot, Direction direction) {
        if (!destination.canTakeItem(source, slot, stack)) {
            return false;
        } else {
            if (destination instanceof WorldlyContainer) {
                WorldlyContainer worldlycontainer = (WorldlyContainer)destination;
                if (!worldlycontainer.canTakeItemThroughFace(slot, stack, direction)) {
                    return false;
                }
            }

            return true;
        }
    }

    private static ItemStack tryMoveInItem(@Nullable Container source, Container destination, ItemStack stack, int slot, @Nullable Direction direction) {
        ItemStack itemstack = destination.getItem(slot);
        if (canPlaceItemInContainer(destination, stack, slot, direction)) {
            boolean flag = false;
            boolean flag1 = destination.isEmpty();
            if (itemstack.isEmpty()) {
                destination.setItem(slot, stack);
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
                if (flag1 && destination instanceof ArcaneHopperBlockEntity) {
                    ArcaneHopperBlockEntity hopperblockentity1 = (ArcaneHopperBlockEntity)destination;
                    if (!hopperblockentity1.isOnCustomCooldown()) {
                        int k = 0;
                        if (source instanceof ArcaneHopperBlockEntity) {
                            ArcaneHopperBlockEntity hopperblockentity = (ArcaneHopperBlockEntity)source;
                            if (hopperblockentity1.tickedGameTime >= hopperblockentity.tickedGameTime) {
                                k = 1;
                            }
                        }

                        hopperblockentity1.setCooldown(getMoveItemSpeed() - k);
                    }
                }

                destination.setChanged();
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
    private static Container getSourceContainer(Level level, Hopper hopper) {
        return getContainerAt(level, hopper.getLevelX(), hopper.getLevelY() + 1.0D, hopper.getLevelZ());
    }

    public static List<ItemEntity> getItemsAtAndAbove(Level level, Hopper hopper) {
        return hopper.getSuckShape().toAabbs().stream().flatMap((p_155558_) -> {
            return level.getEntitiesOfClass(ItemEntity.class, p_155558_.move(hopper.getLevelX() - 0.5D, hopper.getLevelY() - 0.5D, hopper.getLevelZ() - 0.5D), EntitySelector.ENTITY_STILL_ALIVE).stream();
        }).collect(Collectors.toList());
    }

    @Nullable
    public static Container getContainerAt(Level level, BlockPos pos) {
        return getContainerAt(level, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D);
    }

    @Nullable
    private static Container getContainerAt(Level level, double x, double y, double z) {
        Container container = null;
        BlockPos blockpos = BlockPos.containing(x, y, z);
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
            List<Entity> list = level.getEntities((Entity)null, new AABB(x - 0.5D, y - 0.5D, z - 0.5D, x + 0.5D, y + 0.5D, z + 0.5D), EntitySelector.CONTAINER_ENTITY_SELECTOR);
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
        return (double)this.getBlockPos().getX() + 0.5D;
    }

    public double getLevelY() {
        return (double)this.getBlockPos().getY() + 0.5D;
    }

    public double getLevelZ() {
        return (double)this.getBlockPos().getZ() + 0.5D;
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

    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    public static void entityInside(Level level, BlockPos pos, BlockState state, Entity entity, ArcaneHopperBlockEntity blockEntity) {
        if (entity instanceof ItemEntity && Shapes.joinIsNotEmpty(Shapes.create(entity.getBoundingBox().move((double)(-pos.getX()), (double)(-pos.getY()), (double)(-pos.getZ()))), blockEntity.getSuckShape(), BooleanOp.AND)) {
            tryMoveItems(level, pos, state, blockEntity, () -> {
                return addItem(blockEntity, (ItemEntity)entity);
            });
        }

    }

    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new HopperMenu(id, player, this);
    }

    @Override
    protected IItemHandler createUnSidedHandler() {
        return new ArcaneHopperItemHandler(this);
    }

    public long getLastUpdateTime() {
        return this.tickedGameTime;
    }

    @Override
    @NotNull
    public Component getDefaultName() {
        return Component.translatable("gui.wizards_reborn.arcane_hopper.title");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ArcaneHopperContainer(i, level, getBlockPos(), inventory, player);
    }
}
