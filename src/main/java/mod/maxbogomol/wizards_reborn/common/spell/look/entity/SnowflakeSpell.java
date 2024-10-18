package mod.maxbogomol.wizards_reborn.common.spell.look.entity;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.SnowflakeSpellPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class SnowflakeSpell extends EntityLookSpell {

    public SnowflakeSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.frostSpellColor;
    }

    @Override
    public int getCooldown() {
        return 50;
    }

    @Override
    public int getWissenCost() {
        return 80;
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
            float damage = (float) (0.75f + (focusLevel * 0.5)) + magicModifier;

            RayHitResult hit = getEntityHit(level, spellContext);
            Vec3 pos = hit.getPos();
            if (hit.hasEntities()) {
                for (Entity entity : hit.getEntities()) {
                    if (entity instanceof LivingEntity livingEntity) {
                        DamageSource damageSource = new DamageSource(entity.damageSources().freeze().typeHolder(), spellContext.getEntity());
                        entity.hurt(damageSource, damage);
                        entity.clearFire();

                        int frost = entity.getTicksFrozen() + 250 + (focusLevel * 60) + (int) (40 * magicModifier);
                        if (frost <= 1000) entity.setTicksFrozen(frost);

                        PacketHandler.sendToTracking(level, entity.blockPosition(), new SnowflakeSpellPacket(pos, getColor()));
                    }
                }
            }
        }
    }
}
