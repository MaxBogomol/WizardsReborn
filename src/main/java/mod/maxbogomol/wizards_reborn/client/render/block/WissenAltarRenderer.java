package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.block.wissen_altar.WissenAltarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

public class WissenAltarRenderer implements BlockEntityRenderer<WissenAltarBlockEntity> {

    @Override
    public void render(WissenAltarBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Minecraft minecraft = Minecraft.getInstance();

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.890625F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate()));
        poseStack.mulPose(Axis.XP.rotationDegrees(90F));
        poseStack.scale(0.5F,0.5F,0.5F);
        minecraft.getItemRenderer().renderStatic(blockEntity.getItemHandler().getItem(0), ItemDisplayContext.NONE, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.3125F, 0.5F);
        poseStack.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F * blockEntity.getCraftingStage()), 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) ticks));
        poseStack.scale(0.5F * blockEntity.getCraftingStage(), 0.5F * blockEntity.getCraftingStage(), 0.5F * blockEntity.getCraftingStage());
        minecraft.getItemRenderer().renderStatic(blockEntity.getItemHandler().getItem(2), ItemDisplayContext.NONE, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();
    }
}
