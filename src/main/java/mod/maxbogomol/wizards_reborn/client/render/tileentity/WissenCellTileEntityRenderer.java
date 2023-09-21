package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.common.tileentity.WissenCellTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

public class WissenCellTileEntityRenderer implements BlockEntityRenderer<WissenCellTileEntity> {

    public WissenCellTileEntityRenderer() {}

    @Override
    public void render(WissenCellTileEntity cell, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();

        ms.pushPose();
        ms.translate(0.5F, 0.703125F, 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees(cell.getBlockRotate()));
        ms.mulPose(Axis.XP.rotationDegrees(90F));
        ms.scale(0.5F,0.5F,0.5F);
        mc.getItemRenderer().renderStatic(cell.getItemHandler().getItem(0), ItemDisplayContext.FIXED, light, overlay, ms, buffers, cell.getLevel(), 0);
        ms.popPose();
    }
}
