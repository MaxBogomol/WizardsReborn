package mod.maxbogomol.wizards_reborn.common.spell.charge;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.awt.*;

public class FireChargeSpell extends ChargeSpell {

    public FireChargeSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.FIRE);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.fireSpellColor;
    }

    @Override
    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult, Entity target) {
        super.onImpact(level, entity, hitResult, target);

        if (!entity.level().isClientSide()) {
            int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
            float damage = (5.0f + (focusLevel * 1.5f)) + (magicModifier * 2f) + WizardsRebornConfig.SPELL_CHARGE_DAMAGE.get().floatValue() + WizardsRebornConfig.FIRE_CHARGE_DAMAGE.get().floatValue();
            ChargeSpellComponent spellComponent = getSpellComponent(entity);

            float charge = (float) (0.5f + ((spellComponent.charge / getCharge()) / 2f));
            damage = damage * charge;

            int fire = target.getRemainingFireTicks() + 10;
            if (fire > 50) fire = 50;
            target.setSecondsOnFire(fire);
            target.setTicksFrozen(0);

            DamageSource damageSource = getDamage(DamageTypes.ON_FIRE, entity, entity.getOwner());
            target.hurt(damageSource, damage);
        }
    }
}
