package mod.maxbogomol.wizards_reborn.common.block.cork_bamboo;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornItems;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BambooSaplingBlock;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;

import javax.annotation.Nullable;

public class CorkBambooSaplingBlock extends BambooSaplingBlock {

    public CorkBambooSaplingBlock(Properties builder) {
        super(builder);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (pFacing == Direction.UP && pFacingState.is(WizardsRebornBlocks.CORK_BAMBOO.get())) {
                pLevel.setBlock(pCurrentPos, WizardsRebornBlocks.CORK_BAMBOO.get().defaultBlockState(), 2);
            }

            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    @Override
    @Nullable
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).is(WizardsRebornTags.CORK_BAMBOO_PLANTABLE_ON_BLOCK) && !pLevel.getBlockState(pPos.below()).is(WizardsRebornBlocks.CORK_BAMBOO_SAPLING.get());
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(WizardsRebornItems.CORK_BAMBOO_SEED.get());
    }

    protected void growBamboo(Level level, BlockPos state) {
        level.setBlock(state.above(), WizardsRebornBlocks.CORK_BAMBOO.get().defaultBlockState().setValue(BambooStalkBlock.LEAVES, BambooLeaves.SMALL), 3);
    }
}
