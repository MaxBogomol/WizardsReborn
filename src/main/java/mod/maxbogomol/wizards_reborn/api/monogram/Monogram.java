package mod.maxbogomol.wizards_reborn.api.monogram;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurShaders;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Monogram {
    public String id;
    public Color color;

    public Monogram(String id) {
        this.id = id;
        this.color = new Color(255, 255, 255);
    }

    public Monogram(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public ResourceLocation getIcon() {
        return getIcon(id);
    }

    public Color getColor() {
        return color;
    }

    public String getTranslatedName() {
        return getTranslatedName(id);
    }

    public String getTranslatedLoreName() {
        return getTranslatedLoreName(id);
    }

    public ResourceLocation getTexture() {
        return getTexture(id);
    }

    public static ResourceLocation getIcon(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String monogramId = id.substring(i + 1);
        return new ResourceLocation(modId, "textures/monogram/" + monogramId + ".png");
    }

    public static String getTranslatedName(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String monogramId = id.substring(i + 1);
        return "monogram."  + modId + "." + monogramId;
    }

    public static ResourceLocation getTexture(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String monogramId = id.substring(i + 1);
        return new ResourceLocation(modId, "monogram/" + monogramId);
    }

    public static String getTranslatedLoreName(String id) {
        return getTranslatedName(id) + ".lore";
    }

    @OnlyIn(Dist.CLIENT)
    public void renderArcanemiconIcon(ArcanemiconGui book, GuiGraphics gui, int x, int y) {
        Random random = new Random(getId().length());

        float r = 1f;
        float g = 1f;
        float b = 1f;

        if (ClientConfig.MONOGRAM_GLOW_COLOR.get()) {
            r = color.getRed() / 255f;
            g = color.getGreen() / 255f;
            b = color.getBlue() / 255f;
        }

        if (ClientConfig.MONOGRAM_RAYS.get()) {
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(FluffyFurShaders::getGlowing);

            gui.pose().pushPose();
            gui.pose().translate(0, 0, 100);
            RenderSystem.setShaderColor(r, g, b, 0.35F);
            RenderUtils.dragon(gui.pose(), buffersource, x + 8, y + 8, 0, 7.5f, Minecraft.getInstance().getPartialTick(), r, g, b, getId().length());
            buffersource.endBatch();
            gui.pose().popPose();

            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
        }

        if (ClientConfig.MONOGRAM_GLOW.get()) {
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            RenderSystem.setShaderColor(r, g, b, 0.15F);

            for (int i = 0; i < 5; i++) {
                RenderSystem.setShaderColor(r, g, b, (float) (0.15F + (random.nextDouble() / 10)));
                double dst = (360 * random.nextDouble()) + (ClientTickHandler.ticksInGame + Minecraft.getInstance().getFrameTime()) / 8;
                double dstX = (360 * random.nextDouble()) + (ClientTickHandler.ticksInGame + Minecraft.getInstance().getFrameTime()) / 16;
                double dstY = (360 * random.nextDouble()) + (ClientTickHandler.ticksInGame + Minecraft.getInstance().getFrameTime()) / 16;
                int X = (int) (Math.cos(dst) * (4 * Math.sin(Math.toRadians(dstX))));
                int Y = (int) (Math.sin(dst) * (4 * Math.sin(Math.toRadians(dstY))));

                gui.blit(getIcon(), x + X, y + Y, 0, 0, 16, 16, 16, 16);
            }
        }

        RenderSystem.disableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        if (ClientConfig.MONOGRAM_COLOR.get()) {
            r = color.getRed() / 255f;
            g = color.getGreen() / 255f;
            b = color.getBlue() / 255f;
            RenderSystem.setShaderColor(r, g, b, 1F);
        }
        gui.blit(getIcon(), x, y, 0, 0, 16, 16, 16, 16);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
    }

    @OnlyIn(Dist.CLIENT)
    public void renderArcanemiconMiniIcon(ArcanemiconGui book, GuiGraphics gui, int x, int y) {
        Random random = new Random(getId().length());

        float r = 1f;
        float g = 1f;
        float b = 1f;

        if (ClientConfig.MONOGRAM_GLOW_COLOR.get()) {
            r = color.getRed() / 255f;
            g = color.getGreen() / 255f;
            b = color.getBlue() / 255f;
        }

        if (ClientConfig.MONOGRAM_GLOW.get()) {
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            RenderSystem.setShaderColor(r, g, b, 0.15F);

            for (int i = 0; i < 5; i++) {
                RenderSystem.setShaderColor(r, g, b, (float) (0.15F + (random.nextDouble() / 10)));
                double dst = (360 * random.nextDouble()) + (ClientTickHandler.ticksInGame + Minecraft.getInstance().getFrameTime()) / 8;
                double dstX = (360 * random.nextDouble()) + (ClientTickHandler.ticksInGame + Minecraft.getInstance().getFrameTime()) / 16;
                double dstY = (360 * random.nextDouble()) + (ClientTickHandler.ticksInGame + Minecraft.getInstance().getFrameTime()) / 16;
                int X = (int) (Math.cos(dst) * (2 * Math.sin(Math.toRadians(dstX))));
                int Y = (int) (Math.sin(dst) * (2 * Math.sin(Math.toRadians(dstY))));

                gui.blit(getIcon(), x + X, y + Y, 0, 0, 8, 8, 8, 8);
            }

            RenderSystem.disableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        }

        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        if (ClientConfig.MONOGRAM_COLOR.get()) {
            r = color.getRed() / 255f;
            g = color.getGreen() / 255f;
            b = color.getBlue() / 255f;
            RenderSystem.setShaderColor(r, g, b, 1F);
        }
        gui.blit(getIcon(), x, y, 0, 0, 8, 8, 8, 8);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
    }

    public List<Component> getComponentList() {
        List<Component> list = new ArrayList<>();
        list.add(Component.translatable(getTranslatedName()));
        list.add(Component.translatable(getTranslatedLoreName()).withStyle(ChatFormatting.GRAY));
        return list;
    }
}
