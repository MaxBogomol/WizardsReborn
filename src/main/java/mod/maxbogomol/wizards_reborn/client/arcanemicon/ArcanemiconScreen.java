package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.platform.InputConstants;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.ChapterHistoryEntry;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.item.ArcanemiconSoundPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ArcanemiconScreen extends Screen {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon.png");
    public static int xSize = 312;
    public static int ySize = 180;
    public static final Color TEXT_COLOR = new Color(56, 33, 39);
    public static final Color TEXT_SHADOW_COLOR = new Color(220, 199, 182);
    public static final int TEXT_COLOR_INT = ColorUtil.packColor(255, 56, 33, 39);
    public static final int TEXT_SHADOW_COLOR_INT = ColorUtil.packColor(255, 220, 199, 182);

    public static Chapter currentChapter;
    public static int currentPage = 0;
    public ItemStack currentItem;

    public static List<ChapterHistoryEntry> historyEntries = new ArrayList<>();
    public static int currentHistory = 1;

    public BlockPos blockPos = BlockPos.ZERO;
    public boolean isBLock = false;

    public ArcanemiconScreen() {
        super(Component.translatable("gui.wizards_reborn.arcanemicon.title"));
        if (currentChapter == null) {
            currentChapter = ArcanemiconChapters.ARCANE_NATURE_INDEX;
        }
        if (historyEntries.isEmpty()) {
            changeChapter(currentChapter);
            currentHistory = 1;
        }
        for (Category category : ArcanemiconChapters.categories) {
            category.reset();
        }
        for (Category category : ArcanemiconChapters.additionalCategories) {
            category.reset();
        }
    }

    public static void loggedReset() {
        currentChapter = ArcanemiconChapters.ARCANE_NATURE_INDEX;
        currentPage = 0;
        historyEntries.clear();
        currentHistory = 1;
    }

    public void changeChapter(Chapter next) {
        if (historyEntries.size() > 0) {
            int deleteSize = (historyEntries.size() - currentHistory);
            for (int i = 0; i < deleteSize; i++) {
                historyEntries.remove(historyEntries.size() - 1);
            }
            if (deleteSize > 0) {
                currentHistory = historyEntries.size();
            }
        }
        currentHistory = currentHistory + 1;
        historyEntries.add(new ChapterHistoryEntry(next, 0));
        currentChapter = next;
        currentPage = 0;
    }

    @Override
    public void tick() {
        Page left = currentChapter.getPage(currentPage), right = currentChapter.getPage(currentPage + 1);
        if (left != null) left.tick(this);
        if (right != null) right.tick(this);

        if (isBLock && minecraft != null) {
            Player player = minecraft.player;
            if (player != null) {
                float distance = (float) Math.sqrt(Math.pow(player.getX() - blockPos.getX(), 2) + Math.pow(player.getY() + player.getEyeHeight() - blockPos.getY(), 2) + Math.pow(player.getZ() - blockPos.getZ(), 2));
                if (distance - 1.5F > player.getAttributeValue(ForgeMod.BLOCK_REACH.get())) {
                    minecraft.player.closeContainer();
                }
                if (minecraft.level.getBlockState(blockPos).isAir()) {
                    minecraft.player.closeContainer();
                }
            }
        }
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        renderBackground(gui);
        Minecraft minecraft = Minecraft.getInstance();

        currentItem = ItemStack.EMPTY;

        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        int guiLeft = (width - xSize) / 2, guiTop = (height - ySize) / 2;
        gui.blit(BACKGROUND, guiLeft, guiTop, 0, 180, xSize, ySize, 512, 512);

        for (int i = 0; i < ArcanemiconChapters.categories.size(); i ++) {
            int y = guiTop + 12 + (i % 8) * 20;
            if (i >= 8) y = y + (ArcanemiconChapters.additionalCategories.size() * 20);
            ArcanemiconChapters.categories.get(i).draw(this, gui, guiLeft + (i >= 8 ? 301 : 10), y, i >= 8, mouseX, mouseY);
        }
        for (int i = 0; i < ArcanemiconChapters.additionalCategories.size(); i ++) {
            int y = guiTop + 12 + (i % 8) * 20;
            ArcanemiconChapters.additionalCategories.get(i).draw(this, gui, guiLeft + 301, y, true, mouseX, mouseY);
        }

        gui.blit(BACKGROUND, guiLeft, guiTop, 0, 0, xSize, ySize, 512, 512);
        Page left = currentChapter.getPage(currentPage), right = currentChapter.getPage(currentPage + 1);
        if (left != null) left.fullRender(this, gui, guiLeft + 10, guiTop + 8, mouseX, mouseY);
        if (right != null) right.fullRender(this, gui, guiLeft + 174, guiTop + 8, mouseX, mouseY);

        if (currentPage > 0) {
            int x = 11, y = 155;
            int v = 0;
            if (mouseX >= guiLeft + x && mouseY >= guiTop + y && mouseX < guiLeft + x + 30 && mouseY < guiTop + y + 12) v += 12;
            gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 351, v, 30, 12, 512, 512);
        }
        if (currentPage + 2 < currentChapter.size()) {
            int x = 271, y = 155;
            int v = 0;
            if (mouseX >= guiLeft + x && mouseY >= guiTop + y && mouseX < guiLeft + x + 30 && mouseY < guiTop + y + 12) v += 12;
            gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 381, v, 30, 12, 512, 512);
        }

        if (currentChapter != ArcanemiconChapters.RESEARCH) {
            if (currentHistory > 1) {
                int x = 110 + 7, y = 155;
                int v = 0;
                if (mouseX >= guiLeft + x && mouseY >= guiTop + y && mouseX < guiLeft + x + 19 && mouseY < guiTop + y + 12)
                    v += 12;
                gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 411, v, 19, 12, 512, 512);
            }

            if (currentHistory < historyEntries.size()) {
                int x = 169 + 7, y = 155;
                int v = 0;
                if (mouseX >= guiLeft + x && mouseY >= guiTop + y && mouseX < guiLeft + x + 19 && mouseY < guiTop + y + 12)
                    v += 12;
                gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 430, v, 19, 12, 512, 512);
            }
        }

        for (int i = 0; i < ArcanemiconChapters.categories.size(); i ++) {
            int y = guiTop + 12 + (i % 8) * 20;
            if (i >= 8) y = y + (ArcanemiconChapters.additionalCategories.size() * 20);
            ArcanemiconChapters.categories.get(i).drawTooltip(this, gui, guiLeft + (i >= 8 ? 301 : 10), y, i >= 8, mouseX, mouseY);
        }
        for (int i = 0; i < ArcanemiconChapters.additionalCategories.size(); i ++) {
            int y = guiTop + 12 + (i % 8) * 20;
            ArcanemiconChapters.additionalCategories.get(i).drawTooltip(this, gui, guiLeft + 301, y, true, mouseX, mouseY);
        }

        renderTooltip(gui, currentItem, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            Minecraft minecraft = Minecraft.getInstance();
            this.width = minecraft.getWindow().getGuiScaledWidth();
            this.height = minecraft.getWindow().getGuiScaledHeight();
            int guiLeft = (width - xSize) / 2, guiTop = (height - ySize) / 2;

            if (currentPage > 0) {
                int x = guiLeft + 11, y = guiTop + 155;
                if (mouseX >= x && mouseY >= y && mouseX < x + 30 && mouseY < y + 12) {
                    currentPage -= 2;
                    historyEntries.set(currentHistory - 1, new ChapterHistoryEntry(currentChapter, currentPage));
                    Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    return true;
                }
            }
            if (currentPage + 2 < currentChapter.size()) {
                int x = guiLeft + 271, y = guiTop + 155;
                if (mouseX >= x && mouseY >= y && mouseX < x + 30 && mouseY < y + 12) {
                    currentPage += 2;
                    historyEntries.set(currentHistory - 1, new ChapterHistoryEntry(currentChapter, currentPage));
                    Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    return true;
                }
            }

            if (currentChapter != ArcanemiconChapters.RESEARCH) {
                if (currentHistory > 1) {
                    int x = 110 + 7, y = 155;
                    if (mouseX >= guiLeft + x && mouseY >= guiTop + y && mouseX < guiLeft + x + 19 && mouseY < guiTop + y + 12) {
                        currentHistory = currentHistory - 1;
                        currentChapter = historyEntries.get(currentHistory - 1).chapter;
                        currentPage = historyEntries.get(currentHistory - 1).page;

                        Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                        return true;
                    }
                }

                if (currentHistory < historyEntries.size()) {
                    int x = 169 + 7, y = 155;
                    if (mouseX >= guiLeft + x && mouseY >= guiTop + y && mouseX < guiLeft + x + 19 && mouseY < guiTop + y + 12) {
                        currentHistory = currentHistory + 1;
                        currentChapter = historyEntries.get(currentHistory - 1).chapter;
                        currentPage = historyEntries.get(currentHistory - 1).page;

                        Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                        return true;
                    }
                }
            }

            for (int i = 0; i < ArcanemiconChapters.categories.size(); i ++) {
                int y = guiTop + 12 + (i % 8) * 20;
                if (i >= 8) y = y + ArcanemiconChapters.additionalCategories.size() * 20;
                if (ArcanemiconChapters.categories.get(i).click(this, guiLeft + (i >= 8 ? 301 : 10), y, i >= 8, (int)mouseX, (int)mouseY)) return true;
            }
            for (int i = 0; i < ArcanemiconChapters.additionalCategories.size(); i ++) {
                int y = guiTop + 12 + (i % 8) * 20;
                if (ArcanemiconChapters.additionalCategories.get(i).click(this, guiLeft + 301, y, true, (int)mouseX, (int)mouseY)) return true;
            }

            Page left = currentChapter.getPage(currentPage), right = currentChapter.getPage(currentPage + 1);
            if (left != null) if (left.click(this,guiLeft + 11, guiTop + 9, (int)mouseX, (int)mouseY)) return true;
            if (right != null) if (right.click(this,guiLeft + 175, guiTop + 9, (int)mouseX, (int)mouseY)) return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        Minecraft minecraft = Minecraft.getInstance();
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        int guiLeft = (width - xSize) / 2, guiTop = (height - ySize) / 2;

        Page left = currentChapter.getPage(currentPage), right = currentChapter.getPage(currentPage + 1);
        if (left != null) if (left.mouseScrolled(this,guiLeft + 11, guiTop + 9, (int)mouseX, (int)mouseY, (int)delta)) return true;
        if (right != null) if (right.mouseScrolled(this,guiLeft + 175, guiTop + 9, (int)mouseX, (int)mouseY, (int)delta)) return true;

        return false;
    }

    public void renderTooltip(GuiGraphics gui, ItemStack stack, int x, int y) {
        if (!stack.isEmpty()) gui.renderTooltip(Minecraft.getInstance().font, stack, x, y);
    }

    public void renderTooltip(GuiGraphics gui, MutableComponent component, int x, int y) {
        gui.renderTooltip(Minecraft.getInstance().font, component, x, y);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);

        if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
            this.onClose();
            return true;
        }
        if (this.minecraft.options.keyLeft.matches(keyCode, scanCode)) {
            if (currentPage > 0) {
                currentPage -= 2;
                historyEntries.set(currentHistory - 1, new ChapterHistoryEntry(currentChapter, currentPage));
                Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                return true;
            }
        }
        if (this.minecraft.options.keyRight.matches(keyCode, scanCode)) {
            if (currentPage + 2 < currentChapter.size()) {
                currentPage += 2;
                historyEntries.set(currentHistory - 1, new ChapterHistoryEntry(currentChapter, currentPage));
                Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                return true;
            }
        }
        if (currentChapter != ArcanemiconChapters.RESEARCH) {
            if (this.minecraft.options.keyDown.matches(keyCode, scanCode)) {
                if (currentHistory > 1) {
                    currentHistory = currentHistory - 1;
                    currentChapter = historyEntries.get(currentHistory - 1).chapter;
                    currentPage = historyEntries.get(currentHistory - 1).page;

                    Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    return true;
                }
            }
            if (this.minecraft.options.keyUp.matches(keyCode, scanCode)) {
                if (currentHistory < historyEntries.size()) {
                    currentHistory = currentHistory + 1;
                    currentChapter = historyEntries.get(currentHistory - 1).chapter;
                    currentPage = historyEntries.get(currentHistory - 1).page;

                    Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    return true;
                }
            }
        }

        return (super.keyPressed(keyCode, scanCode, modifiers));
    }

    @Override
    public void onClose() {
        super.onClose();
        WizardsRebornPacketHandler.sendToServer(new ArcanemiconSoundPacket(isBLock ? blockPos.getCenter() : WizardsReborn.proxy.getPlayer().position()));
    }
}
