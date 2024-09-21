package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.block.orbital_fluid_retainer.OrbitalFluidRetainerBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;

public class OrbitalFluidRetainerRenderer implements BlockEntityRenderer<OrbitalFluidRetainerBlockEntity> {

    @Override
    public void render(OrbitalFluidRetainerBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        FluidStack fluidStack = blockEntity.getFluidStack();

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksSub = (ClientTickHandler.ticksInGame + partialTicks) * 1;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (random.nextFloat() * 360) + ticksUp;
        ticksUp = (ticksUp) % 360;

        double v = Math.sin(Math.toRadians(ticksUp)) * 0.0625F;

        float lastAmount = Mth.lerp(partialTicks, blockEntity.fluidLastAmount, blockEntity.getFluidStack().getAmount());
        float amount = lastAmount / blockEntity.getMaxCapacity();

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.translate(0F, (float) v, 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) ((random.nextFloat() * 360) + ticks)));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
        poseStack.scale(amount, amount, amount);
        RenderUtil.renderWavyFluid(poseStack, fluidStack, 0.25f, 0.5f, false, light, 0.01f, (float) (ticks + (random.nextFloat() * 100)));

        poseStack.popPose();

        for (int i = 0; i < amount * 25; i++) {
            double f = Math.sin(Math.toRadians(((random.nextFloat() * 360) + ticks))) * amount;
            double j = Math.abs(f);
            poseStack.pushPose();
            poseStack.translate(0.5F, 1.5F, 0.5F);
            poseStack.translate(0F, (float) v, 0F);
            poseStack.mulPose(Axis.YP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
            poseStack.mulPose(Axis.XP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
            poseStack.mulPose(Axis.ZP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
            poseStack.translate(0F, (0.15F + (random.nextFloat() * 0.5F)) * f, 0F);
            poseStack.scale((float) j, (float) j, (float) j);
            poseStack.mulPose(Axis.YP.rotationDegrees((float) ((random.nextFloat() * 360) + ticks)));
            poseStack.mulPose(Axis.XP.rotationDegrees((float) ((random.nextFloat() * 360) + ticks)));
            poseStack.mulPose(Axis.ZP.rotationDegrees((float) ((random.nextFloat() * 360) + ticks)));
            poseStack.translate(-0.25F / 2, -0.25F / 2, -0.25F / 2);

            float size = 1;
            if (i % 2 == 0) size = 2;
            RenderUtil.renderWavyFluid(poseStack, fluidStack, 0.03125f * size, 0.0625f * size, false, light, 0.003125f, (float) (ticks + (random.nextFloat() * 100)));
            poseStack.popPose();
        }
    }
}
