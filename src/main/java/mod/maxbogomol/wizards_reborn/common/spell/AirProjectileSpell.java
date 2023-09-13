package mod.maxbogomol.wizards_reborn.common.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.awt.*;

public class AirProjectileSpell extends ProjectileSpell {
    public AirProjectileSpell(String id) {
        super(id);
        addCrystalType(WizardsReborn.AIR_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return new Color(230, 173, 134);
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, world, projectile, player, target);

        target.hurt(new DamageSource(projectile.damageSources().fall().typeHolder(), projectile, player), 2.0f);
    }
}
