package mod.maxbogomol.wizards_reborn.client.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StrikeSpellItemAnimation extends ItemAnimation {
    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimRight(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        model.rightArm.yRot = -0.1F + model.head.yRot - 0.1F;
        model.rightArm.xRot = (-(float) Math.PI / 2.2F) + model.head.xRot;

        if (entity instanceof Player player) {
            float ticks = (player.getTicksUsingItem() + Minecraft.getInstance().getPartialTick()) * 20;
            model.rightArm.yRot += (float) Math.toRadians(Math.sin(Math.toRadians(ticks)) * 10);
            model.rightArm.xRot += (float) Math.toRadians(Math.cos(Math.toRadians(ticks)) * 10);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimLeft(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        model.leftArm.yRot = 0.1F + model.head.yRot + 0.1F;
        model.leftArm.xRot = (-(float) Math.PI / 2.2F) + model.head.xRot;

        if (entity instanceof Player player) {
            float ticks = (player.getTicksUsingItem() + Minecraft.getInstance().getPartialTick()) * 20;
            model.leftArm.yRot -= (float) Math.toRadians(Math.sin(Math.toRadians(ticks)) * 10);
            model.leftArm.xRot -= (float) Math.toRadians(Math.cos(Math.toRadians(ticks)) * 10);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.translate(0, -0.125F + (-1 / 16.0F), 0);
        poseStack.mulPose(Axis.XP.rotationDegrees(-85f));
        poseStack.mulPose(Axis.YP.rotationDegrees(15f));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
        boolean flag = isRightHand(player, hand);
        int i = flag ? 1 : -1;

        float ticks = (player.getTicksUsingItem() + partialTicks) * 20;

        poseStack.translate((float)i * 0.56F, -0.52F + (equippedProgress * -0.6f), -0.72F);

        poseStack.translate(0, 0.2f, 0);
        poseStack.translate(-0.1 * i, -0.125F + (-1 / 16.0F), 0);

        poseStack.mulPose(Axis.XP.rotationDegrees((float) Math.sin(Math.toRadians(ticks)) * 15));
        poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(25 * i));
        poseStack.mulPose(Axis.YP.rotationDegrees(15f * i));
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) Math.cos(Math.toRadians(ticks)) * 15));
    }
}
