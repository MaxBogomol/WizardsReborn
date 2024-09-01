package mod.maxbogomol.wizards_reborn.common.spell.aura;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.AuraSpellBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.List;

public class WaterAuraSpell extends AuraSpell {
    public WaterAuraSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.waterSpellColor;
    }

    @Override
    public void onAura(Level world, SpellProjectileEntity projectile, Player player, List<Entity> targets) {
        super.onAura(world, projectile, player, targets);

        if (projectile.tickCount % 20 == 0) {
            int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
            float damage = (float) (0.75f + (focusLevel * 0.5)) + magicModifier;
            for (Entity target : targets) {
                if (target instanceof LivingEntity livingEntity) {
                    if (!target.equals(player)) {
                        target.clearFire();
                        int frost = target.getTicksFrozen() + 10;
                        if (frost > 250) frost = 250;
                        target.setTicksFrozen(frost);

                        DamageSource damageSource = new DamageSource(target.damageSources().drown().typeHolder());
                        livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                        livingEntity.hurt(damageSource, damage);

                        Color color = getColor();
                        float r = color.getRed() / 255f;
                        float g = color.getGreen() / 255f;
                        float b = color.getBlue() / 255f;

                        PacketHandler.sendToTracking(world, player.getOnPos(), new AuraSpellBurstEffectPacket((float) target.getX(), (float) target.getY() + (target.getBbHeight() / 2), (float) target.getZ(), r, g, b));
                    } else {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 0, true, false, true));
                        livingEntity.clearFire();
                    }
                }
            }
        }
        for (Entity target : targets) {
            if (target instanceof LivingEntity livingEntity && !target.equals(player)) {
                if (!(livingEntity.getEffect(MobEffects.WATER_BREATHING) != null && livingEntity.getEffect(MobEffects.WATER_BREATHING).getDuration() > 0)) {
                    int air = livingEntity.getAirSupply();
                    air = air - 6;
                    livingEntity.setAirSupply(air);
                }
            }
        }
    }
}
