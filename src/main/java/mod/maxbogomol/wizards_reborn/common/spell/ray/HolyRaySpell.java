package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.awt.*;

public class HolyRaySpell extends RaySpell {
    public HolyRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.EARTH_CRYSTAL_TYPE);
        addCrystalType(WizardsReborn.AIR_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return new Color(255, 248, 194);
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, world, projectile, player, target);

        if (target.tickCount % 10 == 0) {
            ItemStack stack = player.getItemInHand(player.getUsedItemHand());
            removeWissen(stack, projectile.getStats());
            if (target instanceof LivingEntity livingEntity) {
                int focusLevel = CrystalUtils.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
                if (livingEntity.getMobType() == MobType.UNDEAD) {
                    target.hurt(new DamageSource(target.damageSources().magic().typeHolder(), projectile, player), (float) (1.0f + (focusLevel * 0.5)));
                } else {
                    livingEntity.heal((float) (1.0f + (focusLevel * 0.5)));
                }
            }
        }
    }
}