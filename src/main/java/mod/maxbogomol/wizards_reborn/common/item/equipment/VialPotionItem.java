package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtil;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.FluidAlchemyPotion;
import mod.maxbogomol.wizards_reborn.common.block.placed_items.PlacedItemsBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;

public class VialPotionItem extends AlchemyPotionItem {
    public static final ResourceLocation VIAL_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/models/potion/alchemy_vial.png");

    public VialPotionItem(Properties properties) {
        super(properties, 3, WizardsRebornItems.ALCHEMY_VIAL);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderPlacedItem(ItemStack stack, int rotation, float rotate, PlacedItemsBlockEntity items, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        poseStack.pushPose();
        poseStack.translate(0F, 0.001F, 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees((rotation * -22.5f) + rotate));
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));
        WizardsRebornModels.ALCHEMY_VIAL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(VIAL_TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.mulPose(Axis.XP.rotationDegrees(-180f));

        AlchemyPotion potion = AlchemyPotionUtil.getPotion(stack);
        if (!AlchemyPotionUtil.isEmpty(potion)) {
            poseStack.pushPose();
            poseStack.scale(1.25F, 1.25F, 1.25F);
            poseStack.translate(-0.125F, 0.0078125F, -0.125F);
            int uses = AlchemyPotionItem.getUses(stack);
            Color color = potion.getColor();
            int usesI = 3 - uses;
            if (uses == 0) usesI = 0;
            if (uses == 1) usesI = 0;
            Fluid fluid = Fluids.WATER;
            FluidStack fluidStack = new FluidStack(fluid, 1);
            if (potion instanceof FluidAlchemyPotion fluidAlchemyPotion) {
                fluidStack = new FluidStack(fluidAlchemyPotion.getFluid(), 1);
                RenderUtil.renderFluid(poseStack, fluidStack, 0.25f, 0.1875f * (1f - (usesI / 2f)), 0.25f, 0.25f, 0.1875f * (1f - (usesI / 2f)), 0.25f, false, light);
                poseStack.popPose();
                if (uses == 0) {
                    poseStack.translate(0, 0.25, 0);
                    poseStack.scale(1.5F, 1.5F, 1.5F);
                    poseStack.translate(-0.0625F, -0.0078125F, -0.0625F);
                    RenderUtil.renderFluid(poseStack, fluidStack, 0.125f, 0.125f, false, light);
                }
            } else {
                RenderUtil.renderFluid(poseStack, fluidStack, 0.25f, 0.1875f * (1f - (usesI / 2f)), 0.25f, 0.25f, 0.1875f * (1f - (usesI / 2f)), 0.25f, color, false, light);
                poseStack.popPose();
                if (uses == 0) {
                    poseStack.translate(0, 0.25, 0);
                    poseStack.scale(1.5F, 1.5F, 1.5F);
                    poseStack.translate(-0.0625F, -0.0078125F, -0.0625F);
                    RenderUtil.renderFluid(poseStack, fluidStack, 0.125f, 0.125f, color, false, light);
                }
            }
        }

        poseStack.popPose();
    }
}