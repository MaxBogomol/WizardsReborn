package mod.maxbogomol.wizards_reborn.common.entity;

import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPointBuilder;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.arcaneenchantment.SplitArrowBurstPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.ChargeSpellTrailPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class SplitArrowEntity extends AbstractArrow {
    public static final EntityDataAccessor<Boolean> fadeId = SynchedEntityData.defineId(SplitArrowEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> fadeTickId = SynchedEntityData.defineId(SplitArrowEntity.class, EntityDataSerializers.INT);

    public TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(10);

    public SplitArrowEntity(EntityType<? extends SplitArrowEntity> type, Level level) {
        super(type, level);
    }

    public SplitArrowEntity(Level pLevel, double x, double y, double z) {
        super(WizardsRebornEntities.SPLIT_ARROW_PROJECTILE.get(), x, y, z, pLevel);
    }

    public SplitArrowEntity(Level pLevel, LivingEntity pShooter) {
        super(WizardsRebornEntities.SPLIT_ARROW_PROJECTILE.get(), pShooter, pLevel);
    }

    @Override
    public void tick() {
        super.tick();
        if (!getFade()) {
            if (level().isClientSide()) {
                addTrail(new Vec3(position().toVector3f()));
            } else {
                if (tickCount > 30) {
                    setFade(true);
                    setFadeTick(11);
                }

                Color color = WizardsRebornArcaneEnchantments.SPLIT.getColor();
                Vec3 motion = getDeltaMovement();
                Vec3 pos = position();
                Vec3 norm = motion.normalize().scale(0.025f);

                WizardsRebornPacketHandler.sendToTracking(level(), new BlockPos((int) pos.x, (int) pos.y, (int) pos.z), new ChargeSpellTrailPacket(new Vec3(xo, yo, zo), pos, norm, color, 0.5f));
            }
        } else {
            setDeltaMovement(0, 0, 0);
            if (!level().isClientSide()) {
                if (getFadeTick() <= 0) {
                    discard();
                } else {
                    setFadeTick(getFadeTick() - 1);
                }
            }
        }
        if (level().isClientSide()) {
            trailPointBuilder.tickTrailPoints();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!level().isClientSide()) {
            Entity entity = result.getEntity();
            int k = entity.getRemainingFireTicks();
            if (this.isOnFire()) {
                entity.setSecondsOnFire(k);
            }

            boolean end = true;

            if (entity instanceof LivingEntity livingEntity) {
                if (this.getKnockback() > 0) {
                    double d0 = Math.max(0.0D, 1.0D - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    Vec3 vec3 = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.getKnockback() * 0.6D * d0);
                    if (vec3.lengthSqr() > 0.0D) {
                        livingEntity.push(vec3.x, 0.1D, vec3.z);
                    }
                }

                if (livingEntity.isAlive()) {
                    int invulnerableTime = livingEntity.invulnerableTime;
                    livingEntity.invulnerableTime = 0;
                    livingEntity.hurt(new DamageSource(WizardsRebornDamage.create(livingEntity.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder(), this, getOwner()), (float) getBaseDamage());
                    livingEntity.invulnerableTime = invulnerableTime;
                } else {
                    end = false;
                }
            }

            if (end) {
                setFade(true);
                setFadeTick(10);
                Color color = WizardsRebornArcaneEnchantments.SPLIT.getColor();
                WizardsRebornPacketHandler.sendToTracking(level(), blockPosition(), new SplitArrowBurstPacket(position(), color));
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        setFade(true);
        setFadeTick(11);

        if (!level().isClientSide()) {
            Color color = WizardsRebornArcaneEnchantments.SPLIT.getColor();
            WizardsRebornPacketHandler.sendToTracking(level(), blockPosition(), new SplitArrowBurstPacket(position(), color));
        }
        addTrail(result.getLocation());
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return WizardsRebornSounds.SPELL_BURST.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(fadeId, false);
        getEntityData().define(fadeTickId, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        getEntityData().set(fadeId, compound.getBoolean("fade"));
        getEntityData().set(fadeTickId, compound.getInt("fadeTick"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("fade", getEntityData().get(fadeId));
        compound.putInt("fadeTick", getEntityData().get(fadeTickId));
    }

    public boolean getFade() {
        return getEntityData().get(fadeId);
    }

    public void setFade(boolean fade) {
        getEntityData().set(fadeId, fade);
    }

    public int getFadeTick() {
        return getEntityData().get(fadeTickId);
    }

    public void setFadeTick(int fadeTick) {
        getEntityData().set(fadeTickId, fadeTick);
    }

    public void addTrail(Vec3 pos) {
        trailPointBuilder.addTrailPoint(pos);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
