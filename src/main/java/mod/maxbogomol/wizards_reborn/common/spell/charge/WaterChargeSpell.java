package mod.maxbogomol.wizards_reborn.common.spell.charge;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

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
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, level, projectile, player, target);

        int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player) * 2;
        float damage = (3.0f + (focusLevel * 1.5f)) + magicModifier;

        if (projectile.getSpellData() != null) {
            float charge = 0.75f + (((float) projectile.getSpellData().getInt("charge") / getCharge()) / 4f);
            damage = damage * charge;
        }

        target.clearFire();
        int frost = target.getTicksFrozen() + 10;
        if (frost > 250) frost = 250;
        target.setTicksFrozen(frost);

        target.hurt(new DamageSource(target.damageSources().drown().typeHolder(), projectile, player), damage);
    }
}
