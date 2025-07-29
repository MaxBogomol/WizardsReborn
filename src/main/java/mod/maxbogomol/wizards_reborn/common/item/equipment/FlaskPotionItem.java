package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtil;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.FluidAlchemyPotion;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;

public class FlaskPotionItem extends AlchemyPotionItem {
    public static final ResourceLocation FLASK_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/models/potion/alchemy_flask.png");

    public FlaskPotionItem(Properties properties) {
        super(properties, 6, WizardsRebornItems.ALCHEMY_FLASK);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderPlacedItem(ItemStack stack, int rotation, float rotate, Level level, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        poseStack.pushPose();
        poseStack.translate(0F, 0.001F, 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees((rotation * -22.5f) + rotate));
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));
        WizardsRebornModels.ALCHEMY_FLASK.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(FLASK_TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.mulPose(Axis.XP.rotationDegrees(-180f));
        poseStack.scale(1.25F, 1.25F, 1.25F);
        poseStack.translate(-0.125F, 0.015625F, -0.125F);
        AlchemyPotion potion = AlchemyPotionUtil.getPotion(stack);
        if (!AlchemyPotionUtil.isEmpty(potion)) {
            Color color = potion.getColor();
            int uses = AlchemyPotionItem.getUses(stack);
            Fluid fluid = Fluids.WATER;
            FluidStack fluidStack = new FluidStack(fluid, 1);
            if (potion instanceof FluidAlchemyPotion fluidAlchemyPotion) {
                fluidStack = new FluidStack(fluidAlchemyPotion.getFluid(), 1);
                RenderUtil.renderFluid(poseStack, fluidStack, 0.25f, 0.3125f * (1f - (uses / 6f)), 0.25f, 0.25f, 0.3125f * (1f - (uses / 6f)), 0.25f, false, light);
            } else {
                RenderUtil.renderFluid(poseStack, fluidStack, 0.25f, 0.3125f * (1f - (uses / 6f)), 0.25f, 0.25f, 0.3125f * (1f - (uses / 6f)), 0.25f, color, false, light);
            }
        }
        poseStack.popPose();
    }
}