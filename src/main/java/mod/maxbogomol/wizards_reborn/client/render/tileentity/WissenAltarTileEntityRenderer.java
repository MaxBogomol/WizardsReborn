package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.tileentity.WissenAltarTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Vector3f;

public class WissenAltarTileEntityRenderer extends TileEntityRenderer<WissenAltarTileEntity> {

    public WissenAltarTileEntityRenderer(TileEntityRendererDispatcher manager) {
        super(manager);
    }

    @Override
    public void render(WissenAltarTileEntity altar, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffers, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        ms.push();
        ms.translate(0.5F, 0.890625F, 0.5F);
        ms.rotate(Vector3f.YP.rotationDegrees(altar.getBlockRotate()));
        ms.rotate(Vector3f.XP.rotationDegrees(90F));
        ms.scale(0.5F,0.5F,0.5F);
        mc.getItemRenderer().renderItem(altar.getItemHandler().getStackInSlot(0), ItemCameraTransforms.TransformType.FIXED, light, overlay, ms, buffers);
        ms.pop();

        ms.push();
        ms.translate(0.5F, 1.3125F, 0.5F);
        ms.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F * altar.getCraftingStage()), 0F);
        ms.rotate(Vector3f.YP.rotationDegrees((float) ticks));
        ms.scale(0.5F * altar.getCraftingStage(), 0.5F * altar.getCraftingStage(), 0.5F * altar.getCraftingStage());
        mc.getItemRenderer().renderItem(altar.getItemHandler().getStackInSlot(2), ItemCameraTransforms.TransformType.FIXED, light, overlay, ms, buffers);
        ms.pop();
    }
}
