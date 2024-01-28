package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.light.ILightTileEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightUtils;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.common.tileentity.LightTransferLensTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class LightTransferLensTileEntityRenderer implements BlockEntityRenderer<LightTransferLensTileEntity> {

    public LightTransferLensTileEntityRenderer() {}

    @Override
    public void render(LightTransferLensTileEntity lens, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(lens.getBlockPos().asLong());

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.4f;
        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.35f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.3f));

        MultiBufferSource bufferDelayed = WorldRenderHandler.getDelayedRender();

        ms.pushPose();
        ms.translate(0.5F, 0.5F, 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        ms.mulPose(Axis.XP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        ms.mulPose(Axis.ZP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        RenderUtils.renderCustomModel(WizardsRebornClient.HOVERING_LENS_MODEl, ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
        RenderUtils.ray(ms, bufferDelayed, 0.075f, 0.075f, 1f, 0.564f, 0.682f, 0.705f, alpha, 0.564f, 0.682f, 0.705f, alpha);
        ms.popPose();

        if (lens.isToBlock && lens.canWork() && lens.getLight() > 0) {
            BlockPos pos = new BlockPos(lens.blockToX, lens.blockToY, lens.blockToZ);
            if (lens.getLevel().getBlockEntity(pos) instanceof ILightTileEntity lightTile) {
                Vec3 from = LightUtils.getLightLensPos(lens.getBlockPos(), lens.getLightLensPos());
                Vec3 to = LightUtils.getLightLensPos(pos, lightTile.getLightLensPos());

                ms.pushPose();
                ms.translate(0.5F, 0.5F, 0.5F);
                LightUtils.renderLightRay(lens.getLevel(), lens.getBlockPos(), from, to, 25f, lens.getColor(), partialTicks, ms);
                ms.popPose();
            }
        }
    }

    @Override
    public boolean shouldRenderOffScreen(LightTransferLensTileEntity pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(LightTransferLensTileEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
