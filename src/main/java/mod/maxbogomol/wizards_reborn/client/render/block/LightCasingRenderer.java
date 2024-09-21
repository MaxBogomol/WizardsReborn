package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.casing.light.LightCasingBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Random;

public class LightCasingRenderer implements BlockEntityRenderer<LightCasingBlockEntity> {
    
    @Override
    public void render(LightCasingBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.35f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.3f));

        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();

        for (Direction direction : Direction.values()) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            BlockPos pos = new BlockPos(0, 0, 0).relative(direction);
            poseStack.translate(pos.getX() * blockEntity.getLightLensOffset(), pos.getY() * blockEntity.getLightLensOffset(), pos.getZ() * blockEntity.getLightLensOffset());

            RenderUtil.renderCustomModel(WizardsRebornModels.HOVERING_LENS, ItemDisplayContext.FIXED, false, poseStack, bufferSource, light, overlay);

            if (blockEntity.isConnection(direction)) {
                RenderUtil.ray(poseStack, bufferDelayed, 0.075f, 0.075f, 1f, 0.564f, 0.682f, 0.705f, alpha, 0.564f, 0.682f, 0.705f, alpha);

                if (blockEntity.canWork() && blockEntity.getLight() > 0) {
                    Vec3 from = new Vec3(blockEntity.getBlockPos().getX() + 0.5f + (pos.getX() * blockEntity.getLightLensOffset()), blockEntity.getBlockPos().getY() + 0.5f + (pos.getY() * blockEntity.getLightLensOffset()), blockEntity.getBlockPos().getZ() + 0.5f + (pos.getZ() * blockEntity.getLightLensOffset()));
                    Vec3 to = LightUtil.getLightLensPos(blockEntity.getBlockPos().relative(direction), blockEntity.getLightLensPos());

                    Color color = LightUtil.getRayColorFromLumos(blockEntity.getColor(), blockEntity.getLumos(), blockEntity.getBlockPos(), partialTicks);
                    poseStack.pushPose();
                    LightUtil.renderLightRay(blockEntity.getLevel(), blockEntity.getBlockPos(), from, to, 25f, color, partialTicks, poseStack);
                    poseStack.popPose();
                }

                if (WissenUtils.isCanRenderWissenWand()) {
                    poseStack.pushPose();
                    poseStack.translate(-0.2f, -0.2f, -0.2f);
                    RenderUtils.renderBoxLines(new Vec3(0.4f, 0.4f, 0.4f), RenderUtils.colorConnectTo, partialTicks, poseStack);
                    poseStack.popPose();
                }
            }
            poseStack.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(LightCasingBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(LightCasingBlockEntity blockEntity, Vec3 cameraPos) {
        return true;
    }
}
