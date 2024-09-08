package mod.maxbogomol.wizards_reborn.common.block.cork_bamboo;

import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockTags;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
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
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (!state.canSurvive(level, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (facing == Direction.UP && facingState.is(WizardsRebornBlocks.CORK_BAMBOO.get())) {
                level.setBlock(currentPos, WizardsRebornBlocks.CORK_BAMBOO.get().defaultBlockState(), 2);
            }

            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        }
    }

    @Override
    @Nullable
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(WizardsRebornBlockTags.CORK_BAMBOO_PLANTABLE_ON) && !level.getBlockState(pos.below()).is(WizardsRebornBlocks.CORK_BAMBOO_SAPLING.get());
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(WizardsRebornItems.CORK_BAMBOO_SEED.get());
    }

    protected void growBamboo(Level level, BlockPos state) {
        level.setBlock(state.above(), WizardsRebornBlocks.CORK_BAMBOO.get().defaultBlockState().setValue(BambooStalkBlock.LEAVES, BambooLeaves.SMALL), 3);
    }
}
