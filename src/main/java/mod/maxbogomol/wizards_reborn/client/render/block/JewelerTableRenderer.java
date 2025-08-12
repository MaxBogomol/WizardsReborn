package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.block.jeweler_table.JewelerTableBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

public class JewelerTableRenderer implements BlockEntityRenderer<JewelerTableBlockEntity> {
    
    @Override
    public void render(JewelerTableBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Minecraft minecraft = Minecraft.getInstance();
        Vec3 pos = blockEntity.getBlockRotatePos();
        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        double ticksStone = blockEntity.stoneRotate;
        if (blockEntity.stoneSpeed > 0) {
            ticksStone = (blockEntity.stoneRotate + ((partialTicks) * blockEntity.stoneSpeed));
        }

        poseStack.pushPose();
        poseStack.translate(pos.x(), pos.y(), pos.z());
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate()));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) ticksStone));
        RenderUtil.renderCustomModel(WizardsRebornModels.JEWELER_TABLE_STONE, ItemDisplayContext.NONE, false, poseStack, bufferSource, light, overlay);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.703125F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate()));
        poseStack.mulPose(Axis.XP.rotationDegrees(90F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-3F));
        poseStack.translate(0, -0.0725, 0);
        poseStack.scale(0.5F,0.5F,0.5F);
        minecraft.getItemRenderer().renderStatic(blockEntity.itemHandler.getStackInSlot(0), ItemDisplayContext.FIXED, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.703125F + 0.03125F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate()));
        poseStack.mulPose(Axis.XP.rotationDegrees(90F + 5F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(15F));
        poseStack.translate(0.125F, -0.0625F, 0);
        poseStack.scale(0.5F,0.5F,0.5F);
        minecraft.getItemRenderer().renderStatic(blockEntity.itemHandler.getStackInSlot(1), ItemDisplayContext.FIXED, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.5F, 1F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate() + 90F));
        poseStack.translate(0.125F, 0, 0);
        poseStack.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) ticks));
        poseStack.scale(0.5F, 0.5F, 0.5F);
        minecraft.getItemRenderer().renderStatic(blockEntity.itemOutputHandler.getStackInSlot(0), ItemDisplayContext.FIXED, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();
    }
}
