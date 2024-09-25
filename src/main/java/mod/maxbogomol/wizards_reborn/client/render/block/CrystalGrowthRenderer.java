package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
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

        if (blockEntity.getLevel().getBlockState(blockEntity.getBlockPos()).getBlock() instanceof CrystalGrowthBlock growth) {
            Color color = growth.type.getColor();
            RenderBuilder builder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                    .setColor(color).setAlpha(0.8f).setSided(false, true);

            int age = blockEntity.getLevel().getBlockState(blockEntity.getBlockPos()).getValue(growth.getAgeProperty());

            if (blockEntity.getLight() > 0) {
                if (age == 0) {
                    poseStack.pushPose();
                    poseStack.translate(0.46875F, 0.09375f, 0.46875F);
                    builder.renderCenteredCube(poseStack, 0.1171875f, 0.1171875f, 0.1171875f);
                    poseStack.popPose();
                }

                if (age == 1) {
                    poseStack.pushPose();
                    poseStack.translate(0.5f, 0.09375f, 0.5f);
                    builder.renderCenteredCube(poseStack, 0.1484375f, 0.1171875f, 0.1484375f);
                    poseStack.popPose();
                }

                if (age == 2) {
                    poseStack.pushPose();
                    poseStack.translate(0.5f, 0.15625f, 0.5f);
                    builder.renderCenteredCube(poseStack, 0.1484375f, 0.1796875f, 0.1484375f);
                    poseStack.popPose();
                }

                if (age == 3) {
                    poseStack.pushPose();
                    poseStack.translate(0.53125f, 0.21875f, 0.53125f);
                    builder.renderCenteredCube(poseStack, 0.1796875f, 0.2421875f, 0.1796875f);
                    poseStack.popPose();
                }

                if (age == 4) {
                    poseStack.pushPose();
                    poseStack.translate(0.5f, 0.28125f, 0.5f);
                    builder.renderCenteredCube(poseStack, 0.2109375f, 0.3046875f, 0.2109375f);
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