package mod.maxbogomol.wizards_reborn.common.block.cork_bamboo;

import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockTags;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.material.FluidState;

import javax.annotation.Nullable;

public class CorkBambooStalkBlock extends BambooStalkBlock {

    public CorkBambooStalkBlock(Properties builder) {
        super(builder);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        if (!fluidstate.isEmpty()) {
            return null;
        } else {
            BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos().below());
            if (blockstate.is(WizardsRebornBlockTags.CORK_BAMBOO_PLANTABLE_ON)) {
                if (blockstate.is(WizardsRebornBlocks.CORK_BAMBOO_SAPLING.get())) {
                    return this.defaultBlockState().setValue(AGE, Integer.valueOf(0));
                } else if (blockstate.is(WizardsRebornBlocks.CORK_BAMBOO.get())) {
                    int i = blockstate.getValue(AGE) > 0 ? 1 : 0;
                    return this.defaultBlockState().setValue(AGE, Integer.valueOf(i));
                } else {
                    BlockState blockstate1 = pContext.getLevel().getBlockState(pContext.getClickedPos().above());
                    return blockstate1.is(WizardsRebornBlocks.CORK_BAMBOO.get()) ? this.defaultBlockState().setValue(AGE, blockstate1.getValue(AGE)) : WizardsRebornBlocks.CORK_BAMBOO.get().defaultBlockState();
                }
            } else {
                return null;
            }
        }
    }

    @Override
    @Nullable
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).is(WizardsRebornBlockTags.CORK_BAMBOO_PLANTABLE_ON);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            pLevel.scheduleTick(pCurrentPos, this, 1);
        }

        if (pDirection == Direction.UP && pNeighborState.is(WizardsRebornBlocks.CORK_BAMBOO.get()) && pNeighborState.getValue(AGE) > pState.getValue(AGE)) {
            pLevel.setBlock(pCurrentPos, pState.cycle(AGE), 2);
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(WizardsRebornItems.CORK_BAMBOO.get());
    }

    protected void growBamboo(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom, int pAge) {
        BlockState blockstate = pLevel.getBlockState(pPos.below());
        BlockPos blockpos = pPos.below(2);
        BlockState blockstate1 = pLevel.getBlockState(blockpos);
        BambooLeaves bambooleaves = BambooLeaves.NONE;
        if (pAge >= 1) {
            if (blockstate.is(WizardsRebornBlocks.CORK_BAMBOO.get()) && blockstate.getValue(LEAVES) != BambooLeaves.NONE) {
                if (blockstate.is(WizardsRebornBlocks.CORK_BAMBOO.get()) && blockstate.getValue(LEAVES) != BambooLeaves.NONE) {
                    bambooleaves = BambooLeaves.LARGE;
                    if (blockstate1.is(WizardsRebornBlocks.CORK_BAMBOO.get())) {
                        pLevel.setBlock(pPos.below(), blockstate.setValue(LEAVES, BambooLeaves.SMALL), 3);
                        pLevel.setBlock(blockpos, blockstate1.setValue(LEAVES, BambooLeaves.NONE), 3);
                    }
                }
            } else {
                bambooleaves = BambooLeaves.SMALL;
            }
        }

        int i = pState.getValue(AGE) != 1 && !blockstate1.is(WizardsRebornBlocks.CORK_BAMBOO.get()) ? 0 : 1;
        int j = (pAge < 11 || !(pRandom.nextFloat() < 0.25F)) && pAge != 15 ? 0 : 1;
        pLevel.setBlock(pPos.above(), this.defaultBlockState().setValue(AGE, Integer.valueOf(i)).setValue(LEAVES, bambooleaves).setValue(STAGE, Integer.valueOf(j)), 3);
    }

    protected int getHeightAboveUpToMax(BlockGetter pLevel, BlockPos pPos) {
        int i;
        for(i = 0; i < 16 && pLevel.getBlockState(pPos.above(i + 1)).is(WizardsRebornBlocks.CORK_BAMBOO.get()); ++i) {
        }

        return i;
    }

    protected int getHeightBelowUpToMax(BlockGetter pLevel, BlockPos pPos) {
        int i;
        for(i = 0; i < 16 && pLevel.getBlockState(pPos.below(i + 1)).is(WizardsRebornBlocks.CORK_BAMBOO.get()); ++i) {
        }

        return i;
    }
}
