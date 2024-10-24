package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.CrossSpellHeartsPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.CrossSpellSkullsPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamage;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.awt.*;

public class HolyRaySpell extends RaySpell {

    public HolyRaySpell(String id, int points) {
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
                if (target.tickCount % 10 == 0) {
                    if (entity.getSpellContext().canRemoveWissen(this)) {
                        entity.getSpellContext().removeWissen(this);
                        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
                        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
                        float damage = (1.0f + (focusLevel * 0.5f)) + magicModifier;
                        if (livingEntity.isInvertedHealAndHarm()) {
                            DamageSource damageSource = getDamage(WizardsRebornDamage.create(target.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder(), entity, entity.getOwner());
                            target.hurt(damageSource, damage);
                            WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new CrossSpellSkullsPacket(hitResult.getPos(), getColor()));
                        } else {
                            livingEntity.heal(damage);
                            WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new CrossSpellHeartsPacket(hitResult.getPos(), getColor()));
                        }
                    }
                }
            }
        }
    }
}