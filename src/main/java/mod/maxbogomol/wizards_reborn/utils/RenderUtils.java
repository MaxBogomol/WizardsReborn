package mod.maxbogomol.wizards_reborn.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.client.render.item.CustomItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class RenderUtils {

    public static float blitOffset = 0;

    public static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("lightning_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static final RenderStateShard.TransparencyStateShard NORMAL_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("lightning_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static final RenderStateShard.TransparencyStateShard TRANSLUCENT_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static final RenderType GLOWING_SPRITE = RenderType.create(
            WizardsReborn.MOD_ID + ":glowing_sprite",
            DefaultVertexFormat.POSITION_TEX_COLOR,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_BLOCKS, false, false))
                    .setShaderState(new RenderStateShard.ShaderStateShard(WizardsRebornClient::getGlowingSpriteShader))
                    .createCompositeState(false)
    );

    public static final RenderType GLOWING = RenderType.create(
            WizardsReborn.MOD_ID + ":glowing",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setShaderState(new RenderStateShard.ShaderStateShard(WizardsRebornClient::getGlowingShader))
                    .createCompositeState(false)
    );

    public static RenderType GLOWING_PARTICLE = RenderType.create(
            WizardsReborn.MOD_ID + ":glowing_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
                    RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_PARTICLES, false, false))
                    .setShaderState(new RenderStateShard.ShaderStateShard(WizardsRebornClient::getGlowingParticleShader))
                    .createCompositeState(false));

    public static RenderType DELAYED_PARTICLE = RenderType.create(
            WizardsReborn.MOD_ID + ":delayed_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setTransparencyState(NORMAL_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_PARTICLES, false, false))
                    .setShaderState(new RenderStateShard.ShaderStateShard(WizardsRebornClient::getSpriteParticleShader))
                    .createCompositeState(false)
    );

    public static final RenderType FLUID = RenderType.create(
            WizardsReborn.MOD_ID + ":fluid",
            DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
            VertexFormat.Mode.QUADS, 256, false, true,
            RenderType.CompositeState.builder()
                    .setLightmapState(new RenderStateShard.LightmapStateShard(true))
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_BLOCKS, false, true))
                    .setShaderState(new RenderStateShard.ShaderStateShard(WizardsRebornClient::getFluidShader))
                    .setCullState(new RenderStateShard.CullStateShard(true))
                    .createCompositeState(true)
    );

    public static CustomItemRenderer customItemRenderer;

    public static void renderItemModelInGui(ItemStack stack, float x, float y, float xSize, float ySize, float zSize) {
        renderItemModelInGui(stack, x, y, xSize, ySize, zSize, 0, 0, 0);
    }

    public static void renderItemModelInGui(ItemStack stack, float x, float y, float xSize, float ySize, float zSize, float xRot, float yRot, float zRot) {
        Minecraft minecraft = Minecraft.getInstance();
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack, minecraft.level, minecraft.player, 0);
        if (customItemRenderer == null) {
            customItemRenderer = new CustomItemRenderer(minecraft, minecraft.getTextureManager(), minecraft.getModelManager(), minecraft.getItemColors(), minecraft.getItemRenderer().getBlockEntityRenderer());
        }

        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate(x, y, (100.0F));
        posestack.translate((double) xSize / 2, (double) ySize / 2, 0.0D);
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale(xSize, ySize, zSize);
        posestack.mulPose(Axis.XP.rotationDegrees(xRot));
        posestack.mulPose(Axis.YP.rotationDegrees(yRot));
        posestack.mulPose(Axis.ZP.rotationDegrees(zRot));
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !bakedmodel.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        customItemRenderer.render(stack, ItemDisplayContext.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);

        RenderSystem.disableDepthTest();
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();

        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    public static void renderBoxBlockOutline(PoseStack matrixStack, MultiBufferSource bufferIn, VoxelShape voxelShape, double originX, double originY, double originZ, Color color) {
        VertexConsumer builder = bufferIn.getBuffer(RenderType.lines());
        Matrix4f matrix4f = matrixStack.last().pose();

        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int alpha = color.getAlpha();

        voxelShape.forAllEdges((x0, y0, z0, x1, y1, z1) -> {
            builder.vertex(matrix4f, (float)(x0 + originX), (float)(y0 + originY), (float)(z0 + originZ)).color(red, green, blue, alpha).endVertex();
            builder.vertex(matrix4f, (float)(x1 + originX), (float)(y1 + originY), (float)(z1 + originZ)).color(red, green, blue, alpha).endVertex();
        });
    }

    public static void renderLine(PoseStack matrixStack, MultiBufferSource bufferIn, double x1, double y1, double z1, double x2, double y2, double z2, Color color) {
        VertexConsumer builder = bufferIn.getBuffer(RenderType.lines());
        Matrix4f matrix4f = matrixStack.last().pose();

        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int alpha = color.getAlpha();

        builder.vertex(matrix4f, (float) (x1), (float) (y1), (float) (z1)).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix4f, (float) (x2), (float) (y2), (float) (z2)).color(red, green, blue, alpha).endVertex();
    }

    public static void renderFloatingItemModelIntoGUI(GuiGraphics gui, ItemStack stack, int x, int y, float ticks, float ticksUp) {
        Minecraft minecraft = Minecraft.getInstance();
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack, minecraft.level, minecraft.player, 0);
        if (customItemRenderer == null) {
            customItemRenderer = new CustomItemRenderer(minecraft, minecraft.getTextureManager(), minecraft.getModelManager(), minecraft.getItemColors(), minecraft.getItemRenderer().getBlockEntityRenderer());
        }

        blitOffset += 50.0F;
        float old = bakedmodel.getTransforms().gui.rotation.y;

        PoseStack posestack = gui.pose();

        posestack.pushPose();
        posestack.translate((float)(x + 8), (float)(y + 8), 100 + blitOffset);
        posestack.mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
        posestack.scale(16.0F, 16.0F, 16.0F);
        posestack.translate(0.0D, Math.sin(Math.toRadians(ticksUp)) * 0.03125F, 0.0D);
        if (bakedmodel.usesBlockLight()) {
            bakedmodel.getTransforms().gui.rotation.y = ticks;
        } else {
            posestack.mulPose(Axis.YP.rotationDegrees(ticks));
        }
        boolean flag = !bakedmodel.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        customItemRenderer.renderItem(stack, ItemDisplayContext.GUI, false, posestack, Minecraft.getInstance().renderBuffers().bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);

        RenderSystem.disableDepthTest();
        Minecraft.getInstance().renderBuffers().bufferSource().endBatch();
        RenderSystem.enableDepthTest();

        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();

        bakedmodel.getTransforms().gui.rotation.y = old;

        blitOffset -= 50.0F;
    }

    private static final float ROOT_3 = (float)(Math.sqrt(3.0D) / 2.0D);

    public static void dragon(PoseStack mStack, MultiBufferSource buf, double x, double y, double z, float radius, float partialTicks, float r, float g, float b, float randomF) {
        float f5 = 0.5f;
        float f7 = Math.min(f5 > 0.8F ? (f5 - 0.8F) / 0.2F : 0.0F, 1.0F);
        Random random = new Random((long) (432L + randomF));
        VertexConsumer builder = buf.getBuffer(GLOWING);
        mStack.pushPose();
        mStack.translate(x, y, z);

        float rotation = (ClientTickHandler.ticksInGame + partialTicks) / 200;

        for(int i = 0; (float)i < (f5 + f5 * f5) / 2.0F * 60.0F; ++i) {
            mStack.mulPose(Axis.XP.rotationDegrees(random.nextFloat() * 360.0F));
            mStack.mulPose(Axis.YP.rotationDegrees(random.nextFloat() * 360.0F));
            mStack.mulPose(Axis.ZP.rotationDegrees(random.nextFloat() * 360.0F));
            mStack.mulPose(Axis.XP.rotationDegrees(random.nextFloat() * 360.0F));
            mStack.mulPose(Axis.YP.rotationDegrees(random.nextFloat() * 360.0F));
            mStack.mulPose(Axis.ZP.rotationDegrees(random.nextFloat() * 360.0F + rotation * 90.0F));
            float f3 = random.nextFloat() * 20.0F + 5.0F + f7 * 10.0F;
            float f4 = random.nextFloat() * 2.0F + 1.0F + f7 * 2.0F;
            f3 *= 0.05f * radius;
            f4 *= 0.05f * radius;
            Matrix4f mat = mStack.last().pose();
            float alpha = 1 - f7;

            builder.vertex(mat, 0.0F, 0.0F, 0.0F).color(r, g, b, alpha).endVertex();
            builder.vertex(mat, 0.0F, 0.0F, 0.0F).color(r, g, b, alpha).endVertex();
            builder.vertex(mat, -ROOT_3 * f4, f3, -0.5F * f4).color(r, g, b, 0).endVertex();
            builder.vertex(mat, ROOT_3 * f4, f3, -0.5F * f4).color(r, g, b, 0).endVertex();
            builder.vertex(mat, 0.0F, 0.0F, 0.0F).color(r, g, b, alpha).endVertex();
            builder.vertex(mat, 0.0F, 0.0F, 0.0F).color(r, g, b, alpha).endVertex();
            builder.vertex(mat, ROOT_3 * f4, f3, -0.5F * f4).color(r, g, b, 0).endVertex();
            builder.vertex(mat, 0.0F, f3, f4).color(r, g, b, 0).endVertex();
            builder.vertex(mat, 0.0F, 0.0F, 0.0F).color(r, g, b, alpha).endVertex();
            builder.vertex(mat, 0.0F, 0.0F, 0.0F).color(r, g, b, alpha).endVertex();
            builder.vertex(mat, 0.0F, f3, f4).color(r, g, b, 0).endVertex();
            builder.vertex(mat, -ROOT_3 * f4, f3, -0.5F * f4).color(r, g, b, 0).endVertex();
        }

        mStack.popPose();
    }

    public static void ray(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r, float g, float b, float a) {
        ray(mStack, buf, width, height, endOffset, r, g, b, a, r, g, b, a);
    }

    public static void raySided(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r, float g, float b, float a) {
        raySided(mStack, buf, width, height, endOffset, r, g, b, a, r, g, b, a);
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
        VertexConsumer builder = buf.getBuffer(GLOWING);

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

    public static void litQuad(PoseStack mStack, MultiBufferSource buf, float x, float y, float width, float height, float r, float g, float b, float a) {
        VertexConsumer builder = buf.getBuffer(GLOWING);

        Matrix4f mat = mStack.last().pose();
        builder.vertex(mat, x, y + height, 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x + width, y + height, 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x + width, y, 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x, y, 0).color(r, g, b, a).endVertex();
    }

    public static void litQuadCube(PoseStack mStack, MultiBufferSource buf, float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float b, float a) {
        VertexConsumer builder = buf.getBuffer(GLOWING);

        Matrix4f mat = mStack.last().pose();

        builder.vertex(mat, x1, y1 + y2, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1 + y2, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1, z1).color(r, g, b, a).endVertex();

        builder.vertex(mat, x1 + x2, y1 + y2, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1 + y2, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1, z1 + z2).color(r, g, b, a).endVertex();

        builder.vertex(mat, x1 + x2, y1, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1 + y2, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1 + y2, z1 + z2).color(r, g, b, a).endVertex();

        builder.vertex(mat, x1, y1, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1 + y2, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1 + y2, z1).color(r, g, b, a).endVertex();

        builder.vertex(mat, x1, y1 + y2, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1 + y2, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1 + y2, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1 + y2, z1).color(r, g, b, a).endVertex();

        builder.vertex(mat, x1, y1, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1, z1 + z2).color(r, g, b, a).endVertex();
    }

    public static void spriteGlowQuad(PoseStack mStack, MultiBufferSource buf, float x, float y, float width, float height, float u0, float u1, float v0, float v1, float r, float g, float b, float a) {
        VertexConsumer builder = buf.getBuffer(GLOWING_SPRITE);

        Matrix4f mat = mStack.last().pose();
        builder.vertex(mat, x, y + height, 0).uv(u0, v1).uv2(0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x + width, y + height, 0).uv(u1, v1).uv2(0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x + width, y, 0).uv(u1, v0).uv2(0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x, y, 0).color(r, g, b, a).uv(u0, v0).uv2(0).endVertex();
    }

    public static void spriteGlowQuadCenter(PoseStack mStack, MultiBufferSource buf, float x, float y, float width, float height, float u0, float u1, float v0, float v1, float r, float g, float b, float a) {
        VertexConsumer builder = buf.getBuffer(GLOWING_SPRITE);

        Matrix4f mat = mStack.last().pose();
        builder.vertex(mat, x - (width / 2), y + (height / 2), 0).uv(u0, v1).uv2(0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x + (width / 2), y + (height / 2), 0).uv(u1, v1).uv2(0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x + (width / 2), y - (height / 2), 0).uv(u1, v0).uv2(0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x - (width / 2), y - (height / 2), 0).uv(u0, v0).uv2(0).color(r, g, b, a).endVertex();
    }

    public static void renderCustomModel(ModelResourceLocation model, ItemDisplayContext diplayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(model);
        Minecraft.getInstance().getItemRenderer().render(new ItemStack(Items.DIRT), diplayContext, leftHand, poseStack, buffer, combinedLight, combinedOverlay, bakedmodel);
    }

    public static Color colorConnectFrom = new Color(165, 223, 108);
    public static Color colorConnectTo = new Color(118, 184, 214);
    public static Color colorArea = new Color(191, 201, 104);
    public static Color colorMissing = new Color(214, 118, 132);
    public static Color colorSelected = new Color(255, 255, 255);
    public static Color colorFluidSide = new Color(59, 104, 153);
    public static Color colorSteamSide = new Color(141, 156, 179);
    public static Color colorEnergySide = new Color(24, 147, 25);

    public static void renderConnectLine(BlockPos posFrom, BlockPos posTo, Color color, float partialTicks, PoseStack ms) {
        renderConnectLine(posFrom.getCenter(), posTo.getCenter(), color, partialTicks, ms);
    }

    public static void renderConnectLine(Vec3 from, Vec3 to, Color color, float partialTicks, PoseStack ms) {
        MultiBufferSource bufferDelayed = WorldRenderHandler.getDelayedRender();

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
        RenderUtils.ray(ms, bufferDelayed, 0.01f, (float) from.distanceTo(to) + 0.01f, 1f, r, g, b, 0.5f * a);
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

    public static void renderTrail(PoseStack mStack, VertexConsumer builder, Vec3 center, List<Vec3> trailList, float startWidth, float endWidth, float startAlpha, float endAlpha, float scale, Color color, int segments, boolean renderSphere) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        float endU = Mth.PI * 2;
        float stepU = (endU) / segments;

        float size = trailList.size();

        for (int ii = 0; ii < trailList.size() - 1; ii++) {
            mStack.pushPose();
            Vec3 pos = trailList.get(ii).vectorTo(center);
            mStack.translate(-pos.x, -pos.y, -pos.z);

            Vec3 position = trailList.get(ii);
            Vec3 nextPosition = trailList.get(ii + 1);

            double dX = position.x() - nextPosition.x();
            double dY = position.y() - nextPosition.y();
            double dZ = position.z() - nextPosition.z();

            float oX = 0;
            float oY = 0;
            float oZ = 0;

            float distance = (float) Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2) + Math.pow(dZ, 2));

            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Mth.PI;

            double lYaw = yaw;
            double lPitch = pitch;

            if (ii > 0) {
                Vec3 lastPosition = trailList.get(ii - 1);

                double ldX = lastPosition.x() - position.x();
                double ldY = lastPosition.y() - position.y();
                double ldZ = lastPosition.z() - position.z();

                lYaw = Math.atan2(ldZ, ldX);
                lPitch = Math.atan2(Math.sqrt(ldZ * ldZ + ldX * ldX), ldY) + Mth.PI;
            }

            float width1 = Mth.lerp(ii / size, startWidth, endWidth) * scale;
            float width2 = Mth.lerp((ii + 1) / size, startWidth, endWidth) * scale;
            float alpha1 = Mth.lerp(ii / size, startAlpha, endAlpha) * scale;
            float alpha2 = Mth.lerp((ii + 1) / size, startAlpha, endAlpha) * scale;

            if (distance <= 0) {
                width1 = 0;
                width2 = 0;
            }

            if (ii == 0) {
                oX = (float) (Math.sin(pitch) * Math.cos(yaw) * (1f - scale)) * distance;
                oY = (float) (Math.cos(pitch) * (1f - scale)) * distance;
                oZ = (float) (Math.sin(pitch) * Math.sin(yaw) * (1f - scale)) * distance;
            }

            if (ii == trailList.size() - 2) {
                if (!renderSphere) width2 = 0;

                oX = (float) -(Math.sin(pitch) * Math.cos(yaw) * (1f - scale)) * distance;
                oY = (float) -(Math.cos(pitch) * (1f - scale)) * distance;
                oZ = (float) -(Math.sin(pitch) * Math.sin(yaw) * (1f - scale)) * distance;
            }

            for (int i = 0; i < segments; i++) {
                float u = i * stepU;
                float un = (i + 1 == segments) ? endU : (i + 1) * stepU;
                Matrix4f mat = mStack.last().pose();

                mStack.pushPose();
                mStack.translate(-dX, -dY, -dZ);
                mStack.translate(oX, oY, oZ);
                mStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-yaw)));
                mStack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch) - 90f));
                mStack.mulPose(Axis.ZP.rotationDegrees(90));
                mStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(u)));
                mStack.translate(width2, 0, 0);
                mat = mStack.last().pose();
                builder.vertex(mat, 0, 0, 0).color(r, g, b, alpha2).endVertex();
                mStack.popPose();

                mStack.pushPose();
                mStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-lYaw)));
                mStack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-lPitch) - 90f));
                mStack.mulPose(Axis.ZP.rotationDegrees(90));
                mStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(u)));
                mStack.translate(width1, 0, 0);
                mat = mStack.last().pose();
                builder.vertex(mat, 0, 0, 0).color(r, g, b, alpha1).endVertex();
                mStack.popPose();

                mStack.pushPose();
                mStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-lYaw)));
                mStack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-lPitch) - 90f));
                mStack.mulPose(Axis.ZP.rotationDegrees(90));
                mStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(un)));
                mStack.translate(width1, 0, 0);
                mat = mStack.last().pose();
                builder.vertex(mat, 0, 0, 0).color(r, g, b, alpha1).endVertex();
                mStack.popPose();

                mStack.pushPose();
                mStack.translate(-dX, -dY, -dZ);
                mStack.translate(oX, oY, oZ);
                mStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-yaw)));
                mStack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch) - 90f));
                mStack.mulPose(Axis.ZP.rotationDegrees(90));
                mStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(un)));
                mStack.translate(width2, 0, 0);
                mat = mStack.last().pose();
                builder.vertex(mat, 0, 0, 0).color(r, g, b, alpha2).endVertex();
                mStack.popPose();
            }

            if (renderSphere && ii == trailList.size() - 2 && distance > 0 && width2 > 0) {
                mStack.pushPose();
                mStack.translate(-dX, -dY, -dZ);
                mStack.translate(oX, oY, oZ);
                mStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-yaw)));
                mStack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch) - 90f));
                mStack.mulPose(Axis.YP.rotationDegrees(-90));
                RenderUtils.renderSemiSphere(mStack, builder, width2, segments, segments / 2, color, alpha2);
                mStack.popPose();
            }

            mStack.popPose();
        }
    }

    public static void renderSphere(PoseStack mStack, VertexConsumer builder, float radius, int longs, int lats, Color color, float alpha, float endU) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        Matrix4f last = mStack.last().pose();
        float startU = 0;
        float startV = 0;
        float endV = Mth.PI;
        float stepU = (endU - startU) / longs;
        float stepV = (endV - startV) / lats;
        for (int i = 0; i < longs; ++i) {
            for (int j = 0; j < lats; ++j) {
                float u = i * stepU + startU;
                float v = j * stepV + startV;
                float un = (i + 1 == longs) ? endU : (i + 1) * stepU + startU;
                float vn = (j + 1 == lats) ? endV : (j + 1) * stepV + startV;
                Vec3 p0 = parametricSphere(u, v, radius);
                Vec3 p1 = parametricSphere(u, vn, radius);
                Vec3 p2 = parametricSphere(un, v, radius);
                Vec3 p3 = parametricSphere(un, vn, radius);

                builder.vertex(last, (float) p1.x(), (float) p1.y(), (float) p1.z()).color(r, g, b, alpha).endVertex();
                builder.vertex(last, (float) p0.x(), (float) p0.y(), (float) p0.z()).color(r, g, b, alpha).endVertex();
                builder.vertex(last, (float) p2.x(), (float) p2.y(), (float) p2.z()).color(r, g, b, alpha).endVertex();
                builder.vertex(last, (float) p3.x(), (float) p3.y(), (float) p3.z()).color(r, g, b, alpha).endVertex();
            }
        }
    }

    public static void renderSphere(PoseStack mStack, VertexConsumer builder, float radius, int longs, int lats, Color color, float alpha) {
        renderSphere(mStack, builder, radius, longs, lats, color, alpha, Mth.PI * 2);
    }

    public static void renderSemiSphere(PoseStack mStack, VertexConsumer builder, float radius, int longs, int lats, Color color, float alpha) {
        renderSphere(mStack, builder, radius, longs, lats, color, alpha, Mth.PI);
    }

    public static Vec3 parametricSphere(float u, float v, float r) {
        return new Vec3(Mth.cos(u) * Mth.sin(v) * r, Mth.cos(v) * r, Mth.sin(u) * Mth.sin(v) * r);
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
}
