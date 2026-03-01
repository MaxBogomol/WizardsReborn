package mod.maxbogomol.wizards_reborn.common.block.runic_pedestal;

import mod.maxbogomol.fluffy_fur.common.block.entity.NameableBlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.common.gui.menu.RunicPedestalMenu;
import mod.maxbogomol.wizards_reborn.common.item.equipment.RunicWisestonePlateItem;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
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

public class RunicPedestalBlockEntity extends NameableBlockSimpleInventory implements TickableBlockEntity {
    public final ItemStackHandler itemHandler = createHandler(1);
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public RunicPedestalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public RunicPedestalBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.RUNIC_PEDESTAL.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            if (hasRunicPlate()) {
                if (!getBlockState().getValue(BlockStateProperties.LIT)) {
                    BlockState blockState = getBlockState().setValue(BlockStateProperties.LIT, true);
                    level.setBlock(getBlockPos(), blockState, 3);
                }
            } else if (getBlockState().getValue(BlockStateProperties.LIT)) {
                BlockState blockState = getBlockState().setValue(BlockStateProperties.LIT, false);
                level.setBlock(getBlockPos(), blockState, 3);
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

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(100);
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
        return Component.translatable("gui.wizards_reborn.runic_pedestal.title");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new RunicPedestalMenu(i, level, getBlockPos(), inventory, player);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inv", itemHandler.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inv"));
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 1.5f, pos.getZ() + 1.5f);
    }

    public boolean hasRunicPlate() {
        return (!itemHandler.getStackInSlot(0).isEmpty() && itemHandler.getStackInSlot(0).getItem() instanceof RunicWisestonePlateItem);
    }

    public CrystalRitual getCrystalRitual() {
        if (hasRunicPlate()) {
            return CrystalRitualUtil.getCrystalRitual(itemHandler.getStackInSlot(0));
        }
        return null;
    }
}
