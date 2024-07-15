package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.common.block.PlacedItemsBlock;
import mod.maxbogomol.wizards_reborn.common.item.IPlacedItem;
import mod.maxbogomol.wizards_reborn.common.tileentity.PlacedItemsTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.Optional;
import java.util.Random;

public class PlacedItemsTileEntityRenderer implements BlockEntityRenderer<PlacedItemsTileEntity> {

    public PlacedItemsTileEntityRenderer() {}

    @Override
    public void render(PlacedItemsTileEntity items, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(items.getBlockPos().asLong());

        int size = items.getInventorySize();
        int rotate = 0;

        Optional<Integer> rotation = items.getBlockState().getOptionalValue(PlacedItemsBlock.ROTATION);
        if (rotation.isPresent()) rotate = rotation.get();

        for (int i = 0; i < size; i++) {
            ms.pushPose();
            ms.translate(0.5F, 0F, 0.5F);
            if (rotate % 4 == 0) {
                ms.mulPose(Axis.YP.rotationDegrees((float) rotate * -22.5f));
            }
            if (size >= 2) {
                if (i == 0) {
                    ms.translate(-0.25F, 0F, 0F);
                }
                if (i == 1) {
                    ms.translate(0.25F, 0F, 0F);
                }
            }
            if (size >= 3) {
                if (i <= 1) {
                    ms.translate(0F, 0F, -0.25F);
                } else {
                    ms.translate(0F, 0F, 0.25F);
                }
                if (size == 4) {
                    if (i == 2) {
                        ms.translate(-0.25F, 0F, 0F);
                    }
                    if (i == 3) {
                        ms.translate(0.25F, 0F, 0F);
                    }
                }
            }
            if (items.getItemHandler().getItem(i).getItem() instanceof IPlacedItem placedItem) {
                placedItem.renderPlacedItem(items.getItemHandler().getItem(i), rotate, random.nextFloat() * 360f, items, partialTicks, ms, buffers, light, overlay);
            } else {
                double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
                double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
                ticksUp = (ticksUp) % 360;

                float rotateTicks = (random.nextFloat() * 360f) + (rotate * -22.5f);
                if (items.isRotate) {
                    rotateTicks = (float) (rotateTicks + ticks);
                }

                ms.pushPose();
                ms.translate(0F, 0.2875F, 0F);
                ms.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
                ms.mulPose(Axis.YP.rotationDegrees(rotateTicks));
                ms.scale(0.5F, 0.5F, 0.5F);
                Minecraft.getInstance().getItemRenderer().renderStatic(items.getItemHandler().getItem(i), ItemDisplayContext.FIXED, light, overlay, ms, buffers, items.getLevel(), 0);
                ms.popPose();
            }
            ms.popPose();
        }

        MultiBufferSource bufferDelayed = WorldRenderHandler.getDelayedRender();
        if (size <= 0 && items.things) {
            float addTick = random.nextFloat() * 360f;
            double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.05f + addTick;
            double tick = (ClientTickHandler.ticksInGame + partialTicks) * 0.7f;
            float r = (float) (Math.sin(ticks) * 127 + 128) / 255f;
            float g = (float) (Math.sin(ticks + Math.PI / 2) * 127 + 128) / 255f;
            float b = (float) (Math.sin(ticks + Math.PI) * 127 + 128) / 255f;
            ms.pushPose();
            ms.translate(0.5F, 0F, 0.5F);
            ms.mulPose(Axis.ZP.rotationDegrees(90f));
            ms.mulPose(Axis.XP.rotationDegrees((float) tick + addTick));
            RenderUtils.beamSided(ms, bufferDelayed, 0.25f, 0.8f, 1f, 0.984f, 0.827f, 0.220f, 0.2f, r, g, b, 0f);
            ms.popPose();
        }
    }
}
