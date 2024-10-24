package mod.maxbogomol.wizards_reborn.common.spell.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPoint;
import mod.maxbogomol.fluffy_fur.common.raycast.RayCast;
import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellComponent;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.ProjectileSpellBurstPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.ProjectileSpellTrailPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ProjectileSpell extends Spell {

    public ProjectileSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public SpellComponent getSpellComponent() {
        return new ProjectileSpellComponent();
    }

    public ProjectileSpellComponent getSpellComponent(SpellEntity entity) {
        if (entity.getSpellComponent() instanceof ProjectileSpellComponent spellComponent) {
            return spellComponent;
        }
        return new ProjectileSpellComponent();
    }

    @Override
    public void useSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            Vec3 pos = spellContext.getPos();
            Vec3 vel = spellContext.getVec().scale(40).scale(1.0 / 30);
            SpellEntity entity = new SpellEntity(WizardsRebornEntities.SPELL.get(), level);
            entity.setup(pos.x(), pos.y(), pos.z(), spellContext.getEntity(), this.getId(), spellContext.getStats()).setSpellContext(spellContext);
            entity.setDeltaMovement(vel);
            level.addFreshEntity(entity);
            spellContext.setCooldown(this);
            spellContext.removeWissen(this);
            spellContext.awardStat(this);
            spellContext.spellSound(this);
        }
    }

    @Override
    public void entityTick(SpellEntity entity) {
        ProjectileSpellComponent spellComponent = getSpellComponent(entity);

        if (!spellComponent.fade) {
            boolean hasEffect = true;
            RayHitResult hitResult = getHit(entity, entity.position(), entity.position().add(entity.getDeltaMovement()));

            hitTick(entity, hitResult);

            if (hitResult.hasEntities()) {
                onImpact(entity.level(), entity, hitResult, hitResult.getEntities().get(0));
                hasEffect = false;
            } else if (hitResult.hasBlock()) {
                onImpact(entity.level(), entity, hitResult);
            } else {
                updatePos(entity);
                updateRot(entity);
            }

            if (hasEffect && entity.tickCount > 1) trailEffect(entity.level(), entity, hitResult);
            hitEndTick(entity, hitResult);
        } else {
            entity.setDeltaMovement(0, 0, 0);
            if (spellComponent.fadeTick <= 0) {
                entity.remove();
            }
            spellComponent.fadeTick = spellComponent.fadeTick - 1;
            entity.updateSpellComponent(spellComponent);
        }

        if (entity.level().isClientSide()) {
            spellComponent.trailPointBuilder.addTrailPoint(entity.position());
            spellComponent.trailPointBuilder.tickTrailPoints();
        }
    }

    public void updatePos(SpellEntity entity) {
        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.x * 0.98, (motion.y > 0 ? motion.y * 0.98 : motion.y) - 0.01f, motion.z * 0.98);
        Vec3 pos = entity.position();
        entity.xo = pos.x;
        entity.yo = pos.y;
        entity.zo = pos.z;
        entity.setPos(pos.x + motion.x, pos.y + motion.y, pos.z + motion.z);
    }

    public void updateRot(SpellEntity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        double d0 = vec3.horizontalDistance();
        entity.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        entity.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        entity.yRotO = entity.getYRot();
        entity.xRotO = entity.getXRot();
    }

    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult, Entity target) {
        if (!level.isClientSide()) {
            ProjectileSpellComponent spellComponent = getSpellComponent(entity);
            spellComponent.fade = true;
            spellComponent.fadeTick = spellComponent.getTrailSize() + 1;
            entity.updateSpellComponent(spellComponent);
            entity.setPos(hitResult.getPos());
            burstEffectEntity(level, entity);
            burstSound(level, entity);
        }
    }

    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult) {
        if (!level.isClientSide()) {
            ProjectileSpellComponent spellComponent = getSpellComponent(entity);
            spellComponent.fade = true;
            spellComponent.fadeTick = spellComponent.getTrailSize() + 1;
            entity.updateSpellComponent(spellComponent);
            entity.setPos(hitResult.getPos());
            burstEffectBlock(level, entity);
            burstSound(level, entity);
        }
    }

    public void hitTick(SpellEntity entity, RayHitResult hitResult) {

    }

    public void hitEndTick(SpellEntity entity, RayHitResult hitResult) {

    }

    public void burstSound(Level level, SpellEntity entity) {
        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), WizardsRebornSounds.SPELL_BURST.get(), SoundSource.PLAYERS, 0.35f, (1f + ((random.nextFloat() - 0.5f) / 4f)));
    }

    public void burstEffect(Level level, SpellEntity entity) {
        if (!level.isClientSide()) {
            WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new ProjectileSpellBurstPacket(entity.position(), getColor()));
        }
    }

    public void burstEffectEntity(Level level, SpellEntity entity) {
        burstEffect(level, entity);
    }

    public void burstEffectBlock(Level level, SpellEntity entity) {
        burstEffect(level, entity);
    }

    public void trailEffect(Level level, SpellEntity entity, RayHitResult hitResult) {
        if (!level.isClientSide()) {
            Vec3 motion = entity.getDeltaMovement();
            Vec3 pos = entity.position();
            Vec3 norm = motion.normalize().scale(0.005f);
            WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new ProjectileSpellTrailPacket(new Vec3(entity.xo, entity.yo, entity.zo), pos, norm, getColor()));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(SpellEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
        Color color =  getColor();
        ProjectileSpellComponent spellComponent = getSpellComponent(entity);

        List<TrailPoint> trail = new ArrayList<>(spellComponent.trailPointBuilder.getTrailPoints());
        if (trail.size() > 1 && entity.tickCount >= spellComponent.trailPointBuilder.trailLength.get()) {
            TrailPoint position = trail.get(0);
            TrailPoint nextPosition = trail.get(1);
            float x = (float) Mth.lerp(partialTicks, position.getPosition().x, nextPosition.getPosition().x);
            float y = (float) Mth.lerp(partialTicks, position.getPosition().y, nextPosition.getPosition().y);
            float z = (float) Mth.lerp(partialTicks, position.getPosition().z, nextPosition.getPosition().z);
            trail.set(0, new TrailPoint(new Vec3(x, y, z)));
        }

        float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
        float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
        float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());

        if (trail.size() > 0) {
            trail.set(trail.size() - 1, new TrailPoint(new Vec3(x, y, z)));
        }

        poseStack.pushPose();
        poseStack.translate(-x, -y, -z);
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/trail"))
                .setColor(color)
                .setFirstAlpha(0.5f)
                .renderTrail(poseStack, trail, (f) -> {return f * 0.3f;});
        poseStack.popPose();
    }

    public RayHitResult getHit(SpellEntity entity, Vec3 start, Vec3 endPos, Predicate<Entity> entityFilter) {
        return RayCast.getHit(entity.level(), start, endPos, entityFilter, 1, 0.1f, true);
    }

    public RayHitResult getHit(SpellEntity entity, Vec3 start, Vec3 endPos) {
        return RayCast.getHit(entity.level(), start, endPos, getEntityFilter(entity), 1, 0.1f, true);
    }

    public Predicate<Entity> getEntityFilter(SpellEntity entity) {
        return (e) -> {
            return !e.isSpectator() && e.isPickable() && (!e.equals(entity.getOwner()) || entity.tickCount > 5);
        };
    }
}
