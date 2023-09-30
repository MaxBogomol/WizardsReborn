package mod.maxbogomol.wizards_reborn.api.monogram;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class Monogram {
    public String id;

    public Monogram(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ResourceLocation getIcon() {
        return getIcon(id);
    }

    public String getTranslatedName() {
        return getTranslatedName(id);
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

    @OnlyIn(Dist.CLIENT)
    public void renderArcanemiconIcon(ArcanemiconGui book, GuiGraphics gui, int x, int y) {
        Random random = new Random(getId().length());

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        RenderSystem.setShaderColor(1F, 1F, 1F, 0.15F);

        for (int i = 0; i < 5; i++) {
            RenderSystem.setShaderColor(1F, 1F, 1F, (float) (0.05F + (random.nextDouble() / 10)));
            double dst = (360 * random.nextDouble()) + (ClientTickHandler.ticksInGame + Minecraft.getInstance().getFrameTime()) / 8;
            double dstX = (360 * random.nextDouble()) + (ClientTickHandler.ticksInGame + Minecraft.getInstance().getFrameTime()) / 16;
            double dstY = (360 * random.nextDouble()) + (ClientTickHandler.ticksInGame + Minecraft.getInstance().getFrameTime()) / 16;
            int X = (int) (Math.cos(dst) * (4 * Math.sin(Math.toRadians(dstX))));
            int Y = (int) (Math.sin(dst) * (4 * Math.sin(Math.toRadians(dstY))));

            gui.blit(getIcon(), x + X, y + Y, 0, 0, 16, 16, 16, 16);
        }

        RenderSystem.disableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        gui.blit(getIcon(), x, y, 0, 0, 16, 16, 16, 16);
    }

    @OnlyIn(Dist.CLIENT)
    public void renderArcanemiconMiniIcon(ArcanemiconGui book, GuiGraphics gui, int x, int y) {
        Random random = new Random(getId().length());

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        RenderSystem.setShaderColor(1F, 1F, 1F, 0.15F);

        for (int i = 0; i < 5; i++) {
            RenderSystem.setShaderColor(1F, 1F, 1F, (float) (0.05F + (random.nextDouble() / 10)));
            double dst = (360 * random.nextDouble()) + (ClientTickHandler.ticksInGame + Minecraft.getInstance().getFrameTime()) / 8;
            double dstX = (360 * random.nextDouble()) + (ClientTickHandler.ticksInGame + Minecraft.getInstance().getFrameTime()) / 16;
            double dstY = (360 * random.nextDouble()) + (ClientTickHandler.ticksInGame + Minecraft.getInstance().getFrameTime()) / 16;
            int X = (int) (Math.cos(dst) * (2 * Math.sin(Math.toRadians(dstX))));
            int Y = (int) (Math.sin(dst) * (2 * Math.sin(Math.toRadians(dstY))));

            gui.blit(getIcon(), x + X, y + Y, 0, 0, 8, 8, 8, 8);
        }

        RenderSystem.disableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        gui.blit(getIcon(), x, y, 0, 0, 8, 8, 8, 8);
    }
}
