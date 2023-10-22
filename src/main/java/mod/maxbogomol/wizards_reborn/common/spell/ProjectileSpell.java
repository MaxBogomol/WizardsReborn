package mod.maxbogomol.wizards_reborn.common.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
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
    public ProjectileSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public void useSpell(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);

            CompoundTag stats = getStats(stack);
            spawnSpellStandart(world, player, stats);
            setCooldown(stack, stats);
            removeWissen(stack, stats, player);
            world.playSound(WizardsReborn.proxy.getPlayer(), player.getX(), player.getY() + player.getEyeHeight(), player.getZ(), WizardsReborn.SPELL_CAST_SOUND.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
        }
    }

    @Override
    public void entityTick(SpellProjectileEntity entity) {
        if (!entity.level().isClientSide) {
            HitResult ray = ProjectileUtil.getHitResultOnMoveVector(entity, (e) -> {
                return !e.isSpectator() && e.isPickable() && (!e.getUUID().equals(entity.getEntityData().get(entity.casterId).get()) || entity.tickCount > 5);
            });
            if (ray.getType() == HitResult.Type.ENTITY) {
                entity.onImpact(ray, ((EntityHitResult)ray).getEntity());
            } else if (ray.getType() == HitResult.Type.BLOCK) {
                entity.onImpact(ray);
            } else {
                updatePos(entity);
                updateRot(entity);
            }

            entity.rayEffect();
        } else {
            updatePos(entity);
            updateRot(entity);
        }
    }

    public void updatePos(SpellProjectileEntity entity) {
        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.x * 0.98, (motion.y > 0 ? motion.y * 0.98 : motion.y) - 0.01f, motion.z * 0.98);

        Vec3 pos = entity.position();
        entity.xo = pos.x;
        entity.yo = pos.y;
        entity.zo = pos.z;
        entity.setPos(pos.x + motion.x, pos.y + motion.y, pos.z + motion.z);
    }

    public void updateRot(SpellProjectileEntity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        double d0 = vec3.horizontalDistance();
        entity.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        entity.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        entity.yRotO = entity.getYRot();
        entity.xRotO = entity.getXRot();
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        projectile.remove();
        projectile.burstEffect();
        world.playSound(WizardsReborn.proxy.getPlayer(), projectile.getX(), projectile.getY(), player.getZ(), WizardsReborn.SPELL_BURST_SOUND.get(), SoundSource.BLOCKS, 0.35f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player) {
        projectile.remove();
        projectile.setPos(ray.getLocation().x, ray.getLocation().y, ray.getLocation().z);
        projectile.burstEffect();
        world.playSound(WizardsReborn.proxy.getPlayer(), projectile.getX(), projectile.getY(), projectile.getZ(), WizardsReborn.SPELL_BURST_SOUND.get(), SoundSource.BLOCKS, 0.35f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
    }
}
