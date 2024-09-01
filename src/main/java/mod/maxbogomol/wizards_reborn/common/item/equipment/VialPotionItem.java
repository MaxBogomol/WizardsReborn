package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.render.LevelRenderHandler;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtil;
import mod.maxbogomol.wizards_reborn.client.render.fluid.FluidRenderer;
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
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;

import java.awt.*;

public class VialPotionItem extends AlchemyPotionItem {
    public static final ResourceLocation VIAL_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/models/potion/alchemy_vial.png");

    public VialPotionItem(Properties properties) {
        super(properties, 3, WizardsRebornItems.ALCHEMY_VIAL.get());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderPlacedItem(ItemStack stack, int rotation, float rotate, PlacedItemsBlockEntity items, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        ms.pushPose();
        ms.translate(0F, 0.0001F, 0F);
        ms.mulPose(Axis.YP.rotationDegrees((rotation * -22.5f) + rotate));
        ms.mulPose(Axis.XP.rotationDegrees(180f));
        WizardsRebornModels.ALCHEMY_VIAL.renderToBuffer(ms, buffers.getBuffer(RenderType.entityCutoutNoCull(VIAL_TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        ms.mulPose(Axis.XP.rotationDegrees(-180f));

        AlchemyPotion potion = AlchemyPotionUtil.getPotion(stack);
        if (!AlchemyPotionUtil.isEmpty(potion)) {
            ms.pushPose();
            ms.scale(1.25F, 1.25F, 1.25F);
            ms.translate(-0.125F, 0.0078125F, -0.125F);
            int uses = AlchemyPotionItem.getUses(stack);
            Color color = potion.getColor();
            int colorI = ColorUtil.packColor(255, color.getRed(), color.getGreen(), color.getBlue());
            int usesI = 3 - uses;
            if (uses == 0) usesI = 2;
            Fluid fluid = Fluids.WATER;
            FluidStack fluidStack = new FluidStack(fluid, usesI);
            if (potion instanceof FluidAlchemyPotion fluidAlchemyPotion) {
                fluid = fluidAlchemyPotion.getFluid();
                FluidType type = fluid.getFluidType();
                IClientFluidTypeExtensions clientType = IClientFluidTypeExtensions.of(type);
                fluidStack = new FluidStack(fluid, usesI);
                colorI = clientType.getTintColor(fluidStack);
            }
            MultiBufferSource bufferDelayed = LevelRenderHandler.getDelayedRender();
            FluidRenderer.renderScaledCuboid(ms, bufferDelayed, WizardsRebornModels.VIAL_FLUID_CUBE_0, fluidStack, colorI, 0, 2, light, false);
            ms.popPose();

            if (uses == 0) {
                ms.translate(0, 0.25, 0);
                ms.scale(1.5F, 1.5F, 1.5F);
                ms.translate(-0.0625F, -0.0078125F, -0.0625F);
                fluidStack = new FluidStack(fluidStack.getFluid(), 1);
                FluidRenderer.renderScaledCuboid(ms, bufferDelayed, WizardsRebornModels.VIAL_FLUID_CUBE_1, fluidStack, colorI, 0, 1, light, false);
            }
        }

        ms.popPose();
    }
}