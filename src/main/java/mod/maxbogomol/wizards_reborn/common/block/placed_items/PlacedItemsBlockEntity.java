package mod.maxbogomol.wizards_reborn.common.block.placed_items;

import mod.maxbogomol.fluffy_fur.common.block.entity.BlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class PlacedItemsBlockEntity extends BlockSimpleInventory implements IWissenWandFunctionalBlockEntity {
    public boolean things = false;
    public boolean isRotate = true;

    public PlacedItemsBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public PlacedItemsBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.PLACED_ITEMS.get(), pos, state);
    }

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(4) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("things", things);
        tag.putBoolean("isRotate", isRotate);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        things = tag.getBoolean("things");
        isRotate = tag.getBoolean("isRotate");
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 1.5f, pos.getZ() + 1.5f);
    }

    public int getInventorySize() {
        int size = 0;

        for (int i = 0; i < getItemHandler().getContainerSize(); i++) {
            if (!getItemHandler().getItem(i).isEmpty()) {
                size++;
            }
        }

        return size;
    }

    @Override
    public void wissenWandFunction() {
        isRotate = !isRotate;
        BlockEntityUpdate.packet(this);
    }
}
