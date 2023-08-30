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
    private static final EntityDataAccessor<Optional<UUID>> casterId = SynchedEntityData.defineId(SpellProjectileEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<String> spellId = SynchedEntityData.defineId(SpellProjectileEntity.class, EntityDataSerializers.STRING);

    public SpellProjectileEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public Entity shoot(double x, double y, double z, double vx, double vy, double vz, UUID caster, String spell) {
        setPos(x, y, z);
        setDeltaMovement(vx, vy, vz);
        getEntityData().set(casterId, Optional.of(caster));
        getEntityData().set(spellId, spell);
        hurtMarked = true;
        return this;
    }

    @Override
    public void tick() {
        Vec3 motion = getDeltaMovement();
        setDeltaMovement(motion.x * 0.96, (motion.y > 0 ? motion.y * 0.96 : motion.y) - 0.02f, motion.z * 0.96);

        super.tick();

        Vec3 pos = position();
        xo = pos.x;
        yo = pos.y;
        zo = pos.z;
        setPos(pos.x + motion.x, pos.y + motion.y, pos.z + motion.z);

        if (!level().isClientSide) {
            HitResult ray = ProjectileUtil.getHitResultOnMoveVector(this, (e) -> {
                return !e.isSpectator() && e.isPickable() && !e.getUUID().equals(casterId);
            });
            if (ray.getType() == HitResult.Type.ENTITY) {
                onImpact(ray, ((EntityHitResult)ray).getEntity());
            }
            else if (ray.getType() == HitResult.Type.BLOCK) {
                onImpact(ray);
            }
            rayEffect();
        }
    }

    protected void onImpact(HitResult ray, Entity target) {
        removeAfterChangingDimensions();
        burstEffect();
    }

    protected void onImpact(HitResult ray) {
        removeAfterChangingDimensions();
        setPos(ray.getLocation().x, ray.getLocation().y, ray.getLocation().z);
        burstEffect();
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(spellId,"");
        getEntityData().define(casterId, Optional.empty());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        compound.putUUID("caster", getEntityData().get(casterId).get());
        compound.putString("spelll", getEntityData().get(spellId));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        getEntityData().set(casterId, Optional.of(compound.getUUID("caster")));
        getEntityData().set(spellId, compound.getString("spelll"));
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public Spell getSpell() {
        return Spells.getSpell(getEntityData().get(spellId));
    }

    public void rayEffect() {
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

    public void burstEffect() {
        Vec3 pos = position();

        Spell spell = getSpell();
        Color color = spell.getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        PacketHandler.sendToTracking(level(), new BlockPos((int) pos.x, (int) pos.y, (int) pos.z), new SpellBurstEffectPacket((float) pos.x, (float) pos.y + 0.2f, (float) pos.z, r, g, b));
    }
}
