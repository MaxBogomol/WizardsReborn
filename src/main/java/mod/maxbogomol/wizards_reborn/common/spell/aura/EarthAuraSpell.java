package mod.maxbogomol.wizards_reborn.common.spell.aura;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

import java.awt.*;

public class EarthAuraSpell extends AuraSpell {
    public EarthAuraSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.EARTH);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.earthSpellColor;
    }
/*
    @Override
    public void onAura(Level level, SpellProjectileEntity projectile, Player player, List<Entity> targets) {
        super.onAura(level, projectile, player, targets);

        if (projectile.tickCount % 20 == 0) {
            int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
            float damage = (float) (1.0f + (focusLevel * 0.5)) + magicModifier;
            for (Entity target : targets) {
                if (target instanceof LivingEntity livingEntity) {
                    if (!target.equals(player)) {
                        DamageSource damageSource = new DamageSource(target.damageSources().generic().typeHolder());
                        livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                        livingEntity.hurt(damageSource, damage);
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (100 + (50 * (focusLevel + magicModifier))), 0));
                        if (target instanceof Player targetPlayer) {
                            targetPlayer.getInventory().hurtArmor(damageSource, damage, Inventory.ALL_ARMOR_SLOTS);
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, (int) (200 + (150 * (focusLevel + magicModifier))), 0));
                        }

                        Color color = getColor();
                        float r = color.getRed() / 255f;
                        float g = color.getGreen() / 255f;
                        float b = color.getBlue() / 255f;

                        PacketHandler.sendToTracking(level, player.getOnPos(), new AuraSpellBurstEffectPacket((float) target.getX(), (float) target.getY() + (target.getBbHeight() / 2), (float) target.getZ(), r, g, b));
                    } else {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 0, true, false, true));
                    }
                }
            }
        }
    }*/
}
