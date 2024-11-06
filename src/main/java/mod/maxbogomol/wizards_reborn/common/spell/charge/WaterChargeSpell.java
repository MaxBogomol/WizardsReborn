package mod.maxbogomol.wizards_reborn.common.spell.charge;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.awt.*;

public class WaterChargeSpell extends ChargeSpell {

    public WaterChargeSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.waterSpellColor;
    }

    @Override
    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult, Entity target) {
        super.onImpact(level, entity, hitResult, target);

        if (!entity.level().isClientSide()) {
            if (target instanceof LivingEntity livingEntity) {
                int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
                float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
                float damage = (3.0f + (focusLevel * 1.5f)) + (magicModifier * 2f);
                ChargeSpellComponent spellComponent = getSpellComponent(entity);

                float charge = (float) (0.5f + ((spellComponent.charge / getCharge()) / 2f));
                damage = damage * charge;

                target.clearFire();
                int frost = target.getTicksFrozen() + 10;
                if (frost > 250) frost = 250;
                target.setTicksFrozen(frost);

                DamageSource damageSource = getDamage(DamageTypes.DROWN, entity, entity.getOwner());
                target.hurt(damageSource, damage);
            }
        }
    }
}
