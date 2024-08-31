package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.render.LevelRenderHandler;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.Crystals;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.runic_pedestal.RunicPedestalBlockEntity;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class RunicPedestalRenderer implements BlockEntityRenderer<RunicPedestalBlockEntity> {

    public RunicPedestalRenderer() {}

    @Override
    public void render(RunicPedestalBlockEntity pedestal, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(pedestal.getBlockPos().asLong());

        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.35f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.3f));

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        MultiBufferSource bufferDelayed = LevelRenderHandler.getDelayedRender();

        if (pedestal.getBlockState().getValue(BlockStateProperties.LIT)) {
            ms.pushPose();
            RenderUtils.litQuadCube(ms, bufferDelayed, 0.225f, 0.359375f, 0.225f, 0.546875f, 0.09375f, 0.546875f, 0.678f, 0.929f, 0.803f, alpha);
            ms.popPose();
        } else if (!pedestal.hasRunicPlate()) {
            ms.pushPose();
            ms.translate(0.5F, 1.3125F, 0.5F);
            ms.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
            ms.mulPose(Axis.YP.rotationDegrees((float) ticks));
            ms.scale(0.5F, 0.5F, 0.5F);
            ItemStack stack = pedestal.itemHandler.getStackInSlot(0);
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, ms, buffers, pedestal.getLevel(), 0);
            ms.popPose();
        }

        CrystalRitual ritual = pedestal.getCrystalRitual();
        if (!CrystalRitualUtil.isEmpty(ritual)) {
            boolean isRender = false;
            if (pedestal.getLevel().getBlockEntity(pedestal.getBlockPos().above()) instanceof CrystalBlockEntity crystalTile) {
                if (!ritual.canStartWithCrystal(crystalTile)) isRender = true;
            } else {
                isRender = true;
            }

            if (isRender) {
                ItemStack stack = ItemStack.EMPTY;
                List<CrystalType> types = ritual.getCrystalsList();
                if (!types.isEmpty()) {
                    int i = (int) (ticksAlpha % (20 * types.size()));
                    stack = types.get(i / 20).getCrystal();
                } else {
                    int i = (int) (ticksAlpha % (20 * Crystals.getTypes().size()));
                    stack = Crystals.getTypes().get(i / 20).getCrystal();
                }

                Color color = ritual.getColor();
                float r = color.getRed() / 255f;
                float g = color.getGreen() / 255f;
                float b = color.getBlue() / 255f;

                ms.pushPose();
                ms.translate(0.5F, 0.9F, 0.5F);
                ms.mulPose(Axis.YP.rotationDegrees((float) ticks));
                ms.mulPose(Axis.ZP.rotationDegrees((float) (50f + ((Math.sin(Math.toRadians(ticks / 2.5f)) * 20f)))));
                RenderUtils.ray(ms, bufferDelayed, 0.025f, 0.75f, 2, r, g, b, 0.7f);
                RenderUtils.ray(ms, bufferDelayed, 0.02f, 0.74f, 1.5f, r, g, b, 0.4f);
                ms.translate(0.75F, 0F, 0F);
                ms.scale(0.25F, 0.25F, 0.25F);
                ms.mulPose(Axis.YP.rotationDegrees(90));
                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, ms, buffers, pedestal.getLevel(), 0);
                ms.popPose();
            }
        }
    }
}
