package mod.maxbogomol.wizards_reborn.common.spell.look.strike;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

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

    @Override
    public void strikeDamage(SpellEntity entity) {
        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
        float distance = (1f + ((focusLevel + magicModifier) * 0.35f));
        float damage = (20 + ((focusLevel + magicModifier) * 2.5f));
        float heal = (15 + ((focusLevel + magicModifier) * 2.5f));

        for (Entity target : getTargets(entity, distance)) {
            if (target instanceof LivingEntity livingEntity) {
                if (livingEntity.isInvertedHealAndHarm()) {
                    DamageSource damageSource = getDamage(WizardsRebornDamageTypes.ARCANE_MAGIC, entity, entity.getOwner());
                    target.hurt(damageSource, damage);
                } else {
                    livingEntity.heal(heal);
                }
            }
        }
    }
}
