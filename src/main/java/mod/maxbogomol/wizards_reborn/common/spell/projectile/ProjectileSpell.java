package mod.maxbogomol.wizards_reborn.common.spell.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPoint;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectileSpell extends Spell {
    public ProjectileSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public void useSpell(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);

            CompoundTag stats = getStats(stack);
            spawnSpellStandard(level, player, stats);
            setCooldown(stack, stats);
            removeWissen(stack, stats, player);
            awardStat(player, stack);
            spellSound(player, level);
        }
    }

    @Override
    public void entityTick(SpellProjectileEntity entity) {
        if (!entity.level().isClientSide) {
            if (!entity.getFade()) {
                boolean hasEffectTrue = true;
                HitResult ray = ProjectileUtil.getHitResultOnMoveVector(entity, (e) -> {
                    return !e.isSpectator() && e.isPickable() && (!e.getUUID().equals(entity.getSenderUUID()) || entity.tickCount > 5);
                });
                if (ray.getType() == HitResult.Type.ENTITY) {
                    entity.onImpact(ray, ((EntityHitResult) ray).getEntity());
                    hasEffectTrue = false;
                } else if (ray.getType() == HitResult.Type.BLOCK) {
                    entity.onImpact(ray);
                } else {
                    updatePos(entity);
                    updateRot(entity);
                }

                if (hasEffectTrue) entity.rayEffect();
            }
        } else {
            if (!entity.getFade()) {
                updatePos(entity);
                updateRot(entity);
                entity.addTrail(new Vec3(entity.position().toVector3f()));
            }
        }
    }

    public void updatePos(SpellProjectileEntity entity) {
        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.x * 0.98, (motion.y > 0 ? motion.y * 0.98 : motion.y) - 0.01f, motion.z * 0.98);

        Vec3 pos = entity.position();
        entity.xo = pos.x;
        entity.yo = pos.y;
        entity.zo = pos.z;
        entity.setPos(pos.x + motion.x, pos.y + motion.y, pos.z + motion.z);
    }

    public void updateRot(SpellProjectileEntity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        double d0 = vec3.horizontalDistance();
        entity.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        entity.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        entity.yRotO = entity.getYRot();
        entity.xRotO = entity.getXRot();
    }

    @Override
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player, Entity target) {
        projectile.setFade(true);
        projectile.setFadeTick(20);
        projectile.burstEffect();
        level.playSound(WizardsReborn.proxy.getPlayer(), projectile.getX(), projectile.getY(), projectile.getZ(), WizardsRebornSounds.SPELL_BURST.get(), SoundSource.PLAYERS, 0.35f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
    }

    @Override
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player) {
        projectile.setFade(true);
        projectile.setFadeTick(20);
        projectile.setPos(ray.getLocation().x, ray.getLocation().y, ray.getLocation().z);
        projectile.burstEffect();
        level.playSound(WizardsReborn.proxy.getPlayer(), projectile.getX(), projectile.getY(), projectile.getZ(), WizardsRebornSounds.SPELL_BURST.get(), SoundSource.PLAYERS, 0.35f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(SpellProjectileEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();
        VertexConsumer builder = bufferDelayed.getBuffer(FluffyFurRenderTypes.ADDITIVE);
        Color color =  getColor();

        List<Vec3> trailList = new ArrayList<>(entity.trail);
        if (trailList.size() > 1 && entity.tickCount >= 20) {
            Vec3 position = trailList.get(0);
            Vec3 nextPosition = trailList.get(1);
            float x = (float) Mth.lerp(partialTicks, position.x, nextPosition.x);
            float y = (float) Mth.lerp(partialTicks, position.y, nextPosition.y);
            float z = (float) Mth.lerp(partialTicks, position.z, nextPosition.z);
            trailList.set(0, new Vec3(x, y, z));
        }

        float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
        float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
        float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());

        if (trailList.size() > 1) {
            trailList.set(trailList.size() - 1, new Vec3(x, y, z));
        }

        List<TrailPoint> trail = new ArrayList<>();
        for (Vec3 point : trailList) {
            trail.add(new TrailPoint(point.subtract(entity.position())));
        }

        stack.pushPose();
        stack.translate(0, 0.2f, 0);
        stack.translate(entity.getX() - x, entity.getY() - y,  entity.getZ() - z);
        //RenderUtils.renderTrail(stack, builder, entity.position(), trailList, 0,0.15f, 0,1.0f, 1.0f, color, 8, true);
        //RenderUtils.renderTrail(stack, builder, entity.position(), trailList, 0,0.15f, 0,0.75f, 0.75f, color, 8, true);
        TextureAtlasSprite star = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(FluffyFur.MOD_ID, "particle/wisp"));

        RenderBuilder.create()
                .setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(star.getU0(), star.getV0(), star.getU1(), star.getV1())
                .setColor(color)
                .setAlpha(0.5f)
                .renderTrail(stack, trail, (f) -> {return f * 0.3f;});
        stack.popPose();
    }
}
