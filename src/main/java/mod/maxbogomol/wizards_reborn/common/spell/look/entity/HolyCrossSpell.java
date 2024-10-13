package mod.maxbogomol.wizards_reborn.common.spell.look.entity;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.CrossSpellHeartsPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.CrossSpellSkullsPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamage;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class HolyCrossSpell extends EntityLookSpell {
    public HolyCrossSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.EARTH);
        addCrystalType(WizardsRebornCrystals.AIR);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.holySpellColor;
    }

    @Override
    public int getCooldown() {
        return 20;
    }

    @Override
    public int getWissenCost() {
        return 10;
    }

    @Override
    public double getLookAdditionalDistance() {
        return 0.5f;
    }

    @Override
    public void lookSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            int focusLevel = CrystalUtil.getStatLevel(spellContext.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(spellContext.getEntity());
            float damage = (float) (1.0f + ((focusLevel + magicModifier) * 0.5));

            HitResult hit = getEntityHit(level, spellContext);
            Vec3 pos = hit.getPosHit();
            if (hit.hasEntities()) {
                for (Entity entity : hit.getEntities()) {
                    if (entity instanceof LivingEntity livingEntity) {
                        if (livingEntity.isInvertedHealAndHarm()) {
                            DamageSource damageSource = new DamageSource(WizardsRebornDamage.create(livingEntity.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder(), spellContext.getEntity());
                            livingEntity.hurt(damageSource, damage);
                            PacketHandler.sendToTracking(level, entity.blockPosition(), new CrossSpellSkullsPacket(pos, getColor()));
                        } else {
                            livingEntity.heal(damage);
                            PacketHandler.sendToTracking(level, entity.blockPosition(), new CrossSpellHeartsPacket(pos, getColor()));
                        }
                    }
                }
            }
        }
    }
}
