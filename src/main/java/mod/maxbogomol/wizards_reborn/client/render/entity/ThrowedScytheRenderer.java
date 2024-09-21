package mod.maxbogomol.wizards_reborn.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();
        VertexConsumer builder = bufferDelayed.getBuffer(FluffyFurRenderTypes.ADDITIVE);
        Color color = WizardsRebornArcaneEnchantments.THROW.getColor();

        List<Vec3> trailList = new ArrayList<>(entity.trail);
        if (trailList.size() > 1 && entity.tickCount >= 30) {
            Vec3 position = trailList.get(0);
            Vec3 nextPosition = trailList.get(1);
            float x = (float) Mth.lerp(partialTicks, position.x, nextPosition.x);
            float y = (float) Mth.lerp(partialTicks, position.y, nextPosition.y);
            float z = (float) Mth.lerp(partialTicks, position.z, nextPosition.z);
            trailList.set(0, new Vec3(x, y, z));
        }

        List<TrailPoint> trail = new ArrayList<>();
        for (Vec3 point : trailList) {
            trail.add(new TrailPoint(point.subtract(entity.position())));
        }

        float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
        float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
        float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());

        poseStack.pushPose();
        poseStack.translate(0, 0.1f, 0);
        poseStack.translate(entity.getX() - x, entity.getY() - y,  entity.getZ() - z);
        poseStack.translate(0, 0.1f, 0);
        //RenderUtils.renderTrail(poseStack, builder, entity.position(), trailList, 0,0.02f, 0,1.0f, 1.0f, color, 4, true);
        poseStack.translate(0, -0.1f, 0);

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

            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;
            float alpha = entity.getFadeTick() / 30f;

            poseStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-yaw)));
            poseStack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch) - 90f));
            WizardsRebornRenderUtil.scytheTrail(poseStack, bufferDelayed, 2f, distance, 0.5f, r, g, b, 0.5f * alpha, r, g, b, 0.5f * alpha);
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
    public boolean shouldRender(T livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return null;
    }
}