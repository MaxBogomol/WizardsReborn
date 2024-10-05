package mod.maxbogomol.wizards_reborn.common.spell.aura;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

import java.awt.*;

public class AirAuraSpell extends AuraSpell {
    public AirAuraSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.AIR);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.airSpellColor;
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
                        DamageSource damageSource = new DamageSource(target.damageSources().fall().typeHolder());
                        livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                        livingEntity.hurt(damageSource, damage);
                        target.push(0, 0.5f + (focusLevel * 0.1f), 0);
                        target.hurtMarked = true;

                        Color color = getColor();
                        float r = color.getRed() / 255f;
                        float g = color.getGreen() / 255f;
                        float b = color.getBlue() / 255f;

                        PacketHandler.sendToTracking(level, player.getOnPos(), new AuraSpellBurstEffectPacket((float) target.getX(), (float) target.getY() + (target.getBbHeight() / 2), (float) target.getZ(), r, g, b));
                    } else {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 0, true, false, true));
                    }
                }
            }
        }
    }*/
}
