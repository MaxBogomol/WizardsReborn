package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.common.item.ICustomBlockEntityDataItem;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;

public class PlacedItem extends ItemNameBlockItem implements ICustomBlockEntityDataItem, IPlacedItem {

    public PlacedItem(Properties properties) {
        super(WizardsRebornBlocks.PLACED_ITEMS.get(), properties);
    }

    @Override
    public CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag nbt) {
        if (!nbt.contains("Items")) {
            ItemStack newStack = stack.copy();
            NonNullList<ItemStack> ret = NonNullList.withSize(1, ItemStack.EMPTY);
            ret.set(0, newStack);
            ContainerHelper.saveAllItems(nbt, ret);
        }

        return nbt;
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        if (canPlaceBlock(context)) {
            InteractionResult result = super.place(context);
            if (result != InteractionResult.FAIL) return result;
        }

        return InteractionResult.PASS;
    }

    public boolean canPlaceBlock(BlockPlaceContext context) {
        return context.getPlayer() != null && context.getPlayer().isShiftKeyDown();
    }

    @Override
    public void renderPlacedItem(ItemStack stack, int rotation, float rotate, Level level, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        float rotateTicks = rotate + (rotation * -22.5f);
        rotateTicks = (float) (rotateTicks + ticks);

        poseStack.pushPose();
        poseStack.translate(0F, 0.2875F, 0F);
        poseStack.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotateTicks));
        poseStack.scale(0.5F, 0.5F, 0.5F);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.NONE, light, overlay, poseStack, bufferSource, level, 0);
        poseStack.popPose();
    }
}