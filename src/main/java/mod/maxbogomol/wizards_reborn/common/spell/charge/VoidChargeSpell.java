package mod.maxbogomol.wizards_reborn.common.spell.charge;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

import java.awt.*;

public class VoidChargeSpell extends ChargeSpell {
    public VoidChargeSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.VOID);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.voidSpellColor;
    }
/*
    @Override
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, level, projectile, player, target);

        int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player) * 2;
        float damage = (5.5f + (focusLevel * 1.5f)) + magicModifier;

        if (projectile.getSpellData() != null) {
            float charge = 0.75f + (((float) projectile.getSpellData().getInt("charge") / getCharge()) / 4f);
            damage = damage * charge;
        }

        target.hurt(new DamageSource(WizardsRebornDamage.create(target.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder(), projectile, player), damage);
    }*/
}
