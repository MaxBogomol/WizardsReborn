package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CustomSignTileEntity extends SignBlockEntity {
    public CustomSignTileEntity(BlockPos pPos, BlockState pState) {
        super(pPos, pState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return WizardsReborn.SIGN_TILE_ENTITY.get();
    }
}
