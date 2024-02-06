package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.common.tileentity.RunicPedestalTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Random;

public class RunicPedestalTileEntityRenderer implements BlockEntityRenderer<RunicPedestalTileEntity> {

    public RunicPedestalTileEntityRenderer() {}

    @Override
    public void render(RunicPedestalTileEntity pedestal, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(pedestal.getBlockPos().asLong());

        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.35f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.3f));

        MultiBufferSource bufferDelayed = WorldRenderHandler.getDelayedRender();

        if (pedestal.getBlockState().getValue(BlockStateProperties.LIT)) {
            ms.pushPose();
            RenderUtils.litQuadCube(ms, bufferDelayed, 0.225f, 0.359375f, 0.225f, 0.546875f, 0.09375f, 0.546875f, 0.678f, 0.929f, 0.803f, alpha);
            ms.popPose();
        }
    }
}
