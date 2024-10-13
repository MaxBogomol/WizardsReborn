package mod.maxbogomol.wizards_reborn.api.monogram;

import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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
        this.color = Color.WHITE;
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
    public void renderIcon(GuiGraphics gui, int x, int y) {
        Random random = new Random(getId().length());

        TextureAtlasSprite sprite = RenderUtil.getSprite(getTexture());

        gui.pose().pushPose();
        gui.pose().translate(x + 8, y + 8, 100);
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.TRANSLUCENT_TEXTURE)
                .setUV(sprite)
                .setColor(WizardsRebornClientConfig.MONOGRAM_COLOR.get() ? color : Color.WHITE).setAlpha(1f)
                .renderCenteredQuad(gui.pose(), 8f)
                .endBatch();
        gui.pose().popPose();

        if (WizardsRebornClientConfig.MONOGRAM_RAYS.get()) {
            gui.pose().pushPose();
            gui.pose().translate(x + 8, y + 8, 100);
            RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                    .setColor(WizardsRebornClientConfig.MONOGRAM_GLOW_COLOR.get() ? color : Color.WHITE).setAlpha(0.15f)
                    .renderDragon(gui.pose(), 14f, ClientTickHandler.partialTicks, getId().length())
                    .endBatch();
            gui.pose().popPose();
        }

        if (WizardsRebornClientConfig.MONOGRAM_GLOW.get()) {
            for (int i = 0; i < 5; i++) {
                gui.pose().pushPose();
                gui.pose().translate(x + 8, y + 8, 100);
                RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                        .setUV(sprite)
                        .setColor(WizardsRebornClientConfig.MONOGRAM_GLOW_COLOR.get() ? color : Color.WHITE).setAlpha(0.15f)
                        .renderWavyQuad(gui.pose(), 8f + i, 0.1f, ClientTickHandler.getTotal() + (random.nextFloat() * 100))
                        .endBatch();
                gui.pose().popPose();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void renderMiniIcon(GuiGraphics gui, int x, int y) {
        Random random = new Random(getId().length());

        TextureAtlasSprite sprite = RenderUtil.getSprite(getTexture());

        gui.pose().pushPose();
        gui.pose().translate(x + 4, y + 4, 100);
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.TRANSLUCENT_TEXTURE)
                .setUV(sprite)
                .setColor(WizardsRebornClientConfig.MONOGRAM_COLOR.get() ? color : Color.WHITE).setAlpha(1f)
                .renderCenteredQuad(gui.pose(), 4f)
                .endBatch();
        gui.pose().popPose();

        if (WizardsRebornClientConfig.MONOGRAM_RAYS.get()) {
            gui.pose().pushPose();
            gui.pose().translate(x + 4, y + 4, 100);
            RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                    .setColor(WizardsRebornClientConfig.MONOGRAM_GLOW_COLOR.get() ? color : Color.WHITE).setAlpha(0.15f)
                    .renderDragon(gui.pose(), 7f, ClientTickHandler.partialTicks, getId().length())
                    .endBatch();
            gui.pose().popPose();
        }

        if (WizardsRebornClientConfig.MONOGRAM_GLOW.get()) {
            for (int i = 0; i < 5; i++) {
                gui.pose().pushPose();
                gui.pose().translate(x + 4, y + 4, 100);
                RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                        .setUV(sprite)
                        .setColor(WizardsRebornClientConfig.MONOGRAM_GLOW_COLOR.get() ? color : Color.WHITE).setAlpha(0.15f)
                        .renderWavyQuad(gui.pose(), 4f + (i / 2f), 0.1f, ClientTickHandler.getTotal() + (random.nextFloat() * 100))
                        .endBatch();
                gui.pose().popPose();
            }
        }
    }

    public List<Component> getComponentList() {
        List<Component> list = new ArrayList<>();
        list.add(Component.translatable(getTranslatedName()));
        list.add(Component.translatable(getTranslatedLoreName()).withStyle(ChatFormatting.GRAY));
        return list;
    }
}
