package mod.maxbogomol.wizards_reborn.common.spell.look.block;

import mod.maxbogomol.fluffy_fur.common.raycast.RayCast;
import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.spell.look.LookSpell;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BlockLookSpell extends LookSpell {

    public BlockLookSpell(String id, int points) {
        super(id, points);
    }

    public double getBlockDistance() {
        return 10f;
    }

    public double getBlockAdditionalDistance() {
        return 0f;
    }

    public double getBlockDistance(SpellContext spellContext) {
        int focusLevel = CrystalUtil.getStatLevel(spellContext.getStats(), WizardsRebornCrystals.FOCUS);
        return getBlockDistance() + (getBlockAdditionalDistance() * focusLevel);
    }

    @Override
    public boolean canLookSpell(Level level, SpellContext spellContext) {
        return getBlockHit(level, spellContext).hasBlock();
    }

    public RayHitResult getBlockHit(Level level, SpellContext spellContext) {
        Vec3 lookPos = getHit(level, spellContext).getPos();
        return RayCast.getHit(level, lookPos, new Vec3(lookPos.x(), lookPos.y() - getBlockDistance(spellContext), lookPos.z()));
    }
}
