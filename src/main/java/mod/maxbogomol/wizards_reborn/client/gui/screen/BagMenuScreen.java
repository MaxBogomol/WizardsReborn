package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.item.equipment.IBagItem;
import mod.maxbogomol.wizards_reborn.common.network.item.BagOpenPacket;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BagMenuScreen extends Screen {
    public BagMenuScreen(Component titleIn) {
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
        List<ItemStack> bags = getPlayerBags();

        ItemStack selectedItem = getSelectedItem(bags, mouseX, mouseY);
        if (selectedItem != null) {
            WizardsRebornPacketHandler.sendToServer(new BagOpenPacket(selectedItem));
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
        List<ItemStack> bags = getPlayerBags();
        ItemStack selectedItem = getSelectedItem(bags, mouseX, mouseY);

        float step = (float) 360 / bags.size();
        float i = 0;
        int x = width / 2;
        int y = height / 2;

        for (ItemStack stack : bags) {
            double dst = Math.toRadians((i * step) + (step / 2));
            int X = (int) (Math.cos(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));
            int Y = (int) (Math.sin(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));

            if (stack.getItem() instanceof IBagItem bagItem) {
                renderRays(bagItem.getColor(stack), gui, partialTicks, i, step, 1f, stack == selectedItem);
            }

            if (stack == selectedItem) {
                RenderUtil.renderItemModelInGui(stack, x + X - 24, y + Y - 24, 48, 48, 48, 45f * (1f - hoveramount), 45f * (1f - hoveramount), 0);
            } else {
                RenderUtil.renderItemModelInGui(stack, x + X - 16, y + Y - 16, 32, 32, 32, 45f * (1f - hoveramount), 45f * (1f - hoveramount), 0);
            }

            i = i + 1F;
        }
        if (selectedItem != null) {
            gui.renderTooltip(Minecraft.getInstance().font, selectedItem, mouseX, mouseY);
        }
    }

    public ItemStack getSelectedItem(List<ItemStack> bags, double X, double Y) {
        double step = (float) 360 / bags.size();
        double x = width / 2f;
        double y = height / 2f;

        double angle =  Math.toDegrees(Math.atan2(Y-y,X-x));
        if (angle < 0D) {
            angle += 360D;
        }

        for (int i = 1; i <= bags.size(); i += 1) {
            if ((((i-1) * step) <= angle) && (((i * step)) > angle)) {
                return bags.get(i - 1);
            }
        }
        return null;
    }

    public List<ItemStack> getPlayerBags() {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        List<ItemStack> items = player.inventoryMenu.getItems();
        List<SlotResult> curioSlots = CuriosApi.getCuriosInventory(player).resolve().get().findCurios((i) -> {return true;});
        for (SlotResult slot : curioSlots) {
            if (slot.stack() != null) {
                items.add(slot.stack());
            }
        }

        ArrayList<ItemStack> bags = new ArrayList<ItemStack>();

        for (ItemStack stack : items) {
            if (stack.getItem() instanceof IBagItem) {
                bags.add(stack);
            }
        }

        return bags;
    }

    public void renderRays(Color color, GuiGraphics gui, float partialTicks, float i, float step, float scale, boolean choosed) {
        float chooseRay = (choosed) ? 1.2f : 0.8f;

        gui.pose().pushPose();
        gui.pose().translate(width / 2f,  height / 2f, 0);
        gui.pose().mulPose(Axis.ZP.rotationDegrees(i * step + (step / 2)));
        gui.pose().mulPose(Axis.XP.rotationDegrees((ClientTickHandler.ticksInGame + partialTicks + (i * 10) * 5)));
        gui.pose().mulPose(Axis.ZP.rotationDegrees(-90f));
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                .setColor(color)
                .setSecondAlpha(0)
                .renderRay(gui.pose(), 1f, (100 * hoveramount) * chooseRay, 10f)
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
