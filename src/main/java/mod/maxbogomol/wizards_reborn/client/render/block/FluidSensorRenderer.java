package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.sensor.fluid.FluidSensorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.Random;

public class FluidSensorRenderer implements BlockEntityRenderer<FluidSensorBlockEntity> {

    @Override
    public void render(FluidSensorBlockEntity sensor, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(sensor.getBlockPos().asLong());

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.1F;

        double ticksSub = (ClientTickHandler.ticksInGame + partialTicks) * 1;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (random.nextFloat() * 360) + ticksUp;
        ticksUp = (ticksUp) % 360;

        double v = Math.sin(Math.toRadians(ticksUp)) * 0.0625F;

        ms.pushPose();
        ms.translate(0.5F, 0.5F, 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        ms.mulPose(Axis.XP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        ms.mulPose(Axis.ZP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));

        ms.mulPose(Axis.YP.rotationDegrees(sensor.getBlockRotate()));
        ms.mulPose(Axis.XP.rotationDegrees(sensor.getBlockUpRotate()));
        RenderUtil.renderCustomModel(((SensorBaseBlock) sensor.getBlockState().getBlock()).getModel(sensor.getBlockState()), ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
        ms.popPose();

        for (int i = 0; i < 10; i++) {
            double f = Math.sin(Math.toRadians(((random.nextFloat() * 360) + ticks * 20)));
            double j = Math.abs(f);
            ms.pushPose();
            ms.translate(0.5F, 0.5F, 0.5F);
            ms.translate(0F, (float) v, 0F);
            ms.mulPose(Axis.YP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
            ms.mulPose(Axis.XP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
            ms.mulPose(Axis.ZP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
            ms.translate(0F, (0.15F + (random.nextFloat() * 0.3F)) * f, 0F);
            ms.scale((float) j, (float) j, (float) j);
            ms.mulPose(Axis.YP.rotationDegrees((float) ((random.nextFloat() * 360) + ticks)));
            ms.mulPose(Axis.XP.rotationDegrees((float) ((random.nextFloat() * 360) + ticks)));
            ms.mulPose(Axis.ZP.rotationDegrees((float) ((random.nextFloat() * 360) + ticks)));
            RenderUtil.renderWavyFluid(ms, sensor.getTank().getFluid(), 0.03125f, 0.0625f, false, light, 0.003125f, (float) (ticks + (random.nextFloat() * 100)));
            ms.popPose();
        }
    }
}
