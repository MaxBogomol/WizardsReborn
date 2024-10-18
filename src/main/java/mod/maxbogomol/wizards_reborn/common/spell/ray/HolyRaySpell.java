package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.CrossSpellHeartsPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.CrossSpellSkullsPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamage;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.awt.*;

public class HolyRaySpell extends RaySpell {

    public HolyRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.EARTH);
        addCrystalType(WizardsRebornCrystals.AIR);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.holySpellColor;
    }

    @Override
    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult, Entity target) {
        super.onImpact(level, entity, hitResult, target);

        if (!entity.level().isClientSide()) {
            if (target instanceof LivingEntity livingEntity) {
                if (target.tickCount % 10 == 0) {
                    if (entity.getSpellContext().canRemoveWissen(this)) {
                        entity.getSpellContext().removeWissen(this);
                        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
                        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
                        float damage = (1.0f + (focusLevel * 0.5f)) + magicModifier;
                        if (livingEntity.isInvertedHealAndHarm()) {
                            DamageSource damageSource = getDamage(WizardsRebornDamage.create(target.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder(), entity, entity.getOwner());
                            target.hurt(damageSource, damage);
                            PacketHandler.sendToTracking(level, entity.blockPosition(), new CrossSpellSkullsPacket(hitResult.getPos(), getColor()));
                        } else {
                            livingEntity.heal(damage);
                            PacketHandler.sendToTracking(level, entity.blockPosition(), new CrossSpellHeartsPacket(hitResult.getPos(), getColor()));
                        }
                    }
                }
            }/* else {
                int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
                healAura(level, ray.getLocation(), focusLevel + 1, projectile, player);
            }*/
}
    }
/*
    @Override
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player) {
        super.onImpact(ray, level, projectile, player);

        if (player != null) {
            if (player.isShiftKeyDown()) {
                int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
                healAura(level, ray.getLocation(), focusLevel + 1, projectile, player);
            }
        }
    }

    public void healAura(Level level, Vec3 pos, int radius, SpellProjectileEntity projectile, Player player) {
        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
        List<Entity> entities = level.getEntitiesOfClass(Entity.class,  new AABB(pos.x - radius,pos.y - radius,pos.z - radius,pos.x + radius,pos.y + radius,pos.z + radius));
        for (Entity entity : entities) {
            if (entity.tickCount % 20 == 0) {
                if (WissenItemUtil.canRemoveWissen(stack, getWissenCostWithStat(projectile.getStats(), player))) {
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

                            PacketHandler.sendToTracking(level, player.getOnPos(), new HolyRaySpellEffectPacket((float) entity.getX(), (float) entity.getY() + (entity.getBbHeight() / 2), (float) entity.getZ(), r, g, b));
                        }
                    }
                }
            }
        }
    }*/
}