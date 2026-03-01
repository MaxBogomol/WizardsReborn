package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalHandler;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellHandler;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.CrystalBagItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.item.ArcaneWandRemoveCrystalPacket;
import mod.maxbogomol.wizards_reborn.common.network.item.ArcaneWandSetCrystalPacket;
import mod.maxbogomol.wizards_reborn.common.network.item.ArcaneWandSpellSetPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.SpellSetRemovePacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.SpellSetSetCurrentPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.SpellSetSetCurrentSpellPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.SpellSetSetSpellPacket;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import org.lwjgl.glfw.GLFW;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArcaneWandScreen extends Screen {

    public ArcaneWandScreen(Component titleIn) {
        super(titleIn);
        ArcaneWandScreen.initSpells();
    }

    public enum Mode {
        CHOOSE,
        CRYSTAL,
        SPELL,
        SPELL_SETS,
        SPELL_SET
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
    public boolean isSpellSet = false;
    public int idSpellSet = 0;

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
                        WizardsRebornPacketHandler.sendToServer(new ArcaneWandSetCrystalPacket(true, selectedItem));
                    } else {
                        if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                            WizardsRebornPacketHandler.sendToServer(new ArcaneWandSetCrystalPacket(false, selectedItem));
                        }
                    }
                }
            } else {
                if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
                    WizardsRebornPacketHandler.sendToServer(new ArcaneWandRemoveCrystalPacket(true));
                } else {
                    if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                        WizardsRebornPacketHandler.sendToServer(new ArcaneWandRemoveCrystalPacket(false));
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
                for (CrystalType type : CrystalHandler.getTypes()) {
                    if (mouseX >= x - 64 && mouseY >= y - h + (i * 34) + 2 && mouseX < x - 64 + 128 && mouseY < y - h + (i * 34) + 32 - 2) {
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
                    if (mouseX >= x - 64 && mouseY >= y - h + (ii * 34) + 2 && mouseX < x - 64 + 128 && mouseY < y - h + (ii * 34) + 32 - 2) {
                        selectedSpell = spell;
                    }
                    i++;
                }
            }

            if (isSelectedCrystalType) {
                int pages = (int) Math.ceil(spellsList.get(selectedCrystalType).size() / 5f);
                if (page > 0) {
                    if (mouseX >= x - 64 + 148 && mouseY >= y - h + 2 && mouseX < x - 64 + 148 + 32 && mouseY < y - h + 32 - 2) {
                        page--;
                        return true;
                    }
                }
                if (page + 1 < pages) {
                    if (mouseX >= x - 64 + 148 && mouseY >= y - h + 136 + 2 && mouseX < x - 64 + 148 + 32 && mouseY < y - h + 136 + 32 - 2) {
                        page++;
                        return true;
                    }
                }
                if (selectedSpell != null) {
                    if (KnowledgeUtil.isSpell(Minecraft.getInstance().player, selectedSpell)) {
                        if (!isSpellSet) {
                            hover = false;

                            if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
                                WizardsRebornPacketHandler.sendToServer(new ArcaneWandSpellSetPacket(true, selectedSpell.getId()));
                            } else {
                                if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                                    WizardsRebornPacketHandler.sendToServer(new ArcaneWandSpellSetPacket(false, selectedSpell.getId()));
                                }
                            }
                            return true;
                        } else {
                            hoveramount = 0;
                            mode = Mode.SPELL_SET;
                            isSpellSet = false;
                            int currentSpellSet = KnowledgeUtil.getCurrentSpellSet(minecraft.player);
                            WizardsRebornPacketHandler.sendToServer(new SpellSetSetSpellPacket(selectedSpell.getId(), currentSpellSet, idSpellSet));
                            return true;
                        }
                    }
                }
            }
        }

        if (mode == Mode.SPELL_SETS) {
            int choosed = getSelectedSpell(mouseX, mouseY);
            if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                WizardsRebornPacketHandler.sendToServer(new SpellSetRemovePacket(choosed));
                return true;
            } else {
                WizardsRebornPacketHandler.sendToServer(new SpellSetSetCurrentPacket(choosed));
                hoveramount = 0;
                mode = Mode.SPELL_SET;
                return true;
            }
        }

        if (mode == Mode.SPELL_SET) {
            int choosed = getSelectedSpell(mouseX, mouseY);
            int currentSpellSet = KnowledgeUtil.getCurrentSpellSet(minecraft.player);
            if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                WizardsRebornPacketHandler.sendToServer(new SpellSetSetSpellPacket("", currentSpellSet, choosed));
                return true;
            } else {
                selectedSpell = KnowledgeUtil.getSpellFromSet(minecraft.player, currentSpellSet, choosed);

                if (selectedSpell != null) {
                    if (KnowledgeUtil.isSpell(Minecraft.getInstance().player, selectedSpell)) {
                        hover = false;

                        if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
                            WizardsRebornPacketHandler.sendToServer(new ArcaneWandSpellSetPacket(true, selectedSpell.getId()));
                            WizardsRebornPacketHandler.sendToServer(new SpellSetSetCurrentSpellPacket(choosed));
                        } else {
                            if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                                WizardsRebornPacketHandler.sendToServer(new ArcaneWandSpellSetPacket(false, selectedSpell.getId()));
                                WizardsRebornPacketHandler.sendToServer(new SpellSetSetCurrentSpellPacket(choosed));
                            }
                        }
                        return true;
                    }
                }

                idSpellSet = choosed;
                isSpellSet = true;
                isSelectedCrystalType = false;
                page = 0;
                mode = Mode.SPELL;
                return true;
            }
        }

        if (mode == Mode.CHOOSE) {
            hoveramount = 0;
            int choosed = getSelectedMode(mouseX, mouseY, 45);
            switch (choosed) {
                case 0 -> mode = Mode.SPELL_SET;
                case 1 -> mode = Mode.CRYSTAL;
                case 2 -> mode = Mode.SPELL_SETS;
                case 3 -> mode = Mode.SPELL;
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

        this.mouseX = mouseX;

        if (mode == Mode.CHOOSE) {
            int x = width / 2;
            int y = height / 2;

            int choosedRay = getSelectedMode(mouseX, mouseY, 45);
            int currentSpellSet = KnowledgeUtil.getCurrentSpellSet(minecraft.player);
            for (int i = 0; i < 4; i++) {
                double dst = Math.toRadians((i * 90) + 90);
                int X = (int) (Math.cos(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));
                int Y = (int) (Math.sin(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));

                Color color = Color.WHITE;
                ResourceLocation icon = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/research.png");

                if (i == 1) {
                    ItemStack stack = getWandCrystal();
                    if (stack.isEmpty()) {
                        stack = new ItemStack(WizardsRebornItems.EARTH_CRYSTAL.get());
                    }
                    if (i == choosedRay) {
                        RenderUtil.renderItemModelInGui(stack, x + X - 24, y + Y - 24, 48, 48, 48, 45f * (1f - hoveramount), 45f * (1f - hoveramount), 0);
                    } else {
                        RenderUtil.renderItemModelInGui(stack, x + X - 16, y + Y - 16, 32, 32, 32, 45f * (1f - hoveramount), 45f * (1f - hoveramount), 0);
                    }
                }

                if (i == 3) {
                    if (getWand().getItem() instanceof ArcaneWandItem wand) {
                        CompoundTag nbt = getWand().getTag();
                        if (nbt.contains("spell")) {
                            if (nbt.getString("spell") != "") {
                                Spell spell = SpellHandler.getSpell(nbt.getString("spell"));
                                if (KnowledgeUtil.isSpell(Minecraft.getInstance().player, spell)) {
                                    color = spell.getColor();
                                    icon = spell.getIcon();
                                } else {
                                    icon = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/unknown.png");
                                }
                            }
                        }
                    }
                }

                if (i == 2) {
                    Spell spell = null;
                    ArrayList<ArrayList<Spell>> set = KnowledgeUtil.getSpellSets(minecraft.player);
                    for (int ii = 0; ii < 10; ii++) {
                        for (int iii = 0; iii < 10; iii++) {
                            if (set.get(ii).get(iii) != null) {
                                spell = set.get(ii).get(iii);
                                break;
                            }
                        }
                        if (spell != null) break;
                    }
                    if (spell != null) {
                        color = spell.getColor();
                        icon = spell.getIcon();
                    }
                }

                if (i == 0) {
                    Spell spell = null;
                    ArrayList<Spell> set = KnowledgeUtil.getSpellSet(minecraft.player, currentSpellSet);
                    for (int ii = 0; ii < 10; ii++) {
                        if (set.get(ii) != null) {
                            spell = set.get(ii);
                            break;
                        }
                    }
                    if (spell != null) {
                        color = spell.getColor();
                        icon = spell.getIcon();
                    }
                }

                if (i == 1) {
                    if (getWandCrystal().getItem() instanceof CrystalItem crystal) {
                        color = crystal.getType().getColor();
                    }
                }

                renderRays(color, gui, partialTicks, i, 90, 45, i == choosedRay);

                if (i != 1) {
                    if (i == choosedRay) {
                        gui.blit(icon, x + X - 24, y + Y - 24, 0, 0, 48, 48, 48, 48);
                    } else {
                        gui.blit(icon, x + X - 16, y + Y - 16, 0, 0, 32, 32, 32, 32);
                    }
                }
            }
            Component name = Component.empty();

            switch (choosedRay) {
                case 0 -> name = Component.translatable("gui.wizards_reborn.wand.spell_set");
                case 1 -> name = Component.translatable("gui.wizards_reborn.wand.crystal");
                case 2 -> name = Component.translatable("gui.wizards_reborn.wand.spell_sets");
                case 3 -> name = Component.translatable("gui.wizards_reborn.wand.spell");
            }

            gui.renderTooltip(Minecraft.getInstance().font, name, mouseX, mouseY);
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

            gui.pose().pushPose();
            gui.pose().translate(x, y, 0);
            gui.pose().mulPose(Axis.ZP.rotationDegrees(mouseAngle));
            gui.pose().mulPose(Axis.XP.rotationDegrees((ClientTickHandler.ticksInGame + partialTicks + (i * 10) * 5)));
            gui.pose().mulPose(Axis.ZP.rotationDegrees(-90f));
            RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                    .setColor(Color.WHITE)
                    .setAlpha(0.5f)
                    .setSecondAlpha(0)
                    .renderRay(gui.pose(), 1f, mouseDistance, 10f)
                    .endBatch();
            gui.pose().popPose();

            for (ItemStack stack : crystals) {
                double dst = Math.toRadians((i * step) + (step / 2));
                int X = (int) (Math.cos(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));
                int Y = (int) (Math.sin(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));

                renderCrystalRays(stack, gui, x + X, y + Y, mouseX, mouseY, partialTicks, i, step, 1, true);

                if (stack == selectedItem && mouseDistance > getWandItemDistance()) {
                    RenderUtil.renderItemModelInGui(stack, x + X - 24, y + Y - 24, 48, 48, 48, 45f * (1f - hoveramount), 45f * (1f - hoveramount), 0);
                } else {
                    RenderUtil.renderItemModelInGui(stack, x + X - 16, y + Y - 16, 32, 32, 32, 45f * (1f - hoveramount), 45f * (1f - hoveramount), 0);
                }

                i = i + 1F;
            }

            if (isWandItem(getWandCrystal())) {
                renderCrystalRays(getWandCrystal(), gui, x, y, mouseX, mouseY, partialTicks, i, step, 1, false);
                RenderUtil.renderItemModelInGui(getWandCrystal(), x - 16, y - 16, 32, 32, 32, 45f * (1f - hoveramount), 45f * (1f - hoveramount), 0);
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
            float rot = 45f * (1f - hoveramount);
            if (!hover) rot = 0;
            RenderUtil.renderItemModelInGui(getWand(), x - 32 - 144, y - 32, 64, 64, 64, -15 + rot, -15 + rot, -45);

            Spell spellWand = null;
            if (getWand().getItem() instanceof ArcaneWandItem wand) {
                CompoundTag nbt = getWand().getTag();
                if (nbt.contains("spell")) {
                    if (!nbt.getString("spell").equals("")) {
                        spellWand = SpellHandler.getSpell(nbt.getString("spell"));
                    }
                }
            }

            if (!isSelectedCrystalType) {
                for (CrystalType type : CrystalHandler.getTypes()) {
                    int w = 0;
                    if (mouseX >= x - 64 && mouseY >= y - h + (i * 34) + 2 && mouseX < x - 64 + 128 && mouseY < y - h + (i * 34) + 32 - 2) {
                        w = 16;
                        selectedCrystalType = type;
                    }

                    Color color = type.getColor();

                    float chooseRay = 0;
                    if (getWandCrystal().getItem() instanceof CrystalItem crystalItem) {
                        chooseRay = (crystalItem.getType() == type) ? 0.5f : 0f;
                    }

                    gui.pose().pushPose();
                    gui.pose().translate(x - 64 + w + 16, y - h + (i * 34) + 16, 0);
                    float s = (float) (0.5f * (Math.sin(Math.toRadians((ClientTickHandler.ticksInGame * 10 + partialTicks + (i * 10) * 2)))));
                    gui.pose().mulPose(Axis.ZP.rotationDegrees(-90f));
                    RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                            .setColor(color)
                            .setAlpha(0.5f + s)
                            .setSecondAlpha(0.5f - s)
                            .renderRay(gui.pose(), 14f, 128f, 1f)
                            .endBatch();
                    gui.pose().popPose();

                    gui.pose().pushPose();
                    gui.pose().translate(x - 144 + 24, y, 0);
                    gui.pose().mulPose(Axis.ZP.rotationDegrees(i * 20 - 40));
                    gui.pose().mulPose(Axis.XP.rotationDegrees((ClientTickHandler.ticksInGame + partialTicks + (i * 10) * 5)));
                    gui.pose().mulPose(Axis.ZP.rotationDegrees(-90f));
                    RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                            .setColor(color)
                            .setAlpha(0.5f + chooseRay)
                            .setSecondAlpha(0 + chooseRay)
                            .renderRay(gui.pose(), 1f, 85f, 7.5f)
                            .endBatch();
                    gui.pose().popPose();

                    RenderUtil.renderItemModelInGui(type.getCrystal(), x - 64 + w, y - h + (i * 34), 32, 32, 32, 45f * (1f - hoveramount), 45f * (1f - hoveramount), 0);
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
                    boolean isKnow = (KnowledgeUtil.isSpell(Minecraft.getInstance().player, spell));

                    if (mouseX >= x - 64 && mouseY >= y - h + (i * 34) + 2 && mouseX < x - 64 + 128 && mouseY < y - h + (i * 34) + 32 - 2) {
                        w = 16;
                        selectedSpell = spell;
                    }

                    Color color = spell.getColor();

                    if (!isKnow) {
                        color = Color.WHITE;
                    }

                    float chooseRay = 0;

                    gui.pose().pushPose();
                    gui.pose().translate(x - 64 + w + 16, y - h + (i * 34) + 16, 0);
                    float s = (float) (0.5f * (Math.sin(Math.toRadians((ClientTickHandler.ticksInGame * 10 + partialTicks + (i * 10) * 2)))));
                    gui.pose().mulPose(Axis.ZP.rotationDegrees(-90f));
                    RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                            .setColor(color)
                            .setAlpha(0.5f + s)
                            .setSecondAlpha(0.5f - s)
                            .renderRay(gui.pose(), 14f, 128f, 1f)
                            .endBatch();
                    gui.pose().popPose();

                    gui.pose().pushPose();
                    gui.pose().translate(x - 144 + 24, y, 0);
                    gui.pose().mulPose(Axis.ZP.rotationDegrees(i * 20 - 40));
                    gui.pose().mulPose(Axis.XP.rotationDegrees((ClientTickHandler.ticksInGame + partialTicks + (i * 10) * 5)));
                    gui.pose().mulPose(Axis.ZP.rotationDegrees(-90f));
                    RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                            .setColor(color)
                            .setAlpha((0.5f + chooseRay) * f)
                            .setSecondAlpha((0 + chooseRay) * f)
                            .renderRay(gui.pose(), 1f, 85f, 7.5f)
                            .endBatch();
                    gui.pose().popPose();

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

                Color color = Color.WHITE;
                if (getWandCrystal().getItem() instanceof CrystalItem crystalItem) {
                    color = crystalItem.getType().getColor();
                }

                if (page > 0) {
                    gui.pose().pushPose();
                    gui.pose().translate(x - 64 + wPageLeft + 16 + 148, y - h + 16, 0);
                    float s = (float) (0.5f * (Math.sin(Math.toRadians((ClientTickHandler.ticksInGame * 10 + partialTicks + (i * 10) * 3) + (90)))));
                    RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                            .setColor(color)
                            .setAlpha(0.5f + s)
                            .setSecondAlpha(0.5f - s)
                            .renderRay(gui.pose(), 14f, 14f, 1f)
                            .endBatch();
                    gui.pose().popPose();
                }

                gui.pose().pushPose();
                gui.pose().translate(x - 64 + wCount + 16 + 148, y - h + 16 + 54, 0);
                gui.pose().mulPose(Axis.ZP.rotationDegrees(90));
                float s = (float) (0.5f * (Math.sin(Math.toRadians((ClientTickHandler.ticksInGame * 10 + partialTicks + (i * 10) * 3) + (90 * 2)))));
                gui.pose().mulPose(Axis.ZP.rotationDegrees(-90f));
                RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                        .setColor(color)
                        .setAlpha(0.5f + s)
                        .setSecondAlpha(0.5f - s)
                        .renderRay(gui.pose(), 14f, 42f, 1f)
                        .endBatch();
                gui.pose().popPose();

                if (page + 1 < pages) {
                    gui.pose().pushPose();
                    gui.pose().translate(x - 64 + wPageRight + 16 + 148, y - h + 16 + 136, 0);
                    s = (float) (0.5f * (Math.sin(Math.toRadians((ClientTickHandler.ticksInGame * 10 + partialTicks + (i * 10) * 3) + (90 * 3)))));
                    RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                            .setColor(color)
                            .setAlpha(0.5f + s)
                            .setSecondAlpha(0.5f - s)
                            .renderRay(gui.pose(), 14f, 14f, 1f)
                            .endBatch();
                    gui.pose().popPose();
                }

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

            if (mouseX >= x - 144 + 24 - 16 && mouseY >= y - 16 && mouseX < x - 144 + 24 + 16 && mouseY < y + 16) {
                ItemStack wandCrystal = getWandCrystal();
                if (!wandCrystal.isEmpty()) {
                    gui.renderTooltip(Minecraft.getInstance().font, wandCrystal, mouseX, mouseY);
                }
                if (spellWand != null) {
                    if ((KnowledgeUtil.isSpell(Minecraft.getInstance().player, spellWand))) {
                        gui.blit(spellWand.getIcon(), mouseX + 9, mouseY - 68, 0, 0, 32, 32, 32, 32);
                        gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(spellWand.getTranslatedName()), mouseX, mouseY - 18);
                    } else {
                        gui.blit(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/unknown.png"), mouseX + 9, mouseY - 68, 0, 0, 32, 32, 32, 32);
                        gui.renderTooltip(Minecraft.getInstance().font, Component.translatable("wizards_reborn.arcanemicon.unknown"), mouseX, mouseY - 18);
                    }
                    if (!spellWand.canWandWithCrystal(getWand())) {
                        gui.blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/arcane_wand_frame.png"), mouseX + 7, mouseY - 70, 0, 36, 36, 36, 128, 128);
                    }
                }
            }
        }

        if (mode == Mode.SPELL_SETS) {
            int x = width / 2;
            int y = height / 2;

            int choosedRay = getSelectedSpell(mouseX, mouseY);
            int currentSpellSet = KnowledgeUtil.getCurrentSpellSet(minecraft.player);
            for (int i = 0; i < 10; i++) {
                double dst = Math.toRadians((i * 36) + 18 - 108);
                int X = (int) (Math.cos(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));
                int Y = (int) (Math.sin(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));

                Color color = Color.WHITE;
                boolean standard = currentSpellSet == i;

                ResourceLocation resource = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/research.png");
                Spell spell = null;
                ArrayList<Spell> set = KnowledgeUtil.getSpellSet(minecraft.player, i);
                for (int ii = 0; ii < 10; ii++) {
                    if (set.get(ii) != null) {
                        spell = set.get(ii);
                        break;
                    }
                }
                if (spell != null) {
                    if (!KnowledgeUtil.isSpell(minecraft.player, spell)) {
                        resource = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/unknown.png");
                    } else {
                        resource = spell.getIcon();
                        color = spell.getColor();
                    }
                }

                renderRays(color, gui, partialTicks, i, 36, -108, i == choosedRay, standard);

                if (i == choosedRay) {
                    gui.blit(resource, x + X - 24, y + Y - 24, 0, 0, 48, 48, 48, 48);
                } else {
                    gui.blit(resource, x + X - 16, y + Y - 16, 0, 0, 32, 32, 32, 32);
                }
            }

            boolean empty = true;
            ArrayList<Spell> set = KnowledgeUtil.getSpellSet(minecraft.player, choosedRay);
            for (int ii = 0; ii < 10; ii++) {
                if (set.get(ii) != null) {
                    empty = false;
                    break;
                }
            }
            if (empty) {
                gui.renderTooltip(Minecraft.getInstance().font, Component.translatable("gui.wizards_reborn.wand.add_spell_set"), mouseX, mouseY);
            } else {
                gui.renderTooltip(Minecraft.getInstance().font, Component.translatable("gui.wizards_reborn.wand.spell_set").append(" ").append(String.valueOf(choosedRay + 1)), mouseX, mouseY);
            }
        }

        if (mode == Mode.SPELL_SET) {
            int x = width / 2;
            int y = height / 2;

            int choosedRay = getSelectedSpell(mouseX, mouseY);
            int currentSpellSet = KnowledgeUtil.getCurrentSpellSet(minecraft.player);
            for (int i = 0; i < 10; i++) {
                double dst = Math.toRadians((i * 36) + 18 - 108);
                int X = (int) (Math.cos(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));
                int Y = (int) (Math.sin(dst) * (100 * Math.sin(Math.toRadians(90 * hoveramount))));

                Color color = Color.WHITE;
                boolean standard = false;

                Spell spellWand = null;
                if (getWand().getItem() instanceof ArcaneWandItem wand) {
                    CompoundTag nbt = getWand().getTag();
                    if (nbt.contains("spell")) {
                        if (nbt.getString("spell") != "") {
                            spellWand = SpellHandler.getSpell(nbt.getString("spell"));
                        }
                    }
                }

                ResourceLocation resource = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/research.png");
                Spell spell = KnowledgeUtil.getSpellFromSet(minecraft.player, currentSpellSet, i);
                if (spell != null) {
                    if (!KnowledgeUtil.isSpell(minecraft.player, spell)) {
                        resource = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/unknown.png");
                    } else {
                        resource = spell.getIcon();
                        color = spell.getColor();
                    }

                    if (spellWand != null && spell == spellWand) standard = true;
                }

                renderRays(color, gui, partialTicks, i, 36, -108, i == choosedRay, standard);
                if (!(KnowledgeUtil.isSpell(Minecraft.getInstance().player, spell)) && spell != null) {
                    resource = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/unknown.png");
                }

                if (i == choosedRay) {
                    gui.blit(resource, x + X - 24, y + Y - 24, 0, 0, 48, 48, 48, 48);
                } else {
                    gui.blit(resource, x + X - 16, y + Y - 16, 0, 0, 32, 32, 32, 32);
                }
            }

            for (int i = 0; i < 10; i++) {
                Spell spell = KnowledgeUtil.getSpellFromSet(minecraft.player, currentSpellSet, i);

                if (spell != null && i == choosedRay) {
                    if ((KnowledgeUtil.isSpell(Minecraft.getInstance().player, spell))) {
                        gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(spell.getTranslatedName()), mouseX, mouseY);
                    } else {
                        gui.renderTooltip(Minecraft.getInstance().font, Component.translatable("wizards_reborn.arcanemicon.unknown"), mouseX, mouseY);
                    }
                } else if (i == choosedRay) {
                    gui.renderTooltip(Minecraft.getInstance().font, Component.translatable("gui.wizards_reborn.wand.add_spell"), mouseX, mouseY);
                }
            }
        }
    }

    public List<ItemStack> getPlayerCrystals() {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        List<ItemStack> items = player.inventoryMenu.getItems();
        LazyOptional<ICuriosItemHandler> curiosItemHandler = CuriosApi.getCuriosInventory(player);
        if (curiosItemHandler.isPresent() && curiosItemHandler.resolve().isPresent()) {
            List<SlotResult> curioSlots = curiosItemHandler.resolve().get().findCurios((i) -> true);
            for (SlotResult slot : curioSlots) {
                if (slot.stack() != null) {
                    items.add(slot.stack());
                }
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
        double x = width / 2f;
        double y = height / 2f;

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

    public int getSelectedSpell(double X, double Y) {
        double step = (float) 36;
        double x = width / 2f;
        double y = height / 2f;

        double angle =  Math.toDegrees(Math.atan2(Y-y,X-x)) + 108;
        if (angle < 0D) {
            angle += 360D;
        }

        for (int i = 1; i <= 10; i += 1) {
            if ((((i-1) * step) <= angle) && (((i * step)) > angle)) {
                return i - 1;
            }
        }
        return 0;
    }

    public int getSelectedMode(double X, double Y, float offset) {
        double step = (float) 360 / 4;
        double x = width / 2f;
        double y = height / 2f;

        double angle =  Math.toDegrees(Math.atan2(Y-y,X-x)) - offset;
        if (angle < 0D) {
            angle += 360D;
        }

        for (int i = 1; i <= 4; i += 1) {
            if ((((i-1) * step) <= angle) && (((i * step)) > angle)) {
                return i - 1;
            }
        }
        return 0;
    }

    public float getMouseAngle(double X, double Y) {
        double x = width / 2f;
        double y = height / 2f;

        double angle =  Math.toDegrees(Math.atan2(Y-y,X-x));
        if (angle < 0D) {
            angle += 360D;
        }

        return (float) angle;
    }

    public float getMouseDistance(double X, double Y) {
        double x = width / 2f;
        double y = height / 2f;

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
        Color color1 = Color.WHITE;
        Color color2 = Color.WHITE;
        boolean renderPolishing = false;
        float mouseDistance = getMouseDistance(mouseX, mouseY);
        float chooseRay = (stack == selectedItem && mouseDistance > getWandItemDistance()) ? 1.2f : 0.8f;


        if (stack.getItem() instanceof CrystalItem crystalItem) {
            color1 = crystalItem.getType().getColor();;

            if (crystalItem.getPolishing().hasParticle()) {
                renderPolishing = true;
                color2 = crystalItem.getPolishing().getColor();
            }
        }

        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                .setColor(color1).setAlpha(1f)
                .renderDragon(gui.pose(), x, y, 0, 30 * scale, ClientTickHandler.partialTicks, i)
                .endBatch();
        if (renderPolishing) {
            RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                    .setColor(color2).setAlpha(0.5f)
                    .renderDragon(gui.pose(), x, y, 0, 20 * scale, ClientTickHandler.partialTicks, i * 5)
                    .endBatch();
        }

        if (renderRay) {
            gui.pose().pushPose();
            gui.pose().translate(width / 2f,  height / 2f, 0);
            gui.pose().mulPose(Axis.ZP.rotationDegrees(i * step + (step / 2)));
            gui.pose().mulPose(Axis.XP.rotationDegrees((ClientTickHandler.ticksInGame + partialTicks + (i * 10) * 5)));
            gui.pose().mulPose(Axis.ZP.rotationDegrees(-90f));
            RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                    .setColor(color1)
                    .setSecondAlpha(0)
                    .renderRay(gui.pose(), 1f, (100 * hoveramount) * chooseRay, 10f)
                    .endBatch();
            gui.pose().popPose();
        }
    }

    public void renderRays(Color color, GuiGraphics gui, float partialTicks, float i, float step, float offset, boolean choosed) {
        float chooseRay = (choosed) ? 1.2f : 0.8f;
        gui.pose().pushPose();
        gui.pose().translate(width / 2f,  height / 2f, 0);
        gui.pose().mulPose(Axis.ZP.rotationDegrees(i * step + (step / 2) + offset));
        gui.pose().mulPose(Axis.XP.rotationDegrees((ClientTickHandler.ticksInGame + partialTicks + (i * 10) * 5)));
        gui.pose().mulPose(Axis.ZP.rotationDegrees(-90f));
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                .setColor(color)
                .setSecondAlpha(0)
                .renderRay(gui.pose(), 1f, (100 * hoveramount) * chooseRay, 30f)
                .endBatch();
        gui.pose().popPose();
    }

    public void renderRays(Color color, GuiGraphics gui, float partialTicks, float i, float step, float offset, boolean choosed, boolean standard) {
        float chooseRay = (choosed) ? 1.2f : 0.8f;
        float alpha = (standard) ? 1f : 0.5f;

        gui.pose().pushPose();
        gui.pose().translate(width / 2f,  height / 2f, 0);
        gui.pose().mulPose(Axis.ZP.rotationDegrees(i * step + (step / 2) + offset));
        gui.pose().mulPose(Axis.XP.rotationDegrees((ClientTickHandler.ticksInGame + partialTicks + (i * 10) * 5)));
        gui.pose().mulPose(Axis.ZP.rotationDegrees(-90f));
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                .setColor(color)
                .setAlpha(alpha)
                .setSecondAlpha(0)
                .renderRay(gui.pose(), 1f, (100 * hoveramount) * chooseRay, 10f)
                .endBatch();
        gui.pose().popPose();
    }

    public float getWandItemDistance() {
        return 20f;
    }

    public boolean isWandItem(ItemStack stack) {
        return stack.getItem() instanceof CrystalItem;
    }

    public static void initSpells() {
        for (CrystalType type : CrystalHandler.getTypes()) {
            spellsList.put(type, new ArrayList<Spell>());
        }

        for (Spell spell : SpellHandler.getSpells()) {
            for (CrystalType type : spell.getCrystalTypes()) {
                boolean add = true;
                if (spell.isSecret()) add = (KnowledgeUtil.isSpell(Minecraft.getInstance().player, spell));
                if (add) spellsList.get(type).add(spell);
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
