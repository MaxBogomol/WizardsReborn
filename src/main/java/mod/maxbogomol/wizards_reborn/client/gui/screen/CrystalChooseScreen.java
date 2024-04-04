package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.Crystals;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.CrystalBagItem;
import mod.maxbogomol.wizards_reborn.common.network.DeleteCrystalPacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.SetCrystalPacket;
import mod.maxbogomol.wizards_reborn.common.network.SetSpellPacket;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public CrystalType selectedCrystalType;
    public boolean isSelectedCrystalType = false;
    public int page = 0;

    public static Map<CrystalType, ArrayList<Spell>> spellsList = new HashMap<CrystalType, ArrayList<Spell>>();

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

        if (mode == Mode.CRYSTAL) {
            hover = false;

            selectedItem = getSelectedItem(mouseX, mouseY);
            float mouseDistance = getMouseDistance(mouseX, mouseY);
            if (mouseDistance > (100 * hoveramount)) {
                mouseDistance = (100 * hoveramount);
            }
            mouseAngleI = mouseDistance;

            if (mouseAngleI > getWandItemDistance()) {
                if ((getPlayerCrystals().size() > 0) && (selectedItem != null)) {
                    if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
                        PacketHandler.sendToServer(new SetCrystalPacket(true, selectedItem));
                        Minecraft.getInstance().player.playNotifySound(WizardsReborn.CRYSTAL_RESONATE_SOUND.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
                    } else {
                        if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                            PacketHandler.sendToServer(new SetCrystalPacket(false, selectedItem));
                            Minecraft.getInstance().player.playNotifySound(WizardsReborn.CRYSTAL_RESONATE_SOUND.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
                        }
                    }
                }
            } else {
                if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
                    PacketHandler.sendToServer(new DeleteCrystalPacket(true));
                    Minecraft.getInstance().player.playNotifySound(WizardsReborn.CRYSTAL_RESONATE_SOUND.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
                } else {
                    if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                        PacketHandler.sendToServer(new DeleteCrystalPacket(false));
                        Minecraft.getInstance().player.playNotifySound(WizardsReborn.CRYSTAL_RESONATE_SOUND.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
                    }
                }
            }
        }

        if (mode == Mode.SPELL) {
            int i = 0;
            int x = width / 2;
            int y = height / 2;
            int h = 85;

            if (!isSelectedCrystalType) {
                for (CrystalType type : Crystals.getTypes()) {
                    if (mouseX >= x - 64 && mouseY >= y - h + (i * 34) + 2 && mouseX <= x - 64 + 128 && mouseY <= y - h + (i * 34) + 32 - 2) {
                        selectedCrystalType = type;
                        isSelectedCrystalType = true;
                    }
                    i++;
                }
            } else {
                for (int ii = (page * 5); ii < (page + 1) * 5; ii++) {
                    if (spellsList.get(selectedCrystalType).size() <= ii) {
                        break;
                    }
                    Spell spell = spellsList.get(selectedCrystalType).get(ii);
                    if (mouseX >= x - 64 && mouseY >= y - h + (ii * 34) + 2 && mouseX <= x - 64 + 128 && mouseY <= y - h + (ii * 34) + 32 - 2) {
                        selectedSpell = spell;
                    }
                    i++;
                }
            }

            if (isSelectedCrystalType) {
                int pages = (int) Math.ceil(spellsList.get(selectedCrystalType).size() / 5f);
                if (page > 0) {
                    if (mouseX >= x - 64 + 148 && mouseY >= y - h + 2 && mouseX <= x - 64 + 148 + 32 && mouseY <= y - h + 32 - 2) {
                        page--;
                        return true;
                    }
                }
                if (page + 1 < pages) {
                    if (mouseX >= x - 64 + 148 && mouseY >= y - h + 136 + 2 && mouseX <= x - 64 + 148 + 32 && mouseY <= y - h + 136 + 32 - 2) {
                        page++;
                        return true;
                    }
                }
                if (selectedSpell != null) {
                    if (KnowledgeUtils.isSpell(Minecraft.getInstance().player, selectedSpell)) {
                        hover = false;

                        if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
                            PacketHandler.sendToServer(new SetSpellPacket(true, selectedSpell.getId()));
                            Minecraft.getInstance().player.playNotifySound(WizardsReborn.CRYSTAL_RESONATE_SOUND.get(), SoundSource.NEUTRAL, 1.0f, 1.5f);
                        } else {
                            if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                                PacketHandler.sendToServer(new SetSpellPacket(false, selectedSpell.getId()));
                                Minecraft.getInstance().player.playNotifySound(WizardsReborn.CRYSTAL_RESONATE_SOUND.get(), SoundSource.NEUTRAL, 1.0f, 1.5f);
                            }
                        }
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

            selectedItem = getSelectedItem(crystals, mouseX, mouseY);

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
            MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(WizardsRebornClient::getGlowingShader);

            gui.pose().pushPose();
            gui.pose().translate(x, y, 0);
            gui.pose().mulPose(Axis.ZP.rotationDegrees(mouseAngle));
            gui.pose().mulPose(Axis.XP.rotationDegrees((ClientTickHandler.ticksInGame + partialTicks + (i * 10) * 5)));
            RenderUtils.ray(gui.pose(), buffersource, 1f, mouseDistance, 10f, 1, 1, 1, 0.5f, 1, 1, 1, 0F);
            buffersource.endBatch();
            gui.pose().popPose();

            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);

            for (ItemStack stack : crystals) {
                double dst = Math.toRadians((i * step) + (step / 2));
                int X = (int) (Math.cos(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));
                int Y = (int) (Math.sin(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));

                renderCrystalRays(stack, gui, x + X, y + Y, mouseX, mouseY, partialTicks, i, step, 1, true);

                if (stack == selectedItem && mouseDistance > getWandItemDistance()) {
                    RenderUtils.renderItemModelInGui(stack, x + X - 24, y + Y - 24, 48, 48, 48);
                } else {
                    RenderUtils.renderItemModelInGui(stack, x + X - 16, y + Y - 16, 32, 32, 32);
                }

                i = i + 1F;
            }

            if (isWandItem(getWandCrystal())) {
                renderCrystalRays(getWandCrystal(), gui, x, y, mouseX, mouseY, partialTicks, i, step, 1, false);
                RenderUtils.renderItemModelInGui(getWandCrystal(), x - 16, y - 16, 32, 32, 32);
            }

            if (selectedItem != null && mouseDistance > getWandItemDistance()) {
                gui.renderTooltip(Minecraft.getInstance().font, selectedItem, mouseX, mouseY);
            }

            if (mouseDistance <= getWandItemDistance() && isWandItem(getWandCrystal())) {
                gui.renderTooltip(Minecraft.getInstance().font, getWandCrystal(), mouseX, mouseY);
            }
        }

        if (mode == Mode.SPELL) {
            int i = 0;
            int x = width / 2;
            int y = height / 2;
            int h = 85;

            if (isWandItem(getWandCrystal())) {
                renderCrystalRays(getWandCrystal(), gui, x - 144 + 24, y, mouseX, mouseY, partialTicks, i, 0, 1.5f, false);
            }
            RenderUtils.renderItemModelInGui(getWand(), x - 32 - 144, y - 32, 64, 64, 64, -15, -15, -45);

            Spell spellWand = null;
            if (getWand().getItem() instanceof ArcaneWandItem wand) {
                CompoundTag nbt = getWand().getTag();
                if (nbt.contains("spell")) {
                    if (nbt.getString("spell") != "") {
                        spellWand = Spells.getSpell(nbt.getString("spell"));
                    }
                }
            }

            if (!isSelectedCrystalType) {
                for (CrystalType type : Crystals.getTypes()) {
                    int w = 0;
                    if (mouseX >= x - 64 && mouseY >= y - h + (i * 34) + 2 && mouseX <= x - 64 + 128 && mouseY <= y - h + (i * 34) + 32 - 2) {
                        w = 16;
                        selectedCrystalType = type;
                    }

                    Color color = type.getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    float chooseRay = 0;
                    if (getWandCrystal().getItem() instanceof CrystalItem crystalItem) {
                        chooseRay = (crystalItem.getType() == type) ? 0.5f : 0f;
                    }

                    RenderSystem.enableBlend();
                    RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
                    MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
                    RenderSystem.depthMask(false);
                    RenderSystem.setShader(WizardsRebornClient::getGlowingShader);

                    gui.pose().pushPose();
                    gui.pose().translate(x - 64 + w + 16, y - h + (i * 34) + 16, 0);
                    float s = (float) (0.5f * (Math.sin(Math.toRadians((ClientTickHandler.ticksInGame * 10 + partialTicks + (i * 10) * 2)))));
                    RenderUtils.ray(gui.pose(), buffersource, 14, 128, 1f, r, g, b, 0.5f + s, r, g, b, 0.5f - s);
                    buffersource.endBatch();
                    gui.pose().popPose();

                    gui.pose().pushPose();
                    gui.pose().translate(x - 144 + 24, y, 0);
                    gui.pose().mulPose(Axis.ZP.rotationDegrees(i * 20 - 40));
                    gui.pose().mulPose(Axis.XP.rotationDegrees((ClientTickHandler.ticksInGame + partialTicks + (i * 10) * 5)));
                    RenderUtils.ray(gui.pose(), buffersource, 1f, 85, 7.5f, r, g, b, 0.5f + chooseRay, r, g, b, 0F + chooseRay);
                    buffersource.endBatch();
                    gui.pose().popPose();

                    RenderSystem.disableBlend();
                    RenderSystem.depthMask(true);
                    RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);

                    RenderUtils.renderItemModelInGui(type.getCrystal(), x - 64 + w, y - h + (i * 34), 32, 32, 32);
                    gui.drawString(Minecraft.getInstance().font, Component.translatable(type.getTranslatedName()), x - 64 + w + 34, y - h + (i * 34) + 12, -1, true);
                    i++;
                }
            } else {
                int iii = 0;
                int wCount = 0;
                int wPageLeft = 0;
                int wPageRight = 0;

                selectedSpell = null;

                for (int ii = (page * 5); ii < (page + 1) * 5; ii++) {
                    if (spellsList.get(selectedCrystalType).size() <= ii) {
                        break;
                    }
                    Spell spell = spellsList.get(selectedCrystalType).get(ii);
                    int w = 0;
                    float f = 1;
                    if (spellWand != null) {
                        if (spellWand == spell) {
                            f = 2;
                        }
                    }
                    boolean isKnow = (KnowledgeUtils.isSpell(Minecraft.getInstance().player, spell));

                    if (mouseX >= x - 64 && mouseY >= y - h + (i * 34) + 2 && mouseX <= x - 64 + 128 && mouseY <= y - h + (i * 34) + 32 - 2) {
                        w = 16;
                        selectedSpell = spell;
                    }

                    Color color = spell.getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    if (!isKnow) {
                        r = 1f;
                        g = 1f;
                        b = 1f;
                    }

                    float chooseRay = 0;

                    RenderSystem.enableBlend();
                    RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
                    MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
                    RenderSystem.depthMask(false);
                    RenderSystem.setShader(WizardsRebornClient::getGlowingShader);

                    gui.pose().pushPose();
                    gui.pose().translate(x - 64 + w + 16, y - h + (i * 34) + 16, 0);
                    float s = (float) (0.5f * (Math.sin(Math.toRadians((ClientTickHandler.ticksInGame * 10 + partialTicks + (i * 10) * 2)))));
                    RenderUtils.ray(gui.pose(), buffersource, 14, 128, 1f, r, g, b, 0.5f + s, r, g, b, 0.5f - s);
                    buffersource.endBatch();
                    gui.pose().popPose();

                    gui.pose().pushPose();
                    gui.pose().translate(x - 144 + 24, y, 0);
                    gui.pose().mulPose(Axis.ZP.rotationDegrees(i * 20 - 40));
                    gui.pose().mulPose(Axis.XP.rotationDegrees((ClientTickHandler.ticksInGame + partialTicks + (i * 10) * 5)));
                    RenderUtils.ray(gui.pose(), buffersource, 1f, 85, 7.5f, r, g, b, (0.5f + chooseRay) * f, r, g, b, (0F + chooseRay) * f);
                    buffersource.endBatch();
                    gui.pose().popPose();

                    RenderSystem.disableBlend();
                    RenderSystem.depthMask(true);
                    RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);

                    if (iii >= 1 && iii <= 3 && wCount == 0) {
                        wCount = w;
                    }
                    if (iii == 0) {
                        wPageLeft = w;
                    }
                    if (iii == 4) {
                        wPageRight = w;
                    }
                    iii++;

                    if (isKnow) {
                        gui.blit(spell.getIcon(), x - 64 + w, y - h + (i * 34), 0, 0, 32, 32, 32, 32);
                        gui.drawString(Minecraft.getInstance().font, Component.translatable(spell.getTranslatedName()), x - 64 + w + 34, y - h + (i * 34) + 12, -1, true);
                    } else {
                        gui.blit(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/unknown.png"), x - 64 + w, y - h + (i * 34), 0, 0, 32, 32, 32, 32);
                        gui.drawString(Minecraft.getInstance().font, I18n.get("wizards_reborn.arcanemicon.unknown"), x - 64 + w + 34, y - h + (i * 34) + 12, -1, true);
                    }
                    i++;
                }

                int pages = (int) Math.ceil(spellsList.get(selectedCrystalType).size() / 5f);

                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
                MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
                RenderSystem.depthMask(false);
                RenderSystem.setShader(WizardsRebornClient::getGlowingShader);

                float r = 1f;
                float g = 1f;
                float b = 1f;

                if (getWandCrystal().getItem() instanceof CrystalItem crystalItem) {
                    Color color = crystalItem.getType().getColor();
                    r = color.getRed() / 255f;
                    g = color.getGreen() / 255f;
                    b = color.getBlue() / 255f;
                }

                if (page > 0) {
                    gui.pose().pushPose();
                    gui.pose().translate(x - 64 + wPageLeft + 16 + 148, y - h + 16, 0);
                    float s = (float) (0.5f * (Math.sin(Math.toRadians((ClientTickHandler.ticksInGame * 10 + partialTicks + (i * 10) * 3) + (90)))));
                    RenderUtils.ray(gui.pose(), buffersource, 14, 14, 1f, r, g, b, 0.5f + s, r, g, b, 0.5f - s);
                    buffersource.endBatch();
                    gui.pose().popPose();
                }

                gui.pose().pushPose();
                gui.pose().translate(x - 64 + wCount + 16 + 148, y - h + 16 + 54, 0);
                gui.pose().mulPose(Axis.ZP.rotationDegrees(90));
                float s = (float) (0.5f * (Math.sin(Math.toRadians((ClientTickHandler.ticksInGame * 10 + partialTicks + (i * 10) * 3) + (90 * 2)))));
                RenderUtils.ray(gui.pose(), buffersource, 14, 42, 1f, r, g, b, 0.5f + s, r, g, b, 0.5f - s);
                buffersource.endBatch();
                gui.pose().popPose();

                if (page + 1 < pages) {
                    gui.pose().pushPose();
                    gui.pose().translate(x - 64 + wPageRight + 16 + 148, y - h + 16 + 136, 0);
                    s = (float) (0.5f * (Math.sin(Math.toRadians((ClientTickHandler.ticksInGame * 10 + partialTicks + (i * 10) * 3) + (90 * 3)))));
                    RenderUtils.ray(gui.pose(), buffersource, 14, 14, 1f, r, g, b, 0.5f + s, r, g, b, 0.5f - s);
                    buffersource.endBatch();
                    gui.pose().popPose();
                }


                RenderSystem.disableBlend();
                RenderSystem.depthMask(true);
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                RenderSystem.setShader(GameRenderer::getPositionTexShader);

                if (page > 0) {
                    gui.drawString(Minecraft.getInstance().font, Component.literal("<-"), x - 64 + wPageLeft + 34 + 125, y - h + 12, -1, true);
                }
                gui.drawString(Minecraft.getInstance().font, Component.literal(String.valueOf(page + 1)), x - 64 + wCount + 34 + 125, y - h + 12 + 68 - 12, -1, true);
                gui.drawString(Minecraft.getInstance().font, Component.literal("/"), x - 64 + wCount + 34 + 125, y - h + 12 + 68, -1, true);
                gui.drawString(Minecraft.getInstance().font, Component.literal(String.valueOf(pages)), x - 64 + wCount + 34 + 125, y - h + 12 + 68 + 12, -1, true);
                if (page + 1 < pages) {
                    gui.drawString(Minecraft.getInstance().font, Component.literal("->"), x - 64 + wPageRight + 34 + 125, y - h + 12 + 136, -1, true);
                }
            }

            if (mouseX >= x - 144 + 24 - 16 && mouseY >= y - 16 && mouseX <= x - 144 + 24 + 16 && mouseY <= y + 16) {
                ItemStack wandCrystal = getWandCrystal();
                if (!wandCrystal.isEmpty()) {
                    gui.renderTooltip(Minecraft.getInstance().font, wandCrystal, mouseX, mouseY);
                }
                if (spellWand != null) {
                    gui.blit(spellWand.getIcon(), mouseX + 9, mouseY - 68, 0, 0, 32, 32, 32, 32);
                    if (!spellWand.canWandWithCrystal(getWand())) {
                        gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/arcane_wand_frame.png"), mouseX + 7, mouseY - 70, 0, 36, 36, 36, 128, 128);
                    }
                    gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(spellWand.getTranslatedName()), mouseX, mouseY - 18);
                }
            }
        }
    }

    public List<ItemStack> getPlayerCrystals() {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        List<ItemStack> items = player.inventoryMenu.getItems();
        List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(player, (i) -> {return true;});
        for (SlotResult slot : curioSlots) {
            if (slot.stack() != null) {
                items.add(slot.stack());
            }
        }

        ArrayList<ItemStack> crystals = new ArrayList<ItemStack>();

        for (ItemStack stack : items) {
            if (stack.getItem() instanceof CrystalItem) {
                crystals.add(stack);
            }
            if (stack.getItem() instanceof CrystalBagItem) {
                SimpleContainer container = CrystalBagItem.getInventory(stack);
                for (int i = 0; i < container.getContainerSize(); i++) {
                    if (container.getItem(i).getItem() instanceof CrystalItem) {
                        crystals.add(container.getItem(i));
                    }
                }
            }
        }

        return crystals;
    }

    public ItemStack getSelectedItem(double X, double Y) {
        List<ItemStack> crystals = getPlayerCrystals();
        return getSelectedItem(crystals, X, Y);
    }

    public ItemStack getSelectedItem(List<ItemStack> crystals, double X, double Y) {
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

    public ItemStack getWand() {
        Player player = minecraft.player;
        ItemStack main = player.getMainHandItem();
        ItemStack offhand = player.getOffhandItem();

        if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
            return main;
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                return offhand;
            }
        }

        return ItemStack.EMPTY;
    }

    public ItemStack getWandCrystal() {
        Player player = minecraft.player;
        ItemStack main = player.getMainHandItem();
        ItemStack offhand = player.getOffhandItem();

        if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
            return ArcaneWandItem.getInventory(main).getItem(0);
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                return ArcaneWandItem.getInventory(offhand).getItem(0);
            }
        }

        return ItemStack.EMPTY;
    }

    public void renderCrystalRays(ItemStack stack, GuiGraphics gui, float x, float y, int mouseX, int mouseY, float partialTicks, float i, float step, float scale, boolean renderRay) {
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
        MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(WizardsRebornClient::getGlowingShader);

        RenderUtils.dragon(gui.pose(), buffersource, x, y, 0, 30 * scale, partialTicks, r, g, b, i);
        buffersource.endBatch();
        if (renderPolishing) {
            RenderUtils.dragon(gui.pose(), buffersource, x, y, 0, 20 * scale, partialTicks, r1 / 2f, g1 / 2f, b1 / 2f, i * 5);
            buffersource.endBatch();
        }

        if (renderRay) {
            gui.pose().pushPose();
            gui.pose().translate(width / 2,  height / 2, 0);
            gui.pose().mulPose(Axis.ZP.rotationDegrees(i * step + (step / 2)));
            gui.pose().mulPose(Axis.XP.rotationDegrees((ClientTickHandler.ticksInGame + partialTicks + (i * 10) * 5)));
            RenderUtils.ray(gui.pose(), buffersource, 1f, (100 * hoveramount) * chooseRay, 10f, r, g, b, 1, r, g, b, 0F);
            buffersource.endBatch();
            gui.pose().popPose();
        }

        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
    }

    public float getWandItemDistance() {
        return 20f;
    }

    public boolean isWandItem(ItemStack stack) {
        if (stack.getItem() instanceof CrystalItem) {
            return true;
        }
        return false;
    }

    public static void initSpells() {
        for (CrystalType type : Crystals.getTypes()) {
            spellsList.put(type, new ArrayList<Spell>());
        }

        for (Spell spell : Spells.getSpells()) {
            for (CrystalType type : spell.getCrystalTypes()) {
                spellsList.get(type).add(spell);
            }
        }
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
