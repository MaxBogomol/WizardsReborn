package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.casing.wissen.WissenCasingBlockEntity;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public class WissenCasingRenderer implements BlockEntityRenderer<WissenCasingBlockEntity> {

    @Override
    public void render(WissenCasingBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        for (Direction direction : Direction.values()) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            BlockPos pos = new BlockPos(0, 0, 0).relative(direction);
            poseStack.translate(pos.getX() * 0.4375f, pos.getY() * 0.4375f, pos.getZ() * 0.4375f);

            if (blockEntity.isConnection(direction)) {
                if (WissenUtil.isCanRenderWissenWand()) {
                    poseStack.pushPose();
                    poseStack.translate(-0.2f, -0.2f, -0.2f);
                    WizardsRebornRenderUtil.renderBoxLines(new Vec3(0.4f, 0.4f, 0.4f), WizardsRebornRenderUtil.colorConnectTo, partialTicks, poseStack);
                    poseStack.popPose();
                }
            }
            poseStack.popPose();
        }
    }
}
