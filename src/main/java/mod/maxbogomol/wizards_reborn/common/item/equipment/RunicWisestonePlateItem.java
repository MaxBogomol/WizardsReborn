package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtils;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRituals;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.item.IGuiParticleItem;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;

public class RunicWisestonePlateItem extends Item implements IGuiParticleItem {

    public RunicWisestonePlateItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        CrystalRitualUtils.setCrystalRitual(stack, WizardsReborn.EMPTY_CRYSTAL_RITUAL);
        return stack;
    }

    @OnlyIn(Dist.CLIENT)
    public static class ColorHandler implements ItemColor {
        @Override
        public int getColor(ItemStack stack, int tintIndex) {
            if (tintIndex == 1) {
                CrystalRitual ritual = CrystalRitualUtils.getCrystalRitual(stack);
                if (!CrystalRitualUtils.isEmpty(ritual)) {
                    Color color = ritual.getColor();

                    int i = CrystalRituals.getCrystalRituals().indexOf(ritual);

                    float a = (float) Math.abs(Math.sin((ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick() + (i * 10)) / 15));

                    int r = Mth.lerpInt(a, 173, color.getRed());
                    int g = Mth.lerpInt(a, 237, color.getGreen());
                    int b = Mth.lerpInt(a, 205, color.getBlue());

                    return ColorUtils.packColor(255, r, g, b);
                }
            }
            return 0xFFFFFF;
        }
    }

    @Override
    public Component getHighlightTip(ItemStack stack, Component displayName) {
        CrystalRitual ritual = CrystalRitualUtils.getCrystalRitual(stack);
        if (!CrystalRitualUtils.isEmpty(ritual)) {
            return displayName.copy().append(Component.literal(" (")).append(getRitualName(ritual)).append(Component.literal(")"));
        }

        return displayName;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        CrystalRitual ritual = CrystalRitualUtils.getCrystalRitual(stack);
        if (!CrystalRitualUtils.isEmpty(ritual)) {
            list.add(getRitualName(ritual));
        }
    }

    public static Component getRitualName(CrystalRitual ritual) {
        Color color = ritual.getColor();

        return Component.translatable(ritual.getTranslatedName()).withStyle(Style.EMPTY.withColor(ColorUtils.packColor(255, color.getRed(), color.getGreen(), color.getBlue())));
    }

    @Override
    public void renderParticle(PoseStack pose, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        CrystalRitual ritual = CrystalRitualUtils.getCrystalRitual(stack);
        if (!CrystalRitualUtils.isEmpty(ritual)) {
            Color color = ritual.getColor();
            int i = CrystalRituals.getCrystalRituals().indexOf(ritual);

            float a = (float) Math.abs(Math.sin((ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick() + (i * 10)) / 15));
            float ticks = ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick() * 0.2f + (i * 10);
            float alpha = (float) (0.35f + Math.abs(Math.sin(Math.toRadians(ticks)) * 0.25f));
            float r = Mth.lerpInt(a, 173, color.getRed()) / 255f;
            float g = Mth.lerpInt(a, 237, color.getGreen()) / 255f;
            float b = Mth.lerpInt(a, 205, color.getBlue()) / 255f;

            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(WizardsRebornClient::getGlowingShader);
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

            TextureAtlasSprite sparkle = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(WizardsReborn.MOD_ID, "particle/sparkle"));
            TextureAtlasSprite wisp = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(WizardsReborn.MOD_ID, "particle/wisp"));

            pose.pushPose();
            pose.translate(x + 8, y + 8, 100);
            pose.mulPose(Axis.ZP.rotationDegrees(-ticks * 0.55f));
            pose.translate(-8, -8, 0);
            RenderUtils.spriteGlowQuad(pose, buffersource, 0, 0, 16f, 16f, wisp.getU0(), wisp.getU1(), wisp.getV0(), wisp.getV1(), r, g, b, alpha);
            buffersource.endBatch();
            pose.popPose();

            pose.pushPose();
            pose.translate(x + 8, y + 8, 100);
            pose.mulPose(Axis.ZP.rotationDegrees(ticks));
            pose.translate(-9, -9, 0);
            RenderUtils.spriteGlowQuad(pose, buffersource, 0, 0, 18f, 18f, sparkle.getU0(), sparkle.getU1(), sparkle.getV0(), sparkle.getV1(), r, g, b, alpha);
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