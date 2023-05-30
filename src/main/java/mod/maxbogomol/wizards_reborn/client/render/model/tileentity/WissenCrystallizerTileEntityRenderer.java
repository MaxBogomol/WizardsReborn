package mod.maxbogomol.wizards_reborn.client.render.model.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.tileentity.WissenCrystallizerTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Vector3f;

public class WissenCrystallizerTileEntityRenderer extends TileEntityRenderer<WissenCrystallizerTileEntity> {

    public WissenCrystallizerTileEntityRenderer(TileEntityRendererDispatcher manager) {
        super(manager);
    }

    @Override
    public void render(WissenCrystallizerTileEntity сrystallizer, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffers, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        ms.push();
        ms.translate(0.5F, 1.25F, 0.5F);
        ms.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        ms.rotate(Vector3f.YP.rotationDegrees((float) ticks));
        ms.scale(0.5F, 0.5F, 0.5F);
        mc.getItemRenderer().renderItem(сrystallizer.getItemHandler().getStackInSlot(0), ItemCameraTransforms.TransformType.FIXED, light, overlay, ms, buffers);
        ms.pop();

        int size = сrystallizer.getInventorySize();
        float rotate = 360f / (size - 1);

        if (size > 1) {
            for (int i = 0; i < size - 1; i++) {
                ms.push();
                ms.translate(0.5F, 1.125F, 0.5F);
                ms.translate(0F, (float) Math.sin(Math.toRadians(ticksUp + (rotate * i))) * 0.0625F, 0F);
                ms.rotate(Vector3f.YP.rotationDegrees((float) -ticks + ((i - 1) * rotate)));
                ms.translate(0.5F, 0F, 0F);
                ms.rotate(Vector3f.YP.rotationDegrees(90f));
                ms.scale(0.25F, 0.25F, 0.25F);
                mc.getItemRenderer().renderItem(сrystallizer.getItemHandler().getStackInSlot(i + 1), ItemCameraTransforms.TransformType.FIXED, light, overlay, ms, buffers);
                ms.pop();
            }
        }
    }
}
