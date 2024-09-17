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
    public void render(OrbitalFluidRetainerBlockEntity retainer, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(retainer.getBlockPos().asLong());

        FluidStack fluidStack = retainer.getFluidStack();

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksSub = (ClientTickHandler.ticksInGame + partialTicks) * 1;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (random.nextFloat() * 360) + ticksUp;
        ticksUp = (ticksUp) % 360;

        double v = Math.sin(Math.toRadians(ticksUp)) * 0.0625F;

        float lastAmount = Mth.lerp(partialTicks, retainer.fluidLastAmount, retainer.getFluidStack().getAmount());
        float amount = lastAmount / retainer.getMaxCapacity();

        ms.pushPose();
        ms.translate(0.5F, 1.5F, 0.5F);
        ms.translate(0F, (float) v, 0F);
        ms.mulPose(Axis.YP.rotationDegrees((float) ((random.nextFloat() * 360) + ticks)));
        ms.mulPose(Axis.XP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
        ms.mulPose(Axis.ZP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
        ms.scale(amount, amount, amount);
        RenderUtil.renderWavyFluid(ms, fluidStack, 0.25f, 0.5f, false, light, 0.01f, (float) (ticks + (random.nextFloat() * 100)));

        ms.popPose();

        for (int i = 0; i < amount * 25; i++) {
            double f = Math.sin(Math.toRadians(((random.nextFloat() * 360) + ticks))) * amount;
            double j = Math.abs(f);
            ms.pushPose();
            ms.translate(0.5F, 1.5F, 0.5F);
            ms.translate(0F, (float) v, 0F);
            ms.mulPose(Axis.YP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
            ms.mulPose(Axis.XP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
            ms.mulPose(Axis.ZP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
            ms.translate(0F, (0.15F + (random.nextFloat() * 0.5F)) * f, 0F);
            ms.scale((float) j, (float) j, (float) j);
            ms.mulPose(Axis.YP.rotationDegrees((float) ((random.nextFloat() * 360) + ticks)));
            ms.mulPose(Axis.XP.rotationDegrees((float) ((random.nextFloat() * 360) + ticks)));
            ms.mulPose(Axis.ZP.rotationDegrees((float) ((random.nextFloat() * 360) + ticks)));
            ms.translate(-0.25F / 2, -0.25F / 2, -0.25F / 2);

            float size = 1;
            if (i % 2 == 0) size = 2;
            RenderUtil.renderWavyFluid(ms, fluidStack, 0.03125f * size, 0.0625f * size, false, light, 0.003125f, (float) (ticks + (random.nextFloat() * 100)));
            ms.popPose();
        }
    }
}
