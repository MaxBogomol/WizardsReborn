package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.common.block.SaltCampfireBlock;
import mod.maxbogomol.wizards_reborn.common.tileentity.SaltCampfireTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SaltCampfireTileEntityRenderer implements BlockEntityRenderer<SaltCampfireTileEntity> {

    public SaltCampfireTileEntityRenderer() {}

    @Override
    public void render(SaltCampfireTileEntity campfire, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Direction direction = campfire.getBlockState().getValue(SaltCampfireBlock.FACING);
        NonNullList<ItemStack> nonnulllist = campfire.getItems();
        int i = (int)campfire.getBlockPos().asLong();

        for(int j = 0; j < nonnulllist.size(); ++j) {
            ItemStack itemstack = nonnulllist.get(j);
            if (itemstack != ItemStack.EMPTY && (campfire.cookingTime[j] - campfire.cookingProgress[j] > 0)) {
                ms.pushPose();
                ms.translate(0.5F, 0.44921875F, 0.5F);
                Direction direction1 = Direction.from2DDataValue((j + direction.get2DDataValue()) % 4);
                float f = -direction1.toYRot();
                ms.mulPose(Axis.YP.rotationDegrees(f));
                ms.mulPose(Axis.XP.rotationDegrees(90.0F));
                ms.translate(-0.3125F, -0.3125F, 0.0F);
                ms.scale(0.375F, 0.375F, 0.375F);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.FIXED, light, overlay, ms, buffers, campfire.getLevel(), i + j);
                ms.popPose();
            }
        }
    }
}
