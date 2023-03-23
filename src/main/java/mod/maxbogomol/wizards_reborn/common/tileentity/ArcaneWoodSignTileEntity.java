package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ArcaneWoodSignTileEntity extends SignTileEntity {
    public ArcaneWoodSignTileEntity() {
        super();
    }

    @Override
    public TileEntityType<?> getType() {
        return WizardsReborn.ARCANE_WOOD_SIGN_TILE_ENTITY.get();
    }
}
