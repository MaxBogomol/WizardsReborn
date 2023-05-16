package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.inventory.Inventory;
import net.minecraft.tileentity.TileEntityType;

public class ArcanePedestalTileEntity extends TileSimpleInventory {
    public ArcanePedestalTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public ArcanePedestalTileEntity() {
        this(WizardsReborn.ARCANE_PEDESTAL_TILE_ENTITY.get());
    }

    @Override
    protected Inventory createItemHandler() {
        return new Inventory(1) {
            @Override
            public int getInventoryStackLimit() {
                return 1;
            }
        };
    }
}
