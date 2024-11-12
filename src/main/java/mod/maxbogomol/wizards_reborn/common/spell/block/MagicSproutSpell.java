package mod.maxbogomol.wizards_reborn.common.spell.block;

import mod.maxbogomol.fluffy_fur.util.BlockUtil;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.MagicSproutSpellPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.awt.*;

public class MagicSproutSpell extends Spell {

    public MagicSproutSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.EARTH);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.earthSpellColor;
    }

    @Override
    public boolean canUseSpell(Level level, SpellContext spellContext) {
        return false;
    }

    @Override
    public boolean useSpellOn(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            BlockPos blockPos = spellContext.getBlockPos();
            if (BlockUtil.growCrop(level, blockPos)) {
                if (!spellContext.getAlternative()) {
                    useGrow(level, spellContext, 1f, 1f);
                    WizardsRebornPacketHandler.sendToTracking(level, blockPos, new MagicSproutSpellPacket(blockPos.getCenter(), getColor()));
                } else if (spellContext.canRemoveWissen(this, getWissenCost() * 10)) {
                    int focusLevel = CrystalUtil.getStatLevel(spellContext.getStats(), WizardsRebornCrystals.FOCUS);
                    float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(spellContext.getEntity());
                    int radius = (int) (1 + magicModifier);
                    for (int x = -radius; x <= radius; x++) {
                        for (int y = -radius; y <= radius; y++) {
                            for (int z = -radius; z <= radius; z++) {
                                if (random.nextFloat() < 0.15f * (focusLevel + magicModifier)) {
                                    if (BlockUtil.growCrop(level, new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z))) {
                                        WizardsRebornPacketHandler.sendToTracking(level, blockPos, new MagicSproutSpellPacket(new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z).getCenter(), getColor()));
                                    }
                                }
                            }
                        }
                    }
                    useGrow(level, spellContext, 5f, 10f);
                }
                return true;
            }
        }
        return false;
    }

    public void useGrow(Level level, SpellContext spellContext, float cooldownModifier, float costModifier) {
        spellContext.setCooldown(this, (int) (getCooldown() * cooldownModifier));
        spellContext.removeWissen(this, (int) (getWissenCost() * costModifier));
        spellContext.awardStat(this);
        spellContext.spellSound(this);
    }
}
