package mod.maxbogomol.wizards_reborn.common.entity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.SpellBurstEffectPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.awt.*;
import java.util.Optional;
import java.util.UUID;

public class SpellProjectileEntity extends Entity {
    private static final DataParameter<Optional<UUID>> casterId = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private static final DataParameter<String> spellId = EntityDataManager.createKey(SpellProjectileEntity.class, DataSerializers.STRING);

    public SpellProjectileEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public Entity shoot(double x, double y, double z, double vx, double vy, double vz, UUID caster, String spell) {
        setPosition(x, y, z);
        setMotion(vx, vy, vz);
        getDataManager().set(casterId, Optional.of(caster));
        getDataManager().set(spellId, spell);
        velocityChanged = true;
        return this;
    }

    @Override
    public void tick() {
        Vector3d motion = getMotion();
        setMotion(motion.x * 0.96, (motion.y > 0 ? motion.y * 0.96 : motion.y) - 0.03f, motion.z * 0.96);

        super.tick();

        if (!world.isRemote) {
            RayTraceResult ray = ProjectileHelper.func_234618_a_(this, (e) -> !e.isSpectator() && e.canBeCollidedWith() && !e.getUniqueID().equals(getDataManager().get(casterId)));
            if (ray.getType() == RayTraceResult.Type.ENTITY) {
                onImpact(ray, ((EntityRayTraceResult)ray).getEntity());
            }
            else if (ray.getType() == RayTraceResult.Type.BLOCK) {
                onImpact(ray);
            }
        }

        Vector3d pos = getPositionVec();
        prevPosX = pos.x;
        prevPosY = pos.y;
        prevPosZ = pos.z;
        setPosition(pos.x + motion.x, pos.y + motion.y, pos.z + motion.z);

        if (world.isRemote) {
            rayEffect();
        }
    }

    protected void onImpact(RayTraceResult ray, Entity target) {
        setDead();
        if (!world.isRemote) {
            burstEffect();
        }
    }

    protected void onImpact(RayTraceResult ray) {
        setDead();
        if (!world.isRemote) {
            burstEffect();
        }
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(spellId,"");
        getDataManager().register(casterId, Optional.empty());
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        compound.putUniqueId("caster", getDataManager().get(casterId).get());
        compound.putString("spelll", getDataManager().get(spellId));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        getDataManager().set(casterId, Optional.of(compound.getUniqueId("caster")));
        getDataManager().set(spellId, compound.getString("spelll"));
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public Spell getSpell() {
        return Spells.getSpell(getDataManager().get(spellId));
    }

    public void rayEffect() {
        Vector3d motion = getMotion();
        Vector3d pos = getPositionVec();
        Vector3d norm = motion.normalize().scale(0.025f);

        Spell spell = getSpell();
        Color color = spell.getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        for (int i = 0; i < 10; i ++) {
            double lerpX = MathHelper.lerp(i / 10.0f, prevPosX, pos.x);
            double lerpY = MathHelper.lerp(i / 10.0f, prevPosY, pos.y);
            double lerpZ = MathHelper.lerp(i / 10.0f, prevPosZ, pos.z);

            Particles.create(WizardsReborn.WISP_PARTICLE)
                    .addVelocity(-norm.x + ((rand.nextDouble() - 0.5D) / 500), -norm.y + ((rand.nextDouble() - 0.5D) / 500), -norm.z + ((rand.nextDouble() - 0.5D) / 500))
                    .setAlpha(0.2f, 0).setScale(0.15f, 0)
                    .setColor(r, g, b)
                    .setLifetime(20)
                    .spawn(world, lerpX, lerpY, lerpZ);

            if (rand.nextFloat() < 0.1f) {
                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                        .addVelocity(-norm.x + ((rand.nextDouble() - 0.5D) / 250), -norm.y + ((rand.nextDouble() - 0.5D) / 250), -norm.z + ((rand.nextDouble() - 0.5D) / 250))
                        .setAlpha(0.125f, 0).setScale(0.2f, 0)
                        .setColor(r, g, b)
                        .setLifetime(30)
                        .spawn(world, lerpX, lerpY, lerpZ);
            }
        }
    }

    public void burstEffect() {
        Vector3d pos = getPositionVec();

        Spell spell = getSpell();
        Color color = spell.getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        PacketHandler.sendToTracking(world, new BlockPos(pos), new SpellBurstEffectPacket((float) pos.x, (float) pos.y, (float) pos.z, r, g, b));
    }
}
