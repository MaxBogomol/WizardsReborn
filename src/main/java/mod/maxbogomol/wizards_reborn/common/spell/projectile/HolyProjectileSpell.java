package mod.maxbogomol.wizards_reborn.common.spell.projectile;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.awt.*;

public class HolyProjectileSpell extends ProjectileSpell {
    public HolyProjectileSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.EARTH_CRYSTAL_TYPE);
        addCrystalType(WizardsReborn.AIR_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return WizardsReborn.holySpellColor;
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, world, projectile, player, target);

        if (target instanceof LivingEntity livingEntity) {
            int focusLevel = CrystalUtils.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
            float damage = (float) (1.5f + (focusLevel * 0.5)) + magicModifier;
            if (livingEntity.getMobType() == MobType.UNDEAD) {
                target.hurt(new DamageSource(target.damageSources().magic().typeHolder(), projectile, player), damage);
            } else {
                livingEntity.heal(damage);
            }
        }
    }
}