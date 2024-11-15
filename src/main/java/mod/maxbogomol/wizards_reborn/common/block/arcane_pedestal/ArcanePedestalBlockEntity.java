package mod.maxbogomol.wizards_reborn.common.block.arcane_pedestal;

import mod.maxbogomol.fluffy_fur.common.block.entity.ExposedBlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.book.CustomBook;
import mod.maxbogomol.fluffy_fur.common.book.CustomBookComponent;
import mod.maxbogomol.fluffy_fur.common.book.CustomBookHandler;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class ArcanePedestalBlockEntity extends ExposedBlockSimpleInventory implements TickableBlockEntity {

    public CustomBookComponent bookComponent = null;

    public ArcanePedestalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ArcanePedestalBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.ARCANE_PEDESTAL.get(), pos, state);
    }

    @Override
    public void tick() {
        if (getLevel().isClientSide()) {
            CustomBook book = getBook();
            if (book != null) {
                if (bookComponent == null) bookComponent = book.getComponent();
                bookTick(book);
            } else {
                bookComponent = null;
            }
        }
    }

    public void bookTick(CustomBook book) {
        Container container = getItemHandler();
        book.tick(getLevel(), getBlockPos().getCenter(), container.getItem(0), bookComponent, 3);
    }

    public CustomBook getBook() {
        Container container = getItemHandler();
        if (!container.isEmpty()) {
            for (CustomBook book : CustomBookHandler.getBooks()) {
                if (book.isBook(getLevel(), getBlockPos().getCenter(), container.getItem(0))) {
                    return book;
                }
            }
        }
        return null;
    }

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(1) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 1.5f, pos.getZ() + 1.5f);
    }
}
