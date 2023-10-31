package mod.maxbogomol.wizards_reborn.common.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.awt.*;

public class FireProjectileSpell extends ProjectileSpell {
    public FireProjectileSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.FIRE_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return new Color(225, 127, 103);
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, world, projectile, player, target);

        int focusLevel = CrystalUtils.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
        target.hurt(new DamageSource(target.damageSources().onFire().typeHolder(), projectile, player), (float) (3.5f + (focusLevel * 0.5)));
        int fire = target.getRemainingFireTicks() + 5;
        if (fire > 10) {
            fire = 10;
        }
        target.setSecondsOnFire(fire);
    }
}
