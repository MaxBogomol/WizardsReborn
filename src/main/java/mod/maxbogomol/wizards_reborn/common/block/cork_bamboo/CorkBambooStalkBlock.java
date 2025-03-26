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
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        if (!fluidstate.isEmpty()) {
            return null;
        } else {
            BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().below());
            if (blockstate.is(WizardsRebornBlockTags.CORK_BAMBOO_PLANTABLE_ON)) {
                if (blockstate.is(WizardsRebornBlocks.CORK_BAMBOO_SAPLING.get())) {
                    return this.defaultBlockState().setValue(AGE, Integer.valueOf(0));
                } else if (blockstate.is(WizardsRebornBlocks.CORK_BAMBOO.get())) {
                    int i = blockstate.getValue(AGE) > 0 ? 1 : 0;
                    return this.defaultBlockState().setValue(AGE, Integer.valueOf(i));
                } else {
                    BlockState blockstate1 = context.getLevel().getBlockState(context.getClickedPos().above());
                    return blockstate1.is(WizardsRebornBlocks.CORK_BAMBOO.get()) ? this.defaultBlockState().setValue(AGE, blockstate1.getValue(AGE)) : WizardsRebornBlocks.CORK_BAMBOO.get().defaultBlockState();
                }
            } else {
                return null;
            }
        }
    }

    @Override
    @Nullable
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(WizardsRebornBlockTags.CORK_BAMBOO_PLANTABLE_ON);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (!state.canSurvive(level, currentPos)) {
            level.scheduleTick(currentPos, this, 1);
        }

        if (direction == Direction.UP && neighborState.is(WizardsRebornBlocks.CORK_BAMBOO.get()) && neighborState.getValue(AGE) > state.getValue(AGE)) {
            level.setBlock(currentPos, state.cycle(AGE), 2);
        }

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(WizardsRebornItems.CORK_BAMBOO.get());
    }

    protected void growBamboo(BlockState state, Level level, BlockPos pos, RandomSource random, int age) {
        BlockState blockstate = level.getBlockState(pos.below());
        BlockPos blockpos = pos.below(2);
        BlockState blockstate1 = level.getBlockState(blockpos);
        BambooLeaves bambooleaves = BambooLeaves.NONE;
        if (age >= 1) {
            if (blockstate.is(WizardsRebornBlocks.CORK_BAMBOO.get()) && blockstate.getValue(LEAVES) != BambooLeaves.NONE) {
                if (blockstate.is(WizardsRebornBlocks.CORK_BAMBOO.get()) && blockstate.getValue(LEAVES) != BambooLeaves.NONE) {
                    bambooleaves = BambooLeaves.LARGE;
                    if (blockstate1.is(WizardsRebornBlocks.CORK_BAMBOO.get())) {
                        level.setBlock(pos.below(), blockstate.setValue(LEAVES, BambooLeaves.SMALL), 3);
                        level.setBlock(blockpos, blockstate1.setValue(LEAVES, BambooLeaves.NONE), 3);
                    }
                }
            } else {
                bambooleaves = BambooLeaves.SMALL;
            }
        }

        int i = state.getValue(AGE) != 1 && !blockstate1.is(WizardsRebornBlocks.CORK_BAMBOO.get()) ? 0 : 1;
        int j = (age < 11 || !(random.nextFloat() < 0.25F)) && age != 15 ? 0 : 1;
        level.setBlock(pos.above(), this.defaultBlockState().setValue(AGE, Integer.valueOf(i)).setValue(LEAVES, bambooleaves).setValue(STAGE, Integer.valueOf(j)), 3);
    }

    protected int getHeightAboveUpToMax(BlockGetter level, BlockPos pos) {
        int i;
        for (i = 0; i < 16 && level.getBlockState(pos.above(i + 1)).is(WizardsRebornBlocks.CORK_BAMBOO.get()); ++i) {}
        return i;
    }

    protected int getHeightBelowUpToMax(BlockGetter level, BlockPos pos) {
        int i;
        for (i = 0; i < 16 && level.getBlockState(pos.below(i + 1)).is(WizardsRebornBlocks.CORK_BAMBOO.get()); ++i) {}
        return i;
    }
}
