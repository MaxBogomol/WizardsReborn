package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShinyCloverCropBlock extends CropBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(5.0D, 0.0D, 5.0D, 11.0D, 1.0D, 11.0D), Block.box(4.0D, 0.0D, 4.0D, 12.0D, 2, 12.0D)};

    public ShinyCloverCropBlock(Properties builder) {
        super(builder);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 2;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return WizardsRebornItems.SHINY_CLOVER_SEED.get();
    }

    @Override
    public BlockState getStateForAge(int age) {
        return age == 2 ? WizardsRebornBlocks.SHINY_CLOVER.get().defaultBlockState() : super.getStateForAge(age);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (random.nextInt(3) != 0) {
            super.randomTick(state, worldIn, pos, random);
        }
    }

    @Override
    protected int getBonemealAgeIncrease(Level level) {
        return 1;
    }
}
