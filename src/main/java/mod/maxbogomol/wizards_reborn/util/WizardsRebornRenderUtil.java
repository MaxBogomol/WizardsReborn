package mod.maxbogomol.wizards_reborn.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.awt.*;

public class WizardsRebornRenderUtil {

    public static Color colorConnectFrom = new Color(165, 223, 108);
    public static Color colorConnectTo = new Color(118, 184, 214);
    public static Color colorArea = new Color(191, 201, 104);
    public static Color colorMissing = new Color(214, 118, 132);
    public static Color colorSelected = new Color(255, 255, 255);
    public static Color colorFluidSide = new Color(59, 104, 153);
    public static Color colorSteamSide = new Color(141, 156, 179);
    public static Color colorEnergySide = new Color(24, 147, 25);

    public static void ray(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r, float g, float b, float a) {
        ray(mStack, buf, width, height, endOffset, r, g, b, a, r, g, b, a);
    }

    public static void raySided(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        ray(mStack, buf, width, height, endOffset, r1, g1, b1, a1, r2, g2, b2, a2);
        mStack.pushPose();
        mStack.scale(-1, -1, -1);
        mStack.mulPose(Axis.ZP.rotationDegrees(180f));
        ray(mStack, buf, width, height, endOffset, r1, g1, b1, a1, r2, g2, b2, a2);
        mStack.popPose();
    }

    public static void ray(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        VertexConsumer builder = buf.getBuffer(FluffyFurRenderTypes.ADDITIVE);

        Matrix4f mat = mStack.last().pose();

        builder.vertex(mat, -width, width, -width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, -width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, -width, -width, -width).color(r1, g1, b1, a1).endVertex();

        builder.vertex(mat, height, width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, -width, width, width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, -width, -width, width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, -width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();

        builder.vertex(mat, height, -width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, -width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();

        builder.vertex(mat, -width, -width, -width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, -width, -width, width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, -width, width, width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, -width, width, -width).color(r1, g1, b1, a1).endVertex();

        builder.vertex(mat, -width, width, width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, -width, width, -width).color(r1, g1, b1, a1).endVertex();

        builder.vertex(mat, -width, -width, -width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, -width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, -width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, -width, -width, width).color(r1, g1, b1, a1).endVertex();
    }

    public static void beamSided(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        beam(mStack, buf, width, height, endOffset, r1, g1, b1, a1, r2, g2, b2, a2);
        mStack.pushPose();
        mStack.scale(-1, -1, -1);
        mStack.mulPose(Axis.ZP.rotationDegrees(180f));
        beam(mStack, buf, width, height, endOffset, r1, g1, b1, a1, r2, g2, b2, a2);
        mStack.popPose();
    }

    public static void beam(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        VertexConsumer builder = buf.getBuffer(FluffyFurRenderTypes.ADDITIVE);

        Matrix4f mat = mStack.last().pose();

        builder.vertex(mat, 0, width, -width).color(r1, g1, b1, a1).uv(0, 0).endVertex();
        builder.vertex(mat, height, width * endOffset, -width * endOffset).color(r2, g2, b2, a2).uv(0, 0).endVertex();
        builder.vertex(mat, height, -width * endOffset, -width * endOffset).color(r2, g2, b2, a2).uv(0, 0).endVertex();
        builder.vertex(mat, 0, -width, -width).color(r1, g1, b1, a1).uv(0, 0).endVertex();

        builder.vertex(mat, height, width * endOffset, width * endOffset).color(r2, g2, b2, a2).uv(0, 0).endVertex();
        builder.vertex(mat, 0, width, width).color(r1, g1, b1, a1).uv(0, 0).endVertex();
        builder.vertex(mat, 0, -width, width).color(r1, g1, b1, a1).uv(0, 0).endVertex();
        builder.vertex(mat, height, -width * endOffset, width * endOffset).color(r2, g2, b2, a2).uv(0, 0).endVertex();

        builder.vertex(mat, 0, width, width).color(r1, g1, b1, a1).uv(0, 0).endVertex();
        builder.vertex(mat, height, width * endOffset, width * endOffset).color(r2, g2, b2, a2).uv(0, 0).endVertex();
        builder.vertex(mat, height, width * endOffset, -width * endOffset).color(r2, g2, b2, a2).uv(0, 0).endVertex();
        builder.vertex(mat, 0, width, -width).color(r1, g1, b1, a1).uv(0, 0).endVertex();

        builder.vertex(mat, 0, -width, -width).color(r1, g1, b1, a1).uv(0, 0).endVertex();
        builder.vertex(mat, height, -width * endOffset, -width * endOffset).color(r2, g2, b2, a2).uv(0, 0).endVertex();
        builder.vertex(mat, height, -width * endOffset, width * endOffset).color(r2, g2, b2, a2).uv(0, 0).endVertex();
        builder.vertex(mat, 0, -width, width).color(r1, g1, b1, a1).uv(0, 0).endVertex();
    }

    public static void renderConnectLine(BlockPos posFrom, BlockPos posTo, Color color, float partialTicks, PoseStack ms) {
        renderConnectLine(posFrom.getCenter(), posTo.getCenter(), color, partialTicks, ms);
    }

    public static void renderConnectLine(Vec3 from, Vec3 to, Color color, float partialTicks, PoseStack ms) {
        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();

        double dX = to.x() - from.x();
        double dY = to.y() - from.y();
        double dZ = to.z() - from.z();

        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

        ms.pushPose();
        ms.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-yaw)));
        ms.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch) - 90f));
        WizardsRebornRenderUtil.ray(ms, bufferDelayed, 0.01f, (float) from.distanceTo(to) + 0.01f, 1f, r, g, b, 0.5f * a);
        ms.popPose();
    }

    public static void renderConnectLineOffset(Vec3 from, Vec3 to, Color color, float partialTicks, PoseStack ms) {
        ms.pushPose();
        ms.translate(from.x(), from.y(), from.z());
        renderConnectLine(from, to, color, partialTicks, ms);
        ms.popPose();
    }

    public static void renderBoxLines(Vec3 size, Color color, float partialTicks, PoseStack ms) {
        renderConnectLineOffset(new Vec3(0, 0, 0), new Vec3(size.x() , 0, 0), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), 0, 0), new Vec3(size.x(), 0, size.z()), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), 0, size.z()), new Vec3(0, 0, size.z()), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(0, 0, size.z()), new Vec3(0, 0, 0), color, partialTicks, ms);

        renderConnectLineOffset(new Vec3(0, 0, 0), new Vec3(0, size.y(), 0), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), 0, 0), new Vec3(size.x(), size.y(), 0), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), 0, size.z()), new Vec3(size.x(), size.y(), size.z()), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(0, 0, size.z()), new Vec3(0, size.y(), size.z()), color, partialTicks, ms);

        renderConnectLineOffset(new Vec3(0, size.y(), 0), new Vec3(size.x(), size.y(), 0), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), size.y(), 0), new Vec3(size.x() , size.y(), size.z()), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), size.y(), size.z()), new Vec3(0, size.y(), size.z()), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(0, size.y(), size.z()), new Vec3(0, size.y(), 0), color, partialTicks, ms);
    }

    public static void renderSideLines(Vec3 size, Color color, float partialTicks, PoseStack ms) {
        renderConnectLineOffset(new Vec3(0, 0, 0), new Vec3(size.x() , 0, 0), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), 0, 0), new Vec3(size.x(), 0, size.z()), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), 0, size.z()), new Vec3(0, 0, size.z()), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(0, 0, size.z()), new Vec3(0, 0, 0), color, partialTicks, ms);
    }

    public static void renderSide(Direction side, Color color, float partialTicks, PoseStack ms) {
        Vec3 size = new Vec3(1, 1, 1);
        ms.pushPose();
        ms.translate(0.5f, 0.5f, 0.5f);
        ms.mulPose(side.getOpposite().getRotation());
        ms.translate(-size.x() / 2f, -size.y() / 2f, -size.z() / 2f);
        renderSideLines(size, color, partialTicks, ms);
        ms.popPose();
    }

    public static void renderAura(PoseStack mStack, VertexConsumer builder, float radius, float size, int longs, Color color1, Color color2, float alpha1, float alpha2, boolean renderSide, boolean renderFloor) {
        float r1 = color1.getRed() / 255f;
        float g1 = color1.getGreen() / 255f;
        float b1 = color1.getBlue() / 255f;

        float r2 = color2.getRed() / 255f;
        float g2 = color2.getGreen() / 255f;
        float b2 = color2.getBlue() / 255f;

        float startU = 0;
        float endU = Mth.PI * 2;
        float stepU = (endU - startU) / longs;
        for (int i = 0; i < longs; ++i) {
            float u = i * stepU + startU;
            float un = (i + 1 == longs) ? endU : (i + 1) * stepU + startU;

            auraPiece(mStack, builder, radius, size, u, r2, g2, b2, alpha2);
            auraPiece(mStack, builder, radius, 0, u, r1, g2, b1, alpha1);
            auraPiece(mStack, builder, radius, 0, un, r1, g2, b1, alpha1);
            auraPiece(mStack, builder, radius, size, un, r2, g2, b2, alpha2);

            if (renderSide) {
                auraPiece(mStack, builder, radius, 0, u, r1, g2, b1, alpha1);
                auraPiece(mStack, builder, radius, size, u, r2, g2, b2, alpha2);
                auraPiece(mStack, builder, radius, size, un, r2, g2, b2, alpha2);
                auraPiece(mStack, builder, radius, 0, un, r1, g2, b1, alpha1);
            }

            if (renderFloor) {
                auraPiece(mStack, builder, 0, 0, u,r2, g2, b2, alpha2);
                auraPiece(mStack, builder, 0, 0, un, r2, g2, b2, alpha2);
                auraPiece(mStack, builder, radius, 0, u, r1, g1, b1, alpha1);
                auraPiece(mStack, builder, radius, 0, un, r1, g1, b1, alpha1);

                if (renderSide) {
                    auraPiece(mStack, builder, 0, 0, un, r2, g2, b2, alpha2);
                    auraPiece(mStack, builder, 0, 0, u,r2, g2, b2, alpha2);
                    auraPiece(mStack, builder, radius, 0, un, r1, g1, b1, alpha1);
                    auraPiece(mStack, builder, radius, 0, u, r1, g1, b1, alpha1);
                }
            }
        }
    }

    public static void auraPiece(PoseStack mStack, VertexConsumer builder, float radius, float size, float angle, float r, float g, float b, float alpha) {
        mStack.pushPose();
        mStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(angle)));
        mStack.translate(radius, 0, 0);
        Matrix4f mat = mStack.last().pose();
        builder.vertex(mat, 0, size, 0).color(r, g, b, alpha).endVertex();
        mStack.popPose();
    }

    public static void scytheTrail(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        VertexConsumer builder = buf.getBuffer(FluffyFurRenderTypes.ADDITIVE);

        Matrix4f mat = mStack.last().pose();

        builder.vertex(mat, 0, 0, width * endOffset).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, 0, width).color(r2, g2, b2, 0).endVertex();
        builder.vertex(mat, height, 0, 0).color(r2, g2, b2, a2 / 10f).endVertex();
        builder.vertex(mat, 0, 0, 0).color(r1, g1, b1, a1).endVertex();

        builder.vertex(mat, 0, 0, 0).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, 0, 0).color(r2, g2, b2, a2 / 10f).endVertex();
        builder.vertex(mat, height, 0, -width).color(r2, g2, b2, 0).endVertex();
        builder.vertex(mat, 0, 0, -width * endOffset).color(r1, g1, b1, a1).endVertex();

        builder.vertex(mat, 0, 0, 0).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, 0, 0).color(r2, g2, b2, a2 / 10f).endVertex();
        builder.vertex(mat, height, 0, width).color(r2, g2, b2, 0).endVertex();
        builder.vertex(mat, 0, 0, width  * endOffset).color(r1, g1, b1, a1).endVertex();

        builder.vertex(mat, 0, 0, -width * endOffset).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, 0, -width).color(r2, g2, b2, 0).endVertex();
        builder.vertex(mat, height, 0, 0).color(r2, g2, b2, a2 / 10f).endVertex();
        builder.vertex(mat, 0, 0, 0).color(r1, g1, b1, a1).endVertex();
    }

    public static void renderHoveringLensModel(PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        RenderUtil.renderCustomModel(WizardsRebornModels.HOVERING_LENS, ItemDisplayContext.FIXED, false, poseStack, bufferSource, light, overlay);
    }

    public static void renderHoveringLensGlow(PoseStack poseStack) {
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                .setColorRaw(0.564f, 0.682f, 0.705f).setAlpha(0.6f)
                .renderCenteredCube(poseStack, 0.075f);
    }
}
