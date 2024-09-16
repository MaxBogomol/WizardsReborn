package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurShaders;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.SetWissenWandModePacket;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class WissenWandChooseScreen extends Screen {
    public WissenWandChooseScreen(Component titleIn) {
        super(titleIn);
    }

    public float hoveramount = 0;
    public boolean hover = true;

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Player player = minecraft.player;
        ItemStack main = player.getMainHandItem();
        ItemStack offhand = player.getOffhandItem();

        if (!main.isEmpty() && main.getItem() instanceof WissenWandItem) {
            PacketHandler.sendToServer(new SetWissenWandModePacket(true, getSelectedMode(mouseX, mouseY)));
            hover = false;
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof WissenWandItem) {
                PacketHandler.sendToServer(new SetWissenWandModePacket(false, getSelectedMode(mouseX, mouseY)));
                hover = false;
            }
        }

        return true;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        super.render(gui, mouseX, mouseY, partialTicks);

        if (hover && hoveramount < 1) hoveramount += Minecraft.getInstance().getDeltaFrameTime() / 4;
        else if (!hover && hoveramount > 0) hoveramount -= Minecraft.getInstance().getDeltaFrameTime();
        if (hoveramount > 1) {
            hoveramount = 1;
        }
        if (!hover && hoveramount <= 0) {
            minecraft.player.closeContainer();
        }

        int x = width / 2;
        int y = height / 2;

        int choosedRay = getSelectedMode(mouseX, mouseY);
        gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(WissenWandItem.getModeString(choosedRay)).withStyle(WissenWandItem.getModeColor(choosedRay)), mouseX, mouseY);
        for (int i = 0; i < 5; i++) {
            renderRays(WissenWandItem.getModeColor(i), gui, partialTicks, i, 72, i == choosedRay);
        }
        RenderUtil.renderItemModelInGui(getWand(), x - 16, y - 16, 32, 32, 32, 45f * (1f - hoveramount), 45f * (1f - hoveramount), 0);
    }

    public int getSelectedMode(double X, double Y) {
        double step = (float) 360 / 5;
        double x = width / 2;
        double y = height / 2;

        double angle =  Math.toDegrees(Math.atan2(Y-y,X-x));
        if (angle < 0D) {
            angle += 360D;
        }

        for (int i = 1; i <= 5; i += 1) {
            if ((((i-1) * step) <= angle) && (((i * step)) > angle)) {
                return i - 1;
            }
        }
        return 0;
    }

    public ItemStack getWand() {
        Player player = minecraft.player;
        ItemStack main = player.getMainHandItem();
        ItemStack offhand = player.getOffhandItem();

        if (!main.isEmpty() && main.getItem() instanceof WissenWandItem) {
            return main;
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof WissenWandItem) {
                return offhand;
            }
        }

        return ItemStack.EMPTY;
    }

    public void renderRays(ChatFormatting color, GuiGraphics gui, float partialTicks, float i, float step, boolean choosed) {
        float r = ColorUtil.getRed(color.getColor()) / 255f;
        float g = ColorUtil.getGreen(color.getColor()) / 255f;
        float b = ColorUtil.getBlue(color.getColor()) / 255f;

        float chooseRay = (choosed) ? 1.2f : 0.8f;

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(FluffyFurShaders::getAdditive);

        gui.pose().pushPose();
        gui.pose().translate(width / 2,  height / 2, 0);
        gui.pose().mulPose(Axis.ZP.rotationDegrees(i * step + (step / 2)));
        gui.pose().mulPose(Axis.XP.rotationDegrees((ClientTickHandler.ticksInGame + partialTicks + (i * 10) * 5)));
        RenderUtils.ray(gui.pose(), buffersource, 1f, (100 * hoveramount) * chooseRay, 40f, r, g, b, 1, r, g, b, 0F);
        buffersource.endBatch();
        gui.pose().popPose();

        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);

        if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
            this.onClose();
            return true;
        }

        return (super.keyPressed(keyCode, scanCode, modifiers));
    }
}
