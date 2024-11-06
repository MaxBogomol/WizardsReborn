package mod.maxbogomol.wizards_reborn.common.spell.aura;

import mod.maxbogomol.fluffy_fur.common.damage.DamageHandler;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.AuraSpellBurstPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.List;

public class EarthAuraSpell extends AuraSpell {

    public EarthAuraSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.EARTH);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.earthSpellColor;
    }

    @Override
    public void auraTick(Level level, SpellEntity entity, List<Entity> targets) {
        super.auraTick(level, entity, targets);

        if (entity.tickCount % 20 == 0) {
            int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
            float damage = (1.0f + (focusLevel * 0.5f)) + magicModifier;
            for (Entity target : targets) {
                if (target instanceof LivingEntity livingEntity) {
                    if (!target.equals(entity.getOwner())) {
                        DamageSource damageSource = DamageHandler.create(level, DamageTypes.GENERIC);
                        livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                        livingEntity.hurt(damageSource, damage);
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (100 + (50 * (focusLevel + magicModifier))), 0));
                        if (target instanceof Player targetPlayer) {
                            targetPlayer.getInventory().hurtArmor(damageSource, damage, Inventory.ALL_ARMOR_SLOTS);
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, (int) (200 + (150 * (focusLevel + magicModifier))), 0));
                        }
                        WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new AuraSpellBurstPacket(target.position().add(0, target.getBbHeight() / 2f, 0), getColor()));
                    } else {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 0, true, false, true));
                    }
                }
            }
        }
    }
}
