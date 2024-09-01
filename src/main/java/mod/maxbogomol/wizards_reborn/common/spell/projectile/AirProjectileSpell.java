package mod.maxbogomol.wizards_reborn.common.spell.projectile;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.awt.*;

public class AirProjectileSpell extends ProjectileSpell {
    public AirProjectileSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.AIR);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.airSpellColor;
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, world, projectile, player, target);

        int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
        float damage = (float) (2.0f + (focusLevel * 0.5)) + magicModifier;
        target.hurt(new DamageSource(target.damageSources().fall().typeHolder(), projectile, player), damage);
    }
}
