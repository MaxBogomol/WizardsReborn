package mod.maxbogomol.wizards_reborn.common.spell.charge;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.damage.DamageSourceRegistry;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.awt.*;

public class HolyChargeSpell extends ChargeSpell {
    public HolyChargeSpell(String id, int points) {
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
            int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
            float damage = (2.5f + (focusLevel * 1.5f)) + magicModifier;

            if (projectile.getSpellData() != null) {
                float charge = 0.75f + (((float) projectile.getSpellData().getInt("charge") / getCharge()) / 4f);
                damage = damage * charge;
            }

            if (livingEntity.isInvertedHealAndHarm()) {
                target.hurt(new DamageSource(DamageSourceRegistry.create(target.level(), DamageSourceRegistry.ARCANE_MAGIC).typeHolder(), projectile, player), damage);
            } else {
                livingEntity.heal(damage);
            }
        }
    }
}