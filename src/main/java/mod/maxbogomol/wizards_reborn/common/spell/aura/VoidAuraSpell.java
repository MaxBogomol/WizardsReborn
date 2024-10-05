package mod.maxbogomol.wizards_reborn.common.spell.aura;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

import java.awt.*;

public class VoidAuraSpell extends AuraSpell {
    public VoidAuraSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.VOID);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.voidSpellColor;
    }
/*
    @Override
    public void onAura(Level level, SpellProjectileEntity projectile, Player player, List<Entity> targets) {
        super.onAura(level, projectile, player, targets);

        if (projectile.tickCount % 20 == 0) {
            int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
            float damage = (float) (1.75f + (focusLevel * 0.5)) + magicModifier;
            for (Entity target : targets) {
                if (target instanceof LivingEntity livingEntity && !target.equals(player)) {
                    DamageSource damageSource = WizardsRebornDamage.create(target.level(), WizardsRebornDamage.ARCANE_MAGIC);
                    livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                    livingEntity.hurt(damageSource, damage);

                    Color color = getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    PacketHandler.sendToTracking(level, player.getOnPos(), new AuraSpellBurstEffectPacket((float) target.getX(), (float) target.getY() + (target.getBbHeight() / 2), (float) target.getZ(), r, g, b));
                }
            }
        }
    }*/
}
