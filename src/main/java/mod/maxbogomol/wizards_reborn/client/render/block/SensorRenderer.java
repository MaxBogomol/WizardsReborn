package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.Random;

public class SensorRenderer implements BlockEntityRenderer<SensorBlockEntity> {

    @Override
    public void render(SensorBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.1F;

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));

        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate()));
        poseStack.mulPose(Axis.XP.rotationDegrees(blockEntity.getBlockUpRotate()));
        RenderUtil.renderCustomModel(((SensorBaseBlock) blockEntity.getBlockState().getBlock()).getModel(blockEntity.getBlockState()), ItemDisplayContext.NONE, false, poseStack, bufferSource, light, overlay);
        poseStack.popPose();
    }
}
