package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.block.SensorBaseBlock;
import mod.maxbogomol.wizards_reborn.common.tileentity.SensorTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.Random;

public class SensorTileEntityRenderer implements BlockEntityRenderer<SensorTileEntity> {

    public SensorTileEntityRenderer() {}

    @Override
    public void render(SensorTileEntity sensor, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
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
