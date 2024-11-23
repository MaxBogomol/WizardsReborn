package mod.maxbogomol.wizards_reborn.common.spell.look.strike;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.awt.*;

public class IncinerationSpell extends StrikeSpell {
    public Color secondColor = new Color(0.979f, 0.912f, 0.585f);

    public IncinerationSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.FIRE);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.fireSpellColor;
    }

    @Override
    public Color getSecondColor() {
        return secondColor;
    }

    @Override
    public int getCooldown() {
        return 350;
    }

    @Override
    public int getWissenCost() {
        return 600;
    }

    @Override
    public void strikeDamage(SpellEntity entity) {
        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
        float distance = (1f + ((focusLevel + magicModifier) * 0.35f));
        float damage = (10 + ((focusLevel + magicModifier) * 5f));

        for (Entity target : getTargets(entity, distance)) {
            if (target instanceof LivingEntity livingEntity) {
                int fire = target.getRemainingFireTicks() + 250;
                if (fire > 1000) fire = 1000;
                target.setSecondsOnFire(fire);
                target.setTicksFrozen(0);
                DamageSource damageSource = getDamage(DamageTypes.ON_FIRE, entity, entity.getOwner());
                target.hurt(damageSource, damage);
                target.invulnerableTime = 0;
                damageSource = getDamage(WizardsRebornDamageTypes.ARCANE_MAGIC, entity, entity.getOwner());
                target.hurt(damageSource, 10);
            }
        }
    }
}
