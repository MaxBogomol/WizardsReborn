package mod.maxbogomol.wizards_reborn.common.spell.block;

import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.awt.*;

public class LavaBlockSpell extends BlockPlaceSpell {

    public LavaBlockSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.FIRE);
    }

    @Override
    public int getCooldown() {
        return 100;
    }

    @Override
    public int getWissenCost() {
        return 100;
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.fireSpellColor;
    }

    @Override
    public boolean placeBlock(Level level, SpellContext spellContext, BlockPos blockPos) {
        if (!level.isClientSide()) {
            setBlock(level, spellContext, blockPos, Blocks.LAVA.defaultBlockState());
        }
        return true;
    }

    @Override
    public boolean isNoEntity(Level level, SpellContext spellContext, BlockPos blockPos) {
        return true;
    }
}
