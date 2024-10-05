package mod.maxbogomol.wizards_reborn.common.entity;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.CubeParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPointBuilder;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornEntities;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamage;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.awt.*;
import java.util.List;
import java.util.*;

public class ThrowedScytheEntity extends ThrowableItemProjectile {
    public static final EntityDataAccessor<Optional<UUID>> ownerId = SynchedEntityData.defineId(ThrowedScytheEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<Integer> endTickId = SynchedEntityData.defineId(ThrowedScytheEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> blockId = SynchedEntityData.defineId(ThrowedScytheEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> blockTickId = SynchedEntityData.defineId(ThrowedScytheEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> fadeId = SynchedEntityData.defineId(ThrowedScytheEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> fadeTickId = SynchedEntityData.defineId(ThrowedScytheEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> baseDamageId = SynchedEntityData.defineId(ThrowedScytheEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> magicDamageId = SynchedEntityData.defineId(ThrowedScytheEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Integer> slotId = SynchedEntityData.defineId(ThrowedScytheEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> endPointXId = SynchedEntityData.defineId(ThrowedScytheEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> endPointYId = SynchedEntityData.defineId(ThrowedScytheEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> endPointZId = SynchedEntityData.defineId(ThrowedScytheEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Boolean> isRightId = SynchedEntityData.defineId(ThrowedScytheEntity.class, EntityDataSerializers.BOOLEAN);

    public TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(30);
    public Map<UUID, Integer> damagedEntities = new HashMap<>();

    public ThrowedScytheEntity(EntityType<?> type, Level level) {
        super(WizardsRebornEntities.THROWED_SCYTHE_PROJECTILE.get(), level);
        noPhysics = false;
    }

    public ThrowedScytheEntity(Level level) {
        super(WizardsRebornEntities.THROWED_SCYTHE_PROJECTILE.get(), level);
    }

    public ThrowedScytheEntity(Level level, double pX, double pY, double pZ) {
        super(WizardsRebornEntities.THROWED_SCYTHE_PROJECTILE.get(), pX, pY, pZ, level);
    }

    public void setData(Entity owner, float baseDamage, float magicDamage, int slot, boolean isRight) {
        setOwner(owner);
        getEntityData().set(ownerId, Optional.of(owner.getUUID()));
        setBaseDamage(baseDamage);
        setMagicDamage(magicDamage);
        setSlot(slot);
        setIsRight(isRight);
    }

    @Override
    public void tick() {
        if (!getFade()) {
            HitResult ray = ProjectileUtil.getHitResultOnMoveVector(this, (e) -> {
                return !e.isSpectator() && e.isPickable() && (!e.getUUID().equals(this.getSenderUUID()) || this.tickCount > 5);
            });

            if (!level().isClientSide()) {
                Vec3 mov = getDeltaMovement();
                for (int i = 0; i < 5; i++) {
                    double lerpX = Mth.lerp((double) i / 5f, getX(), mov.x());
                    double lerpY = Mth.lerp((double) i / 5f, getY(), mov.y());
                    double lerpZ = Mth.lerp((double) i / 5f, getZ(), mov.z());
                    List<LivingEntity> entityList = level().getEntitiesOfClass(LivingEntity.class, new AABB(lerpX - 1, lerpY - 0.1f, lerpZ - 1, lerpX + 1, lerpY + 0.1f, lerpZ + 1));
                    if (tickCount < 5) {
                        if (getOwner() instanceof LivingEntity living) {
                            if (entityList.contains(living)) {
                                entityList.remove(living);
                            }
                        }
                    }
                    for (LivingEntity target : entityList) {
                        if (!damagedEntities.keySet().contains(target.getUUID())) {
                            hitEntity(target);
                            damagedEntities.put(target.getUUID(), 20);
                        }
                    }
                }
                List<UUID> remove = new ArrayList<>();
                for (UUID uuid : damagedEntities.keySet()) {
                    damagedEntities.put(uuid, damagedEntities.get(uuid) - 1);
                    if (damagedEntities.get(uuid) <= 0) {
                        remove.add(uuid);
                    }
                }
                for (UUID uuid : remove) {
                    damagedEntities.remove(uuid);
                }
            }

            if (ray.getType() == HitResult.Type.BLOCK) {
                setBlock(true);
                setBlockTick(30);
                setDeltaMovement(0, 0, 0);
            }

            Vec3 motion = getDeltaMovement();
            if (motion.distanceTo(Vec3.ZERO) < 0.02f && !getBlock()) {
                setFade(true);
                setFadeTick(30);
                refund();
            } else if (!getBlock()) {
                setDeltaMovement(motion.x * 0.95, motion.y * 0.95, motion.z * 0.95);

                Vec3 pos = position();
                xo = pos.x;
                yo = pos.y;
                zo = pos.z;
                setPos(pos.x + motion.x, pos.y + motion.y, pos.z + motion.z);
            }


            if (level().isClientSide()) {
                int right = getIsRight() ? 1 : -1;
                float yaw = (float) -tickCount * right * 0.8f + (getIsRight() ? 0.3f : -0.3f) + (float) Math.toRadians(-getYRot()) + (getIsRight() ? 0 : (float) Math.PI);
                float pitch = (float) (Math.PI / 2f);

                float x = (float) Math.sin(pitch) * (float) Math.cos(yaw);
                float y = (float) Math.cos(pitch);
                float z = (float) Math.sin(pitch) * (float) Math.sin(yaw);

                addTrail(new Vec3(position().toVector3f()).add(x, y, z));
                Color color = WizardsRebornArcaneEnchantments.THROW.getColor();
                Vec3 pos = new Vec3(getPosition(0.5f).toVector3f()).add(x, y, z);

                if (random.nextFloat() < 0.8f) {
                    ParticleBuilder.create(FluffyFurParticles.SQUARE)
                            .setRenderType(FluffyFurRenderTypes.ADDITIVE)
                            .setBehavior(CubeParticleBehavior.create().build())
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                            .setScaleData(GenericParticleData.create(0.05f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.3f).build())
                            .setLifetime(40)
                            .randomVelocity(0.02f, 0.02f, 0.02f)
                            .spawn(level(), pos.x(), pos.y() + 0.1, pos.z());
                }

                int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, getItem());
                if (i > 0) {
                    if (random.nextFloat() < 0.8f) {
                        ParticleBuilder.create(FluffyFurParticles.SQUARE)
                                .setRenderType(FluffyFurRenderTypes.ADDITIVE)
                                .setBehavior(CubeParticleBehavior.create().build())
                                .setColorData(ColorParticleData.create(0.882f, 0.498f, 0.404f, 0.979f, 0.912f, 0.585f).build())
                                .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                                .setScaleData(GenericParticleData.create(0.05f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                                .setSpinData(SpinParticleData.create().randomSpin(0.3f).build())
                                .setLifetime(40)
                                .randomVelocity(0.02f, 0.02f, 0.02f)
                                .spawn(level(), pos.x(), pos.y() + 0.1, pos.z());
                    }
                }
            } else {
                if (!getFade()) {
                    if ((!getBlock() && tickCount % 4 == 0) || (getBlock() && tickCount % 8 == 0)) {
                        level().playSound(null, blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.5f, 1.3f);
                    }
                }
            }

            Vec3 vec3 = getDeltaMovement();
            double d0 = vec3.horizontalDistance();
            setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
            setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
            yRotO = getYRot();
            xRotO = getXRot();
        } else {
            if (!level().isClientSide()) {
                if (getFadeTick() <= 0) {
                    discard();
                } else {
                    setFadeTick(getFadeTick() - 1);
                }
            }
        }

        if (getBlock()) {
            setDeltaMovement(0, 0, 0);
            if (getBlockTick() <= 0) {
                setBlock(false);
                setFade(true);
                setFadeTick(30);
                refund();
            } else {
                setBlockTick(getBlockTick() - 1);
            }
        } else {
            if (tickCount > 200 && !getFade()) {
                setBlock(true);
                setBlockTick(30);
            }
        }
        if (level().isClientSide()) {
            trailPointBuilder.tickTrailPoints();
        }
    }

    public void refund() {
        if (!level().isClientSide()) {
            boolean dist = false;
            if (getOwner() instanceof Player player) {
                if (distanceTo(getOwner()) < 150 && player.isAlive()) {
                    //PacketHandler.sendTo(player, new AddScreenshakePacket(0.55f));
                    player.knockback(1f, getX() - player.getX(), getZ() - player.getZ());
                    player.hurtMarked = true;

                    level().playSound(null, blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.75f, 1.5f);
                    level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.75f, 1.5f);
                    setEndTick(tickCount);
                    setEndPoint(getSender().position().add(0, getSender().getBbHeight() / 2f, 0));

                    List<LivingEntity> entities = new ArrayList<>();

                    int dst = (int) distanceTo(player);
                    for (int i = 0; i < dst; i++) {
                        double lerpX = Mth.lerp((double) i / dst, getX(), player.getX());
                        double lerpY = Mth.lerp((double) i / dst, getY(), player.getY() + player.getBbHeight() / 2f);
                        double lerpZ = Mth.lerp((double) i / dst, getZ(), player.getZ());
                        List<LivingEntity> entityList = level().getEntitiesOfClass(LivingEntity.class, new AABB(lerpX - 1, lerpY - 0.1f, lerpZ - 1, lerpX + 1, lerpY + 0.1f, lerpZ + 1));
                        for (LivingEntity target : entityList) {
                            if (!entities.contains(target) && !target.equals(player)) {
                                entities.add(target);
                            }
                        }
                    }

                    for (LivingEntity target : entities) {
                        hitEntity(target);
                    }

                    if (player.getInventory().getFreeSlot() > -1) {
                        if (player.getInventory().getItem(getSlot()).isEmpty()) {
                            player.getInventory().setItem(getSlot(), getItem());
                        } else {
                            player.getInventory().add(getItem());
                        }
                    } else {
                        ItemEntity itemEntity = new ItemEntity(level(), player.getX(), player.getY() + 0.5f, player.getZ(), getItem());
                        itemEntity.setPickUpDelay(40);
                        level().addFreshEntity(itemEntity);
                    }
                } else {
                    dist = true;
                }
            }
            if (getOwner() != null && dist) {
                ItemEntity itemEntity = new ItemEntity(level(), getX(), getY() + 0.1f, getZ(), getItem());
                itemEntity.setPickUpDelay(40);
                level().addFreshEntity(itemEntity);
            }
        } else {
            if (getSender() != null && distanceTo(getSender()) < 150 && getSender().isAlive()) {
                Color color = WizardsRebornArcaneEnchantments.THROW.getColor();
                float r = color.getRed() / 255f;
                float g = color.getGreen() / 255f;
                float b = color.getBlue() / 255f;

                for (int i = 0; i < 30; i++) {
                    double lX = Mth.lerp(i / 30.0f, getX(), getSender().getX());
                    double lY = Mth.lerp(i / 30.0f, getY(), getSender().getY() + getSender().getBbHeight() / 2f);
                    double lZ = Mth.lerp(i / 30.0f, getZ(), getSender().getZ());

                    int right = getIsRight() ? 1 : -1;
                    float yaw = (float) -tickCount - (i * 10) * right * 0.8f + (getIsRight() ? 0.3f : -0.3f) + (float) Math.toRadians(-getYRot()) + (getIsRight() ? 0 : (float) Math.PI);
                    float pitch = (float) (Math.PI / 2f);

                    float x = (float) Math.sin(pitch) * (float) Math.cos(yaw);
                    float y = (float) Math.cos(pitch);
                    float z = (float) Math.sin(pitch) * (float) Math.sin(yaw);

                    Vec3 pos = new Vec3(lX, lY, lZ).add(x, y, z);

                    ParticleBuilder.create(FluffyFurParticles.SQUARE)
                            .setRenderType(FluffyFurRenderTypes.ADDITIVE)
                            .setBehavior(CubeParticleBehavior.create().build())
                            .setColorData(ColorParticleData.create(r, g, b).build())
                            .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.3f).build())
                            .setLifetime(40)
                            .randomVelocity(0.02f, 0.02f, 0.02f)
                            .spawn(level(), pos.x(), pos.y() + 0.1, pos.z());
                }

                ParticleBuilder.create(FluffyFurParticles.TINY_STAR)
                        .setColorData(ColorParticleData.create(r, g, b).build())
                        .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                        .setScaleData(GenericParticleData.create(0.4f, 0).setEasing(Easing.SINE_IN_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                        .setLifetime(30)
                        .addVelocity(0f, 0.05, 0f)
                        .randomVelocity(0.03f, 0.03f, 0.03f)
                        .repeat(level(), getX(), getY() + 0.1, getZ(), 5);
                ParticleBuilder.create(FluffyFurParticles.TINY_STAR)
                        .setColorData(ColorParticleData.create(r, g, b).build())
                        .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                        .setScaleData(GenericParticleData.create(0.4f, 0).setEasing(Easing.SINE_IN_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                        .setLifetime(30)
                        .addVelocity(0f, -0.05, 0f)
                        .randomVelocity(0.03f, 0.03f, 0.03f)
                        .repeat(level(), getX(), getY() + 0.1, getZ(), 5);
            }
        }
    }

    public void hitEntity(Entity target) {
        if (!level().isClientSide() && getOwner() instanceof LivingEntity owner) {
            DamageSource source = target.damageSources().mobProjectile(this, owner);
            boolean success = target.hurt(source, getBaseDamage());
            if (success && target instanceof LivingEntity livingEntity) {
                ItemStack scythe = getItem();
                scythe.hurtAndBreak(1, owner, (e) -> remove(RemovalReason.KILLED));
                int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, scythe);
                if (i > 0) {
                    livingEntity.setSecondsOnFire(i * 4);
                }

                if (getMagicDamage() > 0) {
                    target.invulnerableTime = 0;
                    target.hurt(new DamageSource(WizardsRebornDamage.create(target.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder(), this, owner), getMagicDamage());
                    target.invulnerableTime = 30;
                }

                level().playSound(null, blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.75f, 1.5f);
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return WizardsRebornItems.ARCANE_GOLD_SCYTHE.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(ownerId, Optional.empty());
        getEntityData().define(endTickId, 0);
        getEntityData().define(blockId, false);
        getEntityData().define(blockTickId, 0);
        getEntityData().define(fadeId, false);
        getEntityData().define(fadeTickId, 0);
        getEntityData().define(baseDamageId, 0f);
        getEntityData().define(magicDamageId, 0f);
        getEntityData().define(slotId, 0);
        getEntityData().define(endPointXId, 0f);
        getEntityData().define(endPointYId, 0f);
        getEntityData().define(endPointZId, 0f);
        getEntityData().define(isRightId, true);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("owner")) {
            getEntityData().set(ownerId, Optional.of(compound.getUUID("owner")));
        }
        getEntityData().set(endTickId, compound.getInt("endTick"));
        getEntityData().set(blockId, compound.getBoolean("block"));
        getEntityData().set(blockTickId, compound.getInt("blockTick"));
        getEntityData().set(fadeId, compound.getBoolean("fade"));
        getEntityData().set(fadeTickId, compound.getInt("fadeTick"));
        getEntityData().set(baseDamageId, compound.getFloat("baseDamage"));
        getEntityData().set(magicDamageId, compound.getFloat("magicDamage"));
        getEntityData().set(slotId, compound.getInt("slot"));
        getEntityData().set(endPointXId, compound.getFloat("endPointX"));
        getEntityData().set(endPointYId, compound.getFloat("endPointY"));
        getEntityData().set(endPointZId, compound.getFloat("endPointZ"));
        getEntityData().set(isRightId, compound.getBoolean("isRight"));

        damagedEntities.clear();
        ListTag tagList = compound.getList("damagedEntities", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTags = tagList.getCompound(i);
            int tick = itemTags.getInt("tick");
            UUID uuid = itemTags.getUUID("uuid");
            damagedEntities.put(uuid, tick);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (getEntityData().get(ownerId).isPresent()) {
            compound.putUUID("owner", getEntityData().get(ownerId).get());
        }
        compound.putInt("endTick", getEntityData().get(endTickId));
        compound.putBoolean("block", getEntityData().get(blockId));
        compound.putInt("blockTick", getEntityData().get(blockTickId));
        compound.putBoolean("fade", getEntityData().get(fadeId));
        compound.putInt("fadeTick", getEntityData().get(fadeTickId));
        compound.putFloat("baseDamage", getEntityData().get(baseDamageId));
        compound.putFloat("magicDamage", getEntityData().get(magicDamageId));
        compound.putInt("slot", getEntityData().get(slotId));
        compound.putFloat("endPointX", getEntityData().get(endPointXId));
        compound.putFloat("endPointY", getEntityData().get(endPointYId));
        compound.putFloat("endPointZ", getEntityData().get(endPointZId));
        compound.putBoolean("isRight", getEntityData().get(isRightId));

        ListTag nbtTagList = new ListTag();
        for (UUID uuid : damagedEntities.keySet()) {
            CompoundTag itemTag = new CompoundTag();
            itemTag.putInt("tick", damagedEntities.get(uuid));
            itemTag.putUUID("uuid", uuid);
            nbtTagList.add(itemTag);
        }
        compound.put("damagedEntities", nbtTagList);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public float getEyeHeight(Pose pose, EntityDimensions size) {
        return 0.1F;
    }

    public int getEndTick() {
        return getEntityData().get(endTickId);
    }

    public void setEndTick(int endTick) {
        getEntityData().set(endTickId, endTick);
    }

    public boolean getBlock() {
        return getEntityData().get(blockId);
    }

    public void setBlock(boolean block) {
        getEntityData().set(blockId, block);
    }

    public int getBlockTick() {
        return getEntityData().get(blockTickId);
    }

    public void setBlockTick(int blockTick) {
        getEntityData().set(blockTickId, blockTick);
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

    public Player getSender() {
        return (getEntityData().get(ownerId).isPresent()) ? level().getPlayerByUUID(getEntityData().get(ownerId).get()) : null;
    }

    public UUID getSenderUUID() {
        return (getEntityData().get(ownerId).isPresent()) ? getEntityData().get(ownerId).get() : null;
    }

    public float getBaseDamage() {
        return getEntityData().get(baseDamageId);
    }

    public void setBaseDamage(float damage) {
        getEntityData().set(baseDamageId, damage);
    }

    public float getMagicDamage() {
        return getEntityData().get(magicDamageId);
    }

    public void setMagicDamage(float damage) {
        getEntityData().set(magicDamageId, damage);
    }

    public int getSlot() {
        return getEntityData().get(slotId);
    }

    public void setSlot(int slot) {
        getEntityData().set(slotId, slot);
    }

    public void setEndPoint(Vec3 vec) {
        getEntityData().set(endPointXId, (float) vec.x);
        getEntityData().set(endPointYId, (float) vec.y);
        getEntityData().set(endPointZId, (float) vec.z);
    }

    public Vec3 getEndPoint() {
        return new Vec3(getEntityData().get(endPointXId), getEntityData().get(endPointYId), getEntityData().get(endPointZId));
    }

    public void setIsRight(boolean isRight) {
        getEntityData().set(isRightId, isRight);
    }

    public boolean getIsRight() {
        return getEntityData().get(isRightId);
    }
}
