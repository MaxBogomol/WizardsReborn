package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.common.block.pancake.PancakeBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.food.PancakeItem;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class PancakeRenderer implements BlockEntityRenderer<PancakeBlockEntity> {

    @Override
    public void render(PancakeBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        for (int i = 0; i < 8; i++) {
            ItemStack stack = blockEntity.getItemHandler().getItem(i);
            if (stack.getItem() instanceof PancakeItem) {
                ResourceLocation texture = PancakeItem.getModelTexture(stack);

                poseStack.pushPose();
                poseStack.translate(0.5F, 0.125F * i, 0.5F);
                poseStack.mulPose(Axis.XP.rotationDegrees(180f));
                poseStack.mulPose(Axis.YP.rotationDegrees(-blockEntity.getBlockRotate() + 180f));
                WizardsRebornModels.PANCAKE.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                poseStack.popPose();
            }
        }
    }
}
