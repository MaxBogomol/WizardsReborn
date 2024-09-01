package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.common.block.placed_items.PlacedItemsBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FlaskItem extends AlchemyBottleItem {
    public static final ResourceLocation FLASK_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/models/potion/alchemy_flask.png");

    public FlaskItem(Properties properties) {
        super(properties, 6);
    }

    @Override
    public ItemStack getPotionItem() {
        return new ItemStack(WizardsRebornItems.ALCHEMY_FLASK_POTION.get());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderPlacedItem(ItemStack stack, int rotation, float rotate, PlacedItemsBlockEntity items, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        ms.pushPose();
        ms.translate(0F, 0.0001F, 0F);
        ms.mulPose(Axis.YP.rotationDegrees((rotation * -22.5f) + rotate));
        ms.mulPose(Axis.XP.rotationDegrees(180f));
        WizardsRebornClient.ALCHEMY_FLASK_MODEL.renderToBuffer(ms, buffers.getBuffer(RenderType.entityCutoutNoCull(FLASK_TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        ms.popPose();
    }
}