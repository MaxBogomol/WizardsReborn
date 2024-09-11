package mod.maxbogomol.wizards_reborn.common.block.baulk;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CrossBaulkBlockEntity extends PipeBaseBlockEntity implements TickableBlockEntity {

    public CrossBaulkBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public CrossBaulkBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.CROSS_BAULK.get(), pos, state);
    }

    public void tick() {
        if (!level.isClientSide()) {
            if (!loaded) initConnections();
        }
    }
}
