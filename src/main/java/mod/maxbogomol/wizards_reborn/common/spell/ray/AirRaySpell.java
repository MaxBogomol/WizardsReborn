package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.AirRaySpellEffectPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class AirRaySpell extends RaySpell {
    public AirRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.AIR_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return new Color(230, 173, 134);
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, world, projectile, player, target);

        if (target.tickCount % 10 == 0) {
            ItemStack stack = player.getItemInHand(player.getUsedItemHand());
            removeWissen(stack, projectile.getStats());
            int focusLevel = CrystalUtils.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
            target.hurt(new DamageSource(target.damageSources().fall().typeHolder(), projectile, player), (float) (1.5f + (focusLevel * 0.5)));
            if (player.isShiftKeyDown()) {
                if (target instanceof LivingEntity livingEntity) {
                    livingEntity.knockback(((float) focusLevel * 0.5F), Mth.sin(projectile.getYRot() * ((float) Math.PI / 180F)), (-Mth.cos(projectile.getYRot() * ((float) Math.PI / 180F))));

                    Color color = getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    Vec3 pos = ray.getLocation().add(projectile.getLookAngle());
                    Vec3 vel = player.getEyePosition().add(player.getLookAngle().scale(40)).subtract(pos).scale(1.0 / 10).normalize().scale(0.2f);

                    PacketHandler.sendToTracking(world, player.getOnPos(), new AirRaySpellEffectPacket((float) pos.x(), (float) pos.y() + (target.getBbHeight() / 2), (float) pos.z(), (float) vel.x(), (float) vel.y(), (float) vel.z(), r, g, b));
                }
            }
        }
    }
}
