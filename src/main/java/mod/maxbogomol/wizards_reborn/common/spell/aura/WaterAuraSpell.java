package mod.maxbogomol.wizards_reborn.common.spell.aura;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.FrostAuraSpellBurstPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.List;

public class WaterAuraSpell extends AuraSpell {

    public WaterAuraSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.waterSpellColor;
    }

    @Override
    public void auraTick(Level level, SpellEntity entity, List<Entity> targets) {
        super.auraTick(level, entity, targets);

        if (entity.tickCount % 20 == 0) {
            int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
            float damage = (0.75f + (focusLevel * 0.5f)) + magicModifier;
            for (Entity target : targets) {
                if (target instanceof LivingEntity livingEntity) {
                    if (!target.equals(entity.getOwner())) {
                        target.clearFire();
                        int frost = target.getTicksFrozen() + 10;
                        if (frost > 250) frost = 250;
                        target.setTicksFrozen(frost);
                        DamageSource damageSource = new DamageSource(target.damageSources().drown().typeHolder());
                        livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                        livingEntity.hurt(damageSource, damage);
                        WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new FrostAuraSpellBurstPacket(target.position().add(0, target.getBbHeight() / 2f, 0), getColor()));
                    } else {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 0, true, false, true));
                        livingEntity.clearFire();
                    }
                }
            }
        }
        for (Entity target : targets) {
            if (target instanceof LivingEntity livingEntity && !target.equals(entity.getOwner())) {
                if (!(livingEntity.getEffect(MobEffects.WATER_BREATHING) != null && livingEntity.getEffect(MobEffects.WATER_BREATHING).getDuration() > 0)) {
                    int air = livingEntity.getAirSupply();
                    air = air - 6;
                    livingEntity.setAirSupply(air);
                }
            }
        }
    }
}
