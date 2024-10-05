package mod.maxbogomol.wizards_reborn.common.spell.self;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.AirFlowSpellPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class AirFlowSpell extends SelfSpell {

    public AirFlowSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.AIR);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.airSpellColor;
    }

    @Override
    public int getCooldown() {
        return 150;
    }

    @Override
    public int getWissenCost() {
        return 80;
    }

    @Override
    public void selfSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            if (spellContext.getEntity() instanceof LivingEntity entity) {
                int focusLevel = CrystalUtil.getStatLevel(spellContext.getStats(), WizardsRebornCrystals.FOCUS);
                float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity);

                float scale = 0.55f + (focusLevel * 0.15f);

                Vec3 vel = entity.getViewVector(0).scale(scale);
                if (entity.isFallFlying()) {
                    vel = vel.scale(0.65f);
                }
                if (entity.getTicksFrozen() > 0) {
                    vel = vel.scale(0.50f);
                }

                entity.push(vel.x(), vel.y(), vel.z());
                entity.hurtMarked = true;
                if (magicModifier > 0) {
                    entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, (int) (50 * magicModifier), 0));
                }

                PacketHandler.sendToTracking(level, entity.blockPosition(), new AirFlowSpellPacket(entity.position().add(0, 0.2f, 0), vel, getColor()));
            }
        }
    }
}
