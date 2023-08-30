package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import mod.maxbogomol.wizards_reborn.common.network.SetSpellPacket;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.SetCrystalPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class CrystalChooseScreen extends Screen {
    public CrystalChooseScreen(Component titleIn) {
        super(titleIn);
    }

    public enum Mode {
        CHOOSE,
        CRYSTAL,
        SPELL
    }

    public ItemStack selectedItem;
    public float hoveramount = 0;
    public boolean hover = true;
    public List<ItemStack> crystals = new ArrayList<ItemStack>();
    public Mode mode = Mode.CHOOSE;
    public int mouseX = 0;
    public Spell selectedSpell;

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
        Player player = minecraft.player;
        ItemStack main = player.getMainHandItem();
        ItemStack offhand = player.getOffhandItem();

        if (mode == Mode.CRYSTAL) {
            hover = false;

            if ((getPlayerCrystals().size() > 0) && (selectedItem != null)) {
                if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
                    PacketHandler.sendToServer(new SetCrystalPacket(true, selectedItem));
                } else {
                    if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                        PacketHandler.sendToServer(new SetCrystalPacket(false, selectedItem));
                    }
                }
            }
        }

        if (mode == Mode.SPELL) {
            hover = false;

            if ((Spells.size() > 0) && (selectedSpell != null)) {
                if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
                    PacketHandler.sendToServer(new SetSpellPacket(true, selectedSpell.getId()));
                } else {
                    if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                        PacketHandler.sendToServer(new SetSpellPacket(false, selectedSpell.getId()));
                    }
                }
            }
        }

        if (mode == Mode.CHOOSE) {
            hoveramount = 0;
            double x = width / 2;
            if (x > mouseX) {
                mode = Mode.CRYSTAL;
            } else {
                mode = Mode.SPELL;
            }
        }

        return true;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        super.render(gui, mouseX, mouseY, partialTicks);

        if (hover && hoveramount < 1) hoveramount += Minecraft.getInstance().getFrameTime() / 10;
        else if (!hover && hoveramount > 0) hoveramount -= Minecraft.getInstance().getFrameTime() / 5;
        if (hoveramount > 1) {
            hoveramount = 1;
        }
        if (!hover && hoveramount <= 0) {
            minecraft.player.closeContainer();
        }

        this.mouseX = mouseX;

        if (mode == Mode.CRYSTAL) {
            if (hover) {
                crystals = getPlayerCrystals();
            }

            selectedItem = getSelectedItem(mouseX, mouseY);

            float step = (float) 360 / crystals.size();
            float i = 0;
            int x = width / 2;
            int y = height / 2;

            for (ItemStack stack : crystals) {

                double dst = Math.toRadians((i * step) + (step / 2));
                int X = (int) (Math.cos(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));
                int Y = (int) (Math.sin(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));

                if (stack == selectedItem) {
                    RenderUtils.renderItemModelInGui(stack, x + X, y + Y, 48, 48, 48);
                } else {
                    RenderUtils.renderItemModelInGui(stack, x + X, y + Y, 32, 32, 32);
                }

                i = i + 1F;
            }
        }

        if (mode == Mode.SPELL) {
            selectedSpell = getSelectedSpell(mouseX, mouseY);

            float step = (float) 360 / Spells.size();
            float i = 0;
            int x = width / 2;
            int y = height / 2;

            for (Spell spell : Spells.getSpells()) {

                double dst = Math.toRadians((i * step) + (step / 2));
                int X = (int) (Math.cos(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));
                int Y = (int) (Math.sin(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));

                gui.blit(spell.getIcon(), x + X - 16, y + Y - 16, 0, 0, 32, 32, 32, 32);

                i = i + 1F;
            }
        }
    }

    public List<ItemStack> getPlayerCrystals() {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        List<ItemStack> items = player.inventoryMenu.getItems();

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

        double step = (float) 360 / crystals.size();
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

    public Spell getSelectedSpell(double X, double Y) {
        List<Spell> spells = Spells.getSpells();

        double step = (float) 360 / spells.size();
        double x = width / 2;
        double y = height / 2;

        double angle =  Math.toDegrees(Math.atan2(Y-y,X-x));
        if (angle < 0D) {
            angle += 360D;
        }

        for (int i = 1; i <= spells.size(); i += 1) {
            if ((((i-1) * step) <= angle) && (((i * step)) > angle)) {
                return spells.get(i - 1);
            }
        }
        return null;
    }
}
