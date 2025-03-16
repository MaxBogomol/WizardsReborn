package mod.maxbogomol.wizards_reborn.common.spell.ray;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.raycast.RayCast;
import mod.maxbogomol.fluffy_fur.common.raycast.RayCastContext;
import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellComponent;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.client.animation.SpellHandItemAnimation;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.RaySpellTrailPacket;
import mod.maxbogomol.wizards_reborn.common.spell.WandSpellContext;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.entity.WizardsRebornEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.UUID;
import java.util.function.Predicate;

public class RaySpell extends Spell {
    public static SpellHandItemAnimation animation = new SpellHandItemAnimation();

    public RaySpell(String id, int points) {
        super(id, points);
    }

    @Override
    public SpellComponent getSpellComponent() {
        return new RaySpellComponent();
    }

    public RaySpellComponent getSpellComponent(SpellEntity entity) {
        if (entity.getSpellComponent() instanceof RaySpellComponent spellComponent) {
            return spellComponent;
        }
        return new RaySpellComponent();
    }

    @Override
    public int getWissenCost() {
        return 20;
    }

    public float getRayDistance() {
        return 25f;
    }

    public Color getSecondColor() {
        return getColor();
    }

    @Override
    public void useSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            Vec3 pos = spellContext.getPos();
            Vec3 offset = spellContext.getOffset();
            SpellEntity entity = new SpellEntity(WizardsRebornEntities.SPELL.get(), level);
            entity.setup(pos.x() + offset.x(), pos.y() + offset.y(), pos.z() + offset.z(), spellContext.getEntity(), this.getId(), spellContext.getStats()).setSpellContext(spellContext);
            RaySpellComponent spellComponent = getSpellComponent(entity);
            spellComponent.fadeTick = 3;
            spellComponent.useTick = 1;

            updatePos(entity);
            updateRot(entity);
            spellComponent.vecOld = spellComponent.vec;
            entity.updateSpellComponent(spellComponent);

            level.addFreshEntity(entity);
            spellContext.awardStat(this);
            spellContext.spellSound(this);
            spellContext.startUsing(this);

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
                    if (spellEntity.getSpellComponent() instanceof RaySpellComponent spellComponent) {
                        spellComponent.useTick = 1;
                        spellEntity.updateSpellComponent(spellComponent);
                        spellEntity.setSpellContext(spellContext);
                        spellEntity.updateSpellContext(spellContext);

                        if (spellEntity.getSpellContext().canRemoveWissen(1)) {
                            if (spellEntity.tickCount % tickCost() == 0) {
                                spellEntity.getSpellContext().removeWissen(1);
                            }
                        } else {
                            spellContext.startUsing(this);
                        }
                    }
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
    public SpellContext getWandContext(Entity entity, ItemStack stack, InteractionHand hand) {
        WandSpellContext spellContext = WandSpellContext.getFromWand(entity, stack, hand);
        spellContext.setOffset(new Vec3(0, entity.getEyeHeight() - 0.3f, 0));
        return spellContext;
    }

    @Override
    public void entityTick(SpellEntity entity) {
        RaySpellComponent spellComponent = getSpellComponent(entity);
        updatePos(entity);
        updateRot(entity);

        Vec3 vec = spellComponent.vec;

        boolean burst = false;
        RayHitResult hitResult = getHit(entity, entity.position(), entity.position().add(vec.scale(getRayDistance())));

        if (hitResult.hasEntities()) {
            onImpact(entity.level(), entity, hitResult, hitResult.getEntities().get(0));
            burst = true;
        } else if (hitResult.hasBlock()) {
            onImpact(entity.level(), entity, hitResult);
            burst = true;
        }

        hitTick(entity, hitResult);

        if (!entity.level().isClientSide()) {
            if (spellComponent.useTick <= 0) {
                if (spellComponent.fadeTick <= 0) {
                    entity.remove();
                }
                if (spellComponent.fadeTick > 0) {
                    spellComponent.fadeTick = spellComponent.fadeTick - 1;
                }
                entity.updateSpellComponent(spellComponent);
            }

            if (spellComponent.useTick > 0) {
                spellComponent.endTick = entity.tickCount;
                spellComponent.useTick = spellComponent.useTick - 1;
                entity.updateSpellComponent(spellComponent);
            }
        }

        if (hasBurst(entity)) trailEffect(entity.level(), entity, hitResult, burst);
        if (random.nextFloat() < 0.5 && hasSound(entity)) {
            entity.level().playSound(WizardsReborn.proxy.getPlayer(), entity.getX(), entity.getY(), entity.getZ(), WizardsRebornSounds.SPELL_BURST.get(), SoundSource.PLAYERS, 0.25f, (float) (0.5f + ((random.nextFloat() - 0.5D) / 4)));
        }

        hitEndTick(entity, hitResult);
    }

    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult, Entity target) {

    }

    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult) {

    }

    public void hitTick(SpellEntity entity, RayHitResult hitResult) {

    }

    public void hitEndTick(SpellEntity entity, RayHitResult hitResult) {

    }

    public boolean hasBurst(SpellEntity entity) {
        return true;
    }

    public boolean hasSound(SpellEntity entity) {
        return true;
    }

    public int tickCost() {
        return 7;
    }

    public void trailEffect(Level level, SpellEntity entity, RayHitResult hitResult, boolean burst) {
        if (!level.isClientSide()) {
            RaySpellComponent spellComponent = getSpellComponent(entity);
            Vec3 vec = spellComponent.vec;

            double distance = Math.sqrt(Math.pow(entity.getX() - hitResult.getPos().x(), 2) + Math.pow(entity.getY() - hitResult.getPos().y(), 2) + Math.pow(entity.getZ() - hitResult.getPos().z(), 2));
            Vec3 pos = entity.position();
            Vec3 posStart = vec.add(entity.position());
            Vec3 posEnd = vec.scale(distance).add(entity.position());

            WizardsRebornPacketHandler.sendToTracking(level, BlockPos.containing(pos), new RaySpellTrailPacket(posStart, posEnd, getColor(), burst ? 1 : 0));
        }
    }

    public void updatePos(SpellEntity entity) {
        RaySpellComponent spellComponent = getSpellComponent(entity);
        Vec3 offset = entity.getSpellContext().getOffset();

        if (spellComponent.useTick > 0) {
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
        RaySpellComponent spellComponent = getSpellComponent(entity);

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
        RaySpellComponent spellComponent = getSpellComponent(entity);
        Vec3 vec = spellComponent.vec;
        Vec3 vecOld = spellComponent.vecOld;
        double vecX = Mth.lerp(partialTicks, vecOld.x(), vec.x());
        double vecY = Mth.lerp(partialTicks, vecOld.y(), vec.y());
        double vecZ = Mth.lerp(partialTicks, vecOld.z(), vec.z());
        Vec3 lookVec = new Vec3(vecX, vecY, vecZ);

        RayHitResult hitResult = getHit(entity, entity.position(), entity.position().add(vec.scale(getRayDistance())));

        Color color = getColor();
        Color secondColor = getSecondColor();
        float tick = (ClientTickHandler.ticksInGame + partialTicks) * 1.75f;
        float offset = 1f;
        float width = 1f;

        if (entity.tickCount < 3) {
            width = (entity.tickCount + partialTicks) / 3f;
        } else {
            if (spellComponent.useTick <= 0) {
                width = 1f - ((entity.tickCount - spellComponent.endTick + partialTicks) / 3f);
            }
        }
        if (width > 1f) width = 1f;
        if (width < 0f) width = 0f;

        float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
        float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
        float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());

        poseStack.pushPose();
        double distance = Math.sqrt(Math.pow(entity.getX() - hitResult.getPos().x(), 2) + Math.pow(entity.getY() - hitResult.getPos().y(), 2) + Math.pow(entity.getZ() - hitResult.getPos().z(), 2));
        Vec3 pos1 = entity.getPosition(partialTicks).add(lookVec.scale(offset));
        Vec3 pos2 = entity.getPosition(partialTicks).add(lookVec.scale(distance));
        poseStack.translate(-x, -y, -z);
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/trail"))
                .setColor(color)
                .setSecondColor(secondColor)
                .setAlpha(width)
                .renderBeam(poseStack.last().pose(), pos1, pos2, 0.15f, (float) (0.15f * Mth.lerp(distance / getRayDistance(), 1f, 0.5f)));
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(-x, -y, -z);
        poseStack.translate(pos1.x(), pos1.y(), pos1.z());
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Axis.ZP.rotationDegrees(tick));
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/tiny_wisp"))
                .setColor(color)
                .setAlpha(0.5f * width)
                .renderCenteredQuad(poseStack, 0.1f * width)
                .setAlpha(width)
                .renderCenteredQuad(poseStack, 0.075f * width);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(-x, -y, -z);
        poseStack.translate(pos2.x(), pos2.y(), pos2.z());
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Axis.ZP.rotationDegrees(-tick));
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/star"))
                .setColor(secondColor)
                .setAlpha(0.5f * width)
                .renderCenteredQuad(poseStack, 0.15f * width)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/tiny_wisp"))
                .renderCenteredQuad(poseStack, 0.15f * width);
        poseStack.popPose();
    }

    public RayHitResult getHit(SpellEntity entity, Vec3 start, Vec3 end) {
        RayCastContext context = new RayCastContext(start, end).setEntityFilter(getEntityFilter(entity)).setEntityCount(1).setEntityEnd(true);
        return RayCast.getHit(entity.level(), context);
    }

    public Predicate<Entity> getEntityFilter(SpellEntity entity) {
        return (e) -> {
            return !e.isSpectator() && e.isPickable() && !e.equals(entity.getOwner());
        };
    }
}
