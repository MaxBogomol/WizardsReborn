package mod.maxbogomol.wizards_reborn.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.MatrixUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;

public class CustomItemRenderer extends ItemRenderer {
    public CustomItemRenderer(Minecraft pMinecraft, TextureManager pTextureManager, ModelManager pModelManager, ItemColors pItemColors, BlockEntityWithoutLevelRenderer pBlockEntityRenderer) {
        super(pMinecraft, pTextureManager, pModelManager, pItemColors, pBlockEntityRenderer);
    }

    public void renderItem(ItemStack pItemStack, ItemDisplayContext pDisplayContext, boolean pLeftHand, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, int pCombinedOverlay, BakedModel pModel) {
        if (!pItemStack.isEmpty()) {
            pPoseStack.pushPose();
            boolean flag = pDisplayContext == ItemDisplayContext.GUI || pDisplayContext == ItemDisplayContext.GROUND || pDisplayContext == ItemDisplayContext.FIXED;
            if (flag) {
                if (pItemStack.is(Items.TRIDENT)) {
                    pModel = this.itemModelShaper.getModelManager().getModel(TRIDENT_MODEL);
                } else if (pItemStack.is(Items.SPYGLASS)) {
                    pModel = this.itemModelShaper.getModelManager().getModel(SPYGLASS_MODEL);
                }
            }

            pModel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(pPoseStack, pModel, pDisplayContext, pLeftHand);
            pPoseStack.translate(-0.5F, -0.5F, -0.5F);
            if (!pModel.isCustomRenderer() && (!pItemStack.is(Items.TRIDENT) || flag)) {
                boolean flag1;
                if (pDisplayContext != ItemDisplayContext.GUI && !pDisplayContext.firstPerson() && pItemStack.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem) pItemStack.getItem()).getBlock();
                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                } else {
                    flag1 = true;
                }
                for (var model : pModel.getRenderPasses(pItemStack, flag1)) {
                    for (var rendertype : model.getRenderTypes(pItemStack, flag1)) {
                        VertexConsumer vertexconsumer;
                        if (hasAnimatedTexture(pItemStack) && pItemStack.hasFoil()) {
                            pPoseStack.pushPose();
                            PoseStack.Pose posestack$pose = pPoseStack.last();
                            if (pDisplayContext == ItemDisplayContext.GUI) {
                                MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.5F);
                            } else if (pDisplayContext.firstPerson()) {
                                MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.75F);
                            }

                            if (flag1) {
                                vertexconsumer = getCompassFoilBufferDirect(pBuffer, rendertype, posestack$pose);
                            } else {
                                vertexconsumer = getCompassFoilBuffer(pBuffer, rendertype, posestack$pose);
                            }

                            pPoseStack.popPose();
                        } else if (flag1) {
                            vertexconsumer = getFoilBufferDirect(pBuffer, rendertype, true, pItemStack.hasFoil());
                        } else {
                            vertexconsumer = getFoilBuffer(pBuffer, rendertype, true, pItemStack.hasFoil());
                        }

                        this.renderModelLists(model, pItemStack, pCombinedLight, pCombinedOverlay, pPoseStack, vertexconsumer);
                    }
                }
            } else {
                net.minecraftforge.client.extensions.common.IClientItemExtensions.of(pItemStack).getCustomRenderer().renderByItem(pItemStack, pDisplayContext, pPoseStack, pBuffer, pCombinedLight, pCombinedOverlay);
            }

            pPoseStack.popPose();
        }
    }

    private static boolean hasAnimatedTexture(ItemStack pStack) {
        return pStack.is(ItemTags.COMPASSES) || pStack.is(Items.CLOCK);
    }
}
