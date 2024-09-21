package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.block.salt.torch.SaltTorchBlockEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class SaltLanternItem extends BlockItem implements IGuiParticleItem {
    public SaltLanternItem(Block blockIn, Properties properties) {
        super(blockIn, properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack poseStack, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        float ticks = ClientTickHandler.getTotal();
        Color color1 = SaltTorchBlockEntity.colorFirst;
        Color color2 = SaltTorchBlockEntity.colorSecond;

        poseStack.pushPose();
        poseStack.translate(x + 8, y + 10, 100);
        poseStack.mulPose(Axis.ZP.rotationDegrees(ticks));
        RenderBuilder sparkleBuilder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/sparkle"))
                .setColor(color1).setAlpha(0.5f)
                .renderCenteredQuad(poseStack, 6f)
                .endBatch();
        poseStack.mulPose(Axis.ZP.rotationDegrees(45));
        sparkleBuilder.renderCenteredQuad(poseStack, 6f)
                .endBatch();
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(x + 8, y + 10, 100);
        poseStack.mulPose(Axis.ZP.rotationDegrees(ticks));
        RenderBuilder wispBuilder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/wisp"))
                .setColor(color2).setAlpha(0.15f)
                .renderCenteredQuad(poseStack, 5f)
                .endBatch();
        poseStack.mulPose(Axis.ZP.rotationDegrees(45));
        wispBuilder.renderCenteredQuad(poseStack, 5f)
                .endBatch();
        poseStack.popPose();
    }
}