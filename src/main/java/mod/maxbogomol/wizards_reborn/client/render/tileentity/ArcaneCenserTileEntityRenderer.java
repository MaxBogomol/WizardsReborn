package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcaneCenserTileEntity;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.Mth;
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
            int newLight = light;
            int burn = ArcaneCenserTileEntity.getItemBurnCenser(censer.getItemHandler().getItem(i));
            if (burn > 0) {
                int R = (int) Mth.lerp(((float) 3 / burn), ColorUtils.getRed(light), 0);
                int G = (int) Mth.lerp(((float) 3 / burn), ColorUtils.getGreen(light), 0);
                int B = (int) Mth.lerp(((float) 3 / burn), ColorUtils.getBlue(light), 0);
                newLight = -ColorUtils.packColor(255, R, G, B);
            }

            ms.pushPose();
            ms.translate(0.5F, 0.196875F + (0.03125 * i), 0.5F);
            ms.mulPose(Axis.YP.rotationDegrees(censer.getBlockRotate()));
            ms.mulPose(Axis.YP.rotationDegrees((random.nextFloat() * 360)));
            ms.mulPose(Axis.XP.rotationDegrees(90f));
            ms.mulPose(Axis.XP.rotationDegrees((float) (Math.sin(Math.toRadians(random.nextFloat() * 360))) * 10F));
            ms.mulPose(Axis.ZP.rotationDegrees((float) (Math.sin(Math.toRadians(random.nextFloat() * 360))) * 10F));
            ms.scale(0.25F, 0.25F, 0.25F);
            mc.getItemRenderer().renderStatic(censer.getItemHandler().getItem(i), ItemDisplayContext.FIXED, newLight, overlay, ms, buffers, censer.getLevel(), 0);
            ms.popPose();
        }
    }
}
