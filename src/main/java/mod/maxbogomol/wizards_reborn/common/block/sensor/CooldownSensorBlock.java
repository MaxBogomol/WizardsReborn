package mod.maxbogomol.wizards_reborn.common.block.sensor;

import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
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
    protected int getInputSignal(Level level, BlockPos pos, BlockState state) {
        int i = super.getInputSignal(level, pos, state);
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction);

        switch (state.getValue(FACE)) {
            case FLOOR:
                blockpos = pos.above();
                break;
            case WALL:
                break;
            case CEILING:
                blockpos = pos.below();
                break;
        }

        BlockEntity blockEntity = level.getBlockEntity(blockpos);
        if (blockEntity instanceof ICooldownBlockEntity cooldownBlock) {
            i = Mth.floor((1f / cooldownBlock.getCooldown()) * 14.0F);
            if (cooldownBlock.getCooldown() <= 0) {
                i = 0;
            }
        }

        return i;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ModelResourceLocation getModel(BlockState state) {
        return WizardsRebornModels.COOLDOWN_SENSOR_PIECE;
    }
}
