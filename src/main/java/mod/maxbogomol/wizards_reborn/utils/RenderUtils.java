package mod.maxbogomol.wizards_reborn.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.OverlayTexture;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import java.awt.*;

import com.mojang.blaze3d.platform.Lighting;
import org.joml.Matrix4f;
import org.joml.Vector3f;

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

    public static RenderType GLOWING_PARTICLE = RenderType.create(
            WizardsReborn.MOD_ID + ":glowing_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
                    RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_PARTICLES, false, false))
                    .setShaderState(new RenderStateShard.ShaderStateShard(WizardsReborn::getGlowingParticleShader))
                    .createCompositeState(false));

    public static RenderType DELAYED_PARTICLE = RenderType.create(
            WizardsReborn.MOD_ID + ":delayed_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setTransparencyState(NORMAL_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_PARTICLES, false, false))
                    .setShaderState(new RenderStateShard.ShaderStateShard(WizardsReborn::getSpriteParticleShader))
                    .createCompositeState(false));

    public static void renderItemModelInGui(ItemStack stack, int x, int y, int xSize, int ySize, int zSize) {
        Minecraft mc = Minecraft.getInstance();

        RenderSystem.getModelViewStack().popPose();
        RenderSystem.getModelViewStack().translate((float)x+(xSize/2), (float)y+(ySize/2), 10.0F);
        RenderSystem.getModelViewStack().scale((float)xSize/16, (float)ySize/16, (float)zSize/16);
        RenderSystem.getModelViewStack().translate((float)-(x+(xSize/2)), (float)-(y+(ySize/2)), 0.0F);
        //mc.getItemRenderer().renderAndDecorateItem(RenderSystem.getModelViewStack(), stack, x, y);
        RenderSystem.getModelViewStack().popPose();
    }

    public static Vec3 followBodyRotation(LivingEntity living) {
        Vec3 rotate = new Vec3(0, 0, 0);
        EntityRenderer<? super LivingEntity> render = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(living);
        if(render instanceof LivingEntityRenderer) {
            LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>> livingRenderer = (LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>>) render;
            EntityModel<LivingEntity> entityModel = livingRenderer.getModel();
            if (entityModel instanceof HumanoidModel) {
                HumanoidModel<LivingEntity> bipedModel = (HumanoidModel<LivingEntity>) entityModel;
                rotate = new Vec3(bipedModel.body.xRot, bipedModel.body.yRot, bipedModel.body.zRot);;
            }
        }
        return rotate;
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

    public static void renderFloatingItemModelIntoGUI(ItemStack stack, int x, int y, float ticks, float ticksUp) {
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack, (Level)null, (LivingEntity)null, 0);

        blitOffset += 50.0F;

        Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate(x, y, (100.0F + blitOffset));
        posestack.translate(8.0D, 8.0D, 0.0D);
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale(16.0F, 16.0F, 16.0F);
        RenderSystem.getModelViewStack().mulPose(Axis.YP.rotationDegrees(ticks));
        posestack.translate(0.0D, Math.sin(Math.toRadians(ticksUp)) * 0.03125F, 0.0D);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !bakedmodel.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();

        blitOffset -= 50.0F;
    }
}
