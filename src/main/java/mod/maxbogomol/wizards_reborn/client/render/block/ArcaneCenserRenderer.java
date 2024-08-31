package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.common.block.arcane_censer.ArcaneCenserBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.Random;

public class ArcaneCenserRenderer implements BlockEntityRenderer<ArcaneCenserBlockEntity> {

    public ArcaneCenserRenderer() {}

    @Override
    public void render(ArcaneCenserBlockEntity censer, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(censer.getBlockPos().asLong());
        Minecraft mc = Minecraft.getInstance();

        int size = censer.getInventorySize();

        for (int i = 0; i < size; i++) {
            int newLight = light;
            int burn = ArcaneCenserBlockEntity.getItemBurnCenser(censer.getItemHandler().getItem(i));
            if (burn > 0) {
                int R = (int) Mth.lerp(((float) burn / 3), ColorUtil.getRed(light), 0) / 2;
                int G = (int) Mth.lerp(((float) burn / 3), ColorUtil.getGreen(light), 0) / 2;
                int B = (int) Mth.lerp(((float) burn / 3), ColorUtil.getBlue(light), 0) / 2;
                newLight = ColorUtil.packColor(0, R, G, B);
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
