package mod.maxbogomol.wizards_reborn.common.spell.look;

import mod.maxbogomol.fluffy_fur.common.raycast.RayCast;
import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class LookSpell extends Spell {

    public LookSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public void useSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            spellContext.setCooldown(this);
            spellContext.removeWissen(this);
            spellContext.awardStat(this);
            spellContext.spellSound(this);
        }
        lookSpell(level, spellContext);
    }

    @Override
    public boolean canSpell(Level level, SpellContext spellContext) {
        if (super.canSpell(level, spellContext)) {
            return canLookSpell(level, spellContext);
        }
        return false;
    }

    public double getLookDistance() {
        return 5f;
    }

    public double getLookAdditionalDistance() {
        return 0f;
    }

    public double getLookDistance(SpellContext spellContext) {
        int focusLevel = CrystalUtil.getStatLevel(spellContext.getStats(), WizardsRebornCrystals.FOCUS);
        return getLookDistance() + (getLookAdditionalDistance() * focusLevel);
    }

    public double getLookDistance(int focusLevel) {
        return getLookDistance() + (getLookAdditionalDistance() * focusLevel);
    }

    public RayHitResult getHit(Level level, SpellContext spellContext, Predicate<Entity> entityFilter, int entityCount, float size, boolean endE) {
        double distance = getLookDistance(spellContext);
        return RayCast.getHit(level, spellContext.getPos(), spellContext.getPos().add(spellContext.getVec().scale(distance)), entityFilter, entityCount, size, endE);
    }

    public RayHitResult getHit(Level level, SpellContext spellContext) {
        double distance = getLookDistance(spellContext);
        return RayCast.getHit(level, spellContext.getPos(), spellContext.getPos().add(spellContext.getVec().scale(distance)));
    }

    public boolean canLookSpell(Level level, SpellContext spellContext) {
        return true;
    }

    public void lookSpell(Level level, SpellContext spellContext) {

    }
}
