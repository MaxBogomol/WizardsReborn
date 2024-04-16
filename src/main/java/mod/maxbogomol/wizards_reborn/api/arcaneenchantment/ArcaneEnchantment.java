package mod.maxbogomol.wizards_reborn.api.arcaneenchantment;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.awt.*;

public class ArcaneEnchantment {
    public String id;
    public int maxLevel;

    public ArcaneEnchantment(String id, int maxLevel) {
        this.id = id;
        this.maxLevel = maxLevel;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return new Color(255, 255, 255);
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getTranslatedName() {
        return getTranslatedName(id);
    }

    public static String getTranslatedName(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String spellId = id.substring(i + 1);
        return "arcane_enchantment."  + modId + "." + spellId;
    }

    public boolean canEnchantItem(ItemStack stack) {
        return true;
    }

    public boolean checkCompatibility(ArcaneEnchantment arcaneEnchantment) {
        return true;
    }

    public Component getFullname(int level) {
        MutableComponent component = Component.translatable(getTranslatedName());
        if (level != 1 || this.getMaxLevel() != 1) {
            component.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + level));
        }

        return component;
    }

    public void renderParticle(PoseStack pose, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        int i = ArcaneEnchantments.getArcaneEnchantments().indexOf(this);
        int levelEnchantment = ArcaneEnchantmentUtils.getArcaneEnchantment(stack, this);
        if (levelEnchantment > getMaxLevel()) {
            levelEnchantment = getMaxLevel();
        }
        float size = 0.5f + (((float) levelEnchantment / getMaxLevel()) * 0.5f);

        float ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.4f + (i * 35);
        float r = getColor().getRed() / 255f;
        float g = getColor().getGreen() / 255f;
        float b = getColor().getBlue() / 255f;

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(WizardsRebornClient::getGlowingShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        TextureAtlasSprite sparkle = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(WizardsReborn.MOD_ID, "particle/sparkle"));

        pose.pushPose();
        pose.translate(x + 8, y + 8, 100);
        pose.mulPose(Axis.ZP.rotationDegrees(ticks));
        pose.translate(-9 * size, -9 * size, 0);
        RenderUtils.spriteGlowQuad(pose, buffersource, 0, 0, 18f * size, 18f * size, sparkle.getU0(), sparkle.getU1(), sparkle.getV0(), sparkle.getV1(), r, g, b, 0.25f + (0.5f * size));
        buffersource.endBatch();
        pose.popPose();

        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
    }
}
