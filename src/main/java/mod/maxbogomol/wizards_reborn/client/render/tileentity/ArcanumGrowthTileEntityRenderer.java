package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.common.block.ArcanumGrowthBlock;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcanumGrowthTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;

import java.awt.*;
import java.util.Random;

public class ArcanumGrowthTileEntityRenderer implements BlockEntityRenderer<ArcanumGrowthTileEntity> {

    public ArcanumGrowthTileEntityRenderer() {}

    @Override
    public void render(ArcanumGrowthTileEntity crystal, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(crystal.getBlockPos().asLong());

        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.15f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.05f));

        MultiBufferSource bufferDelayed = WorldRenderHandler.getDelayedRender();

        if (crystal.getLight() > 0) {
            if (crystal.getLevel().getBlockState(crystal.getBlockPos()).getBlock() instanceof ArcanumGrowthBlock growth) {
                Color color = new Color(0.466f, 0.643f, 0.815f);
                float r = color.getRed() / 255f;
                float g = color.getGreen() / 255f;
                float b = color.getBlue() / 255f;

                int age = crystal.getLevel().getBlockState(crystal.getBlockPos()).getValue(growth.getAgeProperty());

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
        }
    }
}