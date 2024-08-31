package mod.maxbogomol.wizards_reborn.common.spell.projectile;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.awt.*;

public class FrostProjectileSpell extends ProjectileSpell {
    public FrostProjectileSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.WATER_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return WizardsReborn.frostSpellColor;
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, world, projectile, player, target);

        int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
        float damage = (float) (3.5f + (focusLevel * 0.5)) + magicModifier;

        target.clearFire();
        int frost = target.getTicksFrozen() + 75;
        if (frost > 250) frost = 250;
        target.setTicksFrozen(frost);

        target.hurt(new DamageSource(target.damageSources().freeze().typeHolder(), projectile, player), damage);
    }
}