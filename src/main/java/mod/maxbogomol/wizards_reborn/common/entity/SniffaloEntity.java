package mod.maxbogomol.wizards_reborn.common.entity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.gui.container.SniffaloContainer;
import mod.maxbogomol.wizards_reborn.common.item.CargoCarpetItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.SniffaloScreenPacket;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public class SniffaloEntity extends Sniffer implements ContainerListener, HasCustomInventoryScreen, OwnableEntity, PlayerRideableJumping, Saddleable {
    public static final EntityDataAccessor<Boolean> tamedId = SynchedEntityData.defineId(SniffaloEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> saddledId = SynchedEntityData.defineId(SniffaloEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> risingId = SynchedEntityData.defineId(SniffaloEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<ItemStack> carpetId = SynchedEntityData.defineId(SniffaloEntity.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<ItemStack> bannerId = SynchedEntityData.defineId(SniffaloEntity.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<ItemStack> armorId = SynchedEntityData.defineId(SniffaloEntity.class, EntityDataSerializers.ITEM_STACK);

    @Nullable
    public UUID owner;
    public SimpleContainer inventory;

    public SniffaloEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
        this.createInventory();
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide()) {
            if (this.entityData.get(DATA_STATE) == State.DIGGING) {
                this.entityData.set(risingId, tickCount);
            }
            if (getOwnerUUID() == null) {
                Entity entity = getFirstPassenger();
                if (entity instanceof Player player) {
                    if (getRandom().nextInt(100) < 1 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                        tameWithName(player);
                    }
                }
            }
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.1F).add(Attributes.MAX_HEALTH, 28f).add(Attributes.ARMOR, 2f);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
        return WizardsReborn.SNIFFALO.get().create(level);
    }

    @Override
    public void dropSeed() {
        if (!this.level().isClientSide() && this.entityData.get(DATA_DROP_SEED_AT_TICK) == this.tickCount) {
            ServerLevel serverlevel = (ServerLevel)this.level();
            LootTable loottable = serverlevel.getServer().getLootData().getLootTable(WizardsReborn.SNIFFALO_DIGGING_LOOT_TABLE);
            LootParams lootparams = (new LootParams.Builder(serverlevel)).withParameter(LootContextParams.ORIGIN, this.getHeadPosition()).withParameter(LootContextParams.THIS_ENTITY, this).create(LootContextParamSets.GIFT);
            List<ItemStack> list = loottable.getRandomItems(lootparams);
            BlockPos blockpos = this.getHeadBlock();

            for(ItemStack itemstack : list) {
                ItemEntity itementity = new ItemEntity(serverlevel, (double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), itemstack);
                itementity.setDefaultPickUpDelay();
                serverlevel.addFreshEntity(itementity);
            }

            this.playSound(SoundEvents.SNIFFER_DROP_SEED, 1.0F, 1.0F);
        }
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (pPlayer.isSecondaryUseActive() && !this.isBaby() && this.isTamed() ) {
            this.openCustomInventoryScreen(pPlayer);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            InteractionResult interactionresult = super.mobInteract(pPlayer, pHand);
            boolean flag = this.isFood(itemstack);
            if (interactionresult.consumesAction() && flag) return interactionresult;

            if (itemstack.getItem() instanceof BannerItem && isSaddled()) {
                setBanner(itemstack.copyWithCount(1));
                level().gameEvent(this, GameEvent.EQUIP, this.position());
                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

            interactionresult = itemstack.interactLivingEntity(pPlayer, this, pHand);
            if (interactionresult.consumesAction()) {
                return interactionresult;
            } else {
                if (this.getPassengers().size() < 2 && !this.isBaby()) {
                    getBrain().clearMemories();
                    this.doPlayerRide(pPlayer);
                }

                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
    }

    protected void doPlayerRide(Player pPlayer) {
        if (!this.level().isClientSide) {
            pPlayer.setYRot(this.getYRot());
            pPlayer.setXRot(this.getXRot());
            pPlayer.startRiding(this);
        }
    }

    protected void tickRidden(@NotNull Player player, @NotNull Vec3 travelVector) {
        super.tickRidden(player, travelVector);
        Vec2 vec2 = this.getRiddenRotation(player);
        if (canControl()) {
            this.setRot(vec2.y, vec2.x);
            this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
        }
    }

    protected Vec2 getRiddenRotation(LivingEntity pEntity) {
        return new Vec2(pEntity.getXRot() * 0.5F, pEntity.getYRot());
    }

    protected @NotNull Vec3 getRiddenInput(Player player, @NotNull Vec3 travelVector) {
        if (!canControl()) {
            return Vec3.ZERO;
        } else {
            float f = player.xxa * 0.5F;
            float f1 = player.zza;
            if (f1 <= 0.0F) {
                f1 *= 0.25F;
            }

            return new Vec3(f, 0.0D, f1);
        }
    }

    protected float getRiddenSpeed(Player player) {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.75f;
    }

    protected void positionRider(Entity pPassenger, Entity.MoveFunction pCallback) {
        int i = this.getPassengers().indexOf(pPassenger);
        if (i >= 0) {
            boolean flag = i == 0;
            float f = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
            float f1 = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
            float f2 = flag ? -0.4F : 0.4F;
            float f3 = 0.15F + positionRiderOffset();

            pCallback.accept(pPassenger, this.getX() + (double) (f2 * f), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() + (double) f3, this.getZ() - (double) (f2 * f1));
            if (pPassenger instanceof LivingEntity) {
                ((LivingEntity) pPassenger).yBodyRot = this.yBodyRot;
            }
        }
    }

    public float positionRiderOffset() {
        float offset = 0F;

        if (this.entityData.get(DATA_STATE) == State.DIGGING) {
            int startTick = this.entityData.get(DATA_DROP_SEED_AT_TICK) - 95;
            if (tickCount > startTick) {
                if (tickCount - startTick < 5) {
                    float prt = ((tickCount - startTick) / 5f);
                    offset = offset - (prt * 0.5f);
                } else {
                    offset = offset - 0.5f;
                }
            }
        }
        if (this.entityData.get(DATA_STATE) == State.RISING) {
            int startTick = this.entityData.get(risingId) + 20;
            if (tickCount > startTick) {
                if (tickCount - startTick < 5) {
                    float prt = ((tickCount - startTick) / 5f);
                    offset = offset - 0.5f + (prt * 0.5f);
                }
            } else {
                offset = offset - 0.5f;
            }
        }

        return offset;
    }

    public boolean isPushable() {
        return !this.isVehicle();
    }

    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        if (pFallDistance > 1.0F) {
            this.playSound(SoundEvents.SNIFFER_STEP, 0.4F, 1.0F);
        }

        int i = this.calculateFallDamage(pFallDistance, pMultiplier);
        if (i <= 0) {
            return false;
        } else {
            this.hurt(pSource, (float)i);
            if (this.isVehicle()) {
                for(Entity entity : this.getIndirectPassengers()) {
                    entity.hurt(pSource, (float)i);
                }
            }

            this.playBlockFallSound();
            return true;
        }
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        if (!this.getPassengers().isEmpty() && this.isSaddled()) {
            Entity entity = this.getPassengers().get(0);
            if (entity instanceof LivingEntity) {
                return (LivingEntity)entity;
            }
        }

        return null;
    }

    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() <= 2;
    }

    public boolean tameWithName(Player pPlayer) {
        this.setOwnerUUID(pPlayer.getUUID());
        this.setTamed(true);
        if (pPlayer instanceof ServerPlayer) {
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)pPlayer, this);
        }

        this.level().broadcastEntityEvent(this, (byte)7);
        return true;
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel level, Animal mate) {
        ItemStack itemstack = new ItemStack(WizardsReborn.SNIFFALO_EGG_ITEM.get());
        ItemEntity itementity = new ItemEntity(level, this.position().x(), this.position().y(), this.position().z(), itemstack);
        itementity.setDefaultPickUpDelay();
        this.finalizeSpawnChildFromBreeding(level, mate, (AgeableMob)null);
        this.playSound(SoundEvents.SNIFFER_EGG_PLOP, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 0.5F);
        level.addFreshEntity(itementity);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(WizardsReborn.SNIFFALO_FOOD_ITEM_TAG);
    }

    @Override
    public boolean canMate(Animal pOtherAnimal) {
        if (!(pOtherAnimal instanceof SniffaloEntity sniffalo)) {
            return false;
        } else {
            Set<State> set = Set.of(Sniffer.State.IDLING, Sniffer.State.SCENTING, Sniffer.State.FEELING_HAPPY);
            if (set.contains(this.entityData.get(DATA_STATE)) && set.contains(sniffalo.entityData.get(DATA_STATE))) {
                if (pOtherAnimal == this) {
                    return false;
                } else if (pOtherAnimal.getClass() != this.getClass()) {
                    return false;
                } else {
                    return this.isInLove() && pOtherAnimal.isInLove();
                }
            }
        }
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(tamedId, false);
        getEntityData().define(saddledId, false);
        getEntityData().define(risingId, 0);
        getEntityData().define(carpetId, ItemStack.EMPTY);
        getEntityData().define(bannerId, ItemStack.EMPTY);
        getEntityData().define(armorId, ItemStack.EMPTY);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Tame", getEntityData().get(tamedId));
        compound.putBoolean("Saddled", getEntityData().get(saddledId));
        compound.putInt("RisingTick", getEntityData().get(risingId));
        if (this.getOwnerUUID() != null) {
            compound.putUUID("Owner", this.getOwnerUUID());
        }

        compound.put("inv", serializeNBT());

        compound.put("CarpetItem", getEntityData().get(carpetId).save(new CompoundTag()));
        compound.put("BannerItem", getEntityData().get(bannerId).save(new CompoundTag()));
        compound.put("ArmorItem", getEntityData().get(armorId).save(new CompoundTag()));
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        getEntityData().set(tamedId, compound.getBoolean("Tame"));
        getEntityData().set(saddledId, compound.getBoolean("Saddled"));
        getEntityData().set(risingId, compound.getInt("RisingTick"));
        UUID uuid;
        if (compound.hasUUID("Owner")) {
            uuid = compound.getUUID("Owner");
        } else {
            String s = compound.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }

        if (uuid != null) {
            this.setOwnerUUID(uuid);
        }
        deserializeNBT(compound.getCompound("inv"));

        getEntityData().set(carpetId, ItemStack.of(compound.getCompound("CarpetItem")));
        getEntityData().set(bannerId, ItemStack.of(compound.getCompound("BannerItem")));
        getEntityData().set(armorId, ItemStack.of(compound.getCompound("ArmorItem")));

        this.updateContainerEquipment();
    }

    @Override
    public CompoundTag serializeNBT() {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (!inventory.getItem(i).isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                inventory.getItem(i).save(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        inventory.clearContent();
        ListTag tagList = nbt.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < inventory.getContainerSize()) {
                inventory.setItem(slot, ItemStack.of(itemTags));
            }
        }
    }

    public void spawnTamingParticles() {
        ParticleOptions particleoptions = ParticleTypes.HEART;

        for (int i = 0; i < 25; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 7) {
            this.spawnTamingParticles();
        } else {
            super.handleEntityEvent(pId);
        }
    }

    public boolean canControl() {
        return !(this.entityData.get(DATA_STATE) == State.DIGGING || this.entityData.get(DATA_STATE) == State.RISING);
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return this.owner;
    }

    public void setOwnerUUID(@Nullable UUID pUuid) {
        this.owner = pUuid;
    }

    public boolean isTamed() {
        return getEntityData().get(tamedId);
    }

    public void setTamed(boolean tamed) {
        getEntityData().set(tamedId, tamed);
    }

    @Override
    public void onPlayerJump(int jumpPower) {

    }

    @Override
    public boolean canJump() {
        return true;
    }

    @Override
    public void handleStartJump(int jumpPower) {

    }

    @Override
    public void handleStopJump() {

    }

    @Override
    public boolean isSaddleable() {
        return this.isAlive() && !this.isBaby() && this.isTamed();
    }

    @Override
    public void equipSaddle(@Nullable SoundSource source) {
        this.inventory.setItem(0, new ItemStack(Items.SADDLE));
    }

    @Override
    public boolean isSaddled() {
        return getEntityData().get(saddledId);
    }

    public void setCarpet(ItemStack itemStack) {
        this.inventory.setItem(2, itemStack);
        getEntityData().set(carpetId, itemStack.copy());
    }

    public ItemStack getCarpet() {
        return this.inventory.getItem(2);
    }

    public void setBanner(ItemStack itemStack) {
        this.inventory.setItem(3, itemStack);
        getEntityData().set(bannerId, itemStack.copy());
    }

    public ItemStack getBanner() {
        return this.inventory.getItem(3);
    }

    public ItemStack getCarpetClient() {
        return getEntityData().get(carpetId);
    }

    public ItemStack getBannerClient() {
        return getEntityData().get(bannerId);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected int getInventorySize() {
        return 28;
    }

    public void createInventory() {
        SimpleContainer simplecontainer = this.inventory;
        this.inventory = new SimpleContainer(this.getInventorySize());
        if (simplecontainer != null) {
            simplecontainer.removeListener(this);
            int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

            for(int j = 0; j < i; ++j) {
                ItemStack itemstack = simplecontainer.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }

        this.inventory.addListener(this);
        this.updateContainerEquipment();
        this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.inventory));
    }

    public void updateContainerEquipment() {
        if (!this.level().isClientSide) {
            getEntityData().set(saddledId, !this.inventory.getItem(0).isEmpty());
            getEntityData().set(carpetId, this.inventory.getItem(1).copy());
            getEntityData().set(bannerId, this.inventory.getItem(2).copy());
        }
    }

    @Override
    public void containerChanged(Container pInvBasic) {
        boolean flag = this.isSaddled();
        this.updateContainerEquipment();
        if (this.tickCount > 20 && !flag && this.isSaddled()) {
            this.playSound(this.getSaddleSoundEvent(), 0.5F, 1.0F);
        }
    }

    protected void dropEquipment() {
        super.dropEquipment();
        if (this.inventory != null) {
            for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
                ItemStack itemstack = this.inventory.getItem(i);
                if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                    this.spawnAtLocation(itemstack);
                }
            }
        }
    }

    public boolean hasInventoryChanged(Container pInventory) {
        return this.inventory != pInventory;
    }

    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.core.Direction facing) {
        if (this.isAlive() && capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER && itemHandler != null)
            return itemHandler.cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (itemHandler != null) {
            net.minecraftforge.common.util.LazyOptional<?> oldHandler = itemHandler;
            itemHandler = null;
            oldHandler.invalidate();
        }
    }

    @Override
    public void openCustomInventoryScreen(Player player) {
        if (!this.level().isClientSide && (!this.isVehicle() || this.hasPassenger(player)) && this.isTamed()) {
            openSniffaloInventory(player, this, this.inventory);
        }
    }

    public static void openSniffaloInventory(Player player, SniffaloEntity sniffalo, Container inventory) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.containerMenu != serverPlayer.inventoryMenu) {
                serverPlayer.closeContainer();
            }

            serverPlayer.nextContainerCounter();
            PacketHandler.sendTo(serverPlayer, new SniffaloScreenPacket(serverPlayer.containerCounter, inventory.getContainerSize(), sniffalo.getId()));
            serverPlayer.containerMenu = new SniffaloContainer(serverPlayer.containerCounter, serverPlayer.getInventory(), inventory, serverPlayer, sniffalo);
            serverPlayer.initMenu(serverPlayer.containerMenu);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.PlayerContainerEvent.Open(serverPlayer, serverPlayer.containerMenu));
        }
    }

    private SlotAccess createEquipmentSlotAccess(final int pSlot, final Predicate<ItemStack> pStackFilter) {
        return new SlotAccess() {
            public ItemStack get() {
                return SniffaloEntity.this.inventory.getItem(pSlot);
            }

            public boolean set(ItemStack p_149528_) {
                if (!pStackFilter.test(p_149528_)) {
                    return false;
                } else {
                    SniffaloEntity.this.inventory.setItem(pSlot, p_149528_);
                    SniffaloEntity.this.updateContainerEquipment();
                    return true;
                }
            }
        };
    }

    public SlotAccess getSlot(int pSlot) {
        int i = pSlot - 400;
        if (i >= 0 && i < 28 && i < this.inventory.getContainerSize()) {
            if (i == 0) {
                return this.createEquipmentSlotAccess(i, (itemStack) -> {
                    return itemStack.isEmpty() || itemStack.is(Items.SADDLE);
                });
            }

            if (i == 1) {
                return this.createEquipmentSlotAccess(i, (itemStack) -> {
                    return itemStack.isEmpty() || itemStack.getItem() instanceof CargoCarpetItem;
                });
            }

            if (i == 2) {
                return this.createEquipmentSlotAccess(i, (itemStack) -> {
                    return itemStack.isEmpty() || itemStack.getItem() instanceof BannerItem;
                });
            }
/*
            if (i > 3) {
                return this.createEquipmentSlotAccess(i, (itemStack) -> {
                    return !this.getCarpet().isEmpty();
                });
            }*/
        }

        int j = pSlot - 500 + 28;
        return j >= 28 && j < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, j) : super.getSlot(pSlot);
    }
}
