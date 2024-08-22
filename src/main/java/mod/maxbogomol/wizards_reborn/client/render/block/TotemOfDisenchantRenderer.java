package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.block.totem.disenchant.TotemOfDisenchantBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class TotemOfDisenchantRenderer implements BlockEntityRenderer<TotemOfDisenchantBlockEntity> {

    public TotemOfDisenchantRenderer() {}

    @Override
    public void render(TotemOfDisenchantBlockEntity totem, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        double ticks = (ClientTickHandler.ticksInGame + partialTicks) / 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks);
        ticksUp = (ticksUp) % 360;

        ms.pushPose();
        ms.translate(0.5F, 0.625F, 0.5F);
        ms.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        ms.mulPose(Axis.YP.rotationDegrees((float) ticks));
        ms.scale(0.25F, 0.25F, 0.25F);
        ItemStack stack = totem.itemHandler.getStackInSlot(0);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, ms, buffers, totem.getLevel(), 0);
        ms.popPose();
    }
}
