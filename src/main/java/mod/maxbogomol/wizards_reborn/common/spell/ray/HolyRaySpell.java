package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import mod.maxbogomol.wizards_reborn.common.damage.DamageSourceRegistry;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.HolyRaySpellEffectPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.List;

public class HolyRaySpell extends RaySpell {
    public HolyRaySpell(String id, int points) {
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

        if (player != null) {
            if (!player.isShiftKeyDown()) {
                if (target.tickCount % 10 == 0) {
                    ItemStack stack = player.getItemInHand(player.getUsedItemHand());
                    if (WissenItemUtils.canRemoveWissen(stack, getWissenCostWithStat(projectile.getStats(), player))) {
                        if (target instanceof LivingEntity livingEntity) {
                            int focusLevel = CrystalUtils.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
                            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
                            float damage = (float) (1.0f + (focusLevel * 0.5)) + magicModifier;
                            boolean effect = false;
                            if (livingEntity.isInvertedHealAndHarm()) {
                                target.hurt(new DamageSource(DamageSourceRegistry.create(target.level(), DamageSourceRegistry.ARCANE_MAGIC).typeHolder(), projectile, player), damage);
                                removeWissen(stack, projectile.getStats(), player);
                                effect = true;
                            } else {
                                if (livingEntity.getHealth() != livingEntity.getMaxHealth()) {
                                    livingEntity.heal(damage);
                                    removeWissen(stack, projectile.getStats(), player);
                                    effect = true;
                                }
                            }

                            if (effect) {
                                Color color = getColor();
                                float r = color.getRed() / 255f;
                                float g = color.getGreen() / 255f;
                                float b = color.getBlue() / 255f;

                                PacketHandler.sendToTracking(world, player.getOnPos(), new HolyRaySpellEffectPacket((float) target.getX(), (float) target.getY() + (target.getBbHeight() / 2), (float) target.getZ(), r, g, b));
                            }
                        }
                    }
                }
            } else {
                int focusLevel = CrystalUtils.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
                healAura(world, ray.getLocation(), focusLevel + 1, projectile, player);
            }
        }
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player) {
        super.onImpact(ray, world, projectile, player);

        if (player != null) {
            if (player.isShiftKeyDown()) {
                int focusLevel = CrystalUtils.getStatLevel(projectile.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
                healAura(world, ray.getLocation(), focusLevel + 1, projectile, player);
            }
        }
    }

    public void healAura(Level world, Vec3 pos, int radius, SpellProjectileEntity projectile, Player player) {
        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
        List<Entity> entities = world.getEntitiesOfClass(Entity.class,  new AABB(pos.x - radius,pos.y - radius,pos.z - radius,pos.x + radius,pos.y + radius,pos.z + radius));
        for (Entity entity : entities) {
            if (entity.tickCount % 20 == 0) {
                if (WissenItemUtils.canRemoveWissen(stack, getWissenCostWithStat(projectile.getStats(), player))) {
                    if (entity instanceof LivingEntity livingEntity) {
                        boolean effect = false;
                        if (livingEntity.isInvertedHealAndHarm()) {
                            entity.hurt(new DamageSource(entity.damageSources().magic().typeHolder(), projectile, player), 1);
                            removeWissen(stack, projectile.getStats(), player);
                            effect = true;
                        } else {
                            if (livingEntity.getHealth() != livingEntity.getMaxHealth()) {
                                livingEntity.heal(1);
                                removeWissen(stack, projectile.getStats(), player);
                                effect = true;
                            }
                        }

                        if (effect) {
                            Color color = getColor();
                            float r = color.getRed() / 255f;
                            float g = color.getGreen() / 255f;
                            float b = color.getBlue() / 255f;

                            PacketHandler.sendToTracking(world, player.getOnPos(), new HolyRaySpellEffectPacket((float) entity.getX(), (float) entity.getY() + (entity.getBbHeight() / 2), (float) entity.getZ(), r, g, b));
                        }
                    }
                }
            }
        }
    }
}