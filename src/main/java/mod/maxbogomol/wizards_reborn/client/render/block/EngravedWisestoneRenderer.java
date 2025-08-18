package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.common.block.engraved_wisestone.EngravedWisestoneBlock;
import mod.maxbogomol.wizards_reborn.common.block.engraved_wisestone.EngravedWisestoneBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Random;

public class EngravedWisestoneRenderer implements BlockEntityRenderer<EngravedWisestoneBlockEntity> {

    @Override
    public void render(EngravedWisestoneBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        float ticks = ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick() * 0.1f;

        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        float alpha = Mth.lerp(partialTicks, blockEntity.oldGlowTicks, blockEntity.glowTicks) / 20f;
        alpha = Easing.SINE_IN_OUT.ease(alpha, 0, 1, 1);

        if (alpha > 0 && blockEntity.getBlockState().getBlock() instanceof EngravedWisestoneBlock block && block.hasMonogram()) {
            Monogram monogram = block.getMonogram();
            Color color = monogram.getColor();

            TextureAtlasSprite sprite = RenderUtil.getSprite(monogram.getTexture());

            float width = 1f;
            float offset = 1f + ((1f - alpha) * 10f);
            float rotateOffset = offset / 2f;

            poseStack.pushPose();
            poseStack.translate(0.5f, 0.5f, 0.5f);
            poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getHorizontalBlockRotate()));
            poseStack.mulPose(Axis.XP.rotationDegrees(blockEntity.getVerticalBlockRotate()));
            if (blockEntity.getVerticalBlockRotate() == 0) poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
            poseStack.translate(0f, 0f, -0.505f);
            RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                    .setUV(sprite)
                    .setColor(Color.WHITE).setAlpha(0.5f * alpha)
                    .renderCenteredQuad(poseStack, 0.25f);

            for (int i = 0; i < 5; i++) {
                poseStack.translate(0, 0, -0.005f * offset);
                poseStack.mulPose(Axis.ZP.rotationDegrees((float) Math.sin(Math.toRadians(((random.nextFloat() * 360) + ticks))) * rotateOffset));
                poseStack.mulPose(Axis.XP.rotationDegrees((float) Math.sin(Math.toRadians(((random.nextFloat() * 360) + ticks))) * rotateOffset));
                poseStack.mulPose(Axis.YP.rotationDegrees((float) Math.sin(Math.toRadians(((random.nextFloat() * 360) + ticks))) * rotateOffset));
                RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                        .enableSided()
                        .setUV(sprite)
                        .setColor(color).setAlpha(0.2f * alpha)
                        .renderWavyQuad(poseStack, 0.25f * width, 0.02f * offset, ticks + (random.nextFloat() * 100));
                width = width + 0.1f;
            }

            poseStack.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(EngravedWisestoneBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(EngravedWisestoneBlockEntity blockEntity, Vec3 cameraPos) {
        return true;
    }
}
