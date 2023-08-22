package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcaneWorkbenchTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemDisplayContext;

public class ArcaneWorkbenchTileEntityRenderer implements BlockEntityRenderer<ArcaneWorkbenchTileEntity> {

    public ArcaneWorkbenchTileEntityRenderer() {}

    @Override
    public void render(ArcaneWorkbenchTileEntity workbench, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        ms.pushPose();
        ms.translate(0.5F, 1.5F, 0.5F);
        ms.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        ms.mulPose(Axis.YP.rotationDegrees((float) ticks));
        ms.scale(0.5F, 0.5F, 0.5F);
        mc.getItemRenderer().renderStatic(workbench.itemHandler.getStackInSlot(13), ItemDisplayContext.FIXED, light, overlay, ms, buffers, workbench.getLevel(), 0);
        ms.popPose();

        int x = -1;
        int y = -1;
        for (int i = 0; i < 9; i += 1) {
            ms.pushPose();
            ms.translate(0.5F, 1.125F, 0.5F);
            ms.translate(0F, (float) Math.sin(Math.toRadians(ticksUp + (i * 10) % 360)) * 0.03125F, 0F);
            ms.mulPose(Axis.YP.rotationDegrees(workbench.getBlockRotate()));
            ms.translate((-0.1875F * x), 0F, (-0.1875F * y));
            ms.scale(0.15F, 0.15F, 0.15F);
            ms.mulPose(Axis.YP.rotationDegrees((float) ticks));

            mc.getItemRenderer().renderStatic(workbench.itemHandler.getStackInSlot(i), ItemDisplayContext.FIXED, light, overlay, ms, buffers, workbench.getLevel(), 0);
            ms.popPose();

            x = x + 1;
            if (x > 1) {
                y = y + 1;
                x = -1;
            }
        }

        for (int i = 0; i < 4; i += 1) {
            ms.pushPose();
            ms.translate(0.5F, 1.125F, 0.5F);
            ms.mulPose(Axis.YP.rotationDegrees(-90F * i + workbench.getBlockRotate() - 90F));
            ms.translate(0.375F, 0F, 0F);
            ms.translate(0F, (float) Math.sin(Math.toRadians(ticksUp + (i * 10) % 360)) * 0.03125F, 0F);
            ms.scale(0.15F,0.15F,0.15F);
            ms.mulPose(Axis.YP.rotationDegrees((float) ticks));
            ms.mulPose(Axis.YP.rotationDegrees(workbench.getBlockRotate()));

            mc.getItemRenderer().renderStatic(workbench.itemHandler.getStackInSlot(9 + i), ItemDisplayContext.FIXED, light, overlay, ms, buffers, workbench.getLevel(), 0);
            ms.popPose();
        }
    }
}
