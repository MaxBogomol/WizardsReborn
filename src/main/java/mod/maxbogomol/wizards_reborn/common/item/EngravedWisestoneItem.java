package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.block.EngravedWisestoneBlock;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
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
    public EngravedWisestoneItem(Block blockIn, Properties properties) {
        super(blockIn, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<net.minecraft.network.chat.Component> list, TooltipFlag flags) {
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

            component.append(Component.literal(String.valueOf(c) + " ").withStyle(Style.EMPTY.withColor(ColorUtils.packColor(255, red, green, blue))));
            i++;
        }

        return component;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack pose, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        if (getBlock() instanceof EngravedWisestoneBlock block && block.hasMonogram()) {
            float ticks = ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick();

            Color color = block.getMonogram().getColor();
            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;

            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(WizardsRebornClient::getGlowingShader);
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

            TextureAtlasSprite sparkle = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(WizardsReborn.MOD_ID, "particle/sparkle"));
            TextureAtlasSprite wisp = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(WizardsReborn.MOD_ID, "particle/wisp"));

            pose.pushPose();
            pose.translate(x + 8, y + 8f, 100);
            pose.mulPose(Axis.ZP.rotationDegrees(ticks));
            RenderUtils.spriteGlowQuadCenter(pose, buffersource, 0, 0, 22f, 22f, wisp.getU0(), wisp.getU1(), wisp.getV0(), wisp.getV1(), r, g, b, 0.5F);
            buffersource.endBatch();
            pose.popPose();

            pose.pushPose();
            pose.translate(x + 8, y + 8f, 100);
            pose.mulPose(Axis.ZP.rotationDegrees(-ticks / 2));
            RenderUtils.spriteGlowQuadCenter(pose, buffersource, 0, 0, 24f, 24f, sparkle.getU0(), sparkle.getU1(), sparkle.getV0(), sparkle.getV1(), r, g, b, 0.5F);
            buffersource.endBatch();
            pose.popPose();

            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        }
    }
}