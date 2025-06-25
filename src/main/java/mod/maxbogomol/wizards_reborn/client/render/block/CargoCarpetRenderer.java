package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.block.cargo_carpet.CargoCarpetBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.CargoCarpetItem;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Random;

public class CargoCarpetRenderer implements BlockEntityRenderer<CargoCarpetBlockEntity> {
    public static final ResourceLocation CARPET_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/models/cargo_carpet/white_carpet.png");
    public static final ResourceLocation CARPET_OPEN_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/models/cargo_carpet/white_carpet_open.png");

    @Override
    public void render(CargoCarpetBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        ResourceLocation texture = CARPET_TEXTURE;
        ResourceLocation textureOpen = CARPET_OPEN_TEXTURE;

        ItemStack stack = blockEntity.getItemHandler().getItem(0);
        if (stack.getItem() instanceof CargoCarpetItem carpet) {
            texture = carpet.getCarpetTexture(stack);
            textureOpen = carpet.getCarpetOpenTexture(stack);

            boolean isOpen = blockEntity.getBlockState().getValue(BlockStateProperties.OPEN);

            if (!isOpen) {
                poseStack.pushPose();
                poseStack.translate(0.5F, 0.046875F + 0.01F, 0.5F);
                poseStack.mulPose(Axis.XP.rotationDegrees(180f));
                poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate() + 180f));
                WizardsRebornModels.CARGO_CARPET.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                poseStack.popPose();
            } else {
                poseStack.pushPose();
                poseStack.translate(0.5F, 0.01F, 0.5F);
                poseStack.mulPose(Axis.XP.rotationDegrees(180f));
                poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate() + 180f));
                WizardsRebornModels.CARGO_CARPET_OPEN.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(textureOpen)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                poseStack.popPose();

                SimpleContainer container = CargoCarpetItem.getInventory(stack);
                for (int i = 0; i < 20; i++) {
                    poseStack.pushPose();
                    poseStack.translate(0.5F, 0.03125F + 0.01F + (0.0001F * i), 0.5F);
                    poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate() + 180f));
                    poseStack.translate(((random.nextFloat() - 0.5f) * 2f) * 0.65f, 0.001F, ((random.nextFloat() - 0.5f) * 2f) * 0.65f);
                    poseStack.mulPose(Axis.YP.rotationDegrees(((random.nextFloat() - 0.5f) * 2f) * 360));
                    poseStack.mulPose(Axis.XP.rotationDegrees(90f));
                    poseStack.scale(0.5F, 0.5F, 0.5F);
                    Minecraft.getInstance().getItemRenderer().renderStatic(container.getItem(i), ItemDisplayContext.NONE, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
                    poseStack.popPose();
                }
            }
        }
    }
}
