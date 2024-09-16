package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.fluid.FluidCuboid;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.block.orbital_fluid_retainer.OrbitalFluidRetainerBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Vector3f;

import java.util.Random;

public class OrbitalFluidRetainerRenderer implements BlockEntityRenderer<OrbitalFluidRetainerBlockEntity> {

    FluidCuboid cube = new FluidCuboid(new Vector3f(0, 0, 0), new Vector3f(8, 8, 8), FluidCuboid.DEFAULT_FACES);
    FluidCuboid cube_large = new FluidCuboid(new Vector3f(0, 0, 0), new Vector3f(2, 2, 2), FluidCuboid.DEFAULT_FACES);
    FluidCuboid cube_tiny = new FluidCuboid(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), FluidCuboid.DEFAULT_FACES);

    public OrbitalFluidRetainerRenderer() {}

    @Override
    public void render(OrbitalFluidRetainerBlockEntity retainer, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(retainer.getBlockPos().asLong());

        FluidStack fluidStack = retainer.getFluidStack();
        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();

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
        //ms.translate(-0.25F, -0.25F, -0.25F);
        //FluidRenderer.renderCuboid(ms, bufferDelayed, cube, fluidStack, retainer.getCapacity(), light, false);
        RenderUtil.renderWavyFluid(ms, fluidStack, 0.25f, 0.01f, (float) (ticks + (random.nextFloat() * 100)));

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

            FluidCuboid subCube = cube_tiny;
            float size = 1;
            if (i % 2 == 0) {
                subCube = cube_large;
                size = 2;
            }

            //FluidRenderer.renderCuboid(ms, bufferDelayed, subCube, fluidStack, retainer.getCapacity(), light, false);
            RenderUtil.renderWavyFluid(ms, fluidStack, 0.03125f * size, 0.003125f, (float) (ticks + (random.nextFloat() * 100)));
            ms.popPose();
        }
    }
}
