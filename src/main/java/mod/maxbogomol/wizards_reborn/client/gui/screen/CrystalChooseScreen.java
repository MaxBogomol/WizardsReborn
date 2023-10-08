package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.common.network.DeleteCrystalPacket;
import mod.maxbogomol.wizards_reborn.common.network.SetSpellPacket;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.SetCrystalPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import java.awt.*;
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
    public float mouseAngleI = 0;

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

            if (mouseAngleI > getWandItemDistance()) {
                if ((getPlayerCrystals().size() > 0) && (selectedItem != null)) {
                    if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
                        PacketHandler.sendToServer(new SetCrystalPacket(true, selectedItem));
                    } else {
                        if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                            PacketHandler.sendToServer(new SetCrystalPacket(false, selectedItem));
                        }
                    }
                }
            } else {
                if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
                    PacketHandler.sendToServer(new DeleteCrystalPacket(true));
                } else {
                    if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                        PacketHandler.sendToServer(new DeleteCrystalPacket(false));
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

        if (mode == Mode.CHOOSE) {
            int x = width / 2;
            int y = height / 2;

            RenderUtils.renderItemModelInGui(new ItemStack(WizardsReborn.EARTH_CRYSTAL.get()), x - 48, y - 16, 32, 32, 32);

            gui.blit(WizardsReborn.EARTH_PROJECTILE_SPELL.getIcon(), x + 16, y - 16, 0, 0, 32, 32, 32, 32);
        }

        if (mode == Mode.CRYSTAL) {
            if (hover) {
                crystals = getPlayerCrystals();
            }

            selectedItem = getSelectedItem(mouseX, mouseY);

            float step = (float) 360 / crystals.size();
            float i = 0;
            int x = width / 2;
            int y = height / 2;

            float mouseAngle = getMouseAngle(mouseX, mouseY);
            float mouseDistance = getMouseDistance(mouseX, mouseY);
            if (mouseDistance > (100 * hoveramount)) {
                mouseDistance = (100 * hoveramount);
            }
            mouseAngleI = mouseDistance;

            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            Tesselator tess = Tesselator.getInstance();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(WizardsRebornClient::getGlowingShader);

            gui.pose().pushPose();
            gui.pose().translate(x, y, 0);
            gui.pose().mulPose(Axis.ZP.rotationDegrees(mouseAngle));
            gui.pose().mulPose(Axis.XP.rotationDegrees((ClientTickHandler.ticksInGame + partialTicks + (i * 10) * 5)));
            RenderUtils.ray(gui.pose(), MultiBufferSource.immediate(tess.getBuilder()), 1f, mouseDistance, 10f, 1, 1, 1, 0.5f, 1, 1, 1, 0F);
            gui.pose().popPose();
            tess.end();

            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);

            for (ItemStack stack : crystals) {
                double dst = Math.toRadians((i * step) + (step / 2));
                int X = (int) (Math.cos(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));
                int Y = (int) (Math.sin(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));

                renderCrystalRays(stack, gui, x + X, y + Y, mouseX, mouseY, partialTicks, i, step, true);

                if (stack == selectedItem && mouseDistance > getWandItemDistance()) {
                    RenderUtils.renderItemModelInGui(stack, x + X - 24, y + Y - 24, 48, 48, 48);
                } else {
                    RenderUtils.renderItemModelInGui(stack, x + X - 16, y + Y - 16, 32, 32, 32);
                }

                i = i + 1F;
            }

            if (istWandItem(getWandCrystal())) {
                renderCrystalRays(getWandCrystal(), gui, x, y, mouseX, mouseY, partialTicks, i, step, false);
                RenderUtils.renderItemModelInGui(getWandCrystal(), x - 16, y - 16, 32, 32, 32);
            }

            if (selectedItem != null && mouseDistance > getWandItemDistance()) {
                gui.renderTooltip(Minecraft.getInstance().font, selectedItem, mouseX, mouseY);
            }

            if (mouseDistance <= getWandItemDistance() && istWandItem(getWandCrystal())) {
                gui.renderTooltip(Minecraft.getInstance().font, getWandCrystal(), mouseX, mouseY);
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

            if (selectedSpell != null) {
                gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(selectedSpell.getTranslatedName()), mouseX, mouseY);
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

    public float getMouseAngle(double X, double Y) {
        double x = width / 2;
        double y = height / 2;

        double angle =  Math.toDegrees(Math.atan2(Y-y,X-x));
        if (angle < 0D) {
            angle += 360D;
        }

        return (float) angle;
    }

    public float getMouseDistance(double X, double Y) {
        double x = width / 2;
        double y = height / 2;

        return (float) Math.sqrt(Math.pow(x - X, 2) + Math.pow(y - Y, 2));
    }

    public ItemStack getWandCrystal() {
        Player player = minecraft.player;
        ItemStack main = player.getMainHandItem();
        ItemStack offhand = player.getOffhandItem();

        if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
            return ArcaneWandItem.getInventory(main).getItem(0);
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                ArcaneWandItem.getInventory(offhand).getItem(0);
            }
        }

        return ItemStack.EMPTY;
    }

    public void renderCrystalRays(ItemStack stack, GuiGraphics gui, float x, float y, int mouseX, int mouseY, float partialTicks, float i, float step, boolean renderRay) {
        float r = 1f;
        float g = 1f;
        float b = 1f;
        float r1 = 1f;
        float g1 = 1f;
        float b1 = 1f;
        boolean renderPolishing = false;
        float mouseDistance = getMouseDistance(mouseX, mouseY);
        float chooseRay = (stack == selectedItem && mouseDistance > getWandItemDistance()) ? 1.2f : 0.8f;

        if (stack.getItem() instanceof CrystalItem crystalItem) {
            Color color = crystalItem.getType().getColor();;
            r = color.getRed() / 255f;
            g = color.getGreen() / 255f;
            b = color.getBlue() / 255f;

            if (crystalItem.getPolishing().hasParticle()) {
                renderPolishing = true;
                Color color1 = crystalItem.getPolishing().getColor();
                r1 = color1.getRed() / 255f;
                g1 = color1.getGreen() / 255f;
                b1 = color1.getBlue() / 255f;
            }
        }

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        Tesselator tess = Tesselator.getInstance();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(WizardsRebornClient::getGlowingShader);

        RenderUtils.dragon(gui.pose(), MultiBufferSource.immediate(tess.getBuilder()), x, y, 0, 30, partialTicks, r, g, b, i);
        tess.end();
        if (renderPolishing) {
            RenderUtils.dragon(gui.pose(), MultiBufferSource.immediate(tess.getBuilder()), x, y, 0, 20, partialTicks, r1 / 2f, g1 / 2f, b1 / 2f, i * 5);
            tess.end();
        }

        if (renderRay) {
            gui.pose().pushPose();
            gui.pose().translate(width / 2,  height / 2, 0);
            gui.pose().mulPose(Axis.ZP.rotationDegrees(i * step + (step / 2)));
            gui.pose().mulPose(Axis.XP.rotationDegrees((ClientTickHandler.ticksInGame + partialTicks + (i * 10) * 5)));
            RenderUtils.ray(gui.pose(), MultiBufferSource.immediate(tess.getBuilder()), 1f, (100 * hoveramount) * chooseRay, 10f, r, g, b, 1, r, g, b, 0F);
            gui.pose().popPose();
            tess.end();
        }

        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
    }

    public float getWandItemDistance() {
        return 20f;
    }

    public boolean istWandItem(ItemStack stack) {
        if (stack.getItem() instanceof CrystalItem crystalItem) {
            return true;
        }
        return false;
    }
}
