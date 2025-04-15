package mod.maxbogomol.wizards_reborn.common.block.plant;

import mod.maxbogomol.wizards_reborn.registry.common.levelgen.WizardsRebornFeatures;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class MorBlock extends MushroomBlock {

    public MorBlock(Properties properties) {
        super(properties, WizardsRebornFeatures.TALL_MOR);
    }

    @Override
    public boolean growMushroom(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        ResourceKey<ConfiguredFeature<?, ?>> configuredfeature = WizardsRebornFeatures.TALL_MOR;
        if (random.nextFloat() < 0.4) {
            if (this == WizardsRebornBlocks.MOR.get()) {
                configuredfeature = WizardsRebornFeatures.TALL_MOR;
            } else {
                configuredfeature = WizardsRebornFeatures.TALL_ELDER_MOR;
            }
        } else {
            if (this == WizardsRebornBlocks.MOR.get()) {
                configuredfeature = WizardsRebornFeatures.HUGE_MOR;
            } else {
                configuredfeature = WizardsRebornFeatures.HUGE_ELDER_MOR;
            }
        }

        Optional<? extends Holder<ConfiguredFeature<?, ?>>> optional = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(configuredfeature);
        if (optional.isEmpty()) {
            return false;
        } else {
            var event = net.minecraftforge.event.ForgeEventFactory.blockGrowFeature(level, random, pos, optional.get());
            if (event.getResult().equals(net.minecraftforge.eventbus.api.Event.Result.DENY)) return false;
            level.removeBlock(pos, false);
            if (event.getFeature().value().place(level, level.getChunkSource().getGenerator(), random, pos)) {
                return true;
            } else {
                level.setBlock(pos, state, 3);
                return false;
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return true;
        } else {
            return blockstate.canSustainPlant(level, blockpos, net.minecraft.core.Direction.UP, this);
        }
    }
}
