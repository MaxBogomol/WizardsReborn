package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.tileentity.CrystalTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;

import java.awt.*;
import java.util.Random;

public class CrystalTileEntityRenderer implements BlockEntityRenderer<CrystalTileEntity> {

    public CrystalTileEntityRenderer() {}

    @Override
    public void render(CrystalTileEntity crystal, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(crystal.getBlockPos().asLong());

        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.15f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.05f));

        MultiBufferSource bufferDelayed = WorldRenderHandler.getDelayedRender();

        if (crystal.getLight() > 0) {
            if (!crystal.getItemHandler().getItem(0).isEmpty()) {
                if (crystal.getItemHandler().getItem(0).getItem() instanceof CrystalItem crystalItem) {
                    Color color = crystalItem.getType().getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

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