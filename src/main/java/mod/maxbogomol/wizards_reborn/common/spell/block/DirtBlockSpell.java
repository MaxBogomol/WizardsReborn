package mod.maxbogomol.wizards_reborn.common.spell.block;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

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

    @Override
    public boolean placeBlock(Level level, SpellContext spellContext, BlockPos blockPos) {
        if (!level.isClientSide()) {
            BlockState blockState = Blocks.DIRT.defaultBlockState();
            int focusLevel = CrystalUtil.getStatLevel(spellContext.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(spellContext.getEntity());
            if (random.nextFloat() < 0.15f + (0.05f * (focusLevel + magicModifier))) {
                blockState = blockList.get(random.nextInt(blockList.size())).defaultBlockState();
            }
            setBlock(level, spellContext, blockPos, blockState);
        }
        return true;
    }
}
