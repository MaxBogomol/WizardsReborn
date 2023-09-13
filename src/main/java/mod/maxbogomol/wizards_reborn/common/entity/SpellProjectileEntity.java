package mod.maxbogomol.wizards_reborn.common.entity;

import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.SpellBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.SpellProjectileRayEffectPacket;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.awt.*;
import java.util.Optional;
import java.util.UUID;

public class SpellProjectileEntity extends Entity {
    public static final EntityDataAccessor<Optional<UUID>> casterId = SynchedEntityData.defineId(SpellProjectileEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<String> spellId = SynchedEntityData.defineId(SpellProjectileEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<CompoundTag> crystalStats = SynchedEntityData.defineId(SpellProjectileEntity.class, EntityDataSerializers.COMPOUND_TAG);

    public SpellProjectileEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public Entity shoot(double x, double y, double z, double vx, double vy, double vz, UUID caster, String spell, CompoundTag stats) {
        setPos(x, y, z);
        setDeltaMovement(vx, vy, vz);
        getEntityData().set(casterId, Optional.of(caster));
        getEntityData().set(spellId, spell);
        getEntityData().set(crystalStats, stats);
        hurtMarked = true;
        return this;
    }

    @Override
    public void tick() {
        super.tick();

        Spell spell = getSpell();

        spell.entityTick(this);
    }

    public void onImpact(HitResult ray, Entity target) {
        Spell spell = getSpell();
        spell.onImpact(ray, level(), this, level().getPlayerByUUID(getEntityData().get(casterId).get()), target);
    }

    public void onImpact(HitResult ray) {
        Spell spell = getSpell();
        spell.onImpact(ray, level(), this, level().getPlayerByUUID(getEntityData().get(casterId).get()));
    }

    @Override
    protected void defineSynchedData() {
        getEntityData().define(spellId,"");
        getEntityData().define(casterId, Optional.empty());
        getEntityData().define(crystalStats, new CompoundTag());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        compound.putUUID("caster", getEntityData().get(casterId).get());
        compound.putString("spelll", getEntityData().get(spellId));
        compound.put("stats", getEntityData().get(crystalStats));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        getEntityData().set(casterId, Optional.of(compound.getUUID("caster")));
        getEntityData().set(spellId, compound.getString("spelll"));
        getEntityData().set(crystalStats, compound.getCompound("stats"));
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public Spell getSpell() {
        return Spells.getSpell(getEntityData().get(spellId));
    }

    public void rayEffect() {
        if (tickCount > 1) {
            Vec3 motion = getDeltaMovement();
            Vec3 pos = position();
            Vec3 norm = motion.normalize().scale(0.025f);

            Spell spell = getSpell();
            Color color = spell.getColor();
            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;

            PacketHandler.sendToTracking(level(), new BlockPos((int) pos.x, (int) pos.y, (int) pos.z), new SpellProjectileRayEffectPacket((float) xo, (float) yo + 0.2f, (float) zo, (float) pos.x, (float) pos.y + 0.2f, (float) pos.z, (float) norm.x, (float) norm.y, (float) norm.z, r, g, b));
        }
    }

    public void burstEffect() {
        Vec3 pos = position();

        Spell spell = getSpell();
        Color color = spell.getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        PacketHandler.sendToTracking(level(), new BlockPos((int) pos.x, (int) pos.y, (int) pos.z), new SpellBurstEffectPacket((float) pos.x, (float) pos.y + 0.2f, (float) pos.z, r, g, b));
    }

    public void remove() {
        removeAfterChangingDimensions();
    }

    public CompoundTag getStats() {
        return getEntityData().get(crystalStats);
    }
}
