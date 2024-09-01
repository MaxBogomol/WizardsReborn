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

public class FlaskPotionItem extends AlchemyPotionItem {
    public static final ResourceLocation FLASK_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/models/potion/alchemy_flask.png");

    public FlaskPotionItem(Properties properties) {
        super(properties, 6, WizardsRebornItems.ALCHEMY_FLASK.get());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderPlacedItem(ItemStack stack, int rotation, float rotate, PlacedItemsBlockEntity items, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        ms.pushPose();
        ms.translate(0F, 0.0001F, 0F);
        ms.mulPose(Axis.YP.rotationDegrees((rotation * -22.5f) + rotate));
        ms.mulPose(Axis.XP.rotationDegrees(180f));
        WizardsRebornModels.ALCHEMY_FLASK.renderToBuffer(ms, buffers.getBuffer(RenderType.entityCutoutNoCull(FLASK_TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        ms.mulPose(Axis.XP.rotationDegrees(-180f));
        ms.scale(1.25F, 1.25F, 1.25F);
        ms.translate(-0.125F, 0.015625F, -0.125F);
        AlchemyPotion potion = AlchemyPotionUtil.getPotion(stack);
        if (!AlchemyPotionUtil.isEmpty(potion)) {
            Color color = potion.getColor();
            int colorI = ColorUtil.packColor(255, color.getRed(), color.getGreen(), color.getBlue());
            int uses = AlchemyPotionItem.getUses(stack);
            Fluid fluid = Fluids.WATER;
            FluidStack fluidStack = new FluidStack(fluid, 6 - uses);
            if (potion instanceof FluidAlchemyPotion fluidAlchemyPotion) {
                fluid = fluidAlchemyPotion.getFluid();
                FluidType type = fluid.getFluidType();
                IClientFluidTypeExtensions clientType = IClientFluidTypeExtensions.of(type);
                fluidStack = new FluidStack(fluid, 6 - uses);
                colorI = clientType.getTintColor(fluidStack);
            }
            MultiBufferSource bufferDelayed = LevelRenderHandler.getDelayedRender();
            FluidRenderer.renderScaledCuboid(ms, bufferDelayed, WizardsRebornModels.FLASK_FLUID_CUBE, fluidStack, colorI, 0, 6, light, false);
        }
        ms.popPose();
    }
}