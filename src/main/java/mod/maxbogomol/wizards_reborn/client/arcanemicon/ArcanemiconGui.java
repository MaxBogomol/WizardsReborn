package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.ChapterHistoryEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ArcanemiconGui extends Screen {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon.png");
    public static int xSize = 312;
    public static int ySize = 180;

    public static Chapter currentChapter;
    public static int currentPage = 0;
    public ItemStack currentItem;

    public static List<ChapterHistoryEntry> historyEntries = new ArrayList<>();
    public static int currentHistory = 1;

    public ArcanemiconGui() {
        super(Component.translatable("gui.wizards_reborn.arcanemicon.title"));
        if (currentChapter == null) {
            currentChapter = ArcanemiconChapters.ARCANE_NATURE_INDEX;
            historyEntries.add(new ChapterHistoryEntry(currentChapter, 0));
        }
        for (Category category : ArcanemiconChapters.categories) {
            category.reset();
        }
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
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        renderBackground(gui);
        Minecraft mc = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, BACKGROUND);

        currentItem = ItemStack.EMPTY;

        this.width = mc.getWindow().getGuiScaledWidth();
        this.height = mc.getWindow().getGuiScaledHeight();
        int guiLeft = (width - xSize) / 2, guiTop = (height - ySize) / 2;
        gui.blit(BACKGROUND, guiLeft, guiTop, 0, 180, xSize, ySize, 512, 512);

        for (int i = 0; i < ArcanemiconChapters.categories.size(); i ++) {
            int y = guiTop + 12 + (i % 8) * 20;
            ArcanemiconChapters.categories.get(i).draw(this, gui, guiLeft + (i >= 8 ? 301 : 10), y, i >= 8, mouseX, mouseY);
        }

        gui.blit(BACKGROUND, guiLeft, guiTop, 0, 0, xSize, ySize, 512, 512);
        Page left = currentChapter.getPage(currentPage), right = currentChapter.getPage(currentPage + 1);
        if (left != null) left.fullRender(this, gui, guiLeft + 10, guiTop + 8, mouseX, mouseY);
        if (right != null) right.fullRender(this, gui, guiLeft + 174, guiTop + 8, mouseX, mouseY);

        if (currentPage > 0) {
            int x = 11, y = 151;
            int v = 0;
            if (mouseX >= guiLeft + x && mouseY >= guiTop + y && mouseX <= guiLeft + x + 32 && mouseY <= guiTop + y + 16) v += 16;
            gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 351, v, 32, 16, 512, 512);
        }
        if (currentPage + 2 < currentChapter.size()) {
            int x = 269, y = 151;
            int v = 0;
            if (mouseX >= guiLeft + x && mouseY >= guiTop + y && mouseX <= guiLeft + x + 32 && mouseY <= guiTop + y + 16) v += 16;
            gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 383, v, 32, 16, 512, 512);
        }

        if (currentHistory > 1) {
            int x = 110 + 7, y = 155;
            int v = 0;
            if (mouseX >= guiLeft + x && mouseY >= guiTop + y && mouseX <= guiLeft + x + 19 && mouseY <= guiTop + y + 12) v += 12;
            gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 415, v, 19, 12, 512, 512);
        }

        if (currentHistory < historyEntries.size()) {
            int x = 169 + 7, y = 155;
            int v = 0;
            if (mouseX >= guiLeft + x && mouseY >= guiTop + y && mouseX <= guiLeft + x + 19 && mouseY <= guiTop + y + 12) v += 12;
            gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 434, v, 19, 12, 512, 512);
        }

        for (int i = 0; i < ArcanemiconChapters.categories.size(); i ++) {
            int y = guiTop + 12 + (i % 8) * 20;
            ArcanemiconChapters.categories.get(i).drawTooltip(this, gui, guiLeft + (i >= 8 ? 301 : 10), y, i >= 8, mouseX, mouseY);
        }

        renderTooltip(gui, currentItem, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            Minecraft mc = Minecraft.getInstance();
            this.width = mc.getWindow().getGuiScaledWidth();
            this.height = mc.getWindow().getGuiScaledHeight();
            int guiLeft = (width - xSize) / 2, guiTop = (height - ySize) / 2;

            if (currentPage > 0) {
                int x = guiLeft + 11, y = guiTop + 151;
                if (mouseX >= x && mouseY >= y && mouseX <= x + 32 && mouseY <= y + 16) {
                    currentPage -= 2;
                    historyEntries.set(currentHistory - 1, new ChapterHistoryEntry(currentChapter, currentPage));
                    Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    return true;
                }
            }
            if (currentPage + 2 < currentChapter.size()) {
                int x = guiLeft + 269, y = guiTop + 151;
                if (mouseX >= x && mouseY >= y && mouseX <= x + 32 && mouseY <= y + 16) {
                    currentPage += 2;
                    historyEntries.set(currentHistory - 1, new ChapterHistoryEntry(currentChapter, currentPage));
                    Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    return true;
                }
            }

            if (currentHistory > 1) {
                int x = 110 + 7, y = 155;
                if (mouseX >= guiLeft + x && mouseY >= guiTop + y && mouseX <= guiLeft + x + 19 && mouseY <= guiTop + y + 12) {
                    currentHistory = currentHistory - 1;
                    currentChapter = historyEntries.get(currentHistory - 1).chapter;
                    currentPage = historyEntries.get(currentHistory - 1).page;

                    Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    return true;
                }
            }

            if (currentHistory < historyEntries.size()) {
                int x = 169 + 7, y = 155;
                if (mouseX >= guiLeft + x && mouseY >= guiTop + y && mouseX <= guiLeft + x + 19 && mouseY <= guiTop + y + 12) {
                    currentHistory = currentHistory + 1;
                    currentChapter = historyEntries.get(currentHistory - 1).chapter;
                    currentPage = historyEntries.get(currentHistory - 1).page;

                    Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    return true;
                }
            }

            for (int i = 0; i < ArcanemiconChapters.categories.size(); i ++) {
                int y = guiTop + 12 + (i % 8) * 20;
                if (ArcanemiconChapters.categories.get(i).click(this, guiLeft + (i >= 8 ? 301 : 10), y, i >= 8, (int)mouseX, (int)mouseY)) return true;
            }

            Page left = currentChapter.getPage(currentPage), right = currentChapter.getPage(currentPage + 1);
            if (left != null) if (left.click(this,guiLeft + 11, guiTop + 9, (int)mouseX, (int)mouseY)) return true;
            if (right != null) if (right.click(this,guiLeft + 175, guiTop + 9, (int)mouseX, (int)mouseY)) return true;
        }
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
}
