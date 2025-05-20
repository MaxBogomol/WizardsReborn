package mod.maxbogomol.wizards_reborn.client.render.entity;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPoint;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.entity.ThrownShearsEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ThrownShearsRenderer<T extends ThrownShearsEntity> extends EntityRenderer<T> {
    public static Color STRING_COLOR = new Color(11, 8, 22);
    public static List<TrailPoint> trailPoints = new ArrayList<>();

    public ThrownShearsRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ThrownShearsEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        Color color = WizardsRebornArcaneEnchantments.SILK_SONG.getColor();

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
                .renderTrail(poseStack, trail, (f) -> RenderUtil.LINEAR_IN_ROUND_WIDTH_FUNCTION.apply(f) * 0.15f);
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
            WizardsRebornRenderUtil.renderScytheTrail(renderBuilder, poseStack, 0.25f, distance);
            poseStack.popPose();
        }

        if (!entity.getFade()) {
            if (!entity.getIsCut()) {
                poseStack.pushPose();
                poseStack.translate(0f, 0.1f, 0f);
                poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) - 45f));
                Minecraft.getInstance().getItemRenderer().renderStatic(entity.getItem(), ItemDisplayContext.NONE, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), 0);
                poseStack.popPose();
            }

            Player player = entity.getSender();
            if (player != null) {
                if (entity.getIsCut()) {
                    float tick = (entity.tickCount + partialTicks);
                    int right = entity.getIsRight() ? 1 : -1;

                    poseStack.pushPose();
                    poseStack.translate(0f, player.getBbHeight() / 1.5f, 0f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f * right));
                    poseStack.mulPose(Axis.ZP.rotation(tick * 2f));
                    poseStack.translate(2f, 0, 0f);
                    poseStack.mulPose(Axis.ZP.rotationDegrees(-45f));
                    Minecraft.getInstance().getItemRenderer().renderStatic(entity.getItem(), ItemDisplayContext.NONE, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), 0);
                    poseStack.popPose();
                }

                renderString(entity, partialTicks, poseStack, bufferSource, entity.getSender());
            }
        }
    }

    public static void renderString(ThrownShearsEntity entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, Player player) {
        poseStack.pushPose();
        int i = entity.getIsRight() ? 1 : -1;
        float f = player.getAttackAnim(partialTicks);
        float f1 = Mth.sin(Mth.sqrt(f) * (float)Math.PI);
        float f2 = Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot) * ((float)Math.PI / 180F);
        double d0 = (double)Mth.sin(f2);
        double d1 = (double)Mth.cos(f2);
        double d2 = (double)i * 0.35D;
        double d4;
        double d5;
        double d6;
        float f3;

        if ((Minecraft.getInstance().getEntityRenderDispatcher().options == null || Minecraft.getInstance().getEntityRenderDispatcher().options.getCameraType().isFirstPerson()) && player == Minecraft.getInstance().player) {
            double d7 = 960.0D / (double) Minecraft.getInstance().getEntityRenderDispatcher().options.fov().get().intValue();
            Vec3 vec3 = Minecraft.getInstance().getEntityRenderDispatcher().camera.getNearPlane().getPointOnPlane((float)i * 0.525F, -0.5F);
            vec3 = vec3.scale(d7);
            vec3 = vec3.yRot(f1 * 0.5F);
            vec3 = vec3.xRot(-f1 * 0.7F);
            d4 = Mth.lerp(partialTicks, player.xo, player.getX()) + vec3.x;
            d5 = Mth.lerp(partialTicks, player.yo, player.getY()) + vec3.y;
            d6 = Mth.lerp(partialTicks, player.zo, player.getZ()) + vec3.z;
            f3 = player.getEyeHeight();
        } else {
            d4 = Mth.lerp(partialTicks, player.xo, player.getX()) - d1 * d2 - d0 * 0.2D;
            d5 = player.yo + (double) player.getEyeHeight() + (player.getY() - player.yo) * partialTicks - 0.65D;
            d6 = Mth.lerp(partialTicks, player.zo, player.getZ()) - d0 * d2 + d1 * 0.2D;
            f3 = player.isCrouching() ? -0.1875F : 0.0F;
        }

        Vec3 pos = new Vec3(d4,d5 + f3, d6);
        Vec3 posString = entity.getPosition(partialTicks).add(0, 0.1f, 0);

        float sf = (float) (pos.x() - posString.x());
        float sf1 = (float) (pos.y() - posString.y());
        float sf2 = (float) (pos.z() - posString.z());

        if (entity.getIsCut()) {
            float tick = (entity.tickCount + partialTicks);
            int right = entity.getIsRight() ? 1 : -1;

            float yaw = -(tick * right * 2f);

            float x = (float) Math.cos(yaw) * 2;
            float z = (float) Math.sin(yaw) * 2;

            sf = sf - x;
            sf1 = sf1 - (player.getBbHeight() / 1.5f);
            sf2 = sf2 - z;

            poseStack.translate(x, player.getBbHeight() / 1.5f, z);
        }

        for (int i1 = 0; i1 <= 24; ++i1) {
            addTrailPoint(sf, sf1, sf2, i1);
        }

        RenderBuilder.create().replaceBufferSource(buffer)
                .setUV(RenderUtil.getSprite(new ResourceLocation(FluffyFur.MOD_ID, "particle/square")))
                .setColor(STRING_COLOR)
                .setRenderType(RenderType.cutout())
                .setFormat(DefaultVertexFormat.BLOCK)
                .renderTrail(poseStack, trailPoints, (f4) -> RenderUtil.FULL_WIDTH_FUNCTION.apply(f4) * 0.02f);
        trailPoints.clear();
        poseStack.popPose();
    }

    public static void addTrailPoint(float x, float y, float z, int i) {
        float f = (float) i / 24.0F;
        float f5 = x * f;
        float f6 = y > 0.0F ? y * f * f : y - y * (1.0F - f) * (1.0F - f);
        float f7 = z * f;
        trailPoints.add(new TrailPoint(new Vec3(f5, f6, f7)));
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