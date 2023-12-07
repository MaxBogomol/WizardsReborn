package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcaneCenserTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.Random;

public class ArcaneCenserTileEntityRenderer implements BlockEntityRenderer<ArcaneCenserTileEntity> {

    public ArcaneCenserTileEntityRenderer() {}

    @Override
    public void render(ArcaneCenserTileEntity censer, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(censer.getBlockPos().asLong());
        Minecraft mc = Minecraft.getInstance();

        int size = censer.getInventorySize();

        for (int i = 0; i < size; i++) {
            ms.pushPose();
            ms.translate(0.5F, 0.196875F + (0.03125 * i), 0.5F);
            ms.mulPose(Axis.YP.rotationDegrees(censer.getBlockRotate()));
            ms.mulPose(Axis.YP.rotationDegrees((random.nextFloat() * 360)));
            ms.mulPose(Axis.XP.rotationDegrees(90f));
            ms.mulPose(Axis.XP.rotationDegrees((float) (Math.sin(Math.toRadians(random.nextFloat() * 360))) * 10F));
            ms.mulPose(Axis.ZP.rotationDegrees((float) (Math.sin(Math.toRadians(random.nextFloat() * 360))) * 10F));
            ms.scale(0.25F, 0.25F, 0.25F);
            mc.getItemRenderer().renderStatic(censer.getItemHandler().getItem(i), ItemDisplayContext.FIXED, light, overlay, ms, buffers, censer.getLevel(), 0);
            ms.popPose();
        }
    }
}
