package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.light_transfer_lens.LightTransferLensBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Random;

public class LightTransferLensRenderer implements BlockEntityRenderer<LightTransferLensBlockEntity> {

    @Override
    public void render(LightTransferLensBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.4f;
        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.35f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.3f));

        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        RenderUtil.renderCustomModel(WizardsRebornModels.HOVERING_LENS, ItemDisplayContext.FIXED, false, poseStack, bufferSource, light, overlay);
        RenderUtils.ray(poseStack, bufferDelayed, 0.075f, 0.075f, 1f, 0.564f, 0.682f, 0.705f, alpha, 0.564f, 0.682f, 0.705f, alpha);
        poseStack.popPose();

        if (blockEntity.isToBlock && blockEntity.canWork() && blockEntity.getLight() > 0) {
            BlockPos pos = new BlockPos(blockEntity.blockToX, blockEntity.blockToY, blockEntity.blockToZ);
            if (blockEntity.getLevel().getBlockEntity(pos) instanceof ILightBlockEntity lightTile) {
                Vec3 from = LightUtil.getLightLensPos(blockEntity.getBlockPos(), blockEntity.getLightLensPos());
                Vec3 to = LightUtil.getLightLensPos(pos, lightTile.getLightLensPos());

                poseStack.pushPose();
                poseStack.translate(0.5F, 0.5F, 0.5F);
                Color color = LightUtil.getRayColorFromLumos(blockEntity.getColor(), blockEntity.getLumos(), blockEntity.getBlockPos(), partialTicks);
                LightUtil.renderLightRay(blockEntity.getLevel(), blockEntity.getBlockPos(), from, to, 25f, color, partialTicks, poseStack);
                poseStack.popPose();
            }
        }

        if (WissenUtils.isCanRenderWissenWand()) {
            if (blockEntity.isToBlock) {
                poseStack.pushPose();
                Vec3 blockEntityPos = blockEntity.getLightLensPos();
                poseStack.translate(blockEntityPos.x(), blockEntityPos.y(), blockEntityPos.z());
                BlockPos pos = new BlockPos(blockEntity.blockToX, blockEntity.blockToY, blockEntity.blockToZ);
                if (blockEntity.getLevel().getBlockEntity(pos) instanceof ILightBlockEntity lightTile) {
                    RenderUtils.renderConnectLine(LightUtil.getLightLensPos(blockEntity.getBlockPos(), blockEntity.getLightLensPos()), LightUtil.getLightLensPos(pos, lightTile.getLightLensPos()), RenderUtils.colorConnectTo, partialTicks, poseStack);
                }
                poseStack.popPose();
            }
        }
    }

    @Override
    public boolean shouldRenderOffScreen(LightTransferLensBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(LightTransferLensBlockEntity blockEntity, Vec3 cameraPos) {
        return true;
    }
}
