package mod.maxbogomol.wizards_reborn.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class WizardsRebornItemRenderer extends BlockEntityWithoutLevelRenderer {

    public WizardsRebornItemRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (stack.getItem() == WizardsRebornItems.LIGHT_EMITTER.get()) {
            poseStack.pushPose();
            poseStack.translate(0.5f, 0.5f, 0.5f);
            RenderUtil.renderCustomModel(WizardsRebornModels.LIGHT_EMITTER_PIECE, ItemDisplayContext.FIXED, false, poseStack, buffer, packedLight, packedOverlay);
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.translate(0.5f, 0.8125f, 0.5f);
            RenderBuilder builder = WizardsRebornRenderUtil.renderHoveringLens(poseStack, buffer, packedLight, packedOverlay);
            if (displayContext.firstPerson()) FluffyFurRenderTypes.addCustomItemRenderBuilderFirst(builder);
            if (displayContext == ItemDisplayContext.GUI) FluffyFurRenderTypes.addCustomItemRenderBuilderGui(builder);
            poseStack.popPose();
        }

        if (stack.getItem() == WizardsRebornItems.LIGHT_TRANSFER_LENS.get()) {
            poseStack.pushPose();
            poseStack.translate(0.5f, 0.5f, 0.5f);
            RenderUtil.renderCustomModel(WizardsRebornModels.LIGHT_TRANSFER_LENS_PIECE, ItemDisplayContext.FIXED, false, poseStack, buffer, packedLight, packedOverlay);
            RenderBuilder builder = WizardsRebornRenderUtil.renderHoveringLens(poseStack, buffer, packedLight, packedOverlay);
            if (displayContext.firstPerson()) FluffyFurRenderTypes.addCustomItemRenderBuilderFirst(builder);
            if (displayContext == ItemDisplayContext.GUI) FluffyFurRenderTypes.addCustomItemRenderBuilderGui(builder);
            poseStack.popPose();
        }

        renderLightCasing(WizardsRebornItems.ARCANE_WOOD_LIGHT_CASING.get(), WizardsRebornModels.ARCANE_WOOD_LIGHT_CASING_PIECE, stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
        renderLightCasing(WizardsRebornItems.INNOCENT_WOOD_LIGHT_CASING.get(), WizardsRebornModels.INNOCENT_WOOD_LIGHT_CASING_PIECE, stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
        renderLightCasing(WizardsRebornItems.CORK_BAMBOO_LIGHT_CASING.get(), WizardsRebornModels.CORK_BAMBOO_LIGHT_CASING_PIECE, stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
        renderLightCasing(WizardsRebornItems.WISESTONE_LIGHT_CASING.get(), WizardsRebornModels.WISESTONE_LIGHT_CASING_PIECE, stack, displayContext, poseStack, buffer, packedLight, packedOverlay);

        if (stack.getItem() == WizardsRebornItems.CREATIVE_LIGHT_STORAGE.get()) {
            poseStack.pushPose();
            poseStack.translate(0.5f, 0.5f, 0.5f);
            RenderUtil.renderCustomModel(WizardsRebornModels.CREATIVE_LIGHT_STORAGE_PIECE, ItemDisplayContext.FIXED, false, poseStack, buffer, packedLight, packedOverlay);
            for (Direction direction : Direction.values()) {
                poseStack.pushPose();
                BlockPos pos = new BlockPos(0, 0, 0).relative(direction);
                poseStack.translate(pos.getX() * 0.25f, pos.getY() * 0.25f, pos.getZ() * 0.25f);
                RenderBuilder builder = WizardsRebornRenderUtil.renderHoveringLens(poseStack, buffer, packedLight, packedOverlay);
                if (displayContext.firstPerson()) FluffyFurRenderTypes.addCustomItemRenderBuilderFirst(builder);
                if (displayContext == ItemDisplayContext.GUI) FluffyFurRenderTypes.addCustomItemRenderBuilderGui(builder);
                poseStack.popPose();
            }
            poseStack.popPose();
        }
    }

    public static void renderLightCasing(Item item, ModelResourceLocation model, ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (stack.getItem() == item) {
            poseStack.pushPose();
            poseStack.translate(0.5f, 0.5f, 0.5f);
            RenderUtil.renderCustomModel(model, ItemDisplayContext.FIXED, false, poseStack, buffer, packedLight, packedOverlay);
            for (Direction direction : Direction.values()) {
                poseStack.pushPose();
                BlockPos pos = new BlockPos(0, 0, 0).relative(direction);
                poseStack.translate(pos.getX() * 0.4375f, pos.getY() * 0.4375f, pos.getZ() * 0.4375f);
                RenderBuilder builder = WizardsRebornRenderUtil.renderHoveringLens(poseStack, buffer, packedLight, packedOverlay);
                if (displayContext.firstPerson()) FluffyFurRenderTypes.addCustomItemRenderBuilderFirst(builder);
                if (displayContext == ItemDisplayContext.GUI) FluffyFurRenderTypes.addCustomItemRenderBuilderGui(builder);
                poseStack.popPose();
            }
            poseStack.popPose();
        }
    }
}
