package mod.maxbogomol.wizards_reborn.client.render.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.SetCrystalPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class CrystalChooseScreen extends Screen {
    public CrystalChooseScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    public ItemStack selectedItem = null;

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
        minecraft.player.closeScreen();
        PlayerEntity player = minecraft.player;
        ItemStack main = player.getHeldItemMainhand();
        ItemStack offhand = player.getHeldItemOffhand();

        if ((getPlayerCrystals().size() > 0) && (selectedItem != null)) {
            if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
                PacketHandler.sendToServer(new SetCrystalPacket(true, selectedItem));
            } else {
                if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                    PacketHandler.sendToServer(new SetCrystalPacket(false, selectedItem));
                }
            }
        }

        return true;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        List<ItemStack>  crustals = getPlayerCrystals();

        selectedItem = getSelectedItem(mouseX, mouseY);

        float step = (float) 360/crustals.size();
        float i = 0;
        int x = width / 2;
        int y = height / 2;

        for (ItemStack stack : getPlayerCrystals()) {

            double dst = Math.toRadians((i * step) + (step/2));
            int X = (int) (Math.cos(dst) * (100));
            int Y = (int) (Math.sin(dst) * (100));

            RenderUtils.renderItemModelInGui(stack, x+X-17+16, y+Y-19+16, 32, 32, 32);

            i = i + 1F;
        }
    }

    public List<ItemStack> getPlayerCrystals() {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        List<ItemStack> items = player.container.getInventory();

        ArrayList<ItemStack> crystals = new ArrayList<ItemStack>();

        for (ItemStack stack : items) {
            if (stack.getItem() instanceof CrystalItem) {
                crystals.add(stack);
            }
        }

        return crystals;
    }

    public ItemStack getSelectedItem(double X, double Y) {
        List<ItemStack> crystals = getPlayerCrystals();

        double step = (float) 360/crystals.size();
        double x = width / 2;
        double y = height / 2;

        double angle =  Math.toDegrees(Math.atan2(Y-y,X-x));
        if (angle < 0D) {
            angle += 360D;
        }

        for (int i = 1; i <= crystals.size(); i += 1) {
            if ((((i-1) * step) <= angle) && (((i * step)) > angle)) {
                return crystals.get(i - 1);
            }
        }
        return null;
    }
}
