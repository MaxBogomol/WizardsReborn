package mod.maxbogomol.wizards_reborn.integration.client.jade;

import mod.maxbogomol.wizards_reborn.common.block.arcanum_growth.ArcanumGrowthBlock;
import mod.maxbogomol.wizards_reborn.common.block.crystal_growth.CrystalGrowthBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

public enum CrystalGrowthProvider implements IBlockComponentProvider {
    INSTANCE;

    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockState state = accessor.getBlockState();
        Block block = state.getBlock();
        if (block instanceof CrystalGrowthBlock) {
            addMaturityTooltip(tooltip, (float) state.getValue(CrystalGrowthBlock.AGE) / 4.0F);
        }
        if (block instanceof ArcanumGrowthBlock) {
            addMaturityTooltip(tooltip, (float) state.getValue(ArcanumGrowthBlock.AGE) / 4.0F);
        }
    }

    private static void addMaturityTooltip(ITooltip tooltip, float growthValue) {
        growthValue *= 100.0F;
        if (growthValue < 100.0F) {
            tooltip.add(Component.translatable("tooltip.jade.crop_growth", new Object[]{IThemeHelper.get().info(String.format("%.0f%%", growthValue))}));
        } else {
            tooltip.add(Component.translatable("tooltip.jade.crop_growth", new Object[]{IThemeHelper.get().success(Component.translatable("tooltip.jade.crop_mature"))}));
        }

    }

    public ResourceLocation getUid() {
        return WizardsRebornJade.CRYSTAL_GROWTH;
    }
}
