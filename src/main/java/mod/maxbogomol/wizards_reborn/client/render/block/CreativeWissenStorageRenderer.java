package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.common.block.creative.wissen_storage.CreativeWissenStorageBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

public class CreativeWissenStorageRenderer implements BlockEntityRenderer<CreativeWissenStorageBlockEntity> {

    @Override
    public void render(CreativeWissenStorageBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Minecraft minecraft = Minecraft.getInstance();

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.84375F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate()));
        poseStack.mulPose(Axis.XP.rotationDegrees(90F));
        poseStack.scale(0.5F,0.5F,0.5F);
        minecraft.getItemRenderer().renderStatic(blockEntity.getItemHandler().getItem(0), ItemDisplayContext.NONE, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();
    }
}
