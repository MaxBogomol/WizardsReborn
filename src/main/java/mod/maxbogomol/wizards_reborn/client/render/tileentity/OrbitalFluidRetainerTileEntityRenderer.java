package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.render.fluid.FluidCuboid;
import mod.maxbogomol.wizards_reborn.client.render.fluid.FluidRenderer;
import mod.maxbogomol.wizards_reborn.common.tileentity.OrbitalFluidRetainerTileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Vector3f;

import java.util.Random;

public class OrbitalFluidRetainerTileEntityRenderer implements BlockEntityRenderer<OrbitalFluidRetainerTileEntity> {

    FluidCuboid cube = new FluidCuboid(new Vector3f(0, 0, 0), new Vector3f(8, 8, 8), FluidCuboid.DEFAULT_FACES);
    FluidCuboid cube_large = new FluidCuboid(new Vector3f(0, 0, 0), new Vector3f(2, 2, 2), FluidCuboid.DEFAULT_FACES);
    FluidCuboid cube_tiny = new FluidCuboid(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), FluidCuboid.DEFAULT_FACES); //medium

    public OrbitalFluidRetainerTileEntityRenderer() {}

    @Override
    public void render(OrbitalFluidRetainerTileEntity retainer, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
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
        ms.translate(-0.25F, -0.25F, -0.25F);
        FluidRenderer.renderCuboid(ms, buffers, cube, fluidStack, retainer.getCapacity(), light, false);
        ms.popPose();

        for (int i = 0; i < amount * 25; i++) {
            double f = Math.sin(Math.toRadians(((random.nextFloat() * 360) + ticks))) * amount;
            ms.pushPose();
            ms.translate(0.5F, 1.5F, 0.5F);
            ms.translate(0F, (float) v, 0F);
            ms.mulPose(Axis.YP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
            ms.mulPose(Axis.XP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
            ms.mulPose(Axis.ZP.rotationDegrees((float) ((random.nextFloat() * 360) + ticksSub)));
            ms.translate(0F, (0.15F + (random.nextFloat() * 0.5F)) * f, 0F);
            ms.scale((float) f, (float) f, (float) f);
            ms.mulPose(Axis.YP.rotationDegrees((float) ((random.nextFloat() * 360) + ticks)));
            ms.mulPose(Axis.XP.rotationDegrees((float) ((random.nextFloat() * 360) + ticks)));
            ms.mulPose(Axis.ZP.rotationDegrees((float) ((random.nextFloat() * 360) + ticks)));
            ms.translate(-0.25F / 2, -0.25F / 2, -0.25F / 2);

            FluidCuboid subCube = cube_tiny;
            if (i % 2 == 0) {
                subCube = cube_large;
            }

            FluidRenderer.renderCuboid(ms, buffers, subCube, fluidStack, retainer.getCapacity(), light, false);
            ms.popPose();
        }
    }
}
