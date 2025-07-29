package mod.maxbogomol.wizards_reborn.common.item.food;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.model.block.JamVialModel;
import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyPotionItem;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VialJamItem extends JamItem {
    public static final ResourceLocation VIAL_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/models/potion/alchemy_vial.png");

    public VialJamItem(Properties properties) {
        super(properties, 3, WizardsRebornItems.ALCHEMY_VIAL);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderPlacedItem(ItemStack stack, int rotation, float rotate, Level level, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        poseStack.pushPose();
        poseStack.translate(0F, 0.001F, 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees((rotation * -22.5f) + rotate));
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));
        WizardsRebornModels.ALCHEMY_VIAL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(VIAL_TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.translate(0F, -0.01F, 0F);
        poseStack.scale(0.9f, 0.9f, 0.9f);
        int uses = AlchemyPotionItem.getUses(stack);
        JamVialModel model = WizardsRebornModels.JAM_VIAL_0;
        if (uses == 1) model = WizardsRebornModels.JAM_VIAL_1;
        if (uses == 2) model = WizardsRebornModels.JAM_VIAL_2;
        model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(getModelTexture(stack))), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.popPose();
    }
}
