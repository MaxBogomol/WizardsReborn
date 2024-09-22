package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.common.block.placed_items.PlacedItemsBlock;
import mod.maxbogomol.wizards_reborn.common.block.placed_items.PlacedItemsBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.IPlacedItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.Optional;
import java.util.Random;

public class PlacedItemsRenderer implements BlockEntityRenderer<PlacedItemsBlockEntity> {

    @Override
    public void render(PlacedItemsBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        int size = blockEntity.getInventorySize();
        int rotate = 0;

        Optional<Integer> rotation = blockEntity.getBlockState().getOptionalValue(PlacedItemsBlock.ROTATION);
        if (rotation.isPresent()) rotate = rotation.get();

        for (int i = 0; i < size; i++) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 0F, 0.5F);
            if (rotate % 4 == 0) {
                poseStack.mulPose(Axis.YP.rotationDegrees((float) rotate * -22.5f));
            }
            if (size >= 2) {
                if (i == 0) {
                    poseStack.translate(-0.25F, 0F, 0F);
                }
                if (i == 1) {
                    poseStack.translate(0.25F, 0F, 0F);
                }
            }
            if (size >= 3) {
                if (i <= 1) {
                    poseStack.translate(0F, 0F, -0.25F);
                } else {
                    poseStack.translate(0F, 0F, 0.25F);
                }
                if (size == 4) {
                    if (i == 2) {
                        poseStack.translate(-0.25F, 0F, 0F);
                    }
                    if (i == 3) {
                        poseStack.translate(0.25F, 0F, 0F);
                    }
                }
            }
            if (blockEntity.getItemHandler().getItem(i).getItem() instanceof IPlacedItem placedItem) {
                placedItem.renderPlacedItem(blockEntity.getItemHandler().getItem(i), rotate, random.nextFloat() * 360f, blockEntity, partialTicks, poseStack, bufferSource, light, overlay);
            } else {
                double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
                double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
                ticksUp = (ticksUp) % 360;

                float rotateTicks = (random.nextFloat() * 360f) + (rotate * -22.5f);
                if (blockEntity.isRotate) {
                    rotateTicks = (float) (rotateTicks + ticks);
                }

                poseStack.pushPose();
                poseStack.translate(0F, 0.2875F, 0F);
                poseStack.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
                poseStack.mulPose(Axis.YP.rotationDegrees(rotateTicks));
                poseStack.scale(0.5F, 0.5F, 0.5F);
                Minecraft.getInstance().getItemRenderer().renderStatic(blockEntity.getItemHandler().getItem(i), ItemDisplayContext.FIXED, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
                poseStack.popPose();
            }
            poseStack.popPose();
        }

        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();
        if (size <= 0 && blockEntity.things) {
            float addTick = random.nextFloat() * 360f;
            double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.05f + addTick;
            double tick = (ClientTickHandler.ticksInGame + partialTicks) * 0.7f;
            float r = (float) (Math.sin(ticks) * 127 + 128) / 255f;
            float g = (float) (Math.sin(ticks + Math.PI / 2) * 127 + 128) / 255f;
            float b = (float) (Math.sin(ticks + Math.PI) * 127 + 128) / 255f;
            poseStack.pushPose();
            poseStack.translate(0.5F, 0F, 0.5F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90f));
            poseStack.mulPose(Axis.XP.rotationDegrees((float) tick + addTick));
            //WizardsRebornRenderUtil.beamSided(poseStack, bufferDelayed, 0.25f, 0.8f, 1f, 0.984f, 0.827f, 0.220f, 0.2f, r, g, b, 0f);
            poseStack.popPose();
        }
    }
}
