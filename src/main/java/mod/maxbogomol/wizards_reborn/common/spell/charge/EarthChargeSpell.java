package mod.maxbogomol.wizards_reborn.common.spell.charge;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.awt.*;

public class EarthChargeSpell extends ChargeSpell {
    public EarthChargeSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.EARTH_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return WizardsReborn.earthSpellColor;
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, world, projectile, player, target);

        int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player) * 2;
        float damage = (6.0f + (focusLevel * 1.5f)) + magicModifier;

        if (projectile.getSpellData() != null) {
            float charge = 0.75f + (((float) projectile.getSpellData().getInt("charge") / getCharge()) / 4f);
            damage = damage * charge;
        }

        DamageSource damageSource = new DamageSource(target.damageSources().generic().typeHolder(), projectile, player);
        target.hurt(damageSource, damage);
        if (target instanceof Player targetPlayer) {
            targetPlayer.getInventory().hurtArmor(damageSource, damage, Inventory.ALL_ARMOR_SLOTS);
        }
    }
}
