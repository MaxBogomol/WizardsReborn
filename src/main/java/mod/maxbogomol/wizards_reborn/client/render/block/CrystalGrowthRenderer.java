package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.LevelRenderHandler;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.crystal_growth.CrystalGrowthBlock;
import mod.maxbogomol.wizards_reborn.common.block.crystal_growth.CrystalGrowthBlockEntity;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Random;

public class CrystalGrowthRenderer implements BlockEntityRenderer<CrystalGrowthBlockEntity> {

    public CrystalGrowthRenderer() {}

    @Override
    public void render(CrystalGrowthBlockEntity crystal, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(crystal.getBlockPos().asLong());

        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.15f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.05f));

        MultiBufferSource bufferDelayed = LevelRenderHandler.getDelayedRender();

        if (crystal.getLevel().getBlockState(crystal.getBlockPos()).getBlock() instanceof CrystalGrowthBlock growth) {
            Color color = growth.type.getColor();
            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;

            int age = crystal.getLevel().getBlockState(crystal.getBlockPos()).getValue(growth.getAgeProperty());

            if (crystal.getLight() > 0) {
                if (age == 0) {
                    ms.pushPose();
                    ms.translate(0.46875F, 0.1F, 0.46875F);
                    ms.mulPose(Axis.ZP.rotationDegrees(-90f));
                    RenderUtils.ray(ms, bufferDelayed, 0.10625f, 0.11f, 1f, r, g, b, alpha);
                    ms.popPose();
                }

                if (age == 1) {
                    ms.pushPose();
                    ms.translate(0.5F, 0.06F, 0.5F);
                    ms.mulPose(Axis.ZP.rotationDegrees(-90f));
                    RenderUtils.ray(ms, bufferDelayed, 0.1375f, 0.08f, 1f, r, g, b, alpha);
                    ms.popPose();
                }

                if (age == 2) {
                    ms.pushPose();
                    ms.translate(0.5F, 0.19F, 0.5F);
                    ms.mulPose(Axis.ZP.rotationDegrees(-90f));
                    RenderUtils.ray(ms, bufferDelayed, 0.1375f, 0.21f, 1f, r, g, b, alpha);
                    ms.popPose();
                }

                if (age == 3) {
                    ms.pushPose();
                    ms.translate(0.53125F, 0.29F, 0.53125F);
                    ms.mulPose(Axis.ZP.rotationDegrees(-90f));
                    RenderUtils.ray(ms, bufferDelayed, 0.16875f, 0.3f, 1f, r, g, b, alpha);
                    ms.popPose();
                }

                if (age == 4) {
                    ms.pushPose();
                    ms.translate(0.5F, 0.3825F, 0.5F);
                    ms.mulPose(Axis.ZP.rotationDegrees(-90f));
                    RenderUtils.ray(ms, bufferDelayed, 0.2f, 0.4f, 1f, r, g, b, alpha);
                    ms.popPose();
                }
            }

            if (WissenUtils.isCanRenderWissenWand()) {
                if (age == 4) {
                    ms.pushPose();
                    RenderUtils.renderBoxLines(new Vec3(1, 1, 1), color, partialTicks, ms);
                    ms.popPose();
                }
            }
        }
    }
}