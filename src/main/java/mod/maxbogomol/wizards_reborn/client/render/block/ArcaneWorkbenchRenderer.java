package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.block.arcane_workbench.ArcaneWorkbenchBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

public class ArcaneWorkbenchRenderer implements BlockEntityRenderer<ArcaneWorkbenchBlockEntity> {

    @Override
    public void render(ArcaneWorkbenchBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Minecraft minecraft = Minecraft.getInstance();

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) ticks));
        poseStack.scale(0.5F, 0.5F, 0.5F);
        minecraft.getItemRenderer().renderStatic(blockEntity.itemOutputHandler.getStackInSlot(0), ItemDisplayContext.NONE, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();

        int x = -1;
        int y = -1;
        for (int i = 0; i < 9; i += 1) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 1.125F, 0.5F);
            poseStack.translate(0F, (float) Math.sin(Math.toRadians(ticksUp + (i * 10) % 360)) * 0.03125F, 0F);
            poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate()));
            poseStack.translate((-0.1875F * x), 0F, (-0.1875F * y));
            poseStack.scale(0.15F, 0.15F, 0.15F);
            poseStack.mulPose(Axis.YP.rotationDegrees((float) ticks));

            minecraft.getItemRenderer().renderStatic(blockEntity.itemHandler.getStackInSlot(i), ItemDisplayContext.NONE, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
            poseStack.popPose();

            x = x + 1;
            if (x > 1) {
                y = y + 1;
                x = -1;
            }
        }

        for (int i = 0; i < 4; i += 1) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 1.125F, 0.5F);
            poseStack.mulPose(Axis.YP.rotationDegrees(-90F * i + blockEntity.getBlockRotate() - 90F));
            poseStack.translate(0.375F, 0F, 0F);
            poseStack.translate(0F, (float) Math.sin(Math.toRadians(ticksUp + (i * 10) % 360)) * 0.03125F, 0F);
            poseStack.scale(0.15F,0.15F,0.15F);
            poseStack.mulPose(Axis.YP.rotationDegrees((float) ticks));
            poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate()));

            minecraft.getItemRenderer().renderStatic(blockEntity.itemHandler.getStackInSlot(9 + i), ItemDisplayContext.NONE, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
            poseStack.popPose();
        }
    }
}
