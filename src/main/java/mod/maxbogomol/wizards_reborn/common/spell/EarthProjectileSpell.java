package mod.maxbogomol.wizards_reborn.common.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.awt.*;

public class EarthProjectileSpell extends ProjectileSpell {
    public EarthProjectileSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.EARTH_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return new Color(138, 201, 123);
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, world, projectile, player, target);

        int focusLevel = CrystalUtils.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
        float damage = (float) (4.5f + (focusLevel * 0.5));
        DamageSource damageSource = new DamageSource(target.damageSources().generic().typeHolder(), projectile, player);
        target.hurt(damageSource, damage);
        player.getInventory().hurtArmor(damageSource, damage, Inventory.ALL_ARMOR_SLOTS);
    }
}
