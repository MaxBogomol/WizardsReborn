package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
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

        BlockState blockstate = pLevel.getBlockState(blockpos);
        if (blockstate.hasAnalogOutputSignal()) {
            i = blockstate.getAnalogOutputSignal(pLevel, blockpos);
        } else if (i < 15 && blockstate.isRedstoneConductor(pLevel, blockpos)) {
            blockpos = blockpos.relative(direction);
            blockstate = pLevel.getBlockState(blockpos);
            ItemFrame itemframe = this.getItemFrame(pLevel, direction, blockpos);
            int j = Math.max(itemframe == null ? Integer.MIN_VALUE : itemframe.getAnalogOutput(), blockstate.hasAnalogOutputSignal() ? blockstate.getAnalogOutputSignal(pLevel, blockpos) : Integer.MIN_VALUE);
            if (j != Integer.MIN_VALUE) {
                i = j;
            }
        }

        return i;
    }

    @Override
    protected int getAlternateSignal(SignalGetter pSignalGetter, BlockPos pPos, BlockState pState) {
        Direction direction = pState.getValue(FACING);
        Direction direction1 = direction.getClockWise();
        Direction direction2 = direction.getCounterClockWise();
        boolean flag = this.sideInputDiodesOnly();
        return Math.max(pSignalGetter.getControlInputSignal(pPos.relative(direction1), direction1, flag), pSignalGetter.getControlInputSignal(pPos.relative(direction2), direction2, flag));
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pPlayer.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {
            pState = pState.cycle(MODE);
            float f = pState.getValue(MODE) == ComparatorMode.SUBTRACT ? 0.55F : 0.5F;
            pLevel.playSound(pPlayer, pPos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, f);
            pLevel.setBlock(pPos, pState, 2);
            this.refreshOutputState(pLevel, pPos, pState);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ModelResourceLocation getModel(BlockState state) {
        if (state.getValue(MODE) == ComparatorMode.SUBTRACT) {
            return WizardsRebornClient.REDSTONE_SENSOR_PIECE_ON_MODEl;
        }
        return WizardsRebornClient.REDSTONE_SENSOR_PIECE_MODEl;
    }
}
