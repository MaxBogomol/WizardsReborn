package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBlockEntity;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.Random;

public class SensorRenderer implements BlockEntityRenderer<SensorBlockEntity> {

    public SensorRenderer() {}

    @Override
    public void render(SensorBlockEntity sensor, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(sensor.getBlockPos().asLong());

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.1F;

        ms.pushPose();
        ms.translate(0.5F, 0.5F, 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        ms.mulPose(Axis.XP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        ms.mulPose(Axis.ZP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));

        ms.mulPose(Axis.YP.rotationDegrees(sensor.getBlockRotate()));
        ms.mulPose(Axis.XP.rotationDegrees(sensor.getBlockUpRotate()));
        RenderUtils.renderCustomModel(((SensorBaseBlock) sensor.getBlockState().getBlock()).getModel(sensor.getBlockState()), ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
        ms.popPose();
    }
}
