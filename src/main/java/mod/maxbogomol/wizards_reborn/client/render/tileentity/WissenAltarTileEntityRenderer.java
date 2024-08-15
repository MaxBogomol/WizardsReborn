package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.block.wissen_altar.WissenAltarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

public class WissenAltarTileEntityRenderer implements BlockEntityRenderer<WissenAltarBlockEntity> {

    public WissenAltarTileEntityRenderer() {}

    @Override
    public void render(WissenAltarBlockEntity altar, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        ms.pushPose();
        ms.translate(0.5F, 0.890625F, 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees(altar.getBlockRotate()));
        ms.mulPose(Axis.XP.rotationDegrees(90F));
        ms.scale(0.5F,0.5F,0.5F);
        mc.getItemRenderer().renderStatic(altar.getItemHandler().getItem(0), ItemDisplayContext.FIXED, light, overlay, ms, buffers, altar.getLevel(), 0);
        ms.popPose();

        ms.pushPose();
        ms.translate(0.5F, 1.3125F, 0.5F);
        ms.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F * altar.getCraftingStage()), 0F);
        ms.mulPose(Axis.YP.rotationDegrees((float) ticks));
        ms.scale(0.5F * altar.getCraftingStage(), 0.5F * altar.getCraftingStage(), 0.5F * altar.getCraftingStage());
        mc.getItemRenderer().renderStatic(altar.getItemHandler().getItem(2), ItemDisplayContext.FIXED, light, overlay, ms, buffers, altar.getLevel(), 0);
        ms.popPose();
    }
}
