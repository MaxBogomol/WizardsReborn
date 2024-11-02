package mod.maxbogomol.wizards_reborn.common.spell.aura;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SparkParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.AuraSpellCastPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AuraSpell extends Spell {

    public AuraSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public int getCooldown() {
        return 400;
    }

    @Override
    public int getWissenCost() {
        return 400;
    }

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
    }

    @Override
    public boolean canUseSpell(Level level, SpellContext spellContext) {
        return false;
    }

    @Override
    public boolean useSpellOn(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            Vec3 pos = spellContext.getBlockPos().getCenter();
            SpellEntity entity = new SpellEntity(WizardsRebornEntities.SPELL.get(), level);
            entity.setup(pos.x(), pos.y() + 0.5f, pos.z(), spellContext.getEntity(), this.getId(), spellContext.getStats()).setSpellContext(spellContext);
            level.addFreshEntity(entity);
            spellContext.setCooldown(this);
            spellContext.removeWissen(this);
            spellContext.awardStat(this);
            spellContext.spellSound(this);
            WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new AuraSpellCastPacket(pos, getColor()));
            return true;
        }
        return false;
    }

    @Override
    public void entityTick(SpellEntity entity) {
        if (!entity.level().isClientSide()) {
            auraTick(entity.level(), entity, getTargets(entity));
            if (entity.tickCount > getLifeTime(entity)) {
                entity.remove();
                entity.level().playSound(WizardsReborn.proxy.getPlayer(), entity.getX(), entity.getY(), entity.getZ(), WizardsRebornSounds.SPELL_BURST.get(), SoundSource.PLAYERS, 1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
            }
        }

        if (entity.level().isClientSide) {
            float size = getSize(entity) * getSizeStats(entity) * 0.9f;
            if (random.nextFloat() < 0.6f) {
                Color color = getColor();
                Vec3 pos = entity.position();

                if (random.nextFloat() < 0.35f) {
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                            .setScaleData(GenericParticleData.create(0.3f, 0.5f, 0f).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomSpin(1f).build())
                            .setLifetime(40)
                            .randomVelocity(0.025f)
                            .addVelocity(0, 0.2f, 0)
                            .randomOffset(0.125f, 0, 0.125f)
                            .spawn(entity.level(), pos.x(), pos.y(), pos.z());
                }
                if (random.nextFloat() < 0.5f) {
                    ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 0.15f, 0f).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.4f).build())
                            .setLifetime(30)
                            .randomVelocity(0.035f)
                            .addVelocity(0, 0.22f, 0)
                            .randomOffset(0.15f, 0, 0.15f)
                            .spawn(entity.level(), pos.x(), pos.y(), pos.z());
                }
                if (random.nextFloat() < 0.25f) {
                    ParticleBuilder.create(FluffyFurParticles.SMOKE)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.1f, 0.3f, 0f).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setScaleData(GenericParticleData.create(0.2f, 1f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.05f).build())
                            .setLifetime(30)
                            .randomVelocity(0.025f)
                            .addVelocity(0, 0.2f, 0)
                            .randomOffset(0.35f, 0, 0.35f)
                            .spawn(entity.level(), pos.x(), pos.y(), pos.z());
                }

                if (random.nextFloat() < 0.45f) {
                    ParticleBuilder.create(FluffyFurParticles.SQUARE)
                            .setBehavior(SparkParticleBehavior.create().build())
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.5f, 0.75f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setLifetime(30, 5)
                            .setVelocity(0, 0.15f, 0)
                            .randomOffset(size, 0, size)
                            .spawn(entity.level(), pos.x(), pos.y(), pos.z());
                }
                if (random.nextFloat() < 0.45f) {
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                            .setScaleData(GenericParticleData.create(0.15f, 0f).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.4f).build())
                            .setLifetime(30)
                            .randomVelocity(0.035f)
                            .addVelocity(0, 0.15f, 0)
                            .randomOffset(size, 0, size)
                            .spawn(entity.level(), pos.x(), pos.y(), pos.z());
                }
            }
        }
    }

    public int getLifeTime(SpellEntity entity) {
        return 200;
    }

    public float getSize(SpellEntity entity) {
        return 5;
    }

    public float getSizeStats(SpellEntity entity) {
        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
        return 0.7f + (0.3f * (focusLevel / 3f));
    }

    public List<Entity> getTargets(SpellEntity entity) {
        float size = getSize(entity) * getSizeStats(entity);
        List<Entity> entityList =  entity.level().getEntitiesOfClass(Entity.class, new AABB(entity.getX() - size, entity.getY() - 1, entity.getZ() - size, entity.getX() + size, entity.getY() + 3, entity.getZ() + size));
        List<Entity> targets = new ArrayList<>();
        for (Entity target : entityList) {
            if (Math.sqrt(target.distanceToSqr(entity)) < size && !target.equals(entity)) {
                targets.add(target);
            }
        }

        return targets;
    }

    public void auraTick(Level level, SpellEntity entity, List<Entity> targets) {

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(SpellEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
        Color color = getColor();
        float ticks = (entity.tickCount + partialTicks) * 2f;
        float alpha = 1f;
        int lifeTime = getLifeTime(entity);
        float size = getSize(entity);
        float sizeS = getSizeStats(entity);

        if (entity.tickCount < 20) {
            alpha = (entity.tickCount + partialTicks) / 20f;
        }
        if (entity.tickCount > lifeTime - 20) {
            alpha = ((lifeTime - entity.tickCount - partialTicks) / 20f);
        }
        if (alpha > 1f) alpha = 1f;
        if (alpha < 0f) alpha = 0f;
        float sinSize = (float) Math.sin(Math.toRadians(alpha * 90f));

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(ticks));
        RenderBuilder builder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                .setColor(color)
                .setFirstAlpha(0.05f * alpha)
                .setSecondAlpha(0f)
                .enableSided()
                .renderRay(poseStack, 0.8f, 10, 0.75f);
        poseStack.mulPose(Axis.YP.rotationDegrees(-ticks * 2));
        builder.setFirstAlpha(0.15f * alpha)
                .renderRay(poseStack, 0.5f, 7, 0.75f);
        poseStack.mulPose(Axis.YP.rotationDegrees(ticks * 2));
        poseStack.mulPose(Axis.YP.rotationDegrees(ticks * 0.4f));
        builder.setFirstAlpha(0.5f * alpha)
                .renderRay(poseStack, 0.25f, 5, 0.75f);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0, 0.01f, 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(-ticks * 0.3f));
        builder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                .setColor(color)
                .setFirstAlpha(0.2f * alpha)
                .setSecondAlpha(0f)
                .enableSided();
        WizardsRebornRenderUtil.renderAura(builder, poseStack, size * sizeS * sinSize, 2, 8, true);
        builder.setFirstAlpha(0.3f * alpha);
        WizardsRebornRenderUtil.renderAura(builder, poseStack, (size - 0.5f) * sizeS * sinSize, 1.5f, 8, true);
        builder.setFirstAlpha(0.05f * alpha);
        WizardsRebornRenderUtil.renderAura(builder, poseStack, (size - 1f) * sizeS * sinSize, 1f, 8, true);
        poseStack.popPose();
    }
}
