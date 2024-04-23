package mod.maxbogomol.wizards_reborn.common.spell.aura;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.List;

public class AirAuraSpell extends AuraSpell {
    public AirAuraSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.AIR_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return WizardsReborn.airSpellColor;
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
                    DamageSource damageSource = new DamageSource(target.damageSources().fall().typeHolder());
                    livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                    livingEntity.hurt(damageSource, damage);
                    target.push(0, 0.5f + (focusLevel * 0.1f), 0);
                    target.hurtMarked = true;
                }
            }
        }
    }
}
