package mod.maxbogomol.wizards_reborn.common.spell.look.strike;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

import java.awt.*;

public class RepentanceSpell extends StrikeSpell {
    public RepentanceSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.EARTH);
        addCrystalType(WizardsRebornCrystals.AIR);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.holySpellColor;
    }
/*
    @Override
    public void strikeDamage(SpellProjectileEntity entity, Player player) {
        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
        float distance = (0.75f + ((focusLevel + magicModifier) * 0.25f));
        float damage = (30 + ((focusLevel + magicModifier) * 5f));
        float heal = (15 + ((focusLevel + magicModifier) * 5f));

        for (Entity target : getTargets(entity, distance)) {
            if (target instanceof LivingEntity livingEntity) {
                if (livingEntity.isInvertedHealAndHarm()) {
                    target.hurt(new DamageSource(WizardsRebornDamage.create(target.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder(), entity, player), damage);
                } else {
                    livingEntity.heal(heal);
                }
            }
        }
    }*/
}
