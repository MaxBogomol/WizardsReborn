package mod.maxbogomol.wizards_reborn.common.levelgen;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.maxbogomol.wizards_reborn.registry.common.levelgen.WizardsRebornTrunkPlacerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class ArcaneWoodTrunkPlacer extends TrunkPlacer {

    public ArcaneWoodTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return WizardsRebornTrunkPlacerTypes.ARCANE_WOOD.get();
    }

    public static final Codec<ArcaneWoodTrunkPlacer> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(Codec.intRange(0, 32).fieldOf("base_height").forGetter(placer -> placer.baseHeight),
                            Codec.intRange(0, 24).fieldOf("height_rand_a").forGetter(placer -> placer.heightRandA),
                            Codec.intRange(0, 24).fieldOf("height_rand_b").forGetter(placer -> placer.heightRandB))
                    .apply(builder, ArcaneWoodTrunkPlacer::new));

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> consumer, RandomSource random, int foliageHeight, BlockPos pos, TreeConfiguration baseTreeFeatureConfig) {
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        int numBranches = 0;
        int lastBranch = 0;
        double branchChance = 0.85;

        for (int i = 0; i < foliageHeight; ++i) {
            if (i == foliageHeight - 2) {
                branchChance = 0.85;
            }
            boolean northB = random.nextFloat() <= branchChance;
            boolean southB = random.nextFloat() <= branchChance;
            boolean eastB = random.nextFloat() <= branchChance;
            boolean westB = random.nextFloat() <= branchChance;
            branchChance = 0.35;

            int j2 = y + i;
            BlockPos blockpos1 = new BlockPos(x, j2, z);
            if (TreeFeature.isAirOrLeaves(level, blockpos1)) {
                placeLog(level, consumer, random, blockpos1, baseTreeFeatureConfig);
            }

            if (((i > 1 && i > lastBranch)) && (i <= foliageHeight - 2)) {
                if (northB) {
                    addBranch(level, pos, i, Direction.NORTH, random, baseTreeFeatureConfig, consumer);
                    lastBranch = i + 3;
                    numBranches++;
                    northB = false;
                }
                if (southB) {
                    addBranch(level, pos, i, Direction.SOUTH, random, baseTreeFeatureConfig, consumer);
                    lastBranch = i + 3;
                    numBranches++;
                    southB = false;
                }
                if (eastB) {
                    addBranch(level, pos, i, Direction.EAST, random, baseTreeFeatureConfig, consumer);
                    lastBranch = i + 3;
                    numBranches++;
                    eastB = false;
                }
                if (westB) {
                    addBranch(level, pos, i, Direction.WEST, random, baseTreeFeatureConfig, consumer);
                    lastBranch = i + 3;
                    numBranches++;
                    westB = false;
                }
            }

            if (i > foliageHeight - 6) {
                addBlock(level, pos.above(i).north(), baseTreeFeatureConfig.foliageProvider.getState(random, pos.above(i).north()), consumer);
                addBlock(level, pos.above(i).south(), baseTreeFeatureConfig.foliageProvider.getState(random, pos.above(i).south()), consumer);
                addBlock(level, pos.above(i).west(), baseTreeFeatureConfig.foliageProvider.getState(random, pos.above(i).west()), consumer);
                addBlock(level, pos.above(i).east(), baseTreeFeatureConfig.foliageProvider.getState(random, pos.above(i).east()), consumer);

                if (random.nextFloat() < 0.5f) {
                    addBlock(level, pos.above(i).north(2), baseTreeFeatureConfig.foliageProvider.getState(random, pos.above(i).north()), consumer);
                }

                if (random.nextFloat() < 0.5f) {
                    addBlock(level, pos.above(i).south(2), baseTreeFeatureConfig.foliageProvider.getState(random, pos.above(i).south()), consumer);
                }

                if (random.nextFloat() < 0.5f) {
                    addBlock(level, pos.above(i).west(2), baseTreeFeatureConfig.foliageProvider.getState(random, pos.above(i).west()), consumer);
                }

                if (random.nextFloat() < 0.5f) {
                    addBlock(level, pos.above(i).east(2), baseTreeFeatureConfig.foliageProvider.getState(random, pos.above(i).east()), consumer);
                }
            }

            if (i == foliageHeight - 1) {
                addBlock(level, pos.above(i + 1), baseTreeFeatureConfig.foliageProvider.getState(random, pos.above(i + 1)), consumer);
                addBlock(level, pos.above(i + 2), baseTreeFeatureConfig.foliageProvider.getState(random, pos.above(i + 2)), consumer);
            }
        }

        addBlock(level, pos, baseTreeFeatureConfig.trunkProvider.getState(random, pos), consumer);

        list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(x, y, z), 0, true));
        return list;
    }

    public void addBranch(LevelSimulatedReader level, BlockPos pos, int height, Direction d, RandomSource random, TreeConfiguration baseTreeFeatureConfig, BiConsumer<BlockPos, BlockState> consumer) {
        pos = pos.above(height);
        addLog(level, pos.relative(d).above(1), random, baseTreeFeatureConfig, consumer);
        addLog(level, pos.relative(d).above(2), random, baseTreeFeatureConfig, consumer);
        addLog(level, pos.relative(d, 2).above(2), random, baseTreeFeatureConfig, consumer);
        addLog(level, pos.relative(d, 3).above(2), random, baseTreeFeatureConfig, consumer);
        addLog(level, pos.relative(d, 3).above(1), random, baseTreeFeatureConfig, consumer);
        addLog(level, pos.relative(d, 3).above(0), random, baseTreeFeatureConfig, consumer);

        addLineLeaves(level, pos.relative(d).above(1), d, 3, random, baseTreeFeatureConfig, consumer);
        addLineLeaves(level, pos.relative(d).above(2), d, 3, random, baseTreeFeatureConfig, consumer);
        addLineLeaves(level, pos.relative(d).above(3), d, 3, random, baseTreeFeatureConfig, consumer);

        for (int j = 1; j < 4; j++) {
            addLineLeaves(level, pos.relative(d, j).above(3), d, 3, random, baseTreeFeatureConfig, 0.9f, consumer);
            addLineLeaves(level, pos.relative(d, j).above(2), d, 3, random, baseTreeFeatureConfig, 0.9f, consumer);
            addLineLeaves(level, pos.relative(d, j).above(4), d, 3, random, baseTreeFeatureConfig, .1f, consumer);
        }

        for (int i = 0; i < 2; i++) {
            addHollowLine(level, pos.relative(d, 2 + i).above(1), d, 2, random, baseTreeFeatureConfig, consumer);
            addHollowLine(level, pos.relative(d, 2 + i).above(2), d, 2, random, baseTreeFeatureConfig, consumer);
            addHollowLine(level, pos.relative(d, 2 + i).above(1), d, 3, random, baseTreeFeatureConfig, 0.1f, consumer);
            addHollowLine(level, pos.relative(d, 2 + i).above(2), d, 3, random, baseTreeFeatureConfig, 0.1f, consumer);
        }

        addLineLeaves(level, pos.relative(d, 4).above(1), d, 3, random, baseTreeFeatureConfig, consumer);
        addLineLeaves(level, pos.relative(d, 4).above(2), d, 3, random, baseTreeFeatureConfig, consumer);

        addLineLeaves(level, pos.relative(d, 5).above(1), d, 3, random, baseTreeFeatureConfig, 0.1f, consumer);
        addLineLeaves(level, pos.relative(d, 5).above(2), d, 3, random, baseTreeFeatureConfig, 0.1f, consumer);
    }

    public void addLineLeaves(LevelSimulatedReader level, BlockPos pos, Direction d, int length, RandomSource rand, TreeConfiguration baseTreeFeatureConfig, BiConsumer<BlockPos, BlockState> consumer) {
        if (length % 2 == 0)
            addLineLeavesEven(level, pos, d, length, rand, baseTreeFeatureConfig, 1.0f, consumer);
        else
            addLineLeavesOdd(level, pos, d, length, rand, baseTreeFeatureConfig, 1.0f, consumer);
    }

    public void addLineLeaves(LevelSimulatedReader level, BlockPos pos, Direction d, int length, RandomSource rand, TreeConfiguration baseTreeFeatureConfig, float chance, BiConsumer<BlockPos, BlockState> consumer) {
        if (length % 2 == 0)
            addLineLeavesEven(level, pos, d, length, rand, baseTreeFeatureConfig, chance, consumer);
        else
            addLineLeavesOdd(level, pos, d, length, rand, baseTreeFeatureConfig, chance, consumer);
    }

    public void addLineLeavesEven(LevelSimulatedReader level, BlockPos pos, Direction d, int length, RandomSource rand, TreeConfiguration baseTreeFeatureConfig, float chance, BiConsumer<BlockPos, BlockState> consumer) {
        Direction left = d.getClockWise();
        Direction right = left.getOpposite();

        for (int i = 0; i < length; i++) {
            if (rand.nextFloat() <= chance && TreeFeature.validTreePos(level, pos.relative(left, i - length / 3))) {
                setBlock(level, pos.relative(left, i - length / 3), baseTreeFeatureConfig.foliageProvider.getState(rand, pos.relative(left, i - length / 3)), consumer);
            }
        }
    }

    public void addLineLeavesOdd(LevelSimulatedReader level, BlockPos pos, Direction d, int length, RandomSource rand, TreeConfiguration baseTreeFeatureConfig, float chance, BiConsumer<BlockPos, BlockState> consumer) {
        Direction left = d.getClockWise();
        Direction right = left.getOpposite();
        length += 2;
        for (int i = 0; i < (length - 1) / 2; i++) {
            if (rand.nextFloat() <= chance && TreeFeature.validTreePos(level, pos.relative(left, i))) {
                setBlock(level, pos.relative(left, i), baseTreeFeatureConfig.foliageProvider.getState(rand, pos.relative(left, i)), consumer);
            }

            if (rand.nextFloat() <= chance && TreeFeature.validTreePos(level, pos.relative(right, i))) {
                setBlock(level, pos.relative(right, i), baseTreeFeatureConfig.foliageProvider.getState(rand, pos.relative(right, i)), consumer);
            }
        }
    }

    public void addHollowLine(LevelSimulatedReader level, BlockPos pos, Direction d, int length, RandomSource rand, TreeConfiguration baseTreeFeatureConfig, BiConsumer<BlockPos, BlockState> consumer) {
        addHollowLine(level, pos, d, length, rand, baseTreeFeatureConfig, 1.0f, consumer);
    }

    public void addHollowLine(LevelSimulatedReader level, BlockPos pos, Direction d, int length, RandomSource rand, TreeConfiguration baseTreeFeatureConfig, float chance, BiConsumer<BlockPos, BlockState> consumer) {
        Direction left = d.getClockWise();
        Direction right = left.getOpposite();

        if (rand.nextFloat() <= chance && TreeFeature.validTreePos(level, pos.relative(left, length))) {
            setBlock(level, pos.relative(left, length), baseTreeFeatureConfig.foliageProvider.getState(rand, pos.relative(left, length)), consumer);
        }
        if (rand.nextFloat() <= chance && TreeFeature.validTreePos(level, pos.relative(right, length))) {
            setBlock(level, pos.relative(right, length), baseTreeFeatureConfig.foliageProvider.getState(rand, pos.relative(right, length)), consumer);
        }
    }

    public boolean addLog(LevelSimulatedReader level, BlockPos pos, RandomSource random, TreeConfiguration baseTreeFeatureConfig, BiConsumer<BlockPos, BlockState> consumer) {
        return addBlock(level, pos, baseTreeFeatureConfig.trunkProvider.getState(random, pos), consumer);
    }

    public boolean addBlock(LevelSimulatedReader level, BlockPos pos, BlockState state, BiConsumer<BlockPos, BlockState> consumer) {
        if (TreeFeature.validTreePos(level, pos)) {
            setBlock(level, pos, state, consumer);
            return true;
        }
        return false;
    }

    public void setBlock(LevelSimulatedReader level, BlockPos pos, BlockState state, BiConsumer<BlockPos, BlockState> consumer) {
        consumer.accept(pos, state);
    }
}
