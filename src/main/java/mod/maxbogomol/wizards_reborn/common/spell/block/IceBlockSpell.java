package mod.maxbogomol.wizards_reborn.common.spell.block;

import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.awt.*;

public class IceBlockSpell extends BlockPlaceSpell {

    public IceBlockSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.frostSpellColor;
    }

    @Override
    public boolean placeBlock(Level level, SpellContext spellContext, BlockPos blockPos) {
        if (!level.isClientSide()) {
            setBlock(level, spellContext, blockPos, Blocks.ICE.defaultBlockState());
        }
        return true;
    }
}
