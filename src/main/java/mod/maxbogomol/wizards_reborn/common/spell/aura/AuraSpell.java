package mod.maxbogomol.wizards_reborn.common.spell.aura;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.AuraSpellCastEffectPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
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
    public boolean canSpellAir(Level level, Player player, InteractionHand hand) {
        return false;
    }

    @Override
    public InteractionResult onWandUseOn(ItemStack stack, UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();

        if (!level.isClientSide && canSpell(level, player, context.getHand())) {
            CompoundTag stats = getStats(stack);

            Vec3 pos = context.getClickedPos().getCenter();
            SpellProjectileEntity entity = new SpellProjectileEntity(WizardsRebornEntities.SPELL_PROJECTILE.get(), level).shoot(
                    pos.x, pos.y + 0.5, pos.z, 0, 0, 0, player.getUUID(), this.getId(), stats
            );
            level.addFreshEntity(entity);

            setCooldown(stack, stats);
            removeWissen(stack, stats, player);
            awardStat(player, stack);
            spellSound(player, context.getLevel());

            Color color = getColor();
            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;

            PacketHandler.sendToTracking(level, player.getOnPos(), new AuraSpellCastEffectPacket((float) pos.x, (float) (pos.y + 0.5f), (float) pos.z, r, g, b));

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void entityTick(SpellProjectileEntity entity) {
        if (!entity.level().isClientSide) {
            onAura(entity.level(), entity, entity.getSender(), getTargets(entity));
            if (entity.tickCount > getLifeTime(entity)) {
                entity.remove();
                entity.level().playSound(WizardsReborn.proxy.getPlayer(), entity.getX(), entity.getY(), entity.getZ(), WizardsRebornSounds.SPELL_BURST.get(), SoundSource.PLAYERS, 1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
            }
        }

        if (entity.level().isClientSide) {
            if (random.nextFloat() < 0.6f) {
                Color color = getColor();
                Vec3 pos = entity.position();

                if (random.nextFloat() < 0.35f) {
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                            .setScaleData(GenericParticleData.create(0.5f, 0f).build())
                            .setSpinData(SpinParticleData.create().randomSpin(1f).build())
                            .setLifetime(40)
                            .randomVelocity(0.025f)
                            .addVelocity(0, 0.2f, 0)
                            .randomOffset(0.125f, 0, 0.125f)
                            .spawn(entity.level(), pos.x(), pos.y() - 0.2f, pos.z());
                }
                if (random.nextFloat() < 0.25f) {
                    ParticleBuilder.create(FluffyFurParticles.SMOKE)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.1f, 0).build())
                            .setScaleData(GenericParticleData.create(1f, 0).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.05f).build())
                            .setLifetime(30)
                            .randomVelocity(0.025f)
                            .addVelocity(0, 0.2f, 0)
                            .randomOffset(0.15f, 0, 0.15f)
                            .spawn(entity.level(), pos.x(), pos.y() - 0.2f, pos.z());
                }
                if (random.nextFloat() < 0.5f) {
                    ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                            .setScaleData(GenericParticleData.create(0.15f, 0f).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.4f).build())
                            .setLifetime(30)
                            .randomVelocity(0.035f)
                            .addVelocity(0, 0.22f, 0)
                            .randomOffset(0.15f, 0, 0.15f)
                            .spawn(entity.level(), pos.x(), pos.y() - 0.2f, pos.z());
                }

                if (random.nextFloat() < 0.45f) {
                    ParticleBuilder.create(FluffyFurParticles.CUBE)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                            .setScaleData(GenericParticleData.create(0.15f, 0f).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.4f).build())
                            .setLifetime(30)
                            .randomVelocity(0.035f)
                            .addVelocity(0, 0.25f, 0)
                            .randomOffset(3.75f, 0, 3.75f)
                            .spawn(entity.level(), pos.x(), pos.y() - 0.2f, pos.z());
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
                            .randomOffset(3.75f, 0, 3.75f)
                            .spawn(entity.level(), pos.x(), pos.y() - 0.2f, pos.z());
                }
            }
        }
    }

    public int getLifeTime(SpellProjectileEntity entity) {
        return 200;
    }

    public float getSize(SpellProjectileEntity entity) {
        return 5;
    }

    public float getSizeStats(SpellProjectileEntity entity) {
        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
        return 0.7f + (0.3f * (focusLevel / 3f));
    }

    public List<Entity> getTargets(SpellProjectileEntity projectile) {
        float size = getSize(projectile) * getSizeStats(projectile);
        List<Entity> entityList =  projectile.level().getEntitiesOfClass(Entity.class, new AABB(projectile.getX() - size, projectile.getY() - 1, projectile.getZ() - size, projectile.getX() + size, projectile.getY() + 3, projectile.getZ() + size));
        List<Entity> targets = new ArrayList<>();
        for (Entity target : entityList) {
            if (Math.sqrt(target.distanceToSqr(projectile)) < size && !target.equals(projectile)) {
                targets.add(target);
            }
        }

        return targets;
    }

    public void onAura(Level level, SpellProjectileEntity projectile, Player player, List<Entity> targets) {

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(SpellProjectileEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();
        VertexConsumer builder = bufferDelayed.getBuffer(FluffyFurRenderTypes.GLOWING);
        Color color = getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

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

        stack.pushPose();
        stack.mulPose(Axis.ZP.rotationDegrees(90f));
        stack.mulPose(Axis.XP.rotationDegrees(ticks));
        RenderUtils.raySided(stack, bufferDelayed, 0.8f, 10, 0.75f, r, g, b, 0.05f * alpha, r, g, b, 0F);
        stack.mulPose(Axis.XP.rotationDegrees(-ticks * 2));
        RenderUtils.raySided(stack, bufferDelayed, 0.5f, 7, 0.75f, r, g, b, 0.15f * alpha, r, g, b, 0F);
        stack.mulPose(Axis.XP.rotationDegrees(ticks * 2));
        stack.mulPose(Axis.XP.rotationDegrees(ticks * 0.4f));
        RenderUtils.raySided(stack, bufferDelayed, 0.25f, 5, 0.75f, r, g, b, 0.5f * alpha, r, g, b, 0F);
        stack.popPose();

        stack.pushPose();
        stack.translate(0, 0.01f, 0);
        stack.mulPose(Axis.YP.rotationDegrees(-ticks * 0.3f));
        RenderUtils.renderAura(stack, builder, size * sizeS, 2, 8, color, color, 0.2f * alpha, 0, true, true);
        RenderUtils.renderAura(stack, builder, (size - 0.5f) * sizeS, 1.5f, 8, color, color, 0.3f * alpha, 0, true, false);
        RenderUtils.renderAura(stack, builder, (size - 1f) * sizeS, 1f, 8, color, color, 0.05f * alpha, 0, true, false);
        stack.popPose();
    }
}
