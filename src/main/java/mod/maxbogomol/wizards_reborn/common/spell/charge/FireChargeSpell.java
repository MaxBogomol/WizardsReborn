package mod.maxbogomol.wizards_reborn.common.spell.charge;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

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
/*
    @Override
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, level, projectile, player, target);

        int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player) * 2;
        float damage = (5.0f + (focusLevel * 1.5f)) + magicModifier;

        if (projectile.getSpellData() != null) {
            float charge = 0.75f + (((float) projectile.getSpellData().getInt("charge") / getCharge()) / 4f);
            damage = damage * charge;
        }

        int fire = target.getRemainingFireTicks() + 5;
        if (fire > 10) fire = 10;
        target.setSecondsOnFire(fire);
        target.setTicksFrozen(0);

        target.hurt(new DamageSource(target.damageSources().onFire().typeHolder(), projectile, player), damage);
    }*/
}
