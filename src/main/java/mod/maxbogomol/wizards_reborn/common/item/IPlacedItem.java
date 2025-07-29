package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.common.block.placed_items.PlacedItemsBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IPlacedItem {
    @OnlyIn(Dist.CLIENT)
    default void renderPlacedItem(ItemStack stack, int rotation, float rotate, Level level, PlacedItemsBlockEntity items, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        renderPlacedItem(stack, rotation, rotate, level, partialTicks, poseStack, bufferSource, light, overlay);
    }

    @OnlyIn(Dist.CLIENT)
    void renderPlacedItem(ItemStack stack, int rotation, float rotate, Level level, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay);
}
