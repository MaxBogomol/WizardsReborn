package mod.maxbogomol.wizards_reborn.common.spell.aura;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

import java.awt.*;

public class WaterAuraSpell extends AuraSpell {
    public WaterAuraSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.waterSpellColor;
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
                        target.clearFire();
                        int frost = target.getTicksFrozen() + 10;
                        if (frost > 250) frost = 250;
                        target.setTicksFrozen(frost);

                        DamageSource damageSource = new DamageSource(target.damageSources().drown().typeHolder());
                        livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                        livingEntity.hurt(damageSource, damage);

                        Color color = getColor();
                        float r = color.getRed() / 255f;
                        float g = color.getGreen() / 255f;
                        float b = color.getBlue() / 255f;

                        PacketHandler.sendToTracking(level, player.getOnPos(), new AuraSpellBurstEffectPacket((float) target.getX(), (float) target.getY() + (target.getBbHeight() / 2), (float) target.getZ(), r, g, b));
                    } else {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 0, true, false, true));
                        livingEntity.clearFire();
                    }
                }
            }
        }
        for (Entity target : targets) {
            if (target instanceof LivingEntity livingEntity && !target.equals(player)) {
                if (!(livingEntity.getEffect(MobEffects.WATER_BREATHING) != null && livingEntity.getEffect(MobEffects.WATER_BREATHING).getDuration() > 0)) {
                    int air = livingEntity.getAirSupply();
                    air = air - 6;
                    livingEntity.setAirSupply(air);
                }
            }
        }
    }*/
}
