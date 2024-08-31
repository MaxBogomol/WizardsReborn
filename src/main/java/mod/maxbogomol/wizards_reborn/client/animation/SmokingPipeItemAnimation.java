package mod.maxbogomol.wizards_reborn.client.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SmokingPipeItemAnimation extends ItemAnimation {

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimRight(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        model.rightArm.xRot = Mth.clamp(model.head.xRot - 1.745329273F, -2.4F, 3.3F);
        model.rightArm.yRot = model.head.yRot - 0.2617994F;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimLeft(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        model.leftArm.xRot = Mth.clamp(model.head.xRot - 1.745329273F, -2.4F, 3.3F);
        model.leftArm.yRot = model.head.yRot + 0.2617994F;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.translate(0, -0.125F + (-1 / 16.0F), 0);
        poseStack.mulPose(Axis.XP.rotationDegrees(-125f));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
        boolean flag = isRightHand(player, hand);
        int i = flag ? 1 : -1;
        poseStack.translate((float)i * 0.56F, -0.52F + (equippedProgress * -0.6f), -0.72F);

        poseStack.translate(0, 0.2f, 0);
        poseStack.translate(-0.3 * i, -0.125F + (-1 / 16.0F), 0);

        poseStack.mulPose(Axis.ZP.rotationDegrees(25 * i));
    }
}
