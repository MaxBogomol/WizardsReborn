package mod.maxbogomol.wizards_reborn.common.spell.projectile;

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

public class WaterProjectileSpell extends ProjectileSpell {

    public WaterProjectileSpell(String id, int points) {
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

        if (!level.isClientSide()) {
            int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
            float damage = (2.0f + (focusLevel * 0.5f)) + magicModifier + WizardsRebornConfig.SPELL_PROJECTILE_DAMAGE.get().floatValue() + WizardsRebornConfig.WATER_PROJECTILE_DAMAGE.get().floatValue();
            target.clearFire();
            int frost = target.getTicksFrozen() + 10;
            if (frost > 250) frost = 250;
            target.setTicksFrozen(frost);
            DamageSource damageSource = getDamage(DamageTypes.DROWN, entity, entity.getOwner());
            target.hurt(damageSource, damage);
        }
    }
}
