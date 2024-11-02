package mod.maxbogomol.wizards_reborn.common.spell.charge;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.awt.*;

public class FrostChargeSpell extends ChargeSpell {

    public FrostChargeSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.frostSpellColor;
    }

    @Override
    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult, Entity target) {
        super.onImpact(level, entity, hitResult, target);

        if (!entity.level().isClientSide()) {
            int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
            float damage = (4.5f + (focusLevel * 1.5f)) + (magicModifier * 2f);
            ChargeSpellComponent spellComponent = getSpellComponent(entity);

            float charge = (float) (0.5f + ((spellComponent.charge / getCharge()) / 2f));
            damage = damage * charge;

            target.clearFire();
            int frost = target.getTicksFrozen() + 75;
            if (frost > 250) frost = 250;
            target.setTicksFrozen(frost);

            DamageSource damageSource = getDamage(target.damageSources().freeze().typeHolder(), entity, entity.getOwner());
            target.hurt(damageSource, damage);
        }
    }
}