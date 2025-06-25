package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.common.block.wissen_cell.WissenCellBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

public class WissenCellRenderer implements BlockEntityRenderer<WissenCellBlockEntity> {

    @Override
    public void render(WissenCellBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Minecraft minecraft = Minecraft.getInstance();

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.703125F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate()));
        poseStack.mulPose(Axis.XP.rotationDegrees(90F));
        poseStack.scale(0.5F,0.5F,0.5F);
        minecraft.getItemRenderer().renderStatic(blockEntity.getItemHandler().getItem(0), ItemDisplayContext.NONE, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();
    }
}
