package mod.maxbogomol.wizards_reborn.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.common.entity.ThrowedScytheEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
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
    public void render(ThrowedScytheEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
        MultiBufferSource bufferDelayed = WorldRenderHandler.getDelayedRender();
        VertexConsumer builder = bufferDelayed.getBuffer(RenderUtils.GLOWING);
        Color color = WizardsReborn.THROW_ARCANE_ENCHANTMENT.getColor();

        List<Vec3> trailList = new ArrayList<>(entity.trail);
        if (trailList.size() > 1 && entity.tickCount >= 30) {
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

        stack.pushPose();
        stack.translate(0, 0.1f, 0);
        stack.translate(entity.getX() - x, entity.getY() - y,  entity.getZ() - z);
        RenderUtils.renderTrail(stack, builder, entity.position(), trailList, 0,0.02f, 0,1.0f, 1.0f, color, 4, true);
        stack.popPose();

        if (entity.getFade() && entity.getFadeTick() <= 30 && entity.getEndTick() > 0) {
            stack.pushPose();
            stack.translate(0, 0.1f, 0);
            double dX = entity.endPoint.x() - entity.getX();
            double dY = entity.endPoint.y() - entity.getY();
            double dZ = entity.endPoint.z() - entity.getZ();

            float distance = (float) Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2) + Math.pow(dZ, 2));

            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;
            float alpha = entity.getFadeTick() / 30f;

            stack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-yaw)));
            stack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch) - 90f));
            RenderUtils.scytheTrail(stack, bufferDelayed, 2f, distance, 0.5f, r, g, b, 0.5f * alpha, r, g, b, 0.5f * alpha);
            stack.popPose();
        }

        if (!entity.getFade()) {
            float tick = (entity.tickCount + partialTicks);

            stack.pushPose();
            stack.translate(0f, 0.1f, 0f);
            stack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
            stack.mulPose(Axis.XP.rotationDegrees(-90f));
            stack.mulPose(Axis.ZP.rotation(tick * 0.8f));
            stack.scale(2, 2f, 1f);
            stack.translate(0.25f, 0.25f, 0f);
            Minecraft.getInstance().getItemRenderer().renderStatic(entity.getItem(), ItemDisplayContext.NONE, light, OverlayTexture.NO_OVERLAY, stack, buffer, entity.level(), 0);
            stack.popPose();
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