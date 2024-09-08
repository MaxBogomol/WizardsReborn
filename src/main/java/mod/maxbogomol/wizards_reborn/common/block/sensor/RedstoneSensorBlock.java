package mod.maxbogomol.wizards_reborn.common.block.sensor;

import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RedstoneSensorBlock extends SensorBaseBlock {

    public RedstoneSensorBlock(Properties properties) {
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

        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.hasAnalogOutputSignal()) {
            i = blockstate.getAnalogOutputSignal(level, blockpos);
        } else if (i < 15 && blockstate.isRedstoneConductor(level, blockpos)) {
            blockpos = blockpos.relative(direction);
            blockstate = level.getBlockState(blockpos);
            ItemFrame itemframe = this.getItemFrame(level, direction, blockpos);
            int j = Math.max(itemframe == null ? Integer.MIN_VALUE : itemframe.getAnalogOutput(), blockstate.hasAnalogOutputSignal() ? blockstate.getAnalogOutputSignal(level, blockpos) : Integer.MIN_VALUE);
            if (j != Integer.MIN_VALUE) {
                i = j;
            }
        }

        return i;
    }

    @Override
    protected int getAlternateSignal(SignalGetter pSignalGetter, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        Direction direction1 = direction.getClockWise();
        Direction direction2 = direction.getCounterClockWise();
        boolean flag = this.sideInputDiodesOnly();
        return Math.max(pSignalGetter.getControlInputSignal(pos.relative(direction1), direction1, flag), pSignalGetter.getControlInputSignal(pos.relative(direction2), direction2, flag));
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {
            state = state.cycle(MODE);
            float f = state.getValue(MODE) == ComparatorMode.SUBTRACT ? 0.55F : 0.5F;
            level.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, f);
            level.setBlock(pos, state, 2);
            this.refreshOutputState(level, pos, state);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ModelResourceLocation getModel(BlockState state) {
        if (state.getValue(MODE) == ComparatorMode.SUBTRACT) {
            return WizardsRebornModels.REDSTONE_SENSOR_PIECE_ON;
        }
        return WizardsRebornModels.REDSTONE_SENSOR_PIECE;
    }
}
