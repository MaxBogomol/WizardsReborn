package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.AirRaySpellEffectPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
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
        addCrystalType(WizardsRebornCrystals.AIR);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.airSpellColor;
    }

    @Override
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, level, projectile, player, target);

        if (player != null) {
            if (target.tickCount % 10 == 0) {
                ItemStack stack = player.getItemInHand(player.getUsedItemHand());
                if (WissenItemUtil.canRemoveWissen(stack, getWissenCostWithStat(projectile.getStats(), player))) {
                    removeWissen(stack, projectile.getStats(), player);
                    int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
                    float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
                    float damage = (float) (1.5f + (focusLevel * 0.5)) + magicModifier;
                    target.hurt(new DamageSource(target.damageSources().fall().typeHolder(), projectile, player), damage);
                    if (player.isShiftKeyDown()) {
                        if (target instanceof LivingEntity livingEntity) {
                            livingEntity.knockback(((focusLevel + magicModifier) * 0.5F), Mth.sin(projectile.getYRot() * ((float) Math.PI / 180F)), (-Mth.cos(projectile.getYRot() * ((float) Math.PI / 180F))));

                            Color color = getColor();
                            float r = color.getRed() / 255f;
                            float g = color.getGreen() / 255f;
                            float b = color.getBlue() / 255f;

                            Vec3 pos = ray.getLocation().add(projectile.getLookAngle());
                            Vec3 vel = player.getEyePosition().add(player.getLookAngle().scale(40)).subtract(pos).scale(1.0 / 10).normalize().scale(0.2f);

                            PacketHandler.sendToTracking(level, player.getOnPos(), new AirRaySpellEffectPacket((float) pos.x(), (float) pos.y() + (target.getBbHeight() / 2), (float) pos.z(), (float) vel.x(), (float) vel.y(), (float) vel.z(), r, g, b));
                        }
                    }
                }
            }
        }
    }
}
