package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.light_emitter.LightEmitterBlockEntity;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Random;

public class LightEmitterBlockRenderer implements BlockEntityRenderer<LightEmitterBlockEntity> {

    @Override
    public void render(LightEmitterBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());
        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.4f;
        Color colorLens = LightUtil.getLensColorFromLumos(blockEntity.getLumos(), partialTicks);

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.8125F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        WizardsRebornRenderUtil.renderHoveringLens(poseStack, bufferSource, colorLens, light, overlay);
        poseStack.popPose();


        if (blockEntity.canWork()) {
            if (blockEntity.isToBlock && blockEntity.getLight() > 0) {
                BlockPos pos = new BlockPos(blockEntity.blockToX, blockEntity.blockToY, blockEntity.blockToZ);
                if (blockEntity.getLevel().getBlockEntity(pos) instanceof ILightBlockEntity lightTile) {
                    Vec3 from = LightUtil.getLightLensPos(blockEntity.getBlockPos(), blockEntity.getLightLensPos());
                    Vec3 to = LightUtil.getLightLensPos(pos, lightTile.getLightLensPos());

                    poseStack.pushPose();
                    poseStack.translate(0.5F, 0.8125F, 0.5F);
                    Color color = LightUtil.getColorFromLumos(blockEntity.getRayColor(), blockEntity.getLumos(), partialTicks);
                    Color colorType = LightUtil.getColorFromTypes(blockEntity.getLightTypes());
                    Color colorConcentrated = LightUtil.getColorConcentratedFromTypes(blockEntity.getLightTypes());
                    boolean concentrated = LightUtil.isConcentratedType(blockEntity.getLightTypes());
                    LightUtil.renderLightRay(blockEntity.getLevel(), blockEntity.getBlockPos(), from, to, 25f, color, colorType, colorConcentrated, concentrated, partialTicks, poseStack);
                    poseStack.popPose();
                }
            }
        }

        if (WissenUtil.isCanRenderWissenWand()) {
            if (blockEntity.isToBlock) {
                poseStack.pushPose();
                Vec3 lensPos = blockEntity.getLightLensPos();
                poseStack.translate(lensPos.x(), lensPos.y(), lensPos.z());
                BlockPos pos = new BlockPos(blockEntity.blockToX, blockEntity.blockToY, blockEntity.blockToZ);
                if (blockEntity.getLevel().getBlockEntity(pos) instanceof ILightBlockEntity lightTile) {
                    RenderUtil.renderConnectLine(poseStack, LightUtil.getLightLensPos(blockEntity.getBlockPos(), blockEntity.getLightLensPos()), LightUtil.getLightLensPos(pos, lightTile.getLightLensPos()), WizardsRebornRenderUtil.colorConnectTo, 0.5f);
                }
                poseStack.popPose();
            }
        }
    }

    @Override
    public boolean shouldRenderOffScreen(LightEmitterBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(LightEmitterBlockEntity blockEntity, Vec3 cameraPos) {
        return true;
    }
}
