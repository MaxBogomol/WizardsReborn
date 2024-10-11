package mod.maxbogomol.wizards_reborn.common.spell.look;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.BlinkSpellPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamage;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class BlinkSpell extends LookSpell {
    public boolean isSharp;

    public BlinkSpell(String id, int points, boolean isSharp) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.VOID);
        this.isSharp = isSharp;
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.voidSpellColor;
    }

    @Override
    public int getCooldown() {
        return 100;
    }

    @Override
    public int getWissenCost() {
        return 100;
    }

    @Override
    public float getLookDistance() {
        if (isSharp) return 6f;
        return 5f;
    }

    @Override
    public int getMinimumPolishingLevel() {
        if (isSharp) return 1;
        return 0;
    }

    @Override
    public float getLookAdditionalDistance() {
        return 0.5f;
    }

    @Override
    public void lookSpell(Level level, SpellContext spellContext) {
        Vec3 pos = getHitPos(level, spellContext).getPosHit();
        Entity entity = spellContext.getEntity();

        if (entity != null) {
            PacketHandler.sendToTracking(level, entity.blockPosition(), new BlinkSpellPacket(spellContext.getPos(), pos, getColor(), isSharp ? 1 : 0));
            //PacketHandler.sendTo(player, new SetAdditionalFovPacket(30f));
            level.playSound(WizardsReborn.proxy.getPlayer(), pos.x(), pos.y(), pos.z(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1f, 0.95f);

            int focusLevel = CrystalUtil.getStatLevel(spellContext.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(spellContext.getEntity());
            float damage = (3f + (focusLevel)) + magicModifier;

            if (isSharp) {
                for (Entity target : getHitEntities(level, spellContext.getPos(), pos, 0.5f)) {
                    if (!target.equals(spellContext.getEntity()) && target instanceof LivingEntity) {
                        DamageSource damageSource = getDamage(WizardsRebornDamage.create(target.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder(), target, entity);
                        target.hurt(damageSource, damage);
                    }
                }
            }
            entity.teleportTo(pos.x(), pos.y(), pos.z());
            entity.setDeltaMovement(Vec3.ZERO);
            entity.fallDistance = 0;

            if (magicModifier > 0) {
                if (spellContext.getEntity() instanceof LivingEntity livingEntity) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, (int) (40 * magicModifier), 0));
                }
            }
        }
    }
}
