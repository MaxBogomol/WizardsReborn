package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.common.item.ICustomBlockEntityDataItem;
import mod.maxbogomol.wizards_reborn.common.block.placed_items.PlacedItemsBlockEntity;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PlacedItem extends ItemNameBlockItem implements ICustomBlockEntityDataItem, IPlacedItem {

    public PlacedItem(Properties properties) {
        super(WizardsRebornBlocks.PLACED_ITEMS.get(), properties);
    }

    @Override
    public CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag tileNbt) {
        if (!tileNbt.contains("Items")) {
            ItemStack newStack = stack.copy();
            NonNullList<ItemStack> ret = NonNullList.withSize(1, ItemStack.EMPTY);
            ret.set(0, newStack);
            ContainerHelper.saveAllItems(tileNbt, ret);
        }

        return tileNbt;
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
    @OnlyIn(Dist.CLIENT)
    public void renderPlacedItem(ItemStack stack, int rotation, float rotate, PlacedItemsBlockEntity items, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        float rotateTicks = rotate + (rotation * -22.5f);
        if (items.isRotate) {
            rotateTicks = (float) (rotateTicks + ticks);
        }

        ms.pushPose();
        ms.translate(0F, 0.2875F, 0F);
        ms.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        ms.mulPose(Axis.YP.rotationDegrees(rotateTicks));
        ms.scale(0.5F, 0.5F, 0.5F);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, ms, buffers, items.getLevel(), 0);
        ms.popPose();
    }
}