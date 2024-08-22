package mod.maxbogomol.wizards_reborn.common.block.sensor;

import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LightSensorBlock extends SensorBaseBlock {

    public LightSensorBlock(Properties properties) {
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
        if (tile instanceof ILightBlockEntity lightTile && lightTile.getLight() > 0) {
            i = 14;
        }

        return i;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ModelResourceLocation getModel(BlockState state) {
        return WizardsRebornClient.LIGHT_SENSOR_PIECE_MODEL;
    }
}
