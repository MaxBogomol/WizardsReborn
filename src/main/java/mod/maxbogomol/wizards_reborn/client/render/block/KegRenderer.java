package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.block.keg.KegBlock;
import mod.maxbogomol.wizards_reborn.common.block.keg.KegBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.IPlacedItem;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class KegRenderer implements BlockEntityRenderer<KegBlockEntity> {
    
    @Override
    public void render(KegBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        int x = -1;
        int y = -1;
        for (int i = 0; i < 6; i += 1) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate()));
            poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
            poseStack.translate(0, -0.3125F, -0.125F);
            poseStack.translate((-0.24999F * x), 0F, (-0.375F * y));
            poseStack.scale(0.99999f, 0.9999f, 0.9999f);

            ItemStack itemStack = blockEntity.itemHandler.getStackInSlot(i);
            if (itemStack.getItem() instanceof IPlacedItem placedItem) {
                placedItem.renderPlacedItem(itemStack, 0, 0, blockEntity.getLevel(), partialTicks, poseStack, bufferSource, light, overlay);
            }
            poseStack.popPose();

            x = x + 1;
            if (x > 1) {
                y = y + 1;
                x = -1;
            }
        }

        float door = Mth.lerp(partialTicks, blockEntity.oldDoorTick, blockEntity.doorTick);
        ModelResourceLocation model = WizardsRebornModels.ARCANE_WOOD_KEG_DOOR;

        if (blockEntity.getBlockState().getBlock() instanceof KegBlock kegBlock) {
            model = kegBlock.doorModel;
        }

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate()));
        poseStack.translate(-0.40625F, 0.0625F, -0.40625F);
        poseStack.mulPose(Axis.YP.rotationDegrees(door * 18));
        poseStack.translate(0.40625F, 0, 0);
        RenderUtil.renderCustomModel(model, ItemDisplayContext.NONE, false, poseStack, bufferSource, light, overlay);
        poseStack.popPose();
    }
}
