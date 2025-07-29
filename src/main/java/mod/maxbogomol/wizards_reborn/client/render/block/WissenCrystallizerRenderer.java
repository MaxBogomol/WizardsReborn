package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.block.wissen_crystallizer.WissenCrystallizerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

public class WissenCrystallizerRenderer implements BlockEntityRenderer<WissenCrystallizerBlockEntity> {

    @Override
    public void render(WissenCrystallizerBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Minecraft minecraft = Minecraft.getInstance();

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.25F, 0.5F);
        poseStack.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) ticks));
        poseStack.scale(0.5F, 0.5F, 0.5F);
        minecraft.getItemRenderer().renderStatic(blockEntity.getItemHandler().getItem(0), ItemDisplayContext.FIXED, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();

        int size = blockEntity.getInventorySize();
        float rotate = 360f / (size - 1);

        if (size > 1) {
            for (int i = 0; i < size - 1; i++) {
                poseStack.pushPose();
                poseStack.translate(0.5F, 1.125F, 0.5F);
                poseStack.translate(0F, (float) Math.sin(Math.toRadians(ticksUp + (rotate * i))) * 0.0625F, 0F);
                poseStack.mulPose(Axis.YP.rotationDegrees((float) -ticks + ((i - 1) * rotate)));
                poseStack.translate(0.5F, 0F, 0F);
                poseStack.mulPose(Axis.YP.rotationDegrees(90f));
                poseStack.scale(0.25F, 0.25F, 0.25F);
                minecraft.getItemRenderer().renderStatic(blockEntity.getItemHandler().getItem(i + 1), ItemDisplayContext.FIXED, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
                poseStack.popPose();
            }
        }
    }
}
