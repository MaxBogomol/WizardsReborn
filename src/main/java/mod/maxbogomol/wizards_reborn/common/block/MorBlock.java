package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.world.WorldGen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.server.level.ServerLevel;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MorBlock extends MushroomBlock {
    public MorBlock(Properties pProperties) {
        super(pProperties, WorldGen.TALL_MOR);
    }

    @Override
    public boolean growMushroom(ServerLevel pLevel, BlockPos pPos, BlockState pState, RandomSource pRandom) {
        ResourceKey<ConfiguredFeature<?, ?>> configuredfeature = WorldGen.TALL_MOR;
        if (pRandom.nextFloat() < 0.4) {
            if (this == WizardsReborn.MOR.get()) {
                configuredfeature = WorldGen.TALL_MOR;
            } else {
                configuredfeature = WorldGen.TALL_ELDER_MOR;
            }
        } else {
            if (this == WizardsReborn.ELDER_MOR.get()) {
                configuredfeature = WorldGen.HUGE_MOR;
            } else {
                configuredfeature = WorldGen.HUGE_ELDER_MOR;
            }
        }

        Optional<? extends Holder<ConfiguredFeature<?, ?>>> optional = pLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(configuredfeature);
        if (optional.isEmpty()) {
            return false;
        } else {
            var event = net.minecraftforge.event.ForgeEventFactory.blockGrowFeature(pLevel, pRandom, pPos, optional.get());
            if (event.getResult().equals(net.minecraftforge.eventbus.api.Event.Result.DENY)) return false;
            pLevel.removeBlock(pPos, false);
            if (event.getFeature().value().place(pLevel, pLevel.getChunkSource().getGenerator(), pRandom, pPos)) {
                return true;
            } else {
                pLevel.setBlock(pPos, pState, 3);
                return false;
            }
        }
    }
}
