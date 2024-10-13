package mod.maxbogomol.wizards_reborn.common.spell.projectile;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.awt.*;

public class FireProjectileSpell extends ProjectileSpell {

    public FireProjectileSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.FIRE);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.fireSpellColor;
    }

    @Override
    public void onImpact(Level level, SpellEntity entity, HitResult hitResult, Entity target) {
        super.onImpact(level, entity, hitResult, target);

        if (!level.isClientSide()) {
            int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
            float damage = (float) (3.5f + (focusLevel * 0.5)) + magicModifier;
            DamageSource damageSource = getDamage(target.damageSources().onFire().typeHolder(), entity, entity.getOwner());
            int fire = target.getRemainingFireTicks() + 5;
            if (fire <= 10) target.setSecondsOnFire(fire);
            target.setTicksFrozen(0);
            target.hurt(damageSource, damage);
        }
    }
}
