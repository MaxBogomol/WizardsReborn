package mod.maxbogomol.wizards_reborn.common.spell.charge;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.ProjectileSpellHeartsPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.ProjectileSpellSkullsPacket;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.awt.*;

public class HolyChargeSpell extends ChargeSpell {

    public HolyChargeSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.EARTH);
        addCrystalType(WizardsRebornCrystals.AIR);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.holySpellColor;
    }

    @Override
    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult, Entity target) {
        super.onImpact(level, entity, hitResult, target);

        if (!entity.level().isClientSide()) {
            if (target instanceof LivingEntity livingEntity) {
                int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
                float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
                float damage = (2.5f + (focusLevel * 1.5f)) + (magicModifier * 2f) + WizardsRebornConfig.SPELL_CHARGE_DAMAGE.get().floatValue() + WizardsRebornConfig.HOLY_CHARGE_DAMAGE.get().floatValue();
                ChargeSpellComponent spellComponent = getSpellComponent(entity);

                float charge = (float) (0.5f + ((spellComponent.charge / getCharge()) / 2f));
                damage = damage * charge;

                if (livingEntity.isInvertedHealAndHarm()) {
                    DamageSource damageSource = getDamage(WizardsRebornDamageTypes.ARCANE_MAGIC, entity, entity.getOwner());
                    target.hurt(damageSource, damage);
                    WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new ProjectileSpellSkullsPacket(entity.position(), getColor()));
                } else {
                    livingEntity.heal(damage);
                    WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new ProjectileSpellHeartsPacket(entity.position(), getColor()));
                }
            }
        }
    }

    @Override
    public void burstEffectEntity(Level level, SpellEntity entity) {

    }
}