package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.item.WissenWandSetModePacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class WissenWandScreen extends Screen {
    public WissenWandScreen(Component titleIn) {
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
            WizardsRebornPacketHandler.sendToServer(new WissenWandSetModePacket(true, getSelectedMode(mouseX, mouseY)));
            hover = false;
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof WissenWandItem) {
                WizardsRebornPacketHandler.sendToServer(new WissenWandSetModePacket(false, getSelectedMode(mouseX, mouseY)));
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
        for (int i = 0; i < 5; i++) {
            renderRays(WissenWandItem.getModeColor(i), gui, partialTicks, i, 72, i == choosedRay);
        }
        RenderUtil.renderItemModelInGui(getWand(), x - 16, y - 16, 32, 32, 32, 45f * (1f - hoveramount), 45f * (1f - hoveramount), 0);
        gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(WissenWandItem.getModeString(choosedRay)).withStyle(WissenWandItem.getModeColor(choosedRay)), mouseX, mouseY);
    }

    public int getSelectedMode(double X, double Y) {
        double step = (float) 360 / 5;
        double x = width / 2f;
        double y = height / 2f;

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
        Color color1 = ColorUtil.getColor(color.getColor());

        float chooseRay = (choosed) ? 1.2f : 0.8f;

        gui.pose().pushPose();
        gui.pose().translate(width / 2f,  height / 2f, 0);
        gui.pose().mulPose(Axis.ZP.rotationDegrees(i * step + (step / 2)));
        gui.pose().mulPose(Axis.XP.rotationDegrees((ClientTickHandler.ticksInGame + partialTicks + (i * 10) * 5)));
        gui.pose().mulPose(Axis.ZP.rotationDegrees(-90f));
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                .setColor(color1)
                .setSecondAlpha(0)
                .renderRay(gui.pose(), 1f, (100 * hoveramount) * chooseRay, 40f)
                .endBatch();
        gui.pose().popPose();
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
