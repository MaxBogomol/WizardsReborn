package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.wizards_reborn.common.block.wissen_charger.WissenChargerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class WissenChargerRenderer implements BlockEntityRenderer<WissenChargerBlockEntity> {

    @Override
    public void render(WissenChargerBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Minecraft minecraft = Minecraft.getInstance();

        double ticks = (blockEntity.dischargeTicksUp + partialTicks) * 2;
        double ticksUp = (blockEntity.dischargeTicksUp + partialTicks) * 4;
        ticks = (ticks) % 360;
        ticksUp = (ticksUp) % 360;

        float dischargeTicks = Mth.lerp(partialTicks, blockEntity.oldDischargeTicks, blockEntity.dischargeTicks) / 20f;
        float lerp = Easing.QUINTIC_IN_OUT.ease(dischargeTicks, 0, 1, 1);
        float height = Mth.lerp(lerp, 0.640625F, 1.1875F);

        poseStack.pushPose();
        poseStack.translate(0.5F, height, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate() * (1f - lerp)));
        poseStack.mulPose(Axis.XP.rotationDegrees(90F * (1f - lerp)));
        poseStack.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F) * lerp, 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) ticks * lerp));
        poseStack.scale(0.5F,0.5F,0.5F);
        ItemStack stack = blockEntity.getItemHandler().getItem(0);
        minecraft.getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();
    }
}
