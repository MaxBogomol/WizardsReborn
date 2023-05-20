package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class CrystalGrowthTileEntity extends TileEntity {
    public CrystalGrowthTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public CrystalGrowthTileEntity() {
        this(WizardsReborn.CRYSTAL_GROWTH_TILE_ENTITY.get());
    }
}
