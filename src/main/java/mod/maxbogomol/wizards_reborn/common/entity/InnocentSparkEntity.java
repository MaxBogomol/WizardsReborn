package mod.maxbogomol.wizards_reborn.common.entity;

import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPointBuilder;
import mod.maxbogomol.fluffy_fur.common.damage.DamageHandler;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ScytheItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcanewood.ArcaneWoodTools;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import mod.maxbogomol.wizards_reborn.registry.common.entity.WizardsRebornEntities;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.*;
import java.util.function.Predicate;

public class InnocentSparkEntity extends ThrowableItemProjectile {
    public static final EntityDataAccessor<Optional<UUID>> ownerId = SynchedEntityData.defineId(InnocentSparkEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<Boolean> fadeId = SynchedEntityData.defineId(InnocentSparkEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> fadeTickId = SynchedEntityData.defineId(InnocentSparkEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> slotId = SynchedEntityData.defineId(InnocentSparkEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> startXId = SynchedEntityData.defineId(InnocentSparkEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> startYId = SynchedEntityData.defineId(InnocentSparkEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> startZId = SynchedEntityData.defineId(InnocentSparkEntity.class, EntityDataSerializers.FLOAT);

    public TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(30);
    public Map<UUID, Integer> damagedEntities = new HashMap<>();
    public Vec3 vector = Vec3.ZERO;
    public Vec3 vectorOld = Vec3.ZERO;

    public static List<SparkType> types = new ArrayList<>();

    private static final Random random = new Random();

    public InnocentSparkEntity(EntityType<?> type, Level level) {
        super(WizardsRebornEntities.INNOCENT_SPARK.get(), level);
        noPhysics = false;
    }

    public InnocentSparkEntity(Level level) {
        super(WizardsRebornEntities.INNOCENT_SPARK.get(), level);
    }

    public InnocentSparkEntity(Level level, double x, double y, double z) {
        super(WizardsRebornEntities.INNOCENT_SPARK.get(), x, y, z, level);
    }

    public void setData(Entity owner, int slot, Vec3 start) {
        setOwner(owner);
        getEntityData().set(ownerId, Optional.of(owner.getUUID()));
        setSlot(slot);
        setStart(start);
    }

    @Override
    public void shootFromRotation(Entity shooter, float x, float y, float z, float velocity, float inaccuracy) {
        super.shootFromRotation(shooter, x, y, z, velocity, inaccuracy);
        vector = getDeltaMovement();
        vectorOld = vector;
    }

    @Override
    public void tick() {
        if (!getFade()) {
            if (!level().isClientSide()) {
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

            boolean isTick = true;
            if (getSender() != null) {
                ItemStack item = getSender().getInventory().getItem(getSlot());
                if (item.getItem().equals(getItem().getItem())) {
                    if (item.getDamageValue() < item.getMaxDamage() - 1) {
                        if (!level().isClientSide()) {
                            int tick = 2 + (ArcaneWoodTools.getLifeRoots(item));
                            if (tickCount % tick == 0) {
                                item.hurtAndBreak(1, getSender(), (entity) -> {});
                            }
                        }
                    } else {
                        isTick = false;
                    }
                } else {
                    isTick = false;
                }
            } else {
                isTick = false;
            }

            if (!isTick) {
                setFade(true);
                setFadeTick(30);
            }

            if (!getFade()) {
                boolean hasType = false;
                for (SparkType type : getTypes()) {
                    if (type.isItem(this)) {
                        type.tick(this);
                        hasType = true;
                        break;
                    }
                }
                if (!hasType) {
                    setFade(true);
                    setFadeTick(30);
                }
            }

            if (level().isClientSide()) {
                addTrail(position());
                vectorOld = vector;
                Vec3 motion = getDeltaMovement();
                if (motion.distanceTo(Vec3.ZERO) > 0.15f) {
                    vector = motion;
                }
            }
        } else {
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

    public void tickMove() {
        Vec3 motion = getDeltaMovement();
        setDeltaMovement(motion.x * 0.95, motion.y * 0.95, motion.z * 0.95);

        Vec3 pos = position();
        xo = pos.x;
        yo = pos.y;
        zo = pos.z;
        setPos(pos.x + motion.x, pos.y + motion.y, pos.z + motion.z);
    }

    public void sparkTarget(Vec3 pos, double speed, double extraSpeed) {
        double dX = pos.x() - getX();
        double dY = pos.y() - getY();
        double dZ = pos.z() - getZ();

        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

        speed = speed + (random.nextFloat() * extraSpeed);
        double x = Math.sin(pitch) * Math.cos(yaw) * speed;
        double y = Math.cos(pitch) * speed;
        double z = Math.sin(pitch) * Math.sin(yaw) * speed;
        Vec3 motion = getDeltaMovement();
        setDeltaMovement(motion.x - x, motion.y - y, motion.z - z);
    }

    public Predicate<Entity> getEntityFilter() {
        return (e) -> !e.isSpectator() && e.isPickable() && (!e.equals(getOwner()));
    }

    @Override
    protected Item getDefaultItem() {
        return WizardsRebornItems.INNOCENT_WOOD_SWORD.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(ownerId, Optional.empty());
        getEntityData().define(fadeId, false);
        getEntityData().define(fadeTickId, 0);
        getEntityData().define(slotId, 0);
        getEntityData().define(startXId, 0f);
        getEntityData().define(startYId, 0f);
        getEntityData().define(startZId, 0f);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("owner")) {
            getEntityData().set(ownerId, Optional.of(compound.getUUID("owner")));
        }
        getEntityData().set(fadeId, compound.getBoolean("fade"));
        getEntityData().set(fadeTickId, compound.getInt("fadeTick"));
        getEntityData().set(slotId, compound.getInt("slot"));
        getEntityData().set(startXId, compound.getFloat("startX"));
        getEntityData().set(startYId, compound.getFloat("startY"));
        getEntityData().set(startZId, compound.getFloat("startZ"));

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
        compound.putBoolean("fade", getEntityData().get(fadeId));
        compound.putInt("fadeTick", getEntityData().get(fadeTickId));
        compound.putInt("slot", getEntityData().get(slotId));
        compound.putFloat("startX", getEntityData().get(startXId));
        compound.putFloat("startY", getEntityData().get(startYId));
        compound.putFloat("startZ", getEntityData().get(startZId));

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
        return 0.2F;
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

    public int getSlot() {
        return getEntityData().get(slotId);
    }

    public void setSlot(int slot) {
        getEntityData().set(slotId, slot);
    }

    public void setStart(Vec3 vec) {
        getEntityData().set(startXId, (float) vec.x);
        getEntityData().set(startYId, (float) vec.y);
        getEntityData().set(startZId, (float) vec.z);
    }

    public Vec3 getStart() {
        return new Vec3(getEntityData().get(startXId), getEntityData().get(startYId), getEntityData().get(startZId));
    }

    public static void addType(SparkType type) {
        types.add(type);
    }

    public static List<SparkType> getTypes() {
        return types;
    }

    public static class SparkType {

        public boolean isItem(InnocentSparkEntity entity) {
            return false;
        }

        public TextureAtlasSprite getSprite(InnocentSparkEntity entity) {
            return null;
        }

        public void tick(InnocentSparkEntity entity) {

        }
    }

    public static class SwordSparkType extends SparkType {

        @Override
        public boolean isItem(InnocentSparkEntity entity) {
            return entity.getItem().getItem() == WizardsRebornItems.INNOCENT_WOOD_SWORD.get();
        }

        @Override
        public TextureAtlasSprite getSprite(InnocentSparkEntity entity) {
            return RenderUtil.getSprite(WizardsReborn.MOD_ID, "item/innocent_wood_sword");
        }

        @Override
        public void tick(InnocentSparkEntity entity) {
            entity.tickMove();

            double lx = entity.getX();
            double ly = entity.getY();
            double lz = entity.getZ();
            float d = getDistance(entity);
            List<LivingEntity> entityList = entity.level().getEntitiesOfClass(LivingEntity.class, new AABB(lx - d, ly - d, lz - d, lx + d, ly + d, lz + d));
            if (entityList.contains(entity.getSender())) {
                entityList.remove(entity.getSender());
            }

            LivingEntity livingEntity = null;
            if (entityList.size() > 0) {
                float minDistance = d + 1f;
                for (LivingEntity target : entityList) {
                    float distance = (float) Math.sqrt(target.position().add(0, target.getBbHeight() / 2f, 0).distanceToSqr(entity.position()));
                    if (distance < minDistance) {
                        minDistance = distance;
                        livingEntity = target;
                    }
                }
            }

            Vec3 pos = Vec3.ZERO;
            boolean move = false;
            if (entity.getSender() != null && entity.tickCount > 20) {
                pos = entity.getSender().position().add(0, 3, 0);
                move = true;
            }
            if (livingEntity != null) {
                pos = livingEntity.position().add(0, livingEntity.getBbHeight() / 2f, 0);
                move = true;
            }

            if (move) {
                entity.sparkTarget(pos, 0.05f, 0.01f);
            }

            if (!entity.level().isClientSide()) {
                entityList = entity.level().getEntitiesOfClass(LivingEntity.class, new AABB(lx - 0.2f, ly - 0.2f, lz - 0.2f, lx + 0.2f, ly + 0.2f, lz + 0.2f));
                entityList.remove(entity.getSender());
                for (LivingEntity target : entityList) {
                    if (!entity.damagedEntities.keySet().contains(target.getUUID())) {
                        DamageSource source = DamageHandler.create(entity.level(), WizardsRebornDamageTypes.ARCANE_MAGIC, entity, entity.getSender());
                        if (target.hurt(source, getDamage(entity))) {
                            entity.damagedEntities.put(target.getUUID(), 20);
                        }
                    }
                }
            }
        }

        public float getDistance(InnocentSparkEntity entity) {
            return 10;
        }

        public float getDamage(InnocentSparkEntity entity) {
            return 4;
        }
    }

    public static class BlockSparkType extends SparkType {

        public void blockTick(InnocentSparkEntity entity, BlockPos center, float speed, float extraSpeed, int tick) {
            Level level = entity.level();
            double distance = getDistance(entity);
            double width = getWidth(entity);
            BlockPos near = BlockPos.ZERO;
            boolean move = false;
            double minDistance = distance + 1;

            ArrayList<BlockPos> blockPosList = CrystalRitual.getBlockPosWithArea(level, center, new Vec3(distance, width, distance), new Vec3(distance, width, distance), (p) -> true, false, false, 0);
            for (BlockPos breakPos : blockPosList) {
                double blockDistance = breakPos.getCenter().distanceTo(entity.position());
                if (blockDistance < minDistance && canBreak(entity, level, breakPos, center)) {
                    minDistance = blockDistance;
                    near = breakPos;
                    move = true;
                }
            }

            if (move) {
                entity.sparkTarget(near.getCenter(), speed, extraSpeed);
            } else {
                entity.setFade(true);
                entity.setFadeTick(30);
            }

            if (!entity.level().isClientSide()) {
                if (entity.tickCount % tick == 0) {
                    blockBreak(entity, center);
                }
            }
        }

        public void blockBreak(InnocentSparkEntity entity, BlockPos center) {
            Level level = entity.level();
            BlockPos blockPos = BlockPos.containing(entity.position());
            BlockState blockState = level.getBlockState(blockPos);
            BlockEvent.BreakEvent breakEvent = new BlockEvent.BreakEvent(level, blockPos, level.getBlockState(blockPos), entity.getSender());
            if (canBreak(entity, level, blockPos, center)) {
                if (!blockState.isAir() && !MinecraftForge.EVENT_BUS.post(breakEvent)) {
                    level.destroyBlock(blockPos, true);
                }
            }
        }

        public float getDistance(InnocentSparkEntity entity) {
            return 5;
        }

        public float getWidth(InnocentSparkEntity entity) {
            return getDistance(entity);
        }

        public boolean canBreak(InnocentSparkEntity entity, Level level, BlockPos blockPos, BlockPos center) {
            return true;
        }
    }

    public static class PickaxeSparkType extends BlockSparkType {

        @Override
        public boolean isItem(InnocentSparkEntity entity) {
            return entity.getItem().getItem() == WizardsRebornItems.INNOCENT_WOOD_PICKAXE.get();
        }

        @Override
        public TextureAtlasSprite getSprite(InnocentSparkEntity entity) {
            return RenderUtil.getSprite(WizardsReborn.MOD_ID, "item/innocent_wood_pickaxe");
        }

        @Override
        public void tick(InnocentSparkEntity entity) {
            entity.tickMove();

            if (entity.getSender() != null && entity.tickCount > 20) {
                BlockPos center = BlockPos.containing(entity.getStart());
                blockTick(entity, center, 0.05f, 0.01f, 20);
            }
        }

        @Override
        public float getDistance(InnocentSparkEntity entity) {
            return 5;
        }

        @Override
        public boolean canBreak(InnocentSparkEntity entity, Level level, BlockPos blockPos, BlockPos center) {
            BlockState blockState = level.getBlockState(blockPos);
            float destroyTime = blockState.getBlock().defaultDestroyTime();
            return blockState.is(BlockTags.MINEABLE_WITH_PICKAXE) && destroyTime > 0 && destroyTime < 20f;
        }
    }

    public static class AxeSparkType extends BlockSparkType {

        @Override
        public boolean isItem(InnocentSparkEntity entity) {
            return entity.getItem().getItem() == WizardsRebornItems.INNOCENT_WOOD_AXE.get();
        }

        @Override
        public TextureAtlasSprite getSprite(InnocentSparkEntity entity) {
            return RenderUtil.getSprite(WizardsReborn.MOD_ID, "item/innocent_wood_axe");
        }

        @Override
        public void tick(InnocentSparkEntity entity) {
            entity.tickMove();

            if (entity.getSender() != null && entity.tickCount > 20) {
                BlockPos center = BlockPos.containing(entity.position());
                blockTick(entity, center, 0.05f, 0.01f, 15);
            }
        }

        @Override
        public float getDistance(InnocentSparkEntity entity) {
            return 7;
        }

        @Override
        public boolean canBreak(InnocentSparkEntity entity, Level level, BlockPos blockPos, BlockPos center) {
            BlockState blockState = level.getBlockState(blockPos);
            return blockState.is(BlockTags.MINEABLE_WITH_AXE) && blockState.is(BlockTags.LOGS);
        }
    }

    public static class ShovelSparkType extends BlockSparkType {

        @Override
        public boolean isItem(InnocentSparkEntity entity) {
            return entity.getItem().getItem() == WizardsRebornItems.INNOCENT_WOOD_SHOVEL.get();
        }

        @Override
        public TextureAtlasSprite getSprite(InnocentSparkEntity entity) {
            return RenderUtil.getSprite(WizardsReborn.MOD_ID, "item/innocent_wood_shovel");
        }

        @Override
        public void tick(InnocentSparkEntity entity) {
            entity.tickMove();

            if (entity.getSender() != null && entity.tickCount > 20) {
                BlockPos center = BlockPos.containing(entity.getStart().add(0, 1.5f, 0));
                blockTick(entity, center, 0.05f, 0.01f, 10);
            }
        }

        @Override
        public float getDistance(InnocentSparkEntity entity) {
            return 5;
        }

        @Override
        public float getWidth(InnocentSparkEntity entity) {
            return 3;
        }

        @Override
        public boolean canBreak(InnocentSparkEntity entity, Level level, BlockPos blockPos, BlockPos center) {
            BlockState blockState = level.getBlockState(blockPos);
            float destroyTime = blockState.getBlock().defaultDestroyTime();
            return blockState.is(BlockTags.MINEABLE_WITH_SHOVEL) && destroyTime > 0 && destroyTime < 20f && blockPos.getY() >= center.getY() - 3;
        }
    }

    public static class HoeSparkType extends BlockSparkType {

        @Override
        public boolean isItem(InnocentSparkEntity entity) {
            return entity.getItem().getItem() == WizardsRebornItems.INNOCENT_WOOD_HOE.get();
        }

        @Override
        public TextureAtlasSprite getSprite(InnocentSparkEntity entity) {
            return RenderUtil.getSprite(WizardsReborn.MOD_ID, "item/innocent_wood_hoe");
        }

        @Override
        public void tick(InnocentSparkEntity entity) {
            entity.tickMove();

            if (entity.getSender() != null && entity.tickCount > 20) {
                BlockPos center = BlockPos.containing(entity.position());
                blockTick(entity, center, 0.05f, 0.01f, 5);
            }
        }

        @Override
        public float getDistance(InnocentSparkEntity entity) {
            return 10;
        }

        @Override
        public boolean canBreak(InnocentSparkEntity entity, Level level, BlockPos blockPos, BlockPos center) {
            BlockState blockState = level.getBlockState(blockPos);
            return blockState.is(BlockTags.MINEABLE_WITH_HOE) || blockState.is(BlockTags.LEAVES);
        }
    }

    public static class ScytheSparkType extends BlockSparkType {

        @Override
        public boolean isItem(InnocentSparkEntity entity) {
            return entity.getItem().getItem() == WizardsRebornItems.INNOCENT_WOOD_SCYTHE.get();
        }

        @Override
        public TextureAtlasSprite getSprite(InnocentSparkEntity entity) {
            return RenderUtil.getSprite(WizardsReborn.MOD_ID, "item/innocent_wood_scythe");
        }

        @Override
        public void tick(InnocentSparkEntity entity) {
            entity.tickMove();

            if (entity.getSender() != null && entity.tickCount > 5) {
                BlockPos center = BlockPos.containing(entity.position());
                blockTick(entity, center, 0.05f, 0.01f, 5);
            }
        }

        @Override
        public void blockBreak(InnocentSparkEntity entity, BlockPos center) {
            Level level = entity.level();
            BlockPos blockPos = BlockPos.containing(entity.position());
            BlockState blockState = level.getBlockState(blockPos);
            BlockEvent.BreakEvent breakEvent = new BlockEvent.BreakEvent(level, blockPos, level.getBlockState(blockPos), entity.getSender());
            if (canBreak(entity, level, blockPos, center)) {
                if (!blockState.isAir() && !MinecraftForge.EVENT_BUS.post(breakEvent)) {
                    BlockState replantState = ScytheItem.getReplantState(blockState);
                    BlockEvent.EntityPlaceEvent placeEvent = new BlockEvent.EntityPlaceEvent(BlockSnapshot.create(level.dimension(), level, blockPos), level.getBlockState(blockPos.below()), entity.getSender());
                    if (!MinecraftForge.EVENT_BUS.post(placeEvent)) {
                        level.setBlockAndUpdate(blockPos, replantState);
                        ScytheItem.dropStacks(blockState, (ServerLevel) level, blockPos, entity.getSender(), ItemStack.EMPTY);
                        level.playSound(null, blockPos, SoundEvents.CROP_BREAK, SoundSource.PLAYERS , 1.0f, 1.0f);
                    }
                }
            }
        }

        @Override
        public float getDistance(InnocentSparkEntity entity) {
            return 10;
        }

        @Override
        public boolean canBreak(InnocentSparkEntity entity, Level level, BlockPos blockPos, BlockPos center) {
            BlockState blockState = level.getBlockState(blockPos);
            return ScytheItem.isMature(blockState);
        }
    }
}
