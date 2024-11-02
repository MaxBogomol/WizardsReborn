package mod.maxbogomol.wizards_reborn.common.spell.charge;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPoint;
import mod.maxbogomol.fluffy_fur.common.raycast.RayCast;
import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellComponent;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.client.animation.ChargeSpellHandItemAnimation;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.ChargeSpellTrailPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.ProjectileSpellBurstPacket;
import mod.maxbogomol.wizards_reborn.common.spell.WandSpellContext;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class ChargeSpell extends Spell {
    public static ChargeSpellHandItemAnimation animation = new ChargeSpellHandItemAnimation();

    public ChargeSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public SpellComponent getSpellComponent() {
        return new ChargeSpellComponent();
    }

    public ChargeSpellComponent getSpellComponent(SpellEntity entity) {
        if (entity.getSpellComponent() instanceof ChargeSpellComponent spellComponent) {
            return spellComponent;
        }
        return new ChargeSpellComponent();
    }

    @Override
    public int getWissenCost() {
        return 35;
    }

    public int getCharge() {
        return 30;
    }

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
    }

    @Override
    public void useSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            Vec3 pos = spellContext.getPos();
            Vec3 offset = spellContext.getOffset();
            SpellEntity entity = new SpellEntity(WizardsRebornEntities.SPELL.get(), level);
            entity.setup(pos.x() + offset.x(), pos.y() + offset.y(), pos.z() + offset.z(), spellContext.getEntity(), this.getId(), spellContext.getStats()).setSpellContext(spellContext);
            ChargeSpellComponent spellComponent = getSpellComponent(entity);
            spellComponent.useTick = 1;

            updatePos(entity);
            updateRot(entity);
            spellComponent.vecOld = spellComponent.vec;
            entity.updateSpellComponent(spellComponent);

            level.addFreshEntity(entity);
            spellContext.removeWissen(this);
            spellContext.awardStat(this);
            spellContext.spellSound(this);

            CompoundTag spellData = new CompoundTag();
            spellData.putUUID("entity", entity.getUUID());
            spellContext.setSpellData(spellData);
        }
    }

    @Override
    public void useSpellTick(Level level, SpellContext spellContext, int remainingUseDuration) {
        if (!level.isClientSide()) {
            CompoundTag spellData = spellContext.getSpellData();
            if (spellData.contains("entity")) {
                UUID entityUUID = spellData.getUUID("entity");
                Entity entity = ((ServerLevel) level).getEntity(entityUUID);
                if (entity instanceof SpellEntity spellEntity) {
                    ChargeSpellComponent spellComponent = (ChargeSpellComponent) spellEntity.getSpellComponent();
                    spellComponent.useTick = 1;
                    spellComponent.charge = spellComponent.charge + 1;
                    if (spellComponent.charge > getCharge()) {
                        spellComponent.charge = getCharge();
                    }
                    spellEntity.updateSpellComponent(spellComponent);
                    spellEntity.updateSpellContext(spellContext);
                }
            }
        }
    }

    @Override
    public void stopUseSpell(Level level, SpellContext spellContext, int timeLeft) {
        if (!level.isClientSide()) {
            spellContext.setCooldown(this);
        }
    }

    @Override
    public SpellContext getWandContext(Entity entity, ItemStack stack) {
        WandSpellContext spellContext = WandSpellContext.getFromWand(entity, stack);
        spellContext.setOffset(new Vec3(0, entity.getEyeHeight(), 0));
        return spellContext;
    }

    @Override
    public void useWand(Level level, Player player, InteractionHand hand, ItemStack stack) {
        useSpell(level, getWandContext(player, stack));
        if (!level.isClientSide()) {
            player.startUsingItem(hand);
        }
    }

    @Override
    public void entityTick(SpellEntity entity) {
        ChargeSpellComponent spellComponent = getSpellComponent(entity);

        if (!spellComponent.fade) {
            boolean hasEffect = true;
            RayHitResult hitResult = getHit(entity, entity.position(), entity.position().add(entity.getDeltaMovement()));

            hitTick(entity, hitResult);

            if (spellComponent.throwed) {
                if (hitResult.hasEntities()) {
                    onImpact(entity.level(), entity, hitResult, hitResult.getEntities().get(0));
                    hasEffect = false;
                } else if (hitResult.hasBlock()) {
                    onImpact(entity.level(), entity, hitResult);
                } else {
                    updatePos(entity);
                    updateRot(entity);
                }
            } else {
                updatePos(entity);
                updateRot(entity);
            }

            if (!entity.level().isClientSide()) {
                if (!spellComponent.throwed) {
                    if (spellComponent.useTick <= 0) {
                        spellComponent.throwed = true;
                        Vec3 vel = spellComponent.vec.scale(40).scale(1.0 / 25);
                        entity.setDeltaMovement(vel);
                        entity.updateSpellComponent(spellComponent);
                    }

                    if (spellComponent.useTick > 0) {
                        spellComponent.endTick = entity.tickCount;
                        spellComponent.useTick = spellComponent.useTick - 1;
                        entity.updateSpellComponent(spellComponent);
                    }
                } else {
                    if (spellComponent.tick <= 500) {
                        spellComponent.tick = spellComponent.tick + 1;
                        entity.updateSpellComponent(spellComponent);
                    } else {
                        spellComponent.fade = true;
                        spellComponent.fadeTick = spellComponent.getTrailSize() + 1;
                        burstEffect(entity.level(), entity);
                        entity.updateSpellComponent(spellComponent);
                    }
                }
            }

            if (hasEffect && (spellComponent.tick > 1 || !spellComponent.throwed)) trailEffect(entity.level(), entity, hitResult);
            hitEndTick(entity, hitResult);
        } else {
            entity.setDeltaMovement(0, 0, 0);
            if (!entity.level().isClientSide()) {
                if (spellComponent.fadeTick <= 0) {
                    entity.remove();
                }
                spellComponent.fadeTick = spellComponent.fadeTick - 1;
                entity.updateSpellComponent(spellComponent);
            }
        }

        if (entity.level().isClientSide()) {
            Vec3 vec = spellComponent.vec.scale(40).scale(1.0 / 25);
            if (!spellComponent.throwed) {
                spellComponent.trailPointBuilder.addTrailPoint(entity.position().add(vec));
            } else {
                if (spellComponent.tick > 1) {
                    spellComponent.trailPointBuilder.addTrailPoint(entity.position());
                }
            }
            spellComponent.trailPointBuilder.tickTrailPoints();
        }
    }

    public void updatePos(SpellEntity entity) {
        ChargeSpellComponent spellComponent = getSpellComponent(entity);
        Vec3 offset = entity.getSpellContext().getOffset();

        if (spellComponent.throwed) {
            Vec3 motion = entity.getDeltaMovement();
            entity.setDeltaMovement(motion.x * 0.99, motion.y * 0.99, motion.z * 0.99);

            Vec3 pos = entity.position();
            entity.xo = pos.x;
            entity.yo = pos.y;
            entity.zo = pos.z;
            entity.setPos(pos.x + motion.x, pos.y + motion.y, pos.z + motion.z);
        } else {
            if (entity.getOwner() != null) {
                Entity owner = entity.getOwner();
                entity.setPos(owner.getX() + offset.x(), owner.getY() + offset.y(), owner.getZ() + offset.z());
                entity.xo = owner.xo + offset.x();
                entity.yo = owner.yo + offset.y();
                entity.zo = owner.zo + offset.z();
                entity.xOld = owner.xo + offset.x();
                entity.yOld = owner.yo + offset.y();
                entity.zOld = owner.zo + offset.z();
            }
        }
    }

    public void updateRot(SpellEntity entity) {
        ChargeSpellComponent spellComponent = getSpellComponent(entity);

        if (spellComponent.throwed) {
            Vec3 vec3 = entity.getDeltaMovement();
            double d0 = vec3.horizontalDistance();
            entity.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)));
            entity.setXRot((float) (Mth.atan2(vec3.y, d0) * (double) (180F / (float) Math.PI)));
            entity.yRotO = entity.getYRot();
            entity.xRotO = entity.getXRot();
        }
        if (spellComponent.useTick > 0) {
            if (entity.getOwner() != null) {
                Entity owner = entity.getOwner();
                spellComponent.vecOld = spellComponent.vec;
                spellComponent.vec = owner.getLookAngle();
            }
        } else {
            spellComponent.vecOld = spellComponent.vec;
        }
    }

    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult, Entity target) {
        if (!level.isClientSide()) {
            ChargeSpellComponent spellComponent = getSpellComponent(entity);
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
            ChargeSpellComponent spellComponent = getSpellComponent(entity);
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
            ChargeSpellComponent spellComponent = getSpellComponent(entity);
            float charge = (float) (0.5f + ((spellComponent.charge / getCharge()) / 2f));
            if (!spellComponent.throwed) {
                Vec3 pos = entity.position();
                Vec3 vec = pos.add(spellComponent.vec.scale(40).scale(1.0 / 25));
                Vec3 vecOld = pos.add(spellComponent.vecOld.scale(40).scale(1.0 / 25));
                WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new ChargeSpellTrailPacket(vec, vecOld, Vec3.ZERO, getColor(), charge));
            } else {
                Vec3 motion = entity.getDeltaMovement();
                Vec3 pos = entity.position();
                Vec3 norm = motion.normalize().scale(0.005f);
                WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new ChargeSpellTrailPacket(new Vec3(entity.xo, entity.yo, entity.zo), pos, norm, getColor(), charge));
            }
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public boolean hasCustomAnimation(ItemStack stack) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemAnimation getAnimation(ItemStack stack) {
        return animation;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(SpellEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
        Color color =  getColor();
        ChargeSpellComponent spellComponent = getSpellComponent(entity);
        float charge = (float) (0.5f + (((spellComponent.charge + (!spellComponent.throwed ? partialTicks : 0)) / getCharge()) / 2f));
        if (charge > 1f) charge = 1f;

        Vec3 vec = spellComponent.vec;
        Vec3 vecOld = spellComponent.vecOld;
        double vecX = Mth.lerp(partialTicks, vecOld.x(), vec.x());
        double vecY = Mth.lerp(partialTicks, vecOld.y(), vec.y());
        double vecZ = Mth.lerp(partialTicks, vecOld.z(), vec.z());
        Vec3 lookVec = new Vec3(vecX, vecY, vecZ).scale(40).scale(1.0 / 25);

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

        if (!spellComponent.throwed) {
            if (trail.size() > 0) {
                trail.set(trail.size() - 1, new TrailPoint(new Vec3(x, y, z).add(lookVec)));
            }
        } else if (spellComponent.tick > 1) {
            if (trail.size() > 0) {
                trail.set(trail.size() - 1, new TrailPoint(new Vec3(x, y, z)));
            }
        }

        poseStack.pushPose();
        poseStack.translate(-x, -y, -z);
        float finalCharge = charge;
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/trail"))
                .setColor(color)
                .setFirstAlpha(0.5f)
                .renderTrail(poseStack, trail, (f) -> {return f * 0.3f * finalCharge;});
        poseStack.popPose();
    }

    public RayHitResult getHit(SpellEntity entity, Vec3 start, Vec3 endPos, Predicate<Entity> entityFilter) {
        return RayCast.getHit(entity.level(), start, endPos, entityFilter, 1, 0.1f, true);
    }

    public RayHitResult getHit(SpellEntity entity, Vec3 start, Vec3 endPos) {
        return RayCast.getHit(entity.level(), start, endPos, getEntityFilter(entity), 1, 0.1f, true);
    }

    public Predicate<Entity> getEntityFilter(SpellEntity entity) {
        ChargeSpellComponent spellComponent = getSpellComponent(entity);
        return (e) -> {
            return !e.isSpectator() && e.isPickable() && (!e.equals(entity.getOwner()) || (spellComponent.tick > 5));
        };
    }
}
