package mod.maxbogomol.wizards_reborn.common.spell.look.entity;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.CrossSpellHeartsPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.CrossSpellSkullsPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class CurseCrossSpell extends EntityLookSpell {
    public CurseCrossSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.FIRE);
        addCrystalType(WizardsRebornCrystals.VOID);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.curseSpellColor;
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

            RayHitResult hit = getEntityHit(level, spellContext);
            Vec3 pos = hit.getPos();
            if (hit.hasEntities()) {
                for (Entity entity : hit.getEntities()) {
                    if (entity instanceof LivingEntity livingEntity) {
                        if (!livingEntity.isInvertedHealAndHarm()) {
                            DamageSource damageSource = getDamage(WizardsRebornDamageTypes.ARCANE_MAGIC, spellContext.getEntity());
                            livingEntity.hurt(damageSource, damage);
                            WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new CrossSpellSkullsPacket(pos, getColor()));
                        } else {
                            livingEntity.heal(damage);
                            WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new CrossSpellHeartsPacket(pos, getColor()));
                        }
                    }
                }
            }
        }
    }
}
