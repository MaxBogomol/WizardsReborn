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
import mod.maxbogomol.wizards_reborn.client.render.item.CustomItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.ForgeHooksClient;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.awt.*;
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
                    .createCompositeState(false));

    public static void renderItemModelInGui(ItemStack stack, int x, int y, float xSize, float ySize, float zSize) {
        renderItemModelInGui(stack, x, y, xSize, ySize, zSize, 0, 0, 0);
    }

    public static void renderItemModelInGui(ItemStack stack, int x, int y, float xSize, float ySize, float zSize, float xRot, float yRot, float zRot) {
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack, (Level)null, (LivingEntity)null, 0);

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

        Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);

        RenderSystem.disableDepthTest();
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();

        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
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
    public static void renderFloatingItemModelIntoGUI(GuiGraphics gui, ItemStack stack, int x, int y, float ticks, float ticksUp) {
        Minecraft minecraft = Minecraft.getInstance();
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack, minecraft.level, minecraft.player, 0);
        CustomItemRenderer customItemRenderer = new CustomItemRenderer(minecraft, minecraft.getTextureManager(), minecraft.getModelManager(), minecraft.getItemColors(), minecraft.getItemRenderer().getBlockEntityRenderer());

        blitOffset += 50.0F;
        float old = bakedmodel.getTransforms().gui.rotation.y;

        PoseStack posestack = gui.pose();

        posestack.pushPose();
        posestack.translate((float)(x + 8), (float)(y + 8), 100 + blitOffset);
        posestack.mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
        //posestack.translate(0.0D, 0.0D, -blitOffset + 200);
        posestack.scale(16.0F, 16.0F, 16.0F);
        posestack.translate(0.0D, Math.sin(Math.toRadians(ticksUp)) * 0.03125F, 0.0D);
        if (stack.getItem() instanceof BlockItem) {
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

    public static void quadCrystallType(PoseStack mStack, MultiBufferSource buf, float x, float y, float width, float height, float r, float g, float b, float a) {
        VertexConsumer builder = buf.getBuffer(GLOWING);

        Matrix4f mat = mStack.last().pose();
        builder.vertex(mat, x, y + (height / 2), 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x + width, y + (height / 2), 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x + width, y, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x, y, 0).color(r, g, b, 0).endVertex();

        builder.vertex(mat, x, y + height, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x + width, y + height, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x + width, y + (height / 2), 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x, y + (height / 2), 0).color(r, g, b, a).endVertex();


        builder.vertex(mat, x - (width / 8), y + (height / 2), 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x, y + (height / 2), 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x, y, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x - (width / 8), y, 0).color(r, g, b, 0).endVertex();

        builder.vertex(mat, x, y + height, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x, y + (height / 2), 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x - (width / 8), y + (height / 2), 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x - (width / 8), y + height, 0).color(r, g, b, 0).endVertex();


        builder.vertex(mat, x + width + (width / 8), y + (height / 2), 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x + width + (width / 8), y, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x + width, y, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x + width, y + (height / 2), 0).color(r, g, b, a).endVertex();

        builder.vertex(mat, x + width + (width / 8), y + (height / 2), 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x + width, y + (height / 2), 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x + width, y + height, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x + width + (width / 8), y + height, 0).color(r, g, b, 0).endVertex();
    }

    public static void quadMonogram(PoseStack mStack, MultiBufferSource buf, float x, float y, float width, float height, float r, float g, float b, float a) {
        VertexConsumer builder = buf.getBuffer(GLOWING);

        Matrix4f mat = mStack.last().pose();
        builder.vertex(mat, x, y + (height / 2), 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x + width, y + (height / 2), 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x + width, y, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x, y, 0).color(r, g, b, 0).endVertex();

        builder.vertex(mat, x, y + height, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x + width, y + height, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x + width, y + (height / 2), 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x, y + (height / 2), 0).color(r, g, b, a).endVertex();


        builder.vertex(mat, x - (width / 8), y + (height / 2), 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x, y + (height / 2), 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x, y, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x - (width / 8), y, 0).color(r, g, b, 0).endVertex();

        builder.vertex(mat, x, y + height, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x, y + (height / 2), 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x - (width / 8), y + (height / 2), 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x - (width / 8), y + height, 0).color(r, g, b, 0).endVertex();


        builder.vertex(mat, x + width + (width / 8), y + (height / 2), 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x + width + (width / 8), y, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x + width, y, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x + width, y + (height / 2), 0).color(r, g, b, a).endVertex();

        builder.vertex(mat, x + width + (width / 8), y + (height / 2), 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x + width, y + (height / 2), 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x + width, y + height, 0).color(r, g, b, 0).endVertex();
        builder.vertex(mat, x + width + (width / 8), y + height, 0).color(r, g, b, 0).endVertex();
    }

    public static void renderCustomModel(ModelResourceLocation model, ItemDisplayContext diplayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(model);
        Minecraft.getInstance().getItemRenderer().render(new ItemStack(Items.DIRT), diplayContext, leftHand, poseStack, buffer, combinedLight, combinedOverlay, bakedmodel);
    }
}
