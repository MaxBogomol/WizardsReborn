package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.crystal_growth.CrystalGrowthBlock;
import mod.maxbogomol.wizards_reborn.common.block.crystal_growth.CrystalGrowthBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Random;

public class CrystalGrowthRenderer implements BlockEntityRenderer<CrystalGrowthBlockEntity> {

    @Override
    public void render(CrystalGrowthBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.15f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.05f));

        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();

        if (blockEntity.getLevel().getBlockState(blockEntity.getBlockPos()).getBlock() instanceof CrystalGrowthBlock growth) {
            Color color = growth.type.getColor();
            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;

            int age = blockEntity.getLevel().getBlockState(blockEntity.getBlockPos()).getValue(growth.getAgeProperty());

            if (blockEntity.getLight() > 0) {
                if (age == 0) {
                    poseStack.pushPose();
                    poseStack.translate(0.46875F, 0.1F, 0.46875F);
                    poseStack.mulPose(Axis.ZP.rotationDegrees(-90f));
                    //WizardsRebornRenderUtil.ray(poseStack, bufferDelayed, 0.10625f, 0.11f, 1f, r, g, b, alpha);
                    poseStack.popPose();
                }

                if (age == 1) {
                    poseStack.pushPose();
                    poseStack.translate(0.5F, 0.06F, 0.5F);
                    poseStack.mulPose(Axis.ZP.rotationDegrees(-90f));
                    //WizardsRebornRenderUtil.ray(poseStack, bufferDelayed, 0.1375f, 0.08f, 1f, r, g, b, alpha);
                    poseStack.popPose();
                }

                if (age == 2) {
                    poseStack.pushPose();
                    poseStack.translate(0.5F, 0.19F, 0.5F);
                    poseStack.mulPose(Axis.ZP.rotationDegrees(-90f));
                    //WizardsRebornRenderUtil.ray(poseStack, bufferDelayed, 0.1375f, 0.21f, 1f, r, g, b, alpha);
                    poseStack.popPose();
                }

                if (age == 3) {
                    poseStack.pushPose();
                    poseStack.translate(0.53125F, 0.29F, 0.53125F);
                    poseStack.mulPose(Axis.ZP.rotationDegrees(-90f));
                    //WizardsRebornRenderUtil.ray(poseStack, bufferDelayed, 0.16875f, 0.3f, 1f, r, g, b, alpha);
                    poseStack.popPose();
                }

                if (age == 4) {
                    poseStack.pushPose();
                    poseStack.translate(0.5F, 0.3825F, 0.5F);
                    poseStack.mulPose(Axis.ZP.rotationDegrees(-90f));
                    //WizardsRebornRenderUtil.ray(poseStack, bufferDelayed, 0.2f, 0.4f, 1f, r, g, b, alpha);
                    poseStack.popPose();
                }
            }

            if (WissenUtil.isCanRenderWissenWand()) {
                if (age == 4) {
                    poseStack.pushPose();
                    RenderUtil.renderConnectBoxLines(poseStack, new Vec3(1, 1, 1), color, 0.5f);
                    poseStack.popPose();
                }
            }
        }
    }
}