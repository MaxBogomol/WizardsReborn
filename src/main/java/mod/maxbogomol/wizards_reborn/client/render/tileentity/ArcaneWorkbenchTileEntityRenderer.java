package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcaneWorkbenchTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Vector3f;

public class ArcaneWorkbenchTileEntityRenderer extends TileEntityRenderer<ArcaneWorkbenchTileEntity> {

    public ArcaneWorkbenchTileEntityRenderer(TileEntityRendererDispatcher manager) {
        super(manager);
    }

    @Override
    public void render(ArcaneWorkbenchTileEntity workbench, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffers, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        ms.push();
        ms.translate(0.5F, 1.5F, 0.5F);
        ms.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        ms.rotate(Vector3f.YP.rotationDegrees((float) ticks));
        ms.scale(0.5F, 0.5F, 0.5F);
        mc.getItemRenderer().renderItem(workbench.itemHandler.getStackInSlot(13), ItemCameraTransforms.TransformType.FIXED, light, overlay, ms, buffers);
        ms.pop();

        int x = -1;
        int y = -1;
        for (int i = 0; i < 9; i += 1) {
            ms.push();
            ms.translate(0.5F, 1.125F, 0.5F);
            ms.translate(0F, (float) Math.sin(Math.toRadians(ticksUp + (i * 10) % 360)) * 0.03125F, 0F);
            ms.rotate(Vector3f.YP.rotationDegrees(workbench.getBlockRotate()));
            ms.translate((-0.1875F * x), 0F, (-0.1875F * y));
            ms.scale(0.15F, 0.15F, 0.15F);
            ms.rotate(Vector3f.YP.rotationDegrees((float) ticks));

            mc.getItemRenderer().renderItem(workbench.itemHandler.getStackInSlot(i), ItemCameraTransforms.TransformType.FIXED, light, overlay, ms, buffers);
            ms.pop();

            x = x + 1;
            if (x > 1) {
                y = y + 1;
                x = -1;
            }
        }

        for (int i = 0; i < 4; i += 1) {
            ms.push();
            ms.translate(0.5F, 1.125F, 0.5F);
            ms.rotate(Vector3f.YP.rotationDegrees(-90F * i + workbench.getBlockRotate() - 90F));
            ms.translate(0.375F, 0F, 0F);
            ms.translate(0F, (float) Math.sin(Math.toRadians(ticksUp + (i * 10) % 360)) * 0.03125F, 0F);
            ms.scale(0.15F,0.15F,0.15F);
            ms.rotate(Vector3f.YP.rotationDegrees((float) ticks));
            ms.rotate(Vector3f.YP.rotationDegrees(workbench.getBlockRotate()));

            mc.getItemRenderer().renderItem(workbench.itemHandler.getStackInSlot(9 + i), ItemCameraTransforms.TransformType.FIXED, light, overlay, ms, buffers);
            ms.pop();
        }
    }
}
