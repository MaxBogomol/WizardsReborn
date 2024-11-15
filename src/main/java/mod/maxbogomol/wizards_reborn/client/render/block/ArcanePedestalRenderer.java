package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.common.book.CustomBook;
import mod.maxbogomol.fluffy_fur.common.book.CustomBookComponent;
import mod.maxbogomol.wizards_reborn.common.block.arcane_pedestal.ArcanePedestalBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ArcanePedestalRenderer implements BlockEntityRenderer<ArcanePedestalBlockEntity> {

    @Override
    public void render(ArcanePedestalBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        CustomBook book = blockEntity.getBook();
        if (book == null) {
            double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
            double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
            ticksUp = (ticksUp) % 360;

            poseStack.pushPose();
            poseStack.translate(0.5F, 1.1875F, 0.5F);
            poseStack.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
            poseStack.mulPose(Axis.YP.rotationDegrees((float) ticks));
            poseStack.scale(0.5F, 0.5F, 0.5F);
            ItemStack stack = blockEntity.getItemHandler().getItem(0);
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
            poseStack.popPose();
        } else {
            CustomBookComponent component = blockEntity.bookComponent;
            ItemStack stack = blockEntity.getItemHandler().getItem(0);
            if (component != null) {
                poseStack.pushPose();
                poseStack.translate(0.5F, 0.85F, 0.5F);
                float f = (float) component.time + partialTicks;
                poseStack.translate(0.0F, 0.1F + Mth.sin(f * 0.1F) * 0.01F, 0.0F);

                float f1;
                for (f1 = component.rot - component.oRot; f1 >= (float) Math.PI; f1 -= ((float) Math.PI * 2F)) {}

                while (f1 < -(float) Math.PI) {
                    f1 += ((float) Math.PI * 2F);
                }

                float f2 = component.oRot + f1 * partialTicks;
                poseStack.mulPose(Axis.YP.rotation(-f2));
                poseStack.mulPose(Axis.ZP.rotationDegrees(80.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(180f));
                poseStack.scale(0.75f, 0.75f, 0.75f);
                book.render(blockEntity.getLevel(), blockEntity.getBlockPos().getCenter(), stack, component, partialTicks, poseStack, bufferSource, light, overlay);
                poseStack.popPose();
            }
        }
    }
}
