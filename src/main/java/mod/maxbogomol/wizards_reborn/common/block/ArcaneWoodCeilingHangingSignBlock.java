package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.common.tileentity.CustomHangingSignTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import javax.annotation.Nullable;

public class ArcaneWoodCeilingHangingSignBlock extends CeilingHangingSignBlock {
    public ArcaneWoodCeilingHangingSignBlock(Properties properties, WoodType type) {
        super(properties, type);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CustomHangingSignTileEntity(pPos, pState);
    }
}