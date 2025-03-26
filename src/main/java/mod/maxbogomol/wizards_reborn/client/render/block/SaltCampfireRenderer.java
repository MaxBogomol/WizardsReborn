package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.common.block.salt.campfire.SaltCampfireBlock;
import mod.maxbogomol.wizards_reborn.common.block.salt.campfire.SaltCampfireBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SaltCampfireRenderer implements BlockEntityRenderer<SaltCampfireBlockEntity> {

    @Override
    public void render(SaltCampfireBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Direction direction = blockEntity.getBlockState().getValue(SaltCampfireBlock.FACING);
        NonNullList<ItemStack> nonnulllist = blockEntity.getItems();
        int i = (int) blockEntity.getBlockPos().asLong();

        for (int j = 0; j < nonnulllist.size(); ++j) {
            ItemStack itemStack = nonnulllist.get(j);
            if (itemStack != ItemStack.EMPTY && (blockEntity.cookingTime[j] - blockEntity.cookingProgress[j] > 0)) {
                poseStack.pushPose();
                poseStack.translate(0.5F, 0.44921875F, 0.5F);
                Direction direction1 = Direction.from2DDataValue((j + direction.get2DDataValue()) % 4);
                float f = -direction1.toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(f));
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.translate(-0.3125F, -0.3125F, 0.0F);
                poseStack.scale(0.375F, 0.375F, 0.375F);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.FIXED, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), i + j);
                poseStack.popPose();
            }
        }
    }
}
