package mod.maxbogomol.wizards_reborn.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPoint;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.entity.InnocentSparkEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InnocentSparkRenderer<T extends InnocentSparkEntity> extends EntityRenderer<T> {
    public static Color RED_COLOR = new Color(0.694F, 0.274F, 0.309F);

    public InnocentSparkRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(InnocentSparkEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        Color color1 = WizardsRebornArcaneEnchantments.LIFE_ROOTS.getColor();
        Color color2 = RED_COLOR;

        List<TrailPoint> trail = new ArrayList<>(entity.trailPointBuilder.getTrailPoints());
        if (trail.size() > 1 && entity.tickCount >= entity.trailPointBuilder.trailLength.get()) {
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

        float alpha = 1f;
        if (entity.tickCount < 10) {
            alpha = (entity.tickCount + partialTicks) / 10f;
        }

        poseStack.pushPose();
        poseStack.translate(-x, -y, -z);
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/trail"))
                .setColor(color1)
                .setSecondColor(color2)
                .setAlpha(0)
                .setSecondAlpha(alpha)
                .renderTrail(poseStack, trail, (f) -> RenderUtil.LINEAR_IN_SEMI_ROUND_WIDTH_FUNCTION.apply(f) * 0.8f)
                .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_TEXTURE)
                .renderTrail(poseStack, trail, (f) -> RenderUtil.LINEAR_IN_SEMI_ROUND_WIDTH_FUNCTION.apply(f) * 0.4f);
        poseStack.popPose();

        if (entity.getFade()) alpha = entity.getFadeTick() / 30f;

        poseStack.pushPose();
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/star"))
                .setColor(color1)
                .setAlpha(0.5f * alpha)
                .renderCenteredQuad(poseStack, 0.7f)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/tiny_wisp"))
                .setColor(color2)
                .setAlpha(alpha)
                .renderCenteredQuad(poseStack, 0.5f);
        poseStack.popPose();

        for (InnocentSparkEntity.SparkType type : InnocentSparkEntity.getTypes()) {
            if (type.isItem(entity)) {
                poseStack.pushPose();
                float dX = (float) Mth.lerp(partialTicks, -entity.vectorOld.x(), -entity.vector.x());
                float dY = (float) Mth.lerp(partialTicks, -entity.vectorOld.y(), -entity.vector.y());
                float dZ = (float) Mth.lerp(partialTicks, -entity.vectorOld.z(), -entity.vector.z());

                double yaw = Math.atan2(dZ, dX);
                double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                poseStack.mulPose(Axis.YP.rotationDegrees((float) (Math.toDegrees(-yaw))));
                poseStack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch)));
                poseStack.translate(0, 1, 0);
                poseStack.mulPose(Axis.YP.rotationDegrees( 180f));
                poseStack.mulPose(Axis.ZP.rotationDegrees( 135f));
                poseStack.translate(0, 0, -0.125f);
                for (int i = 0; i < 5; i++) {
                    poseStack.pushPose();
                    poseStack.translate(0, 0, i * 0.025f);
                    RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                            .setUV(type.getSprite(entity))
                            .setColor(Color.WHITE)
                            .setAlpha(0.1f * alpha)
                            .enableSided()
                            .renderCenteredQuad(poseStack, 0.75f);
                    poseStack.popPose();
                }
                poseStack.popPose();
                break;
            }
        }
    }

    @Override
    public boolean shouldRender(T livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return null;
    }
}