package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownTileEntity;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CooldownSensorBlock extends SensorBaseBlock {

    public CooldownSensorBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected int getInputSignal(Level pLevel, BlockPos pPos, BlockState pState) {
        int i = super.getInputSignal(pLevel, pPos, pState);
        Direction direction = pState.getValue(FACING);
        BlockPos blockpos = pPos.relative(direction);

        switch (pState.getValue(FACE)) {
            case FLOOR:
                blockpos = pPos.above();
                break;
            case WALL:
                break;
            case CEILING:
                blockpos = pPos.below();
                break;
        }

        BlockEntity tile = pLevel.getBlockEntity(blockpos);
        if (tile instanceof ICooldownTileEntity cooldownTile) {
            i = Mth.floor((1f / cooldownTile.getCooldown()) * 14.0F);
            if (cooldownTile.getCooldown() <= 0) {
                i = 0;
            }
        }

        return i;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ModelResourceLocation getModel(BlockState state) {
        return WizardsRebornClient.COOLDOWN_SENSOR_PIECE_MODEl;
    }
}
