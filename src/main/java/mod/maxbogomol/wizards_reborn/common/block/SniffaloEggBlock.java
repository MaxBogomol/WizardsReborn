package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SnifferEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class SniffaloEggBlock extends SnifferEggBlock implements SimpleWaterloggedBlock {
    public SniffaloEggBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(HATCH, Integer.valueOf(0)).setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HATCH).add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    private boolean isReadyToHatch(BlockState state) {
        return this.getHatchLevel(state) == 2;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!this.isReadyToHatch(state)) {
            level.playSound(null, pos, SoundEvents.SNIFFER_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
            level.setBlock(pos, state.setValue(HATCH, Integer.valueOf(this.getHatchLevel(state) + 1)), 2);
        } else {
            level.playSound(null, pos, SoundEvents.SNIFFER_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
            level.destroyBlock(pos, false);
            SniffaloEntity sniffalo = WizardsRebornEntities.SNIFFALO.get().create(level);
            if (sniffalo != null) {
                Vec3 vec3 = pos.getCenter();
                sniffalo.setBaby(true);
                sniffalo.moveTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(level.random.nextFloat() * 360.0F), 0.0F);
                level.addFreshEntity(sniffalo);
            }
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pState.getValue(BlockStateProperties.WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }
}
