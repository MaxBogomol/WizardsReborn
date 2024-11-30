package mod.maxbogomol.wizards_reborn.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPoint;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.entity.ThrowedScytheEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ThrowedScytheRenderer<T extends ThrowedScytheEntity> extends EntityRenderer<T> {

    public ThrowedScytheRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ThrowedScytheEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        Color color = WizardsRebornArcaneEnchantments.THROW.getColor();

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

        poseStack.pushPose();
        poseStack.translate(-x, -y, -z);
        poseStack.translate(0, 0.1f, 0);
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/trail"))
                .setColor(color)
                .setAlpha(0.5f)
                .renderTrail(poseStack, trail, (f) -> {return f * 0.08f;});
        poseStack.popPose();

        if (entity.getFade() && entity.getFadeTick() <= 30 && entity.getEndTick() > 0) {
            poseStack.pushPose();
            poseStack.translate(0, 0.1f, 0);
            Vec3 endPoint = entity.getEndPoint();
            double dX = endPoint.x() - entity.getX();
            double dY = endPoint.y() - entity.getY();
            double dZ = endPoint.z() - entity.getZ();

            float distance = (float) Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2) + Math.pow(dZ, 2));

            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

            float alpha = entity.getFadeTick() / 30f;

            poseStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-yaw)));
            poseStack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch) - 180f));
            poseStack.mulPose(Axis.YP.rotationDegrees(90f));
            RenderBuilder renderBuilder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                    .setColor(color)
                    .setAlpha(0.5f * alpha)
                    .setSecondAlpha(0.05f * alpha)
                    .enableSided();
            WizardsRebornRenderUtil.renderScytheTrail(renderBuilder, poseStack, 1f, distance);
            poseStack.popPose();
        }

        if (!entity.getFade()) {
            float tick = (entity.tickCount + partialTicks);
            int right = entity.getIsRight() ? 1 : -1;

            poseStack.pushPose();
            poseStack.translate(0f, 0.1f, 0f);
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
            poseStack.mulPose(Axis.XP.rotationDegrees(-90f * right));
            poseStack.mulPose(Axis.ZP.rotation(tick * 0.8f));
            poseStack.scale(2, 2f, 1f);
            poseStack.translate(0.25f, 0.25f, 0f);
            Minecraft.getInstance().getItemRenderer().renderStatic(entity.getItem(), ItemDisplayContext.NONE, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), 0);
            poseStack.popPose();
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