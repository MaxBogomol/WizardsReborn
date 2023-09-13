package mod.maxbogomol.wizards_reborn.common.spell;

import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ProjectileSpell extends Spell {
    public ProjectileSpell(String id) {
        super(id);
    }

    @Override
    public int getCooldown() {
        return 20;
    }

    @Override
    public int getWissenCost() {
        return 50;
    }

    @Override
    public float getCooldownStatModifier() {
        return 0.15f;
    }

    @Override
    public float getWissenStatModifier() {
        return 0.15f;
    }

    @Override
    public void useSpell(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        CompoundTag stats = getStats(stack);
        spawnSpellStandart(world, player, stats);
        setCooldown(stack, stats);
        removeWissen(stack, stats);
    }

    @Override
    public void entityTick(SpellProjectileEntity entity) {
        if (!entity.level().isClientSide) {
            HitResult ray = ProjectileUtil.getHitResultOnMoveVector(entity, (e) -> {
                return !e.isSpectator() && e.isPickable() && (!e.getUUID().equals(entity.getEntityData().get(entity.casterId).get()) || entity.tickCount > 5);
            });
            if (ray.getType() == HitResult.Type.ENTITY) {
                entity.onImpact(ray, ((EntityHitResult)ray).getEntity());
            }
            else if (ray.getType() == HitResult.Type.BLOCK) {
                entity.onImpact(ray);
            }
        }

        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.x * 0.98, (motion.y > 0 ? motion.y * 0.98 : motion.y) - 0.01f, motion.z * 0.98);

        Vec3 pos = entity.position();
        entity.xo = pos.x;
        entity.yo = pos.y;
        entity.zo = pos.z;
        entity.setPos(pos.x + motion.x, pos.y + motion.y, pos.z + motion.z);

        if (!entity.level().isClientSide) {
            entity.rayEffect();
        }
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        projectile.remove();
        projectile.burstEffect();
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player) {
        projectile.remove();
        projectile.setPos(ray.getLocation().x, ray.getLocation().y, ray.getLocation().z);
        projectile.burstEffect();
    }
}
