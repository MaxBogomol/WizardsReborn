package mod.maxbogomol.wizards_reborn.common.entity;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.ParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPointBuilder;
import mod.maxbogomol.fluffy_fur.common.damage.DamageHandler;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.raycast.RayCast;
import mod.maxbogomol.fluffy_fur.common.raycast.RayCastContext;
import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.fluffy_fur.config.FluffyFurConfig;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.entity.ThrownShearsBurstPacket;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornParticles;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import mod.maxbogomol.wizards_reborn.registry.common.entity.WizardsRebornEntities;
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
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.*;

public class ThrownShearsEntity extends ThrowableItemProjectile {
    public static final EntityDataAccessor<Optional<UUID>> ownerId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<Integer> endTickId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> blockId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> blockTickId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> fadeId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> fadeTickId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> baseDamageId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> magicDamageId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Integer> slotId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> endPointXId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> endPointYId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> endPointZId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Boolean> isRightId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> isCutId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> cutTickId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> isThrownId = SynchedEntityData.defineId(ThrownShearsEntity.class, EntityDataSerializers.BOOLEAN);

    public TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(30);
    public Map<UUID, Integer> damagedEntities = new HashMap<>();

    public ThrownShearsEntity(EntityType<?> type, Level level) {
        super(WizardsRebornEntities.THROWN_SHEARS.get(), level);
        noPhysics = false;
    }

    public ThrownShearsEntity(Level level) {
        super(WizardsRebornEntities.THROWN_SHEARS.get(), level);
    }

    public ThrownShearsEntity(Level level, double x, double y, double z) {
        super(WizardsRebornEntities.THROWN_SHEARS.get(), x, y, z, level);
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
            RayHitResult hitResult = RayCast.getHit(level(), new RayCastContext(position(), position().add(getDeltaMovement())));

            if (!level().isClientSide()) {
                if (getIsCut()) {
                    Player player = getSender();
                    if (player != null) {
                        for (int i = 0; i < 8; i++) {
                            float tick = (float) Math.toRadians(i * 45f);
                            int right = getIsRight() ? 1 : -1;

                            float yaw = -(tick * right);

                            float x = (float) Math.cos(yaw) * 2;
                            float z = (float) Math.sin(yaw) * 2;

                            double lx = getX() + x;
                            double ly = getY() + player.getBbHeight() / 1.5f;
                            double lz = getZ() + z;
                            List<LivingEntity> entityList = level().getEntitiesOfClass(LivingEntity.class, new AABB(lx - 1f, ly - 0.3f, lz - 1f, lx + 1f, ly + 0.5f, lz + 1f));
                            if (tickCount < 5) {
                                if (getOwner() instanceof LivingEntity living) {
                                    if (entityList.contains(living)) {
                                        entityList.remove(living);
                                    }
                                }
                            }
                            for (LivingEntity target : entityList) {
                                if (!damagedEntities.keySet().contains(target.getUUID()) && !target.equals(player)) {
                                    hitEntity(target);
                                    damagedEntities.put(target.getUUID(), 10);
                                }
                            }
                        }
                        Vec3 vel = player.getViewVector(0);
                        int propellingLevel = ArcaneEnchantmentUtil.getArcaneEnchantment(getItem(), WizardsRebornArcaneEnchantments.PROPELLING);
                        if (player.isFallFlying()) {
                            player.push(vel.x() * ((propellingLevel / 3f) * 0.005f), 0.02f + ((propellingLevel / 3f) * 0.02f), vel.z()  * ((propellingLevel / 3f) * 0.005f));
                            player.hurtMarked = true;
                        } else if (!player.onGround() && propellingLevel > 0) {
                            player.push(vel.x() * ((propellingLevel / 3f) * 0.02f), ((propellingLevel / 3f) * 0.06f), vel.z()  * ((propellingLevel / 3f) * 0.02f));
                            player.hurtMarked = true;
                        }
                    }
                } else {
                    Vec3 mov = getDeltaMovement();
                    for (int i = 0; i < 5; i++) {
                        double lx = Mth.lerp((double) i / 5f, getX(), mov.x());
                        double ly = Mth.lerp((double) i / 5f, getY(), mov.y());
                        double lz = Mth.lerp((double) i / 5f, getZ(), mov.z());
                        List<LivingEntity> entityList = level().getEntitiesOfClass(LivingEntity.class, new AABB(lx - 0.2f, ly - 0.1f, lz - 0.2f, lx + 0.2f, ly + 0.3f, lz + 0.2f));
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

            if (getIsCut()) {
                Player player = getSender();
                if (player != null) {
                    setPos(player.position());

                    if (!getBlock()) {
                        for (int i = 0; i < 8; i++) {
                            float tick = (float) Math.toRadians(i * 45f);
                            int right = getIsRight() ? 1 : -1;

                            float yaw = -(tick * right);

                            float x = (float) Math.cos(yaw) * 2;
                            float z = (float) Math.sin(yaw) * 2;

                            hitResult = RayCast.getHit(level(), new RayCastContext(position().add(x, player.getBbHeight() / 1.5f, z), position().add(x, player.getBbHeight() / 1.5f + 0.1f, z)));
                            if (hitResult.hasBlock() || (position().y() < level().dimensionType().minY() + FluffyFurConfig.VOID_HEIGHT.get())) {
                                setCutTick(0);
                                setIsCut(false);
                                setBlock(true);
                                setBlockTick(5);
                                setPos(hitResult.getPos());
                            }
                        }
                    }
                }
                setDeltaMovement(Vec3.ZERO);
            } else {
                if (getCutTick() > 0) {
                    Player player = getSender();
                    if (player != null) {
                        double distance = Math.sqrt(distanceToSqr(player));
                        if (distance < 3) {
                            setIsCut(true);
                        } else {
                            target(player.position().add(0, player.getBbHeight(), 0), 0.5f);
                        }
                    }
                }

                if (!getBlock() && (hitResult.hasBlock() || (position().y() < level().dimensionType().minY() + FluffyFurConfig.VOID_HEIGHT.get())) && !getIsCut()) {
                    setCutTick(0);
                    setIsCut(false);
                    setBlock(true);
                    setBlockTick(5);
                    setPos(hitResult.getPos());
                }

                Vec3 motion = getDeltaMovement();
                if (!getBlock()) {
                    float friction = getCutTick() > 0 ? 0.99f : 0.9f;
                    float gravity = getCutTick() > 0 ? 0 : 0.05f;
                    setDeltaMovement(motion.x * friction, (motion.y > 0 ? motion.y * friction : motion.y) - gravity, motion.z * friction);

                    Vec3 pos = position();
                    xo = pos.x;
                    yo = pos.y;
                    zo = pos.z;
                    setPos(pos.x + motion.x, pos.y + motion.y, pos.z + motion.z);
                }
            }

            if (level().isClientSide()) {
                if (!getIsCut()) {
                    addTrail(new Vec3(position().toVector3f()));
                } else {
                    Player player = getSender();
                    if (player != null) {
                        int right = getIsRight() ? 1 : -1;
                        ParticleBuilder.create(WizardsRebornParticles.THROWN_SHEARS_CUT)
                                .setBehavior(ParticleBehavior.create(90, 0, 0)
                                        .setXSpinData(SpinParticleData.create().randomOffsetDegrees(-15, 15).build())
                                        .setYSpinData(SpinParticleData.create().randomOffsetDegrees(-15, 15).build())
                                        .setZSpinData(SpinParticleData.create(-0.5f * right, -2 * right).randomOffset().build())
                                        .build())
                                .setColorData(ColorParticleData.create(WizardsRebornArcaneEnchantments.SILK_SONG.getColor()).build())
                                .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                                .setScaleData(GenericParticleData.create(3f, 1.5f).setEasing(Easing.SINE_IN_OUT).build())
                                .setLifetime(10, 5)
                                .randomVelocity(0.15f, 0, 0.15f)
                                .spawn(level(), player.position().add(0, player.getBbHeight() / 1.5f, 0));
                    }
                }
            } else {
                if (!getFade() && getIsCut()) {
                    if ((!getBlock() && tickCount % 3 == 0)) {
                        Player player = getSender();
                        if (player != null) {
                            float tick = tickCount;
                            int right = getIsRight() ? 1 : -1;

                            float yaw = -(tick * right * 2f) * 10f;

                            float x = (float) Math.cos(yaw) * 2;
                            float z = (float) Math.sin(yaw) * 2;

                            level().playSound(null, player.getX() + x, player.getY() + (player.getBbHeight() / 1.5f), player.getZ() + z, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.5f, 1.3f);
                        }
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
            if (getBlockTick() <= 0) {
                setIsCut(false);
                setBlock(false);
                setFade(true);
                setFadeTick(30);
                refund();
            } else {
                setBlockTick(getBlockTick() - 1);
            }
        } else {
            if (tickCount > 1000 && !getFade()) {
                setCutTick(0);
                setIsCut(false);
                setBlock(true);
                setBlockTick(30);
            }
        }
        if (getCutTick() <= 0) {
            if (getIsCut()) {
                setIsCut(false);
                Player player = getSender();
                if (player != null) {
                    float tick = tickCount;
                    int right = getIsRight() ? 1 : -1;

                    float yaw = -(tick * right * 2f) * 10f;

                    float x = (float) Math.cos(yaw) * 2;
                    float z = (float) Math.sin(yaw) * 2;
                    if (getIsThrown()) {
                        x = 0;
                        z = 0;
                    }
                    setPos(position().add(x, player.getBbHeight() / 1.5f, z));
                    if (getIsThrown()) {
                        shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3F, 0.1F);
                    } else {
                        setDeltaMovement(x / 2, 0.75f, z / 2);
                    }
                    setIsThrown(false);
                }
            }
        } else {
            setCutTick(getCutTick() - 1);
        }
        if (level().isClientSide()) {
            trailPointBuilder.tickTrailPoints();
        }
    }

    public void refund() {
        if (!level().isClientSide()) {
            boolean dist = false;
            if (getOwner() instanceof Player player) {
                if (distanceTo(getOwner()) < 250 && player.isAlive()) {
                    level().playSound(null, position().x(), position().y(), position().z(), WizardsRebornSounds.THROWN_SHEARS_RETURN_CLOSE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                    level().playSound(null, player.getX(), player.getY(), player.getZ(), WizardsRebornSounds.THROWN_SHEARS_RETURN_CLOSE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                    setEndTick(tickCount);
                    setEndPoint(getSender().position().add(0, getSender().getBbHeight() / 2f, 0));

                    List<LivingEntity> entities = new ArrayList<>();

                    int dst = (int) distanceTo(player);
                    for (int i = 0; i < dst; i++) {
                        double lx = Mth.lerp((double) i / dst, getX(), player.getX());
                        double ly = Mth.lerp((double) i / dst, getY(), player.getY() + player.getBbHeight() / 2f);
                        double lz = Mth.lerp((double) i / dst, getZ(), player.getZ());
                        List<LivingEntity> entityList = level().getEntitiesOfClass(LivingEntity.class, new AABB(lx - 1, ly - 0.1f, lz - 1, lx + 1, ly + 0.1f, lz + 1));
                        for (LivingEntity target : entityList) {
                            if (!entities.contains(target) && !target.equals(player)) {
                                entities.add(target);
                            }
                        }
                    }

                    for (LivingEntity target : entities) {
                        hitEntity(target);
                    }
                    WizardsRebornPacketHandler.sendTo(player, new ThrownShearsBurstPacket(position(), WizardsRebornArcaneEnchantments.SILK_SONG.getColor()));
                } else {
                    dist = true;
                }
            }
            if (getOwner() != null && dist) {
                ItemEntity itemEntity = new ItemEntity(level(), getX(), getY() + 0.1f, getZ(), getItem());
                itemEntity.setPickUpDelay(40);
                itemEntity.setTarget(getSenderUUID());
                itemEntity.setNoGravity(true);
                itemEntity.setDeltaMovement(Vec3.ZERO);
                level().addFreshEntity(itemEntity);
            }
        }
    }

    public void hitEntity(Entity target) {
        if (!level().isClientSide() && getOwner() instanceof LivingEntity owner) {
            ItemStack oldStack = owner.getItemBySlot(EquipmentSlot.MAINHAND);
            owner.setItemSlot(EquipmentSlot.MAINHAND, getItem());
            DamageSource source = DamageHandler.create(target.level(), DamageTypes.GENERIC, this, owner);
            boolean success = target.hurt(source, getBaseDamage());
            if (success && target instanceof LivingEntity livingEntity) {
                ItemStack shears = getItem();
                shears.hurtAndBreak(1, owner, (e) -> remove(RemovalReason.KILLED));
                int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, shears);
                if (i > 0) {
                    livingEntity.setSecondsOnFire(i * 4);
                }

                if (getMagicDamage() > 0) {
                    int invulnerableTime = target.invulnerableTime;
                    target.invulnerableTime = 0;
                    target.hurt(DamageHandler.create(target.level(), WizardsRebornDamageTypes.ARCANE_MAGIC, this, owner), getMagicDamage());
                    target.invulnerableTime = invulnerableTime;
                }

                level().playSound(null, position().x(), position().y(), position().z(), WizardsRebornSounds.THROWN_SHEARS_HIT.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            }
            owner.setItemSlot(EquipmentSlot.MAINHAND, oldStack);
        }
    }

    public void target(Vec3 pos, double speed) {
        double dX = pos.x() - getX();
        double dY = pos.y() - getY();
        double dZ = pos.z() - getZ();

        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

        double x = Math.sin(pitch) * Math.cos(yaw) * speed;
        double y = Math.cos(pitch) * speed;
        double z = Math.sin(pitch) * Math.sin(yaw) * speed;
        Vec3 motion = getDeltaMovement();
        setDeltaMovement(motion.x - x, motion.y- y, motion.z - z);
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
        getEntityData().define(isCutId, false);
        getEntityData().define(cutTickId, 0);
        getEntityData().define(isThrownId, false);
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
        getEntityData().set(isCutId, compound.getBoolean("isCut"));
        getEntityData().set(cutTickId, compound.getInt("cutTickId"));
        getEntityData().set(isThrownId, compound.getBoolean("isThrown"));

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
        compound.putBoolean("isCut", getEntityData().get(isCutId));
        compound.putInt("cutTickId", getEntityData().get(cutTickId));
        compound.putBoolean("isThrown", getEntityData().get(isThrownId));

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

    public void setSender(Player player) {
        getEntityData().set(ownerId, Optional.of(player.getUUID()));
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

    public void setIsCut(boolean isCut) {
        getEntityData().set(isCutId, isCut);
    }

    public boolean getIsCut() {
        return getEntityData().get(isCutId);
    }

    public void setCutTick(int cutTick) {
        getEntityData().set(cutTickId, cutTick);
    }

    public int getCutTick() {
        return getEntityData().get(cutTickId);
    }

    public void setIsThrown(boolean isThrown) {
        getEntityData().set(isThrownId, isThrown);
    }

    public boolean getIsThrown() {
        return getEntityData().get(isThrownId);
    }
}
