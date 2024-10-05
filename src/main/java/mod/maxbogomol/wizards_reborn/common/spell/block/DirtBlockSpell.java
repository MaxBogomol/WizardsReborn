package mod.maxbogomol.wizards_reborn.common.spell.block;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DirtBlockSpell extends BlockPlaceSpell {
    List<Block> blockList = new ArrayList<>();

    public DirtBlockSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.EARTH);
        blockList.add(Blocks.GRASS_BLOCK);
        blockList.add(Blocks.COARSE_DIRT);
        blockList.add(Blocks.ROOTED_DIRT);
        blockList.add(Blocks.PODZOL);
        blockList.add(Blocks.MYCELIUM);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.earthSpellColor;
    }
/*
    @Override
    public InteractionResult placeBlock(ItemStack stack, UseOnContext context, BlockPos blockPos) {
        Level level = context.getLevel();
        BlockPos blockPos1 = blockPos.relative(context.getClickedFace());
        BlockState blockState = Blocks.DIRT.defaultBlockState();

        CompoundTag stats = getStats(stack);
        int focusLevel = CrystalUtil.getStatLevel(stats, WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(context.getPlayer());
        if (random.nextFloat() < 0.15f + (0.05f* (focusLevel + magicModifier))) {
            blockState = blockList.get(random.nextInt(blockList.size())).defaultBlockState();
        }
        setBlock(level, blockPos1, blockState, context.getPlayer());
        return InteractionResult.SUCCESS;
    }*/
}
