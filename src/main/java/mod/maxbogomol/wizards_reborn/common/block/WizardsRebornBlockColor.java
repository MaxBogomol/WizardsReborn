package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class WizardsRebornBlockColor {

    private static final Plants PLANTS_INSTANCE = new Plants();

    public static final Block[] PLANTS = {
            WizardsRebornBlocks.PETALS_OF_INNOCENCE.get()
    };

    public static Plants getPlantsInstance() {
        return PLANTS_INSTANCE;
    }

    public static class Plants implements BlockColor {
        @Override
        public int getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int tintIndex) {
            if (state.getBlock() == WizardsRebornBlocks.PETALS_OF_INNOCENCE.get() && tintIndex == 1) {
                return level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : -1;
            }
            return -1;
        }
    }
}