package mod.maxbogomol.wizards_reborn.common.entity;

import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellComponent;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.api.spell.SpellHandler;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpellEntity extends Entity {
    public static final EntityDataAccessor<Optional<UUID>> ownerId = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<String> spellId = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<CompoundTag> statsId = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.COMPOUND_TAG);
    public static final EntityDataAccessor<CompoundTag> spellContextId = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.COMPOUND_TAG);
    public static final EntityDataAccessor<CompoundTag> spellComponentId = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.COMPOUND_TAG);

    public SpellComponent spellComponent;
    public SpellContext spellContext;

    public Entity cachedOwner;

    public SpellEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public SpellEntity setup(double x, double y, double z, Entity owner, String spell, CompoundTag stats) {
        setPos(x, y, z);
        if (owner != null) {
            getEntityData().set(ownerId, Optional.of(owner.getUUID()));
        }
        getEntityData().set(spellId, spell);
        getEntityData().set(statsId, stats);
        return this;
    }

    public SpellEntity setSpellContext(SpellContext spellContext) {
        this.spellContext = spellContext;
        updateSpellContext(spellContext);
        return this;
    }

    @Override
    public void tick() {
        Spell spell = getSpell();
        if (spell != null) {
            spell.entityTick(this);
        } else {
            remove();
        }
    }

    @Override
    protected void defineSynchedData() {
        getEntityData().define(spellId,"");
        getEntityData().define(ownerId, Optional.empty());
        getEntityData().define(statsId, new CompoundTag());
        getEntityData().define(spellContextId, new CompoundTag());
        getEntityData().define(spellComponentId, new CompoundTag());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("owner")) {
            getEntityData().set(ownerId, Optional.of(compound.getUUID("owner")));
        }
        getEntityData().set(spellId, compound.getString("spell"));
        getEntityData().set(statsId, compound.getCompound("stats"));
        getEntityData().set(spellContextId, compound.getCompound("spellContext"));
        getEntityData().set(spellComponentId, compound.getCompound("spellComponent"));
        getSpellContext().fromTag(getSpellContextData());
        getSpellComponent().fromTag(getSpellComponentData());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        if (getEntityData().get(ownerId).isPresent()) {
            compound.putUUID("owner", getEntityData().get(ownerId).get());
        }
        compound.putString("spell", getEntityData().get(spellId));
        compound.put("stats", getEntityData().get(statsId));
        compound.put("spellContext", getEntityData().get(spellContextId));
        compound.put("spellComponent", getEntityData().get(spellComponentId));
    }

    @Override
    public void onSyncedDataUpdated(List<SynchedEntityData.DataValue<?>> dataValues) {
        getSpellContext().fromTag(getSpellContextData());
        getSpellComponent().fromTag(getSpellComponentData());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        double d0 = pPacket.getX();
        double d1 = pPacket.getY();
        double d2 = pPacket.getZ();
        this.setPos(d0, d1, d2);
    }

    @Override
    public float getEyeHeight(Pose pose, EntityDimensions size) {
        return 0.2F;
    }

    public Spell getSpell() {
        return SpellHandler.getSpell(getEntityData().get(spellId));
    }

    public void remove() {
        removeAfterChangingDimensions();
    }

    public CompoundTag getStats() {
        return getEntityData().get(statsId);
    }

    public CompoundTag getSpellContextData() {
        return getEntityData().get(spellContextId);
    }

    public void setSpellContextData(CompoundTag nbt) {
        getEntityData().set(spellContextId, nbt);
    }

    public CompoundTag getSpellComponentData() {
        return getEntityData().get(spellComponentId);
    }

    public void setSpellComponentData(CompoundTag nbt) {
        getEntityData().set(spellComponentId, nbt);
    }

    public Entity getOwner() {
        if (cachedOwner != null && !cachedOwner.isRemoved()) {
            return cachedOwner;
        } else if (getOwnerUUID() != null) {
            if (level() instanceof ServerLevel serverLevel) {
                cachedOwner = serverLevel.getEntity(getOwnerUUID());
                return cachedOwner;
            }
            if (level() instanceof ClientLevel clientLevel) {
                cachedOwner = clientLevel.getEntities().get(getOwnerUUID());
                return cachedOwner;
            }
        }
        return null;
    }

    public UUID getOwnerUUID() {
        return (getEntityData().get(ownerId).isPresent()) ? getEntityData().get(ownerId).get() : null;
    }

    public void initSpellContext() {
        if (spellContext == null) {
            spellContext = new SpellContext();
        }
    }

    public SpellContext getSpellContext() {
        if (spellContext == null) initSpellContext();
        return spellContext;
    }

    public void updateSpellContext(SpellContext spellContext) {
        this.spellContext = spellContext;
        setSpellContextData(spellContext.toTag());
    }

    public void initSpellComponent() {
        if (spellComponent == null) {
            Spell spell = getSpell();
            if (spell != null) {
                spellComponent = spell.getSpellComponent();
            } else {
                spellComponent = new SpellComponent();
            }
        }
    }

    public SpellComponent getSpellComponent() {
        if (spellComponent == null) initSpellComponent();
        return spellComponent;
    }

    public void updateSpellComponent(SpellComponent spellComponent) {
        this.spellComponent = spellComponent;
        setSpellComponentData(spellComponent.toTag());
    }
}
