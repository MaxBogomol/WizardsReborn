package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalHandler;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.runic_pedestal.RunicPedestalBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.List;
import java.util.Random;

public class RunicPedestalRenderer implements BlockEntityRenderer<RunicPedestalBlockEntity> {

    @Override
    public void render(RunicPedestalBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        if (blockEntity.getBlockState().getValue(BlockStateProperties.LIT)) {
            poseStack.pushPose();
            poseStack.translate(0.5f, 0.40625f, 0.5f);
            RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                    .setColorRaw(0.678f, 0.929f, 0.803f).setAlpha(0.5f)
                    .renderCenteredCube(poseStack, 0.265625f, 0.046875f, 0.265625f);
            poseStack.popPose();
        } else if (!blockEntity.hasRunicPlate()) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 1.3125F, 0.5F);
            poseStack.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
            poseStack.mulPose(Axis.YP.rotationDegrees((float) ticks));
            poseStack.scale(0.5F, 0.5F, 0.5F);
            ItemStack stack = blockEntity.itemHandler.getStackInSlot(0);
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.NONE, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
            poseStack.popPose();
        }

        CrystalRitual ritual = blockEntity.getCrystalRitual();
        if (!CrystalRitualUtil.isEmpty(ritual)) {
            boolean isRender = false;
            if (blockEntity.getLevel().getBlockEntity(blockEntity.getBlockPos().above()) instanceof CrystalBlockEntity crystalTile) {
                if (!ritual.canStartWithCrystal(crystalTile)) isRender = true;
            } else {
                isRender = true;
            }

            if (isRender) {
                ItemStack stack;
                List<CrystalType> types = ritual.getCrystalsList();
                if (!types.isEmpty()) {
                    int i = (int) (ticksAlpha % (20 * types.size()));
                    stack = types.get(i / 20).getCrystal();
                } else {
                    int i = (int) (ticksAlpha % (20 * CrystalHandler.getTypes().size()));
                    stack = CrystalHandler.getTypes().get(i / 20).getCrystal();
                }

                poseStack.pushPose();
                poseStack.translate(0.5F, 0.9F, 0.5F);
                poseStack.mulPose(Axis.YP.rotationDegrees((float) ticks));
                poseStack.mulPose(Axis.ZP.rotationDegrees((float) (50f + ((Math.sin(Math.toRadians(ticks / 2.5f)) * 20f)))));
                poseStack.pushPose();
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
                RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                        .setColor(ritual.getColor())
                        .setFirstAlpha(0.7f)
                        .renderRay(poseStack,  0.025f, 0.75f, 2)
                        .setFirstAlpha(0.4f)
                        .renderRay(poseStack,  0.02f, 0.74f, 1.5f);
                poseStack.popPose();
                poseStack.translate(0.75F, 0F, 0F);
                poseStack.scale(0.25F, 0.25F, 0.25F);
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.NONE, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
                poseStack.popPose();
            }
        }
    }
}
