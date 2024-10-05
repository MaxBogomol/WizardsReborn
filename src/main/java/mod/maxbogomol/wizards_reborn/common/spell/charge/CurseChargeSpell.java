package mod.maxbogomol.wizards_reborn.common.spell.charge;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

import java.awt.*;

public class CurseChargeSpell extends ChargeSpell {
    public CurseChargeSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.FIRE);
        addCrystalType(WizardsRebornCrystals.VOID);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.curseSpellColor;
    }
/*
    @Override
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, level, projectile, player, target);

        if (target instanceof LivingEntity livingEntity) {
            int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
            float damage = (2.5f + (focusLevel * 1.5f)) + magicModifier;

            if (projectile.getSpellData() != null) {
                float charge = 0.75f + (((float) projectile.getSpellData().getInt("charge") / getCharge()) / 4f);
                damage = damage * charge;
            }

            if (livingEntity.getMobType() != MobType.UNDEAD) {
                target.hurt(new DamageSource(WizardsRebornDamage.create(target.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder(), projectile, player), damage);
            } else {
                livingEntity.heal(damage);
            }
        }
    }*/
}