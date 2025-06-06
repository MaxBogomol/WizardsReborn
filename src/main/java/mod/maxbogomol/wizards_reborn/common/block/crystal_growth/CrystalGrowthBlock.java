package mod.maxbogomol.wizards_reborn.common.block.crystal_growth;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.crystalritual.IGrowableCrystal;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.FracturedCrystalItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.block.CrystalGrowthBreakPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class CrystalGrowthBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {
    public CrystalType type;
    private static final Random random = new Random();

    public static final IntegerProperty AGE =  IntegerProperty.create("age", 0, 4);

    private static final VoxelShape SHAPE_1 = Block.box(6, 0, 6, 9, 3, 9);
    private static final VoxelShape SHAPE_2 = Block.box(6, 0, 6, 10, 3, 10);

    private static final VoxelShape SHAPE_3 = Stream.of(
            Block.box(6, 0, 6, 10, 5, 10),
            Block.box(5, 0, 5, 8, 3, 8),
            Block.box(9, 0, 9, 12, 3, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_4 = Stream.of(
            Block.box(6, 0, 6, 11, 7, 11),
            Block.box(4, 0, 4, 8, 5, 8),
            Block.box(9, 0, 9, 13, 3, 13),
            Block.box(9, 0, 5, 12, 3, 8)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_5 = Stream.of(
            Block.box(5, 0, 5, 11, 9, 11),
            Block.box(3, 0, 3, 8, 7, 8),
            Block.box(9, 0, 9, 13, 5, 13),
            Block.box(8, 0, 4, 12, 3, 8),
            Block.box(3, 0, 9, 6, 3, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{SHAPE_1, SHAPE_2, SHAPE_3, SHAPE_4, SHAPE_5};

    public CrystalGrowthBlock(CrystalType type, Properties properties) {
        super(properties);
        this.type = type;
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED).add(AGE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return getBlockConnected(state).getOpposite() == direction && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = getBlockConnected(state).getOpposite();
        return Block.canSupportCenter(level, pos.relative(direction), direction.getOpposite());
    }

    protected static Direction getBlockConnected(BlockState state) {
        return Direction.UP;
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 4;
    }

    protected int getAge(BlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    public BlockState withAge(int age) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), Integer.valueOf(age));
    }

    public boolean isMaxAge(BlockState state) {
        return state.getValue(this.getAgeProperty()) >= this.getMaxAge();
    }

    public boolean isRandomlyTicking(BlockState state) {
        return !this.isMaxAge(state);
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;
        int i = this.getAge(state);
        if (i < this.getMaxAge()) {
            float f = getGrowthChance(this, level, pos);
            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, pos, state, random.nextFloat() <= (0.166f * f))) {
                level.setBlock(pos, this.withAge(i + 1), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
            }
        }
    }

    public void grow(Level level, BlockPos pos, BlockState state) {
        int i = this.getAge(state) + 1;
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        level.setBlock(pos, this.withAge(i), 2);
    }

    public float getGrowthChance(Block block, BlockGetter level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof IGrowableCrystal growable) {
            if (growable.getGrowingPower() > 0) {
                return growable.getGrowingPower();
            }
        }
        return 1f;
    }

    @Override
    public java.util.List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> items = super.getDrops(state, builder);
        for (ItemStack stack : items) {
            if (stack.getItem() instanceof CrystalItem) {
                CrystalUtil.createCrystalItemStats(stack, type, builder.getLevel(), type.getRandomStats());
            }
            if (stack.getItem() instanceof FracturedCrystalItem) {
                CrystalUtil.createCrystalItemStats(stack, type, builder.getLevel(), type.getRandomStats());
            }
        }
        return items;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.getBlockEntity(pos) instanceof IGrowableCrystal growable) {
            if (growable.getGrowingPower() > 0) {
                Color color = type.getColor();
                if (random.nextFloat() < 0.1f * growable.getGrowingPower()) {
                    ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                            .setLifetime(30)
                            .randomVelocity(0.015f)
                            .randomOffset(0.25f)
                            .spawn(level, pos.getX() + 0.5f, pos.getY() + 0.35f, pos.getZ() + 0.5f);
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrystalGrowthBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            WizardsRebornPacketHandler.sendToTracking(level, pos, new CrystalGrowthBreakPacket(pos, type.getColor(), 5 * (getAge(state) + 1)));
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return (getAge(state) / getMaxAge()) * 14;
    }
}
