package mod.maxbogomol.wizards_reborn.client.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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

public class ScytheThrowItemAnimation extends ItemAnimation {
    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimRight(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof Player player) {
            float ticks = player.getUseItem().getUseDuration() - player.getUseItemRemainingTicks() + Minecraft.getInstance().getPartialTick();
            if (ticks > 8) {
                float tick = (ticks - 8) / 5f;
                if (tick > 1) tick = 1f;

                float tickEnd = 0;
                if (ticks > 13) {
                    tickEnd = (ticks - 18) / 2f;
                    if (tickEnd > 1) tickEnd = 1f;
                }

                model.rightArm.xRot = (float) (model.rightArm.xRot - (tick * (Math.PI / 1.2f)));
                model.rightArm.zRot = (float) (model.rightArm.zRot - (tick * (Math.PI / 4f)));

                if (tickEnd > 0) model.rightArm.xRot = (float) (model.rightArm.xRot + (tickEnd * (Math.PI / 1.2f)));
                if (tickEnd > 0) model.rightArm.zRot = (float) (model.rightArm.zRot - (tick * (Math.PI / 8f)));
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimLeft(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof Player player) {
            float ticks = player.getUseItem().getUseDuration() - player.getUseItemRemainingTicks() + Minecraft.getInstance().getPartialTick();
            if (ticks > 8) {
                float tick = (ticks - 8) / 5f;
                if (tick > 1) tick = 1f;

                float tickEnd = 0;
                if (ticks > 13) {
                    tickEnd = (ticks - 18) / 2f;
                    if (tickEnd > 1) tickEnd = 1f;
                }

                model.leftArm.xRot = (float) (model.leftArm.xRot - (tick * (Math.PI / 1.2f)));
                model.leftArm.zRot = (float) (model.leftArm.zRot + (tick * (Math.PI / 4f)));

                if (tickEnd > 0) model.leftArm.xRot = (float) (model.leftArm.xRot + (tickEnd * (Math.PI / 1.2f)));
                if (tickEnd > 0) model.leftArm.zRot = (float) (model.leftArm.zRot + (tick * (Math.PI / 8f)));
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
        boolean flag = hand == InteractionHand.MAIN_HAND;
        int i = flag ? 1 : -1;
        poseStack.translate((float)i * 0.56F, -0.52F + (equippedProgress * -0.6f), -0.72F);

        float ticks = player.getUseItem().getUseDuration() - player.getUseItemRemainingTicks() + Minecraft.getInstance().getPartialTick();
        if (ticks > 8) {
            float tick = (ticks - 8) / 5f;
            if (tick > 1) tick = 1f;

            float tickEnd = 0;
            if (ticks > 13) {
                tickEnd = (ticks - 18) / 2f;
                if (tickEnd > 1) tickEnd = 1f;
            }

            poseStack.translate(0, -0.2f * tick, 0);
            if (tickEnd > 0) poseStack.translate((-0.3f * i) * tickEnd, 0.5f * tickEnd, 0);

            poseStack.mulPose(Axis.XP.rotationDegrees(50 * tick));
            poseStack.mulPose(Axis.ZP.rotationDegrees(-20 * i * tick));
            poseStack.mulPose(Axis.YP.rotationDegrees(-10 * i * tick));

            if (tickEnd > 0) poseStack.mulPose(Axis.XP.rotationDegrees(-150 * tickEnd));
        }
    }
}
