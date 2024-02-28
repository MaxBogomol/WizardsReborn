package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class CustomBlockColor implements BlockColor {

    private static final CustomBlockColor INSTANCE = new CustomBlockColor();

    public static final Block[] PLANTS = {
            WizardsReborn.PETALS_OF_INNOCENCE.get()
    };

    public static CustomBlockColor getInstance() {
        return INSTANCE;
    }

    @Override
    public int getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int tintIndex) {
        if (state.getBlock() == WizardsReborn.PETALS_OF_INNOCENCE.get() && tintIndex == 1) {
            return level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : -1;
        }
        return -1;
    }
}