package mod.maxbogomol.wizards_reborn.common.spell.aura;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.AuraSpellBurstEffectPacket;
import net.minecraft.world.damagesource.DamageSource;
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
        addCrystalType(WizardsReborn.WATER_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return WizardsReborn.waterSpellColor;
    }

    @Override
    public void onAura(Level world, SpellProjectileEntity projectile, Player player, List<Entity> targets) {
        super.onAura(world, projectile, player, targets);

        if (projectile.tickCount % 20 == 0) {
            int focusLevel = CrystalUtils.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
            float damage = (float) (0.75f + (focusLevel * 0.5)) + magicModifier;
            for (Entity target : targets) {
                if (target instanceof LivingEntity livingEntity && !target.equals(player)) {
                    DamageSource damageSource = new DamageSource(target.damageSources().drown().typeHolder());
                    livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                    livingEntity.hurt(damageSource, damage);
                    target.clearFire();
                    int frost = target.getTicksFrozen() + 10;
                    if (frost > 250) {
                        frost = 250;
                    }
                    target.setTicksFrozen(frost);

                    Color color = getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    PacketHandler.sendToTracking(world, player.getOnPos(), new AuraSpellBurstEffectPacket((float) target.getX(), (float) target.getY() + (target.getBbHeight() / 2), (float) target.getZ(), r, g, b));
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
