package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.common.block.engraved_wisestone.EngravedWisestoneBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;

public class EngravedWisestoneItem extends BlockItem implements IGuiParticleItem {

    public EngravedWisestoneItem(Block block, Properties properties) {
        super(block, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<net.minecraft.network.chat.Component> list, TooltipFlag flags) {
        if (getBlock() instanceof EngravedWisestoneBlock block && block.hasMonogram()) {
            Monogram monogram = block.getMonogram();
            list.add(getMonogramComponent(monogram));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Component getHighlightTip(ItemStack stack, Component displayName) {
        if (getBlock() instanceof EngravedWisestoneBlock block && block.hasMonogram()) {
            Monogram monogram = block.getMonogram();
            return displayName.copy().append(" (")
                    .append(getMonogramComponent(monogram))
                    .append(")");
        }
        return super.getHighlightTip(stack, displayName);
    }

    @OnlyIn(Dist.CLIENT)
    public static Component getMonogramComponent(Monogram monogram) {
        Color color = monogram.getColor();
        float ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 1.2f;
        MutableComponent component = Component.empty();

        int i = 0;
        String string = Component.translatable(monogram.getTranslatedName()).getString();
        for (char c : string.toCharArray()) {
            float ii = (float) Math.abs(Math.sin(Math.toRadians(-ticks + i * 10)));
            int red = (int) Mth.lerp(ii, Color.BLACK.getRed(), color.getRed());
            int green = (int) Mth.lerp(ii, Color.BLACK.getGreen(), color.getGreen());
            int blue = (int) Mth.lerp(ii, Color.BLACK.getBlue(), color.getBlue());

            component.append(Component.literal(String.valueOf(c) + " ").withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, red, green, blue))));
            i++;
        }

        return component;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack poseStack, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        if (getBlock() instanceof EngravedWisestoneBlock block && block.hasMonogram()) {
            float ticks = ClientTickHandler.getTotal();
            Color color = block.getMonogram().getColor();

            poseStack.pushPose();
            poseStack.translate(x + 8, y + 8f, 100);
            poseStack.mulPose(Axis.ZP.rotationDegrees(ticks));
            RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                    .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/wisp"))
                    .setColor(color).setAlpha(0.5f)
                    .renderCenteredQuad(poseStack, 11f)
                    .endBatch();
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(x + 8, y + 8f, 100);
            poseStack.mulPose(Axis.ZP.rotationDegrees(-ticks / 2));
            RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                    .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/sparkle"))
                    .setColor(color).setAlpha(0.5f)
                    .renderCenteredQuad(poseStack, 12f)
                    .endBatch();
            poseStack.popPose();
        }
    }
}