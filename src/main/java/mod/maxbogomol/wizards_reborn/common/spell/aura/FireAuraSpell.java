package mod.maxbogomol.wizards_reborn.common.spell.aura;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

import java.awt.*;

public class FireAuraSpell extends AuraSpell {
    public FireAuraSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.FIRE);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.fireSpellColor;
    }
/*
    @Override
    public void onAura(Level level, SpellProjectileEntity projectile, Player player, List<Entity> targets) {
        super.onAura(level, projectile, player, targets);

        if (projectile.tickCount % 20 == 0) {
            int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
            float damage = (float) (0.75f + (focusLevel * 0.5)) + magicModifier;
            for (Entity target : targets) {
                if (target instanceof LivingEntity livingEntity) {
                    if (!target.equals(player)) {
                        int fire = target.getRemainingFireTicks() + 30;
                        if (fire > 100) fire = 100;
                        target.setSecondsOnFire(fire);
                        target.setTicksFrozen(0);

                        DamageSource damageSource = new DamageSource(target.damageSources().onFire().typeHolder());
                        livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                        livingEntity.hurt(damageSource, damage);

                        Color color = getColor();
                        float r = color.getRed() / 255f;
                        float g = color.getGreen() / 255f;
                        float b = color.getBlue() / 255f;

                        PacketHandler.sendToTracking(level, player.getOnPos(), new FireRaySpellEffectPacket((float) target.getX(), (float) target.getY() + (target.getBbHeight() / 2), (float) target.getZ(), r, g, b));
                    } else {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0, true, false, true));
                        livingEntity.setTicksFrozen(0);
                    }
                }
            }
        }
    }*/
}
