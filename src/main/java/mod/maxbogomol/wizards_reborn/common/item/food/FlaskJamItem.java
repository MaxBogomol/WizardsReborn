package mod.maxbogomol.wizards_reborn.common.item.food;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.model.block.JamFlaskModel;
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

public class FlaskJamItem extends JamItem {
    public static final ResourceLocation FLASK_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/models/potion/alchemy_flask.png");

    public FlaskJamItem(Properties properties) {
        super(properties, 6, WizardsRebornItems.ALCHEMY_FLASK);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderPlacedItem(ItemStack stack, int rotation, float rotate, Level level, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        poseStack.pushPose();
        poseStack.translate(0F, 0.001F, 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees((rotation * -22.5f) + rotate));
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));
        WizardsRebornModels.ALCHEMY_FLASK.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(FLASK_TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.translate(0F, -0.01F, 0F);
        poseStack.scale(0.9f, 0.9f, 0.9f);
        int uses = AlchemyPotionItem.getUses(stack);
        JamFlaskModel model = WizardsRebornModels.JAM_FLASK_0;
        if (uses == 1) model = WizardsRebornModels.JAM_FLASK_1;
        if (uses == 2) model = WizardsRebornModels.JAM_FLASK_2;
        if (uses == 3) model = WizardsRebornModels.JAM_FLASK_3;
        if (uses == 4) model = WizardsRebornModels.JAM_FLASK_4;
        if (uses == 5) model = WizardsRebornModels.JAM_FLASK_5;
        model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(getModelTexture(stack))), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.popPose();
    }
}
