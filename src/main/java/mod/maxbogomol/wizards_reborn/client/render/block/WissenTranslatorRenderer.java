package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.wissen_translator.WissenTranslatorBlockEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class WissenTranslatorRenderer implements BlockEntityRenderer<WissenTranslatorBlockEntity> {

    public WissenTranslatorRenderer() {}

    @Override
    public void render(WissenTranslatorBlockEntity translator, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        if (WissenUtils.isCanRenderWissenWand()) {
            if (translator.isToBlock) {
                ms.pushPose();
                ms.translate(0.5F,0.5F,0.5F);
                RenderUtils.renderConnectLine(translator.getBlockPos(), new BlockPos(translator.blockToX, translator.blockToY, translator.blockToZ), RenderUtils.colorConnectTo, partialTicks, ms);
                ms.popPose();
            }
            if (translator.isFromBlock) {
                ms.pushPose();
                ms.translate(0.5F,0.5F,0.5F);
                RenderUtils.renderConnectLine(translator.getBlockPos(), new BlockPos(translator.blockFromX, translator.blockFromY, translator.blockFromZ), RenderUtils.colorConnectFrom, partialTicks, ms);
                ms.popPose();
            }
        }
    }

    @Override
    public boolean shouldRenderOffScreen(WissenTranslatorBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(WissenTranslatorBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
