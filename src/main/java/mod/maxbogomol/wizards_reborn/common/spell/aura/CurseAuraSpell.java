package mod.maxbogomol.wizards_reborn.common.spell.aura;

import mod.maxbogomol.fluffy_fur.common.damage.DamageHandler;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.AuraSpellBurstPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.CrossSpellHeartsPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.CrossSpellSkullsPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.List;

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

    @Override
    public void auraTick(Level level, SpellEntity entity, List<Entity> targets) {
        super.auraTick(level, entity, targets);

        if (entity.tickCount % 20 == 0) {
            int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
            float damage = (0.5f + (focusLevel * 0.5f)) + magicModifier;
            for (Entity target : targets) {
                if (target instanceof LivingEntity livingEntity) {
                    boolean effect = false;
                    boolean effectHurt = false;
                    if (!livingEntity.isInvertedHealAndHarm()) {
                        livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                        target.hurt(DamageHandler.create(target.level(), WizardsRebornDamageTypes.ARCANE_MAGIC), damage);
                        effectHurt = true;
                    } else {
                        if (livingEntity.getHealth() != livingEntity.getMaxHealth()) {
                            livingEntity.heal(damage);
                            effect = true;
                        }
                    }

                    if (effect) WizardsRebornPacketHandler.sendToTracking(level, target.blockPosition(), new CrossSpellHeartsPacket(target.position().add(0, target.getBbHeight() / 2f, 0), getColor()));
                    if (effectHurt) {
                        WizardsRebornPacketHandler.sendToTracking(level, target.blockPosition(), new CrossSpellSkullsPacket(target.position().add(0, target.getBbHeight() / 2f, 0), getColor()));
                        WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new AuraSpellBurstPacket(target.position().add(0, target.getBbHeight() / 2f, 0), getColor()));
                    }
                }
            }
        }
    }
}
