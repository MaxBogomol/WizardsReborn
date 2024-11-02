package mod.maxbogomol.wizards_reborn.common.spell.look.strike;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.client.animation.StrikeSpellItemAnimation;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.StrikeSpellBurstPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.StrikeSpellScreenshakePacket;
import mod.maxbogomol.wizards_reborn.common.spell.look.block.BlockLookSpell;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;

public class StrikeSpell extends BlockLookSpell {
    public static StrikeSpellItemAnimation animation = new StrikeSpellItemAnimation();

    public StrikeSpell(String id, int points) {
        super(id, points);
    }

    public Color getSecondColor() {
        return getColor();
    }

    @Override
    public double getLookDistance() {
        return 10f;
    }

    @Override
    public double getBlockDistance() {
        return 25f;
    }

    @Override
    public int getCooldown() {
        return 300;
    }

    @Override
    public int getWissenCost() {
        return 500;
    }

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
    }

    @Override
    public void useSpellTick(Level level, SpellContext spellContext, int time) {
        if (!level.isClientSide()) {
            if (time > getUseTime(spellContext)) {
                spellContext.setCooldown(this);
                spellContext.removeWissen(this);
                spellContext.awardStat(this);
                spellContext.spellSound(this);
            }
        } else {
            Vec3 pos = getBlockHit(level, spellContext).getPos();
            float stage = (float) time / getUseTime(spellContext);
            ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setColorData(ColorParticleData.create(getColor(), getSecondColor()).build())
                    .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                    .setScaleData(GenericParticleData.create(0.2f * stage, 0).build())
                    .setLifetime(30)
                    .randomVelocity(0.025f)
                    .addVelocity(0, 0.03f, 0)
                    .spawn(level, pos.x(), pos.y(), pos.z());
        }
        if (time > getUseTime(spellContext)) {
            lookSpell(level, spellContext);
        }
    }

    @Override
    public void useWand(Level level, Player player, InteractionHand hand, ItemStack stack) {
        if (!level.isClientSide()) {
            player.startUsingItem(hand);
        }
    }

    @Override
    public void useWandTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.useWandTick(level, livingEntity, stack, remainingUseDuration);
        if (!super.canSpell(level, getWandContext(livingEntity, stack))) {
            livingEntity.stopUsingItem();
        }
        if (remainingUseDuration > getUseTime(getWandContext(livingEntity, stack))) {
            livingEntity.stopUsingItem();
        }
    }

    @Override
    public boolean canLookSpell(Level level, SpellContext spellContext) {
        return true;
    }

    @Override
    public void lookSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            Vec3 pos = getBlockHit(level, spellContext).getPos();
            SpellEntity entity = new SpellEntity(WizardsRebornEntities.SPELL.get(), level);
            entity.setup(pos.x(), pos.y(), pos.z(), spellContext.getEntity(), this.getId(), spellContext.getStats()).setSpellContext(spellContext);
            level.addFreshEntity(entity);
        }
    }

    @Override
    public void entityTick(SpellEntity entity) {
        if (!entity.level().isClientSide()) {
            if (entity.tickCount > getLifeTime(entity)) {
                entity.remove();
            }
            if (entity.tickCount == 20) {
                WizardsRebornPacketHandler.sendToTracking(entity.level(), entity.blockPosition(), new StrikeSpellScreenshakePacket(entity.position()));

                strikeDamage(entity);
                strikeEffect(entity);

                for (int i = 0; i < 8; i++) {
                    BlockPos blockPos = entity.getOnPos();
                    BlockState blockState = entity.level().getBlockState(blockPos);
                    boolean blockSound = false;
                    if (!blockState.isAir()) {
                        blockSound = true;
                    } else {
                        blockPos = entity.getOnPos().below();
                        blockState = entity.level().getBlockState(blockPos);
                        if (!blockState.isAir()) blockSound = true;
                    }

                    if (blockSound) {
                        SoundType soundType = blockState.getBlock().getSoundType(blockState);
                        entity.level().playSound(WizardsReborn.proxy.getPlayer(), entity.getX() + ((random.nextDouble() - 0.5D) * 5f), entity.getY() + ((random.nextDouble() - 0.5D) * 5f), entity.getZ() + ((random.nextDouble() - 0.5D) * 5f), soundType.getBreakSound(), SoundSource.PLAYERS, 1f, 1f);
                    }

                    entity.level().playSound(WizardsReborn.proxy.getPlayer(), entity.getX() + ((random.nextDouble() - 0.5D) * 5f), entity.getY() + ((random.nextDouble() - 0.5D) * 5f), entity.getZ() + ((random.nextDouble() - 0.5D) * 5f), WizardsRebornSounds.SPELL_BURST.get(), SoundSource.PLAYERS, 1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                }
            }
        }
    }

    public List<Entity> getTargets(SpellEntity entity, float distance) {
        return entity.level().getEntitiesOfClass(Entity.class, new AABB(entity.getX() - distance, entity.getY() - 1, entity.getZ() - distance, entity.getX() + distance, entity.getY() + 3, entity.getZ() + distance));
    }

    public void strikeDamage(SpellEntity entity) {

    }

    public void strikeEffect(SpellEntity entity) {
        WizardsRebornPacketHandler.sendToTracking(entity.level(), entity.blockPosition(), new StrikeSpellBurstPacket(entity.position(), getColor()));
    }

    public int getUseTime(SpellContext spellContext) {
        return 100;
    }

    public int getLifeTime(SpellEntity entity) {
        return 60;
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
        float ticks = (entity.tickCount + partialTicks);
        float alpha = 1f;
        int lifeTime = getLifeTime(entity);
        float size = 1f;
        float sizeEnd = 1f;

        if (entity.tickCount < 20) {
            alpha = (entity.tickCount + partialTicks) / 20f;
            size = alpha;
            sizeEnd = alpha;
        }
        if (entity.tickCount > 20) {
            alpha = ((lifeTime - entity.tickCount - partialTicks) / (lifeTime - 20));
            sizeEnd = 1f + ((1f - alpha) * 3);
        }
        if (alpha > 1f) alpha = 1f;
        if (alpha < 0f) alpha = 0f;

        poseStack.pushPose();
        poseStack.translate(0, 100 - (100 * size), 0);
        poseStack.pushPose();
        RenderBuilder builder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                .setColor(getColor())
                .setFirstAlpha(0.4f * alpha)
                .setSecondAlpha(0f)
                .enableSided()
                .renderRay(poseStack, 0.2f * alpha, 100 * size, 0.4f)
                .setFirstAlpha(0.2f * alpha)
                .setColor(getSecondColor())
                .renderRay(poseStack, 0.5f * alpha, 100 * size, 0.4f);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(ticks));
        builder.setColor(getColor())
                .setFirstAlpha(0.08f * alpha)
                .renderRay(poseStack, 0.38f * sizeEnd, 100 * size, 0.4f)
                .setFirstAlpha(0.05f * alpha)
                .renderRay(poseStack, 0.4f * sizeEnd, 100 * size, 0.4f);
        poseStack.popPose();
        poseStack.popPose();
    }
}
