package mod.maxbogomol.wizards_reborn.common.block.plant;

import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.block.OldRootsPacket;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.Random;

public class OldRootsBlock extends CropBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
    private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);

    public static ArrayList<Block> plants = new ArrayList<>();
    public static ArrayList<Block> rarePlants = new ArrayList<>();
    public static ArrayList<Block> rareRarePlants = new ArrayList<>();

    public Random random = new Random();

    public OldRootsBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 vec3 = state.getOffset(level, pos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 1;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return WizardsRebornItems.OLD_ROOTS.get();
    }

    @Override
    public BlockState getStateForAge(int age) {
        if (age == 1) {
            Block block = plants.get(random.nextInt(0, plants.size()));
            if (random.nextFloat() < 0.1f) {
                block = rarePlants.get(random.nextInt(0, rarePlants.size()));
            }
            if (random.nextFloat() < 0.01f) {
                block = rareRarePlants.get(random.nextInt(0, rareRarePlants.size()));
            }
            return block.defaultBlockState();
        }
        return super.getStateForAge(age);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (!(level.getBlockState(pos).getBlock() instanceof OldRootsBlock)) {
            Vec3 vec3 = state.getOffset(level, pos);
            ItemStack itemStack = level.getBlockState(pos).getBlock().asItem().getDefaultInstance();
            WizardsRebornPacketHandler.sendToTracking(level, pos, new OldRootsPacket(pos.getCenter().add(vec3), itemStack));
        }
    }

    @Override
    public void growCrops(Level level, BlockPos pos, BlockState state) {
        super.growCrops(level, pos, state);
        if (!level.isClientSide()) {
            if (!(level.getBlockState(pos).getBlock() instanceof OldRootsBlock)) {
                Vec3 vec3 = state.getOffset(level, pos);
                ItemStack itemStack = level.getBlockState(pos).getBlock().asItem().getDefaultInstance();
                WizardsRebornPacketHandler.sendToTracking(level, pos, new OldRootsPacket(pos.getCenter().add(vec3), itemStack));
            }
        }
    }

    @Override
    protected int getBonemealAgeIncrease(Level level) {
        return 1;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.DIRT) || state.is(Blocks.FARMLAND);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.below();
        if (state.getBlock() == this) {
            return level.getBlockState(blockpos).canSustainPlant(level, blockpos, Direction.UP, this);
        }
        return this.mayPlaceOn(level.getBlockState(blockpos), level, blockpos);
    }
}
