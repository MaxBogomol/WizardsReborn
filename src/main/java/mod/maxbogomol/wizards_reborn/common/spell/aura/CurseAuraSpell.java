package mod.maxbogomol.wizards_reborn.common.spell.aura;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

import java.awt.*;

public class CurseAuraSpell extends AuraSpell {
    public CurseAuraSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.FIRE);
        addCrystalType(WizardsRebornCrystals.VOID);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.curseSpellColor;
    }
/*
    @Override
    public void onAura(Level level, SpellProjectileEntity projectile, Player player, List<Entity> targets) {
        super.onAura(level, projectile, player, targets);

        if (projectile.tickCount % 20 == 0) {
            int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
            float damage = (float) (0.5f + (focusLevel * 0.5)) + magicModifier;
            for (Entity target : targets) {
                if (target instanceof LivingEntity livingEntity) {
                    boolean effect = false;
                    boolean effectHurt = false;
                    if (livingEntity.getMobType() != MobType.UNDEAD) {
                        livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                        target.hurt(WizardsRebornDamage.create(target.level(), WizardsRebornDamage.ARCANE_MAGIC), damage);
                        effectHurt = true;
                    } else {
                        if (livingEntity.getHealth() != livingEntity.getMaxHealth()) {
                            livingEntity.heal(damage);
                            effect = true;
                        }
                    }

                    Color color = getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    if (effect) PacketHandler.sendToTracking(level, player.getOnPos(), new HolyRaySpellEffectPacket((float) target.getX(), (float) target.getY() + (target.getBbHeight() / 2), (float) target.getZ(), r, g, b));
                    if (effectHurt) PacketHandler.sendToTracking(level, player.getOnPos(), new AuraSpellBurstEffectPacket((float) target.getX(), (float) target.getY() + (target.getBbHeight() / 2), (float) target.getZ(), r, g, b));
                }
            }

        }
    }*/
}
