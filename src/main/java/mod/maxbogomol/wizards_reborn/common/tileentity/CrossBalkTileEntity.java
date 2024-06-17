package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CrossBalkTileEntity extends PipeBaseTileEntity implements TickableBlockEntity {

    public CrossBalkTileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public CrossBalkTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.CROSS_BALK_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, (e) -> e.getUpdateTag());
    }

    public void tick() {
        if (!level.isClientSide()) {
            if (!loaded)
                initConnections();
        }
    }
}
