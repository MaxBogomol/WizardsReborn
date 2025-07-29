package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.block.placed_items.PlacedItemsBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BunchOfThingsItem extends PlacedItem {

    public BunchOfThingsItem(Properties properties) {
        super(properties);
    }

    @Override
    public CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag nbt) {
        if (!nbt.contains("things")) {
            nbt.putBoolean("things", true);
        }

        return nbt;
    }

    @Override
    public boolean canPlaceBlock(BlockPlaceContext context) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderPlacedItem(ItemStack stack, int rotation, float rotate, Level level, PlacedItemsBlockEntity items, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        float rotateTicks = rotate + (rotation * -22.5f);
        if (items.isRotate) {
            rotateTicks = (float) (rotateTicks + ticks);
        }

        poseStack.pushPose();
        poseStack.translate(0F, 0.2875F, 0F);
        poseStack.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotateTicks));
        poseStack.scale(0.5F, 0.5F, 0.5F);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.NONE, light, overlay, poseStack, bufferSource, level, 0);
        poseStack.popPose();
    }
}