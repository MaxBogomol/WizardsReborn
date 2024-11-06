package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.fluffy_fur.common.raycast.RayCast;
import mod.maxbogomol.fluffy_fur.common.raycast.RayCastContext;
import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class EmberRaySpell extends FireRaySpell {
    public Color secondColor = new Color(0.979f, 0.912f, 0.585f);

    public EmberRaySpell(String id, int points) {
        super(id, points);
    }

    @Override
    public int getCooldown() {
        return 80;
    }

    @Override
    public int getWissenCost() {
        return 60;
    }

    @Override
    public Color getSecondColor() {
        return secondColor;
    }

    @Override
    public float getRayDistance() {
        return 35f;
    }

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
    }

    @Override
    public void hitTick(SpellEntity entity, RayHitResult hitResult) {
        if (!entity.level().isClientSide()) {
            boolean remove = false;
            int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
            float damage = (1f + (focusLevel * 0.5f)) + magicModifier;

            for (Entity target : RayCast.getHitEntities(entity.level(), entity.position(), hitResult.getPos(), 0.2f)) {
                if (!target.equals(entity.getOwner()) && target instanceof LivingEntity) {
                    if (target.tickCount % 10 == 0) {
                        if (entity.getSpellContext().canRemoveWissen(this)) {
                            remove = true;
                            int fire = target.getRemainingFireTicks() + 20;
                            if (fire > 1000) fire = 1000;
                            target.setSecondsOnFire(fire);
                            target.setTicksFrozen(0);

                            DamageSource damageSource = getDamage(DamageTypes.ON_FIRE, entity, entity.getOwner());
                            target.hurt(damageSource, damage);
                            target.invulnerableTime = 0;
                            damageSource = getDamage(WizardsRebornDamageTypes.ARCANE_MAGIC, entity, entity.getOwner());
                            target.hurt(damageSource, 1);
                        }
                    }
                }
            }
            if (remove) entity.getSpellContext().removeWissen(this);
        }
    }

    @Override
    public RayHitResult getHit(SpellEntity entity, Vec3 start, Vec3 end) {
        RayCastContext context = new RayCastContext(start, end);
        return RayCast.getHit(entity.level(), context);
    }

    @Override
    public int getBlockTicks(SpellEntity projectile, int focusLevel) {
        return 3;
    }

    @Override
    public int getBlockWissen(SpellEntity projectile, int focusLevel) {
        return 10;
    }
}
