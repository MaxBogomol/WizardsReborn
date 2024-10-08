package mod.maxbogomol.wizards_reborn.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Matrix4f;
import org.joml.Vector3f;

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

    public static void renderHoveringLensModel(PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        RenderUtil.renderCustomModel(WizardsRebornModels.HOVERING_LENS, ItemDisplayContext.FIXED, false, poseStack, bufferSource, light, overlay);
    }

    public static RenderBuilder renderHoveringLensGlow(PoseStack poseStack, Color color) {
        return RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                .setColor(color).setAlpha(0.6f)
                .renderCenteredCube(poseStack, 0.075f);
    }

    public static RenderBuilder renderHoveringLens(PoseStack poseStack, MultiBufferSource bufferSource, Color color, int light, int overlay) {
        renderHoveringLensModel(poseStack, bufferSource, light, overlay);
        return renderHoveringLensGlow(poseStack, color);
    }

    public static RenderBuilder renderHoveringLensGlow(PoseStack poseStack) {
        return renderHoveringLensGlow(poseStack, LightUtil.standardLensColor);
    }

    public static RenderBuilder renderHoveringLens(PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        return renderHoveringLens(poseStack, bufferSource, LightUtil.standardLensColor, light, overlay);
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

    public static void scytheTrail(RenderBuilder builder, PoseStack poseStack, float width, float height, float endOffset) {
        Matrix4f last = poseStack.last().pose();
        Vector3f[] positions = new Vector3f[]{
                new Vector3f(-1, 1, 0), new Vector3f(0, 1, 0), new Vector3f(0, 0, 0), new Vector3f(-1, 0, 0),
                new Vector3f(0, 1, 0), new Vector3f(1, 1, 0), new Vector3f(1, 0, 0), new Vector3f(0, 0, 0)};
        for (int i = 0; i < 8; ++i) {
            positions[i].mul(width, height, width);
        }

        RenderBuilder.VertexConsumerActor supplier = builder.getSupplier();
        VertexConsumer vertexConsumer = builder.getVertexConsumer();

        if (builder.firstSide) {
            supplier.placeVertex(vertexConsumer, last, builder, positions[0].x(), positions[0].y(), positions[0].z(), builder.r2, builder.g2, builder.b2, 0, builder.u0, builder.v1, builder.l2);
            supplier.placeVertex(vertexConsumer, last, builder, positions[1].x(), positions[1].y(), positions[1].z(), builder.r2, builder.g2, builder.b2, builder.a2, builder.u1, builder.v1, builder.l1);
            supplier.placeVertex(vertexConsumer, last, builder, positions[2].x(), positions[2].y(), positions[2].z(), builder.r1, builder.g1, builder.b1, builder.a1, builder.u1, builder.v0, builder.l1);
            supplier.placeVertex(vertexConsumer, last, builder, positions[3].x(), positions[3].y(), positions[3].z(), builder.r1, builder.g1, builder.b1, builder.a1, builder.u0, builder.v0, builder.l1);

            supplier.placeVertex(vertexConsumer, last, builder, positions[4].x(), positions[4].y(), positions[4].z(), builder.r2, builder.g2, builder.b2, builder.a2, builder.u0, builder.v1, builder.l2);
            supplier.placeVertex(vertexConsumer, last, builder, positions[5].x(), positions[5].y(), positions[5].z(), builder.r2, builder.g2, builder.b2, 0, builder.u1, builder.v1, builder.l1);
            supplier.placeVertex(vertexConsumer, last, builder, positions[6].x(), positions[6].y(), positions[6].z(), builder.r1, builder.g1, builder.b1, builder.a1, builder.u1, builder.v0, builder.l1);
            supplier.placeVertex(vertexConsumer, last, builder, positions[7].x(), positions[7].y(), positions[7].z(), builder.r1, builder.g1, builder.b1, builder.a1, builder.u0, builder.v0, builder.l1);
        }

        if (builder.secondSide) {
            supplier.placeVertex(vertexConsumer, last, builder, positions[3].x(), positions[3].y(), positions[3].z(), builder.r1, builder.g1, builder.b1, builder.a1, builder.u1, builder.v0, builder.l1);
            supplier.placeVertex(vertexConsumer, last, builder, positions[2].x(), positions[2].y(), positions[2].z(), builder.r1, builder.g1, builder.b1, builder.a1, builder.u0, builder.v0, builder.l1);
            supplier.placeVertex(vertexConsumer, last, builder, positions[1].x(), positions[1].y(), positions[1].z(), builder.r2, builder.g2, builder.b2, builder.a2, builder.u0, builder.v1, builder.l2);
            supplier.placeVertex(vertexConsumer, last, builder, positions[0].x(), positions[0].y(), positions[0].z(), builder.r2, builder.g2, builder.b2, 0, builder.u1, builder.v1, builder.l2);

            supplier.placeVertex(vertexConsumer, last, builder, positions[7].x(), positions[7].y(), positions[7].z(), builder.r1, builder.g1, builder.b1, builder.a1, builder.u1, builder.v0, builder.l1);
            supplier.placeVertex(vertexConsumer, last, builder, positions[6].x(), positions[6].y(), positions[5].z(), builder.r1, builder.g1, builder.b1, builder.a1, builder.u0, builder.v0, builder.l1);
            supplier.placeVertex(vertexConsumer, last, builder, positions[5].x(), positions[5].y(), positions[6].z(), builder.r2, builder.g2, builder.b2, 0, builder.u0, builder.v1, builder.l2);
            supplier.placeVertex(vertexConsumer, last, builder, positions[4].x(), positions[4].y(), positions[4].z(), builder.r2, builder.g2, builder.b2, builder.a2, builder.u1, builder.v1, builder.l2);
        }
    }
}
