package mod.maxbogomol.wizards_reborn.common.block.baulk;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CrossBaulkBlockEntity extends PipeBaseTileEntity implements TickableBlockEntity {

    public CrossBaulkBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public CrossBaulkBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.CROSS_BAULK_TILE_ENTITY.get(), pos, state);
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
