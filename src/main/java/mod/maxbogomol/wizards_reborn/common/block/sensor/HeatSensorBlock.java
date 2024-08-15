package mod.maxbogomol.wizards_reborn.common.block.sensor;

import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.alchemy.IHeatTileEntity;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HeatSensorBlock extends SensorBaseBlock {

    public HeatSensorBlock(Properties properties) {
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
        if (tile instanceof IHeatTileEntity heatTile) {
            i = Mth.floor(((float) heatTile.getHeat() / heatTile.getMaxHeat()) * 14.0F);
        }

        return i;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ModelResourceLocation getModel(BlockState state) {
        return WizardsRebornClient.HEAT_SENSOR_PIECE_MODEL;
    }
}
