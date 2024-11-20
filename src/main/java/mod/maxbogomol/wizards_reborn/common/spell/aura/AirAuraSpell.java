package mod.maxbogomol.wizards_reborn.common.spell.aura;

import mod.maxbogomol.fluffy_fur.common.damage.DamageHandler;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.AuraSpellBurstPacket;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.List;

public class AirAuraSpell extends AuraSpell {

    public AirAuraSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.AIR);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.airSpellColor;
    }

    @Override
    public void auraTick(Level level, SpellEntity entity, List<Entity> targets) {
        super.auraTick(level, entity, targets);

        if (entity.tickCount % 20 == 0) {
            int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
            float damage = (0.75f + (focusLevel * 0.5f)) + magicModifier + WizardsRebornConfig.SPELL_AURA_DAMAGE.get().floatValue() + WizardsRebornConfig.AIR_AURA_DAMAGE.get().floatValue();
            for (Entity target : targets) {
                if (target instanceof LivingEntity livingEntity) {
                    if (!target.equals(entity.getOwner())) {
                        DamageSource damageSource = DamageHandler.create(level, DamageTypes.FALL);
                        livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                        livingEntity.hurt(damageSource, damage);
                        target.push(0, 0.5f + (focusLevel * 0.1f), 0);
                        target.hurtMarked = true;
                        WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new AuraSpellBurstPacket(target.position().add(0, target.getBbHeight() / 2f, 0), getColor()));
                    } else {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 0, true, false, true));
                    }
                }
            }
        }
    }
}
