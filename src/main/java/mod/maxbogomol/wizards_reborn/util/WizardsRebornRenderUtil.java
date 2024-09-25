package mod.maxbogomol.wizards_reborn.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
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

    public static void renderHoveringLensModel(PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        RenderUtil.renderCustomModel(WizardsRebornModels.HOVERING_LENS, ItemDisplayContext.FIXED, false, poseStack, bufferSource, light, overlay);
    }

    public static RenderBuilder renderHoveringLensGlow(PoseStack poseStack) {
        return RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                .setColorRaw(0.564f, 0.682f, 0.705f).setAlpha(0.6f)
                .renderCenteredCube(poseStack, 0.075f);
    }

    public static RenderBuilder renderHoveringLens(PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        renderHoveringLensModel(poseStack, bufferSource, light, overlay);
        return renderHoveringLensGlow(poseStack);
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
}
