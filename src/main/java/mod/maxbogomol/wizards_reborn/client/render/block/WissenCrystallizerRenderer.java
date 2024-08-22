package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.block.wissen_crystallizer.WissenCrystallizerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

public class WissenCrystallizerRenderer implements BlockEntityRenderer<WissenCrystallizerBlockEntity> {

    public WissenCrystallizerRenderer() {}

    @Override
    public void render(WissenCrystallizerBlockEntity crystallizer, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        ms.pushPose();
        ms.translate(0.5F, 1.25F, 0.5F);
        ms.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        ms.mulPose(Axis.YP.rotationDegrees((float) ticks));
        ms.scale(0.5F, 0.5F, 0.5F);
        mc.getItemRenderer().renderStatic(crystallizer.getItemHandler().getItem(0), ItemDisplayContext.FIXED, light, overlay, ms, buffers, crystallizer.getLevel(), 0);
        ms.popPose();

        int size = crystallizer.getInventorySize();
        float rotate = 360f / (size - 1);

        if (size > 1) {
            for (int i = 0; i < size - 1; i++) {
                ms.pushPose();
                ms.translate(0.5F, 1.125F, 0.5F);
                ms.translate(0F, (float) Math.sin(Math.toRadians(ticksUp + (rotate * i))) * 0.0625F, 0F);
                ms.mulPose(Axis.YP.rotationDegrees((float) -ticks + ((i - 1) * rotate)));
                ms.translate(0.5F, 0F, 0F);
                ms.mulPose(Axis.YP.rotationDegrees(90f));
                ms.scale(0.25F, 0.25F, 0.25F);
                mc.getItemRenderer().renderStatic(crystallizer.getItemHandler().getItem(i + 1), ItemDisplayContext.FIXED, light, overlay, ms, buffers, crystallizer.getLevel(), 0);
                ms.popPose();
            }
        }
    }
}
