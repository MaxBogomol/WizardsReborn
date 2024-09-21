package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.wissen_translator.WissenTranslatorBlockEntity;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class WissenTranslatorRenderer implements BlockEntityRenderer<WissenTranslatorBlockEntity> {

    @Override
    public void render(WissenTranslatorBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        if (WissenUtils.isCanRenderWissenWand()) {
            if (blockEntity.isToBlock) {
                poseStack.pushPose();
                poseStack.translate(0.5F,0.5F,0.5F);
                RenderUtils.renderConnectLine(blockEntity.getBlockPos(), new BlockPos(blockEntity.blockToX, blockEntity.blockToY, blockEntity.blockToZ), RenderUtils.colorConnectTo, partialTicks, poseStack);
                poseStack.popPose();
            }
            if (blockEntity.isFromBlock) {
                poseStack.pushPose();
                poseStack.translate(0.5F,0.5F,0.5F);
                RenderUtils.renderConnectLine(blockEntity.getBlockPos(), new BlockPos(blockEntity.blockFromX, blockEntity.blockFromY, blockEntity.blockFromZ), RenderUtils.colorConnectFrom, partialTicks, poseStack);
                poseStack.popPose();
            }
        }
    }

    @Override
    public boolean shouldRenderOffScreen(WissenTranslatorBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(WissenTranslatorBlockEntity blockEntity, Vec3 cameraPos) {
        return true;
    }
}
