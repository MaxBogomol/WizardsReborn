package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.common.block.placed_items.PlacedItemsBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VialItem extends AlchemyBottleItem {
    public static final ResourceLocation VIAL_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/models/potion/alchemy_vial.png");

    public VialItem(Properties properties) {
        super(properties, 3);
    }

    @Override
    public ItemStack getPotionItem() {
        return new ItemStack(WizardsReborn.ALCHEMY_VIAL_POTION.get());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderPlacedItem(ItemStack stack, int rotation, float rotate, PlacedItemsBlockEntity items, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        ms.pushPose();
        ms.translate(0F, 0.0001F, 0F);
        ms.mulPose(Axis.YP.rotationDegrees((rotation * -22.5f) + rotate));
        ms.mulPose(Axis.XP.rotationDegrees(180f));
        WizardsRebornClient.ALCHEMY_VIAL_MODEL.renderToBuffer(ms, buffers.getBuffer(RenderType.entityCutoutNoCull(VIAL_TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        ms.popPose();
    }
}