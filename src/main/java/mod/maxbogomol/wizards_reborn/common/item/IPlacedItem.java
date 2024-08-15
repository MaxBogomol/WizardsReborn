package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.common.block.placed_items.PlacedItemsBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IPlacedItem {
    @OnlyIn(Dist.CLIENT)
    void renderPlacedItem(ItemStack stack, int rotation, float rotate, PlacedItemsBlockEntity items, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay);
}
