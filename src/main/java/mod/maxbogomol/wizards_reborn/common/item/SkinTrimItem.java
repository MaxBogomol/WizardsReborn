package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;

import java.awt.*;
import java.util.List;

public class SkinTrimItem extends Item implements IGuiParticleItem {
    public ItemSkin skin;

    public SkinTrimItem(Properties properties, ItemSkin skin) {
        super(properties);
        this.skin = skin;
    }

    public ItemSkin getSkin() {
        return skin;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        list.add(getSkin().getSkinComponent());
        list.add(Component.empty());
        list.addAll(getSkin().getApplyingItemsComponents(Screen.hasShiftDown()));
    }

    @Override
    public Component getHighlightTip(ItemStack stack, Component displayName) {
        return displayName.copy().append(Component.literal(" (")).append(getSkin().getSkinName()).append(Component.literal(")"));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack poseStack, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        float ticks = ClientTickHandler.getTotal();
        Color color = getSkin().getColor();

        poseStack.pushPose();
        poseStack.translate(x + 8, y + 8, 100);
        poseStack.mulPose(Axis.ZP.rotationDegrees(ticks));
        RenderBuilder sparkleBuilder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/sparkle"))
                .setColor(color).setAlpha(0.5f)
                .renderCenteredQuad(poseStack, 10f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(45f));
        sparkleBuilder.renderCenteredQuad(poseStack, 10f);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(x + 8, y + 8, 100);
        poseStack.mulPose(Axis.ZP.rotationDegrees(-ticks));
        sparkleBuilder.renderCenteredQuad(poseStack, 9f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(45f));
        sparkleBuilder.renderCenteredQuad(poseStack, 9f)
                .endBatch();
        poseStack.popPose();
    }

    @OnlyIn(Dist.CLIENT)
    public static void onTooltipRenderColor(RenderTooltipEvent.Color event) {
        if (event.getItemStack().getItem() instanceof SkinTrimItem item) {
            Color color = item.skin.getColor();
            int packColorStart = ColorUtil.packColor(255 / 10, color.getRed(), color.getGreen(), color.getBlue());
            int packColorEnd = ColorUtil.packColor(color);
            event.setBorderStart(packColorStart);
            event.setBorderEnd(packColorEnd);
        }
    }
}
