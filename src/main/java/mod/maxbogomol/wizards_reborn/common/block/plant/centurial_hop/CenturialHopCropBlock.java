package mod.maxbogomol.wizards_reborn.common.block.plant.centurial_hop;

import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CenturialHopCropBlock extends CropBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(5.0D, 12.0D, 5.0D, 11.0D, 16.0D, 11.0D), Block.box(4.0D, 10.0D, 4.0D, 12.0D, 16.0D, 12.0D), Block.box(3.0D, 6.0D, 3.0D, 13.0D, 16.0D, 13.0D), Block.box(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D)};

    public CenturialHopCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 4;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return WizardsRebornItems.CENTURIAL_HOP_SEED.get();
    }

    @Override
    public BlockState getStateForAge(int age) {
        return age == 4 ? WizardsRebornBlocks.CENTURIAL_HOP_VINES.get().defaultBlockState() : super.getStateForAge(age);
    }

    @Override
    protected int getBonemealAgeIncrease(Level level) {
        return 1;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.relative(Direction.UP);
        BlockState blockstate = level.getBlockState(blockpos);
        return blockstate.isFaceSturdy(level, blockpos, Direction.DOWN);
    }
}
