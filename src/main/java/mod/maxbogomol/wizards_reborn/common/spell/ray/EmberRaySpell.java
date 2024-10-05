package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;

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

/*    @Override
    public void rayTick(SpellProjectileEntity entity, HitResult ray) {
        Player player = entity.getSender();

        if (player != null) {
            boolean remove = false;

            ItemStack stack = player.getItemInHand(player.getUsedItemHand());
            int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
            float damage = (1f + (focusLevel * 0.5f)) + magicModifier;


            for (Entity target : LookSpell.getHitEntities(entity.level(), player.getEyePosition(), ray.getLocation(), 0.2f)) {
                if (!target.equals(player) && target instanceof LivingEntity) {
                    if (target.tickCount % 10 == 0) {
                        if (WissenItemUtil.canRemoveWissen(stack, getWissenCostWithStat(entity.getStats(), player))) {
                            remove = true;
                            int fire = target.getRemainingFireTicks() + 20;
                            if (fire > 1000) fire = 1000;
                            target.setSecondsOnFire(fire);
                            target.setTicksFrozen(0);

                            target.hurt(new DamageSource(target.damageSources().onFire().typeHolder(), entity, player), damage);
                            target.invulnerableTime = 0;
                            target.hurt(new DamageSource(WizardsRebornDamage.create(target.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder(), entity, player), 1);
                        }
                    }
                }
            }

            if (remove) removeWissen(stack, entity.getStats(), player);
        }
    }

    @Override
    public HitResult getHitResult(SpellProjectileEntity pProjectile, Vec3 pStartVec, Vec3 pEndVecOffset, Level pLevel, Predicate<Entity> pFilter) {
        return getHitResultStandard(pProjectile, pStartVec, pEndVecOffset, pLevel, (e) -> {return false;});
    }*/

    @Override
    public int getBlockTicks(SpellEntity projectile, int focusLevel) {
        return 3;
    }

    @Override
    public int getBlockWissen(SpellEntity projectile, int focusLevel) {
        return 10;
    }
}
