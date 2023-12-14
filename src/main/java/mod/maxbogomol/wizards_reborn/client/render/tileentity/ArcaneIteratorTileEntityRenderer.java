package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcaneIteratorTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.Random;

public class ArcaneIteratorTileEntityRenderer implements BlockEntityRenderer<ArcaneIteratorTileEntity> {

    public ArcaneIteratorTileEntityRenderer() {}

    @Override
    public void render(ArcaneIteratorTileEntity iterator, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(iterator.getBlockPos().asLong());

        Minecraft mc = Minecraft.getInstance();
        double ticks = (ClientTickHandler.ticksInGame + partialTicks);
        double ticksOffset = (ClientTickHandler.ticksInGame + partialTicks) * 0.1F;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        float x = 0.15625F;
        float y = 0.15625F;
        float z = 0.15625F;
        float offset = 1;

        offset = offset + Math.abs((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticksOffset))));
        float size =  Math.abs((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks / 100))));
        size = 1;

        ms.pushPose();
        ms.translate(0.5F, 0.5F, 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        ms.mulPose(Axis.XP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        ms.scale(size, size, size);
        ms.mulPose(Axis.ZP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        renderPiece(-x * offset, y * offset, -z * offset, 90F, 0F, size, iterator, partialTicks, ms, buffers, light, overlay);
        renderPiece(x * offset, y * offset, -z * offset, 0F, 0F, size, iterator, partialTicks, ms, buffers, light, overlay);
        renderPiece(-x * offset, y * offset, z * offset, 180F, 0F, size, iterator, partialTicks, ms, buffers, light, overlay);
        renderPiece(x * offset, y * offset, z * offset, -90F, 0F, size, iterator, partialTicks, ms, buffers, light, overlay);
        renderPiece(-x * offset, -y * offset, -z * offset, 90F, 90F, size, iterator, partialTicks, ms, buffers, light, overlay);
        renderPiece(x * offset, -y * offset, -z * offset, 0F, 90F, size, iterator, partialTicks, ms, buffers, light, overlay);
        renderPiece(-x * offset, -y * offset, z * offset, 180F, 90F, size, iterator, partialTicks, ms, buffers, light, overlay);
        renderPiece(x * offset, -y * offset, z * offset, -90F, 90F, size, iterator, partialTicks, ms, buffers, light, overlay);
        ms.popPose();
    }

    public void renderPiece(float x, float y, float z, float yRot, float zRot, float size ,ArcaneIteratorTileEntity iterator, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        ms.pushPose();
        ms.translate(x, y, z);
        ms.mulPose(Axis.YP.rotationDegrees(yRot));
        ms.mulPose(Axis.ZP.rotationDegrees(-zRot));
        ms.scale(size, size, size);
        RenderUtils.renderCustomModel(WizardsRebornClient.ARCANE_ITERATOR_PIECE_MODEl, ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
        ms.popPose();
    }
}
